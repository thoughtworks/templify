package com.twlabs.kinds.handlers.javahandler;

import com.twlabs.kinds.api.KindTemplate;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.kinds.api.KindMetadata;


@KindMetadata(name = "JavaHandler")
public class JavaHandlerKind extends KindTemplate<JavaHandlerSpec> {

    private JavaHandlerKind(KindMappingTemplate map) {
        super(map, JavaHandlerSpec.class);
    }

    @Override
    public boolean execute() {


        return false;
    }

}
