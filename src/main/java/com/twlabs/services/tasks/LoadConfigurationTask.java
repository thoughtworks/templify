package com.twlabs.services.tasks;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.RunnerTask;
import com.twlabs.services.logger.RunnerLogger;

/**
 * LoadConfigurationTask
 */
public class LoadConfigurationTask implements RunnerTask {

    @Inject
    private ConfigReader reader;

    @Override
    public CreateTemplateRequest execute(CreateTemplateRequest req) {

        final ObjectMapper mapper = new ObjectMapper();

        PluginConfig config = null;

        RunnerLogger logger = req.getLogger();

        try {

            logger.warn("Template file: " + req.getConfigFilePath());

            // BUG settings are read from config file in template folder
            config = reader.read(req.getConfigFilePath());

            Map<String, String> defaultPlaceholderSettings = new HashMap<>();
            defaultPlaceholderSettings.put("prefix", "{{");
            defaultPlaceholderSettings.put("suffix", "}}");


            if (config.getSettings() == null || config.getSettings().isEmpty()) {
                Map<String, Object> defaultSettings = new HashMap<>();
                defaultSettings.put("placeholder", defaultPlaceholderSettings);
                config.setSettings(defaultSettings);

                logger.warn("Using default placeholder settings!! -> Prefix:" + "{{"
                        + " and Suffix: " + "}}");

                // TODO strange logic, needs refactory
                req.setPlaceholder(mapper.convertValue(config.getSettings().get("placeholder"),
                        PlaceholderSettings.class));

            } else {
                req.setPlaceholder(mapper.convertValue(config.getSettings().get("placeholder"),
                        PlaceholderSettings.class));

                logger.warn("Using custom placeholder settings!! -> Prefix:"
                        + req.getPlaceholder().getPrefix() + " and Suffix: "
                        + req.getPlaceholder().getSuffix());
            }

            req.setConfiguration(config);
            return req;

        } catch (

        Exception e) {
            logger.error("Error to read the settings from the config file", e);
            throw new RuntimeException("Error to read the settings from the config file");
        }
    }

}
