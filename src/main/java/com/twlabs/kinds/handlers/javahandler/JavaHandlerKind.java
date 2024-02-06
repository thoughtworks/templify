package com.twlabs.kinds.handlers.javahandler;

import java.util.List;
import com.twlabs.config.PlaceholderSettings;
import com.twlabs.kinds.api.FileHandlerException;
import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindHandlerBase;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;
import com.twlabs.kinds.handlers.base.KindPlaceholder;
import com.twlabs.services.logger.RunnerLogger;


@KindHandler(name = "JavaHandler", specClass = JavaHandlerSpec.class)
public class JavaHandlerKind extends KindHandlerBase<JavaHandlerSpec> {

    private JavaFileHandler javaHandler = new JavaFileHandler();

    @Override
    public void execute(KindHandlerCommand<JavaHandlerSpec> command) {

        command.getLogger().info("Executing JavaHandlerKind.");

        String templateDirectory = command.getRequest().getTemplateDir();
        PlaceholderSettings placeholder = command.getRequest().getPlaceholder();

        for (final JavaHandlerSpec spec : command.getSpecs()) {
            handleJavaType(command.getLogger(), templateDirectory, placeholder, spec.getBaseDir(),
                    spec.getPlaceholders());
        }
    }


    protected void handleJavaType(RunnerLogger logger, String templateDirectory,
            PlaceholderSettings placeholder, String baseDir,
            List<KindPlaceholder> placeholdersNew) {

        for (KindPlaceholder placeholderIt : placeholdersNew) {
            String match = placeholderIt.getMatch();
            String replace = placeholderIt.getReplace();

            try {
                logger.warn("Replace: " + match + " with: " + placeholder.getPrefix()
                        + replace + placeholder.getSuffix());

                this.javaHandler.replace(templateDirectory + "/" + baseDir,
                        match, placeholder.getPrefix() + replace + placeholder.getSuffix());

            } catch (FileHandlerException e) {
                logger.error("Error to replace placeholders", e);
                throw new RuntimeException(e);
            }
        }
    }
}
