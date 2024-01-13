package com.twlabs.kinds.handlers.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import com.twlabs.kinds.api.DefaultSpecification;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.FileHandlerException;
import com.twlabs.kinds.api.Kind;
import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.api.KindHandlerEvent;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.config.PlaceholderSettings;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.logger.RunnerLogger;

/**
 * KindTemplate is an abstract class that implements the Kind interface. It provides a basic
 * implementation for the Kind interface, including getters for the kind, specs, and metadata.
 * 
 * @param <S> the type of the specs
 * @param <M> the type of the metadata
 */
public abstract class KindHandlerBase<S extends Serializable>
        implements Kind<S> {


    // TODO TypeReference may resolves the Jackson problem with generics
    // protected KindHandlerBase() {
    // Type genericSuperclass = this.getClass().getGenericSuperclass();
    // this._type = (Class<S>) ((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
    // System.out.println("type from supper" +_type);
    // }

    private boolean shouldProcessEvent(KindHandlerEvent event) {
        KindHandler annotation = this.getClass().getAnnotation(KindHandler.class);
        if (annotation != null) {
            String checkKindAndVersion = annotation.name() + annotation.apiVersion();
            return checkKindAndVersion.equals(event.getKindName() + event.getApiVersion());
        }
        return false;
    }

    private KindHandlerCommand<S> mapEventToCommand(KindHandlerEvent event) {

        KindMappingTemplate mappingTemplate = event.getMappingTemplate();
        String name = mappingTemplate.getKind();
        List<S> specs = new ArrayList<>();
        Map<String, String> metadata = new HashMap<>();
        CreateTemplateCommand request = event.getRequest();

        Class<?> specClass = this.getClass().getAnnotation(KindHandler.class).specClass();

        for (Map<String, Object> spec : mappingTemplate.getSpec()) {
            specs.add(getSpecification(specClass, spec));
        }

        if (mappingTemplate.getMetadata() != null) {
            for (Map.Entry<String, Object> entry : mappingTemplate.getMetadata().entrySet()) {
                metadata.put(entry.getKey(), (String) entry.getValue());
            }
        }

        return new KindHandlerCommand<S>(name, metadata, specs, request);
    }

    private S getSpecification(Class<?> specClass, Map<String, Object> spec) {
        return (S) new ObjectMapper().convertValue(spec, specClass);
    }

    @Subscribe
    synchronized public void subscribeToKindHandlerEvent(final KindHandlerEvent event) {
        if (this.shouldProcessEvent(event)) {
            event.getRequest().getLogger().info("Event accepted.");
            this.execute(this.mapEventToCommand(event));
        } else {
            event.getRequest().getLogger().warn("Event ignored.");
        }
    }

    protected void executeDefaultFileHandlers(FileHandler fileHandler,
            KindHandlerCommand<S> command) {

        command.getLogger().info("Executing:" + fileHandler.getClass().getName());
        String templateDirectory = command.getRequest().getTemplateDir();
        PlaceholderSettings placeholder = command.getRequest().getPlaceholder();

        for (final S spec : command.getSpecs()) {
            if (spec instanceof DefaultSpecification) {
                DefaultSpecification defaultSpec = (DefaultSpecification) spec;
                this.handleFiles(command.getLogger(), templateDirectory, placeholder,
                        defaultSpec.getFiles(),
                        defaultSpec.getPlaceholders(), fileHandler);
            } else {
                command.getLogger().error(
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
}
