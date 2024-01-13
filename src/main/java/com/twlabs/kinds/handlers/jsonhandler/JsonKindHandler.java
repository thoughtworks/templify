package com.twlabs.kinds.handlers.jsonhandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerBase;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;

/**
 * JsonKindHandler
 */
@KindHandler(name = JsonKindHandler.NAME)
public class JsonKindHandler extends KindHandlerBase<KindDefaultSpec> {

    public static final String NAME = "JsonHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new JsonHandler(), command);
    }


}
