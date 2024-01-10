package com.twlabs.kinds.api;

import com.twlabs.services.CreateTemplateRequest;

public class KindHandlerEvent {

    private String kindName;
    private KindMappingTemplate mappingTemplate;
    private CreateTemplateRequest request;

    public KindHandlerEvent(KindMappingTemplate mappingTemplate,
            CreateTemplateRequest request) {
        this.kindName = mappingTemplate.getKind();
        this.mappingTemplate = mappingTemplate;
        this.request = request;
    }

    public String getKindName() {
        return kindName;
    }

    public KindMappingTemplate getMappingTemplate() {
        return mappingTemplate;
    }

    public CreateTemplateRequest getRequest() {
        return request;
    }
}
