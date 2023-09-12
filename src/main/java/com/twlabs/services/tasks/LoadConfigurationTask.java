package com.twlabs.services.tasks;

import java.io.IOException;
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

    private RunnerLogger logger;

    @Override
    public CreateTemplateRequest execute(CreateTemplateRequest req) {

        PluginConfig config = null;
        logger = req.getLogger();

        try {
            logger.warn("Template file: " + req.getConfigFilePath());

            // BUG settings are read from config file in template folder
            config = reader.read(req.getConfigFilePath());
            config = setConfigSettings(config);

            req.setPlaceholder(getConfigPlaceHolders(config));
            req.setConfiguration(config);
            return req;

        } catch (IOException | RuntimeException e) {
            logger.error("Error to read the settings from the config file");
            throw new RuntimeException(e);
        }
    }

    private PlaceholderSettings getConfigPlaceHolders(PluginConfig config) {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(config.getSettings().get("placeholder"),
                PlaceholderSettings.class);
    }



    private PluginConfig setConfigSettings(PluginConfig config) {

        if (!(config.getSettings() == null || config.getSettings().isEmpty())) {
            logger.warn("Using custom placeholder settings!! -> Prefix:"
                    + getConfigPlaceHolders(config).getPrefix() + " and Suffix: "
                    + getConfigPlaceHolders(config).getSuffix());
            return config;
        }

        Map<String, String> defaultPlaceholderSettings = new HashMap<>();
        defaultPlaceholderSettings.put("prefix", "{{");
        defaultPlaceholderSettings.put("suffix", "}}");

        Map<String, Object> defaultSettings = new HashMap<>();
        defaultSettings.put("placeholder", defaultPlaceholderSettings);

        config.setSettings(defaultSettings);

        logger.warn("Using default placeholder settings!! -> Prefix:" + "{{"
                + " and Suffix: " + "}}");



        return config;


    }
}
