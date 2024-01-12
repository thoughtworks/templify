package com.twlabs.kinds.handlers.javahandler;

import java.util.List;
import com.twlabs.kinds.api.FileHandlerException;
import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindPlaceholder;
import com.twlabs.kinds.handlers.base.KindTemplate;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.services.logger.RunnerLogger;


@KindHandler(name = "JavaHandler", specClass = JavaHandlerSpec.class)
public class JavaHandlerKind extends KindTemplate<JavaHandlerSpec> {

    private JavaHandler javaHandler = new JavaHandler();

    @Override
    public void execute() {

        this.getLogger().info("Executing JavaHandlerKind.");

        String templateDirectory = this.getRequest().getTemplateDir();
        PlaceholderSettings placeholder = this.getRequest().getPlaceholder();

        for (final JavaHandlerSpec spec : this.getSpecs()) {
            handleJavaType(this.getLogger(), templateDirectory, placeholder, spec.getBaseDir(),
                    spec.getPlaceholders());
        }
    }


    private void handleJavaType(RunnerLogger logger, String templateDirectory,
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
