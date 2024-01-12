package com.twlabs.services;

/**
 * RunnerTask
 */
public interface RunnerTask {

    public static class Names {
        public static final String COPY_PROJECT_TASK = "copyProjectTask";
        public static final String DELETE_TEMPLATE_FOLDER_TASK = "deleteTemplateFolderIfExistsTask";
        public static final String LOAD_PLUGIN_CONFIGURATION_TASK = "loadPluginConfigurationTask";
        public static final String EXECUTE_STEPS_TASK = "executeStepsTask";
    }

    public CreateTemplateCommand execute(CreateTemplateCommand request);
}
