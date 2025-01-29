package com.thoughtworks.kinds.handlers.plaintexthandler;

import com.thoughtworks.kinds.api.KindHandler;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;
import com.thoughtworks.kinds.handlers.base.KindHandlerBase;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;

/**
 * PlainTextHandlerKind
 */
@KindHandler(name = PlainTextHandlerKind.NAME)
public class PlainTextHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    public static final String NAME = "PlainTextHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new PlainTextHandler(), command);
    }

}
