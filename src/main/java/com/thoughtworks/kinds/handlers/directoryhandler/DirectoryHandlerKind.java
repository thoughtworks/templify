package com.thoughtworks.kinds.handlers.directoryhandler;

import java.io.File;
import java.util.List;

import com.thoughtworks.config.PlaceholderSettings;
import com.thoughtworks.kinds.api.FileHandlerException;
import com.thoughtworks.kinds.api.KindHandler;
import com.thoughtworks.kinds.handlers.base.KindHandlerBase;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;
import com.thoughtworks.kinds.handlers.base.KindPlaceholder;
import com.thoughtworks.kinds.handlers.javahandler.JavaHandlerSpec;
import com.thoughtworks.services.logger.RunnerLogger;

@KindHandler(name = DirectoryHandlerKind.NAME, specClass = DirectoryHandlerSpec.class)
public class DirectoryHandlerKind extends KindHandlerBase<DirectoryHandlerSpec> {

    public static final String NAME = "DirectoryHandler";
    private DirectoryHandler directoryHandler;

    public DirectoryHandlerKind(DirectoryHandler directoryHandler) {
        this.directoryHandler = directoryHandler;
    }

    public DirectoryHandlerKind() {
        this.directoryHandler = new DirectoryHandler();
    }

    @Override
    public void execute(KindHandlerCommand<DirectoryHandlerSpec> command) {
        command.getLogger().info("Executing DirectoryHandlerKind.");

        String templateDirectory = command.getRequest().getTemplateDir();
        PlaceholderSettings placeholderSetting = command.getRequest().getPlaceholder();

        command.getLogger()
                .info("[DirectoryHandlerKind] Executing with placeholder settings: " + placeholderSetting.getPrefix()
                        + " and " + placeholderSetting.getSuffix());

        List<DirectoryHandlerSpec> specs = command.getSpecs();
        command.getLogger().info("[DirectoryHandlerKind] For " + specs.size() + " specs.");
        for (final DirectoryHandlerSpec spec : command.getSpecs()) {
            String baseDir = spec.getBaseDir();
            List<KindPlaceholder> placeholders = spec.getPlaceholders();
            command.getLogger().info("[DirectoryHandlerKind] Executing spec with baseDir: " + baseDir);

            this.doReplace(command.getLogger(), templateDirectory, placeholderSetting, baseDir, placeholders);
        }

    }

    protected void doReplace(RunnerLogger logger, String templateDirectory, PlaceholderSettings placeholderSettings,
            String baseDir,
            List<KindPlaceholder> placeholders) {

        for (KindPlaceholder placholderNew : placeholders) {
            String match = placholderNew.getMatch();
            String replace = placholderNew.getReplace();
            String replaceWtihPlaceholder = placeholderSettings.getPrefix() + replace + placeholderSettings.getSuffix();
            String filePath = templateDirectory + File.separator + baseDir;

            try {
                logger.warn("Replace: " + match + " with: " + replaceWtihPlaceholder);

                this.directoryHandler.replace(filePath, match, replaceWtihPlaceholder);

            } catch (FileHandlerException e) {
                logger.error("Error to replace placeholders", e);
                throw new RuntimeException(e);
            }

        }
    }

}
