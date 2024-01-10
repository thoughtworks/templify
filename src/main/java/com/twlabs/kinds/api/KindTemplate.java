package com.twlabs.kinds.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
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

    private Class<S> clazz;

    private RunnerLogger logger;

    /**
     * Creates a new KindTemplate object with the specified class. Due to a limitation in Java
     * generics, the class must be specified as a parameter. It is not possible to retrieve S.class.
     *
     * @param clazz the class to be used for the KindTemplate object
     * @throws IllegalArgumentException if the clazz parameter is null
     */
    public KindTemplate(Class<S> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("class parameter cannot be null");
        }
        this.clazz = clazz;
    }

    public KindTemplate() { }

    private boolean shouldProcessEvent(KindHandlerEvent event) {
        if (this.clazz == null)
            this.clazz = (Class<S>) this.getClass().getAnnotation(KindHandler.class).spec();

        String annotationKindName = this.getClass().getAnnotation(KindHandler.class).name();

        return this.clazz.isAnnotationPresent(KindHandler.class) &&
                annotationKindName.equalsIgnoreCase(event.getKindName());
    }

    private void map(KindHandlerEvent event) {

        ObjectMapper objectMapper = new ObjectMapper();
        KindMappingTemplate mappingTemplate = event.getMappingTemplate();

        this.name = mappingTemplate.getKind();
        this.specs = new ArrayList<>();
        this.metadata = new HashMap<>();

        for (Map<String, Object> spec : mappingTemplate.getSpec()) {
            specs.add(objectMapper.convertValue(spec, clazz));
        }

        for (Map.Entry<String, Object> entry : mappingTemplate.getMetadata().entrySet()) {
            this.metadata.put(entry.getKey(), (String) entry.getValue());
        }
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
            this.logger.info("Event ignored.");
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
}
