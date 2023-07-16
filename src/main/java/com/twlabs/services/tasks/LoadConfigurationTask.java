package com.twlabs.services.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.model.settings.PluginConfig;
import com.twlabs.services.RunnerRequest;
import com.twlabs.services.RunnerTask;

/**
 * LoadConfigurationTask
 */
public class LoadConfigurationTask implements RunnerTask {

    @Inject
    private ConfigReader reader;

    private static Logger logger = Logger.getLogger(LoadConfigurationTask.class.getName());

    @Override
    public RunnerRequest execute(RunnerRequest req) {

        final ObjectMapper mapper = new ObjectMapper();

        PluginConfig config = null;

        try {

            logger.warning("Template file: " + req.getConfigFilePath());

            // BUG settings are read from config file in template folder
            config = reader.read(req.getConfigFilePath());

            Map<String, String> defaultPlaceholderSettings = new HashMap<>();
            defaultPlaceholderSettings.put("prefix", "{{");
            defaultPlaceholderSettings.put("suffix", "}}");


            if (config.getSettings() == null || config.getSettings().isEmpty()) {
                Map<String, Object> defaultSettings = new HashMap<>();
                defaultSettings.put("placeholder", defaultPlaceholderSettings);
                config.setSettings(defaultSettings);

                logger.warning("Using default placeholder settings!! -> Prefix:" + "{{"
                        + " and Suffix: " + "}}");

                // TODO strange logic, needs refactory
                req.setPlaceholder(mapper.convertValue(config.getSettings().get("placeholder"),
                        PlaceholderSettings.class));

            } else {
                req.setPlaceholder(mapper.convertValue(config.getSettings().get("placeholder"),
                        PlaceholderSettings.class));

                logger.warning("Using custom placeholder settings!! -> Prefix:"
                        + req.getPlaceholder().getPrefix() + " and Suffix: "
                        + req.getPlaceholder().getSuffix());
            }

            req.setConfiguration(config);
            return req;

        } catch (

        Exception e) {
            logger.log(Level.SEVERE, "Error to read the settings from the config file", e);
            throw new RuntimeException("Error to read the settings from the config file");
        }
    }

}
