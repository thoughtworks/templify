package com.thoughtworks.kinds.handlers;

import java.util.Set;
import com.thoughtworks.kinds.handlers.javahandler.JavaHandlerKind;
import com.thoughtworks.kinds.handlers.jsonhandler.JsonHandlerKind;
import com.thoughtworks.kinds.handlers.plaintexthandler.PlainTextHandlerKind;
import com.thoughtworks.kinds.handlers.xmlhandler.XmlHandlerKind;
import com.thoughtworks.kinds.handlers.yamlhandler.YamlHandlerKind;
import com.thoughtworks.kinds.handlers.directoryhandler.DirectoryHandlerKind;

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
                PlainTextHandlerKind.NAME,
                DirectoryHandlerKind.NAME

        );
    }
}
