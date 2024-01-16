package com.twlabs.kinds.handlers.plaintexthandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerBase;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;

/**
 * PlainTextHandlerKind
 */
@KindHandler( name = PlainTextHandlerKind.PLAIN_TEXT_HANDLER)
public class PlainTextHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    static final String PLAIN_TEXT_HANDLER = "PlainTextHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new PlainTextHandler(), command);
    }

}
