package com.twlabs.kinds.handlers.yamlhandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindTemplate;

@KindHandler(name = YamlHandlerKind.NAME)
public class YamlHandlerKind extends KindTemplate<KindDefaultSpec> {

    static final String NAME = "YmlHandler";

    @Override
    public void execute() throws RuntimeException {
        this.executeDefaultFileHandlers(new YamlHandler());
    }
}
