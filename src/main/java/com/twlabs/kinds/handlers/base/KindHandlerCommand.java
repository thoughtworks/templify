package com.twlabs.kinds.handlers.base;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.logger.RunnerLogger;

/**
 * KindHandlerCommand
 */
public class KindHandlerCommand<S> {

    public KindHandlerCommand(String name, Map<String, String> metadata, List<S> specs,
            CreateTemplateCommand request) {
        this.name = name;
        this.metadata = metadata;
        this.specs = specs;
        this.request = request;
        this.logger = request.getLogger();
    }

    private RunnerLogger logger;

    private CreateTemplateCommand request;

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
