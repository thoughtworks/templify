package com.twlabs.kinds.handlers.javahandler;

import com.twlabs.kinds.api.KindHandler;
import com.twlabs.kinds.api.KindTemplate;


@KindHandler(name = "JavaHandler", spec = JavaHandlerSpec.class)
public class JavaHandlerKind extends KindTemplate<JavaHandlerSpec> {

    @Override
    public boolean execute() {

        // this.getMetadata();
        // this.getSpecs();
        // LOGIC HERE...

        return true;
    }

}
