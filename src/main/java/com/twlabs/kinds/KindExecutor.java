package com.twlabs.kinds;

import com.twlabs.model.settings.StepsKindTemplate;
import com.twlabs.services.CreateTemplateRequest;

/**
 * Kind
 */
public interface KindExecutor {

    public static class Names {
        public static final String FILE_HANDLER_KIND = "FileHandler";
    }

    public void execute(StepsKindTemplate step, CreateTemplateRequest settings);

}
