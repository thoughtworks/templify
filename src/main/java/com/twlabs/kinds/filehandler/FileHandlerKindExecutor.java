package com.twlabs.kinds.filehandler;

import static com.twlabs.interfaces.FileHandler.Names.JAVA;
import static com.twlabs.interfaces.FileHandler.Names.JSON;
import static com.twlabs.interfaces.FileHandler.Names.XML;
import static com.twlabs.interfaces.FileHandler.Names.YAML;
import static com.twlabs.interfaces.FileHandler.Names.YML;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.kinds.KindExecutor;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.model.settings.StepsKindTemplate;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.logger.RunnerLogger;

/**
 * Processes a step of kind FileHandler
 *
 * This method takes a StepsKindTemplate object as a parameter and processes the file handler
 * associated with the given step. The file handler is responsible for performing specific actions
 * related to the step, such as reading or writing data to a file.
 */
public class FileHandlerKindExecutor implements KindExecutor {

    @Inject
    @Named(YAML)
    private FileHandler yamlHandler;

    @Inject
    @Named(XML)
    private FileHandler xmlHandler;

    @Inject
    @Named(JSON)
    private FileHandler jsonHandler;

    @Inject
    @Named(JAVA)
    private FileHandler javaHandler;

    @Override
    public void execute(StepsKindTemplate fileHandlerKindStep, CreateTemplateRequest req) {

        RunnerLogger logger = req.getLogger();

        // nothing change from v1
        var fileHandlersRegistry = getFileHandlerRegistry();

        String templateDirectory = req.getTemplateDir();
        PlaceholderSettings placeholder = req.getPlaceholder();

        var typeVar = fileHandlerKindStep.getMetadata().getOrDefault("type", "unknown");

        // in v1 was extension, now it's type, ex: xml, yaml
        String type = typeVar.toString();

        // exists handler for this type?
        if (!fileHandlersRegistry.containsKey(type)) {
            String msg = String.format("Unsupported Kind: FileHandler type: %s", type);
            logger.warn(msg);
            throw new IllegalArgumentException(String.format(msg));
        }


        List<Map<String, Object>> specs = fileHandlerKindStep.getSpec();

        for (Map<String, Object> spec : specs) {

            // BUG - JavaHandler does not have files
            List<String> files = (List<String>) spec.getOrDefault("files", new ArrayList<>());

            // BUG - When it's not java, it's empty
            String baseDir = String.valueOf(spec.getOrDefault("base_dir", ""));

            List<Map<String, String>> placeholders = (List<Map<String, String>>) spec
                    .getOrDefault("placeholders", new ArrayList<>());

            if ("java".equals(type)) {
                for (Map<String, String> placeholderIt : placeholders) {
                    String match = placeholderIt.get("match");
                    String replace = placeholderIt.get("replace");
                    try {
                        logger.warn("Replace: " + match + " with: " + placeholder.getPrefix()
                                + replace + placeholder.getSuffix());

                        fileHandlersRegistry.get(type).replace(templateDirectory + "/" + baseDir,
                                match, placeholder.getPrefix() + replace + placeholder.getSuffix());
                    } catch (FileHandlerException e) {
                        logger.error("Error to replace placeholders", e);
                        throw new RuntimeException(e);
                    }
                }
            } else {
                for (String file : files) {
                    String filePath = templateDirectory + "/" + file;
                    logger.warn("Start placeholder for: " + filePath);

                    for (Map<String, String> placeholderIt : placeholders) {
                        String match = placeholderIt.get("match");
                        String replace = placeholderIt.get("replace");
                        try {
                            logger.warn("Replace: " + match + " with: " + placeholder.getPrefix()
                                    + replace + placeholder.getSuffix());

                            fileHandlersRegistry.get(type).replace(filePath, match,
                                    placeholder.getPrefix() + replace + placeholder.getSuffix());

                        } catch (FileHandlerException e) {
                            logger.error("Error to replace placeholders", e);
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * Registry pattern for open-closed to set, add, remove, expand the handlers outside of mojo
     * logic withouth changing setPlaceHolders code block.
     */
    private Map<String, FileHandler> getFileHandlerRegistry() {
        return Map.of(XML, this.xmlHandler, YAML, this.yamlHandler, YML, this.yamlHandler, JSON,
                this.jsonHandler, JAVA, this.javaHandler);
    }

}
