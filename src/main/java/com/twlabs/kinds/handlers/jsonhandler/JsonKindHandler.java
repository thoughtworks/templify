package com.twlabs.kinds.handlers.jsonhandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;
import com.twlabs.kinds.handlers.base.KindTemplate;

/**
 * JsonKindHandler
 */
@KindHandler(name = JsonKindHandler.NAME)
public class JsonKindHandler extends KindTemplate<KindDefaultSpec> {

    public static final String NAME = "JsonHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new JsonHandler(), command);
    }


}
