package com.twlabs.kinds.handlers.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import com.twlabs.kinds.api.DefaultSpecification;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.FileHandlerException;
import com.twlabs.kinds.api.Kind;
import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.api.KindHandlerEvent;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.logger.RunnerLogger;

/**
 * KindTemplate is an abstract class that implements the Kind interface. It provides a basic
 * implementation for the Kind interface, including getters for the kind, specs, and metadata.
 * 
 * @param <S> the type of the specs
 * @param <M> the type of the metadata
 */
public abstract class KindTemplate<S extends Serializable>
        implements Kind<S> {

    private RunnerLogger logger;

    private CreateTemplateCommand request;

    public KindTemplate() {}

    private boolean shouldProcessEvent(KindHandlerEvent event) {
        boolean isKindHandler = this.getClass().isAnnotationPresent(KindHandler.class);
        if (isKindHandler) {
            String checkKindAndVersion = this.getClass().getAnnotation(KindHandler.class).name()
                    + this.getClass().getAnnotation(KindHandler.class).apiVersion();

            return checkKindAndVersion.equals(event.getKindName() + event.getApiVersion());
        }

        return isKindHandler;
    }

    private void map(KindHandlerEvent event) {

        ObjectMapper objectMapper = new ObjectMapper();
        KindMappingTemplate mappingTemplate = event.getMappingTemplate();

        this.name = mappingTemplate.getKind();
        this.specs = new ArrayList<>();
        this.metadata = new HashMap<>();
        this.request = event.getRequest();
        Class<?> specClass = this.getClass().getAnnotation(KindHandler.class).specClass();

        for (Map<String, Object> spec : mappingTemplate.getSpec()) {
            specs.add(getSpecification(objectMapper, specClass, spec));
        }

        if (mappingTemplate.getMetadata() != null) {
            for (Map.Entry<String, Object> entry : mappingTemplate.getMetadata().entrySet()) {
                this.metadata.put(entry.getKey(), (String) entry.getValue());
            }
        }
    }

    private S getSpecification(ObjectMapper objectMapper, Class<?> specClass,
            Map<String, Object> spec) {
        return (S) objectMapper.convertValue(spec, specClass);
    }

    private void clean() {
        this.name = null;
        this.logger = null;
        this.specs = new ArrayList<>();
        this.metadata = new HashMap<>();
    }

    @Subscribe
    synchronized public void subscribeKindHandlerEvent(final KindHandlerEvent event) {
        this.logger = event.getRequest().getLogger();

        if (this.shouldProcessEvent(event)) {
            this.logger.info("Event accepted.");
            this.map(event);
            this.execute();
            this.clean();
        } else {
            this.logger.warn("Event ignored.");
        }
    }

    protected void executeDefaultFileHandlers(FileHandler fileHandler) {

        this.getLogger().info("Executing:" + fileHandler.getClass().getName());
        String templateDirectory = this.getRequest().getTemplateDir();
        PlaceholderSettings placeholder = this.getRequest().getPlaceholder();

        for (final S spec : this.getSpecs()) {
            if (spec instanceof DefaultSpecification) {
                DefaultSpecification defaultSpec = (DefaultSpecification) spec;
                this.handleFiles(this.getLogger(), templateDirectory, placeholder,
                        defaultSpec.getFiles(),
                        defaultSpec.getPlaceholders(), fileHandler);
            } else {
                this.getLogger().error(
                        "In order to use the utils executeDefaultFileHandler your KindHandler must: \n"
                                +
                                "1. It's specification object must implement the DefaultSpecification interface. \n"
                                +
                                "2.  Or It's have to use the DefaultSpecification class.");

            }
        }
    }

    protected void handleFiles(RunnerLogger logger, String templateDirectory,
            PlaceholderSettings placeholder,
            List<String> files, List<KindPlaceholder> placeholders, FileHandler handler) {

        for (String file : files) {
            String filePath = templateDirectory + "/" + file;
            logger.warn("Start placeholder for: " + filePath);

            for (KindPlaceholder placeholderIt : placeholders) {
                String match = placeholderIt.getMatch();
                String replace = placeholderIt.getReplace();
                try {
                    logger.warn("Replace: " + match + " with: " + placeholder.getPrefix()
                            + replace + placeholder.getSuffix());

                    handler.replace(filePath, match,
                            placeholder.getPrefix() + replace + placeholder.getSuffix());

                } catch (FileHandlerException e) {
                    logger.error("Error to replace placeholders", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String name;

    private List<S> specs;

    private Map<String, String> metadata;

    /**
     * Returns the metadata associated with this KindTemplate.
     * 
     * @return the metadata
     */
    public Optional<Map<String, String>> getMetadata() {
        return Optional.ofNullable(this.metadata);
    }

    /**
     * Returns the list of specs associated with this KindTemplate.
     * 
     * @return the list of specs
     */
    public List<S> getSpecs() {
        return this.specs;
    }

    /**
     * Returns the kind of this KindTemplate.
     * 
     * @return the kind
     */
    public String getName() {
        return this.name;
    }

    public RunnerLogger getLogger() {
        return logger;
    }

    public CreateTemplateCommand getRequest() {
        return request;
    }


}
