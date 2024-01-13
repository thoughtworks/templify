package com.twlabs.services.tasks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.twlabs.config.ConfigReader;
import com.twlabs.config.PlaceholderSettings;
import com.twlabs.config.PluginConfig;
import com.twlabs.services.CreateTemplateCommand;
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
    public CreateTemplateCommand execute(CreateTemplateCommand command) {

        PluginConfig config = null;
        logger = command.getLogger();

        try {
            logger.warn("Template file: " + command.getConfigFilePath());

            // BUG settings are read from config file in template folder
            config = validateConfigSettings(reader.read(command.getConfigFilePath()));

            command.setPlaceholder(getConfigPlaceHolders(config));
            command.setConfiguration(config);
            return command;

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



    private PluginConfig validateConfigSettings(PluginConfig config) {

        if (!(config.getSettings().isEmpty())) {
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
