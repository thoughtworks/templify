package com.twlabs.kinds.handlers.jsonhandler;

import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerBase;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;

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
