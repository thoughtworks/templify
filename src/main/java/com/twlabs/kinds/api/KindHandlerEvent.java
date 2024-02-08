package com.twlabs.kinds.api;

import com.twlabs.services.CreateTemplateCommand;

public class KindHandlerEvent {

    private String kindName;
    private KindMappingTemplate mappingTemplate;
    private CreateTemplateCommand command;
    private String apiVersion;


    public KindHandlerEvent(KindMappingTemplate mappingTemplate,
            CreateTemplateCommand command) {
        this.kindName = mappingTemplate.getKind();
        this.apiVersion = mappingTemplate.getApiVersion();
        this.mappingTemplate = mappingTemplate;
        this.command = command;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getKindName() {
        return kindName;
    }

    public KindMappingTemplate getMappingTemplate() {
        return mappingTemplate;
    }

    public CreateTemplateCommand getCommand() {
        return command;
    }
}
