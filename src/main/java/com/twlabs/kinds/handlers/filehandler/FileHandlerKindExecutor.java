package com.twlabs.kinds.handlers.filehandler;

import static com.twlabs.interfaces.FileHandler.Names.JAVA;
import static com.twlabs.interfaces.FileHandler.Names.JSON;
import static com.twlabs.interfaces.FileHandler.Names.XML;
import static com.twlabs.interfaces.FileHandler.Names.YAML;
import static com.twlabs.interfaces.FileHandler.Names.YML;
import com.twlabs.kinds.handlers.filehandler.FileHandlerKindExecutor;
import java.util.List;
import java.util.Map;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.kinds.handlers.KindExecutor;
import com.twlabs.kinds.handlers.filehandler.FileHandlerKindModel.FileHandlerKindModelPlaceholder;
import com.twlabs.kinds.handlers.filehandler.FileHandlerKindModel.FileHandlerKindModelSpec;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.kinds.api.KindMappingTemplate;
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

    private FileHandlerKindModelFactory fileHandlerKindModelFactory =
            new FileHandlerKindModelFactory();

    @Override
    public void execute(KindMappingTemplate fileHandlerKindStep, CreateTemplateRequest req) {

        FileHandlerKindModel model = fileHandlerKindModelFactory.build(fileHandlerKindStep);
        RunnerLogger logger = req.getLogger();
        String templateDirectory = req.getTemplateDir();
        PlaceholderSettings placeholder = req.getPlaceholder();
        String type = model.getMetadata().getType();

        if (!this.getFileHandlerRegistry().containsKey(type)) {
            String msg = String.format("Unsupported Kind: FileHandler type: %s", type);
            logger.warn(msg);
            throw new IllegalArgumentException(String.format(msg));
        }

        for (FileHandlerKindModelSpec spec : model.getSpec()) {

            List<String> files = spec.getFiles();

            String baseDir = spec.getBase_dir();

            List<FileHandlerKindModelPlaceholder> placeholders = spec.getPlaceholders();

            if ("java".equals(type)) {
                handleJavaType(logger, templateDirectory, placeholder, type,
                        baseDir, placeholders);
            } else {
                handleFileTypes(logger, templateDirectory, placeholder,
                        type, files, placeholders);
            }
        }
    }

    private void handleFileTypes(RunnerLogger logger, String templateDirectory,
            PlaceholderSettings placeholder, String type,
            List<String> files, List<FileHandlerKindModelPlaceholder> placeholders) {

        var fileHandlersRegistry = getFileHandlerRegistry();

        for (String file : files) {
            String filePath = templateDirectory + "/" + file;
            logger.warn("Start placeholder for: " + filePath);

            for (FileHandlerKindModelPlaceholder placeholderIt : placeholders) {
                String match = placeholderIt.getMatch();
                String replace = placeholderIt.getReplace();
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

    private void handleJavaType(RunnerLogger logger, String templateDirectory,
            PlaceholderSettings placeholder, String type, String baseDir,
            List<FileHandlerKindModelPlaceholder> placeholders) {

        for (FileHandlerKindModelPlaceholder placeholderIt : placeholders) {
            String match = placeholderIt.getMatch();
            String replace = placeholderIt.getReplace();
            try {
                logger.warn("Replace: " + match + " with: " + placeholder.getPrefix()
                        + replace + placeholder.getSuffix());

                getFileHandlerRegistry().get(type).replace(templateDirectory + "/" + baseDir,
                        match, placeholder.getPrefix() + replace + placeholder.getSuffix());
            } catch (FileHandlerException e) {
                logger.error("Error to replace placeholders", e);
                throw new RuntimeException(e);
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
