package com.twlabs.kinds.handlers;

import java.util.Set;
import com.twlabs.kinds.handlers.javahandler.JavaHandlerKind;
import com.twlabs.kinds.handlers.jsonhandler.JsonHandlerKind;
import com.twlabs.kinds.handlers.plaintexthandler.PlainTextHandlerKind;
import com.twlabs.kinds.handlers.xmlhandler.XmlHandlerKind;
import com.twlabs.kinds.handlers.yamlhandler.YamlHandlerKind;

/**
 * Index
 */
public class KindHandlersIndex {

    public Set<String> getRegisteredKinds() {
        return Set.of(

                JsonHandlerKind.NAME,
                YamlHandlerKind.NAME,
                JavaHandlerKind.NAME,
                XmlHandlerKind.NAME,
                PlainTextHandlerKind.NAME

        );
    }
}
