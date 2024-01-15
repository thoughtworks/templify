package com.twlabs.kinds.handlers.xmlhandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerBase;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;

@KindHandler(name = XmlHandlerKind.NAME)
public class XmlHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    static final String NAME = "XmlHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new XmlFileHandler(), command);
    }

}
