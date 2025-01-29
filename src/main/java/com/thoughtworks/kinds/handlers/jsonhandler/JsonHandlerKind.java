package com.thoughtworks.kinds.handlers.jsonhandler;

import com.thoughtworks.kinds.api.FileHandler;
import com.thoughtworks.kinds.api.KindHandler;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;
import com.thoughtworks.kinds.handlers.base.KindHandlerBase;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;

/**
 * JsonKindHandler
 */
@KindHandler(name = JsonHandlerKind.NAME)
public class JsonHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    public static final String NAME = "JsonHandler";
    private FileHandler fileHandler;

    public JsonHandlerKind(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public JsonHandlerKind() {
        this.fileHandler = new JsonFileHandler();
    }

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(fileHandler, command);
    }


}
