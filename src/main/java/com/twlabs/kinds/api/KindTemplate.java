package com.twlabs.kinds.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * KindTemplate is an abstract class that implements the Kind interface. It provides a basic
 * implementation for the Kind interface, including getters for the kind, specs, and metadata.
 * 
 * @param <S> the type of the specs
 * @param <M> the type of the metadata
 */
public abstract class KindTemplate<S extends Serializable>
        implements Kind<S> {

    /**
     * Creates a new KindTemplate object.
     *
     * @param map the KindMappingTemplate object used for mapping the kind
     * @param kindName the name of the kind
     * @param clazz the Class object representing the type of the kind
     * @throws NullPointerException if map or kindName is null
     */
    public KindTemplate(KindMappingTemplate map, Class<S> clazz) {

        if (!clazz.isAnnotationPresent(KindMetadata.class)) {
            throw new IllegalArgumentException("The class "
                    + clazz.getSimpleName()
                    + " is not annotated with KindMetadata.");
        }

        String kindName = clazz.getAnnotation(KindMetadata.class).name();

        if (map.getKind() == null || !kindName.equalsIgnoreCase(map.getKind())) {
            throw new IllegalArgumentException(
                    String.format("Invalid Kind for %s: %s", clazz.getName(), map.getKind()));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        this.name = map.getKind();

        this.specs = new ArrayList<>();

        for (Map<String, Object> spec : map.getSpec()) {
            specs.add(objectMapper.convertValue(spec, clazz));
        }

        this.metadata = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.getMetadata().entrySet()) {
            this.metadata.put(entry.getKey(), (String) entry.getValue());
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

    protected KindTemplate() {}

}
