package com.thoughtworks.kinds.handlers.xmlhandler;

import com.thoughtworks.kinds.api.KindHandler;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;
import com.thoughtworks.kinds.handlers.base.KindHandlerBase;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;

@KindHandler(name = XmlHandlerKind.NAME)
public class XmlHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    public static final String NAME = "XmlHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new XmlFileHandler(), command);
    }
}
