package com.twlabs.kinds.handlers;

import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.services.CreateTemplateRequest;

/**
 * Kind
 */
public interface KindExecutor {

    public static class Names {
        public static final String FILE_HANDLER_KIND = "FileHandler";
    }

    public void execute(KindMappingTemplate step, CreateTemplateRequest settings);

}
