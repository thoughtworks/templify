package com.twlabs.kinds.handlers.yamlhandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerBase;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;

@KindHandler(name = YamlHandlerKind.NAME)
public class YamlHandlerKind extends KindHandlerBase<KindDefaultSpec> {

    public static final String NAME = "YmlHandler";

    @Override
    public void execute(KindHandlerCommand<KindDefaultSpec> command) throws RuntimeException {
        this.executeDefaultFileHandlers(new YamlFileHandler(), command);
    }
}
