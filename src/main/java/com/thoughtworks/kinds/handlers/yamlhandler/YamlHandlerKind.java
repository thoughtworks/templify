package com.thoughtworks.kinds.handlers.yamlhandler;

import com.thoughtworks.kinds.api.KindHandler;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;
import com.thoughtworks.kinds.handlers.base.KindHandlerBase;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;

@KindHandler(name = YamlHandlerKind.NAME)
public class YamlHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    public static final String NAME = "YmlHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new YamlFileHandler(), command);
    }
}
