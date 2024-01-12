package com.twlabs.kinds.handlers.base;

import java.util.List;
import com.twlabs.kinds.api.DefaultSpecification;

/**
 * This class represents a KindDefaultSpec object.
 * 
 * A KindDefaultSpec object is a serializable class that provides default specifications for a kind.
 * 
 * Example usage: class MyFileHandlerSpec extends KindDefaultSpec{ ... }
 * 
 * Note: This class implements the Serializable interface, which means objects of this class can be
 * converted into a byte stream and stored in a file or sent over a network.
 */
public class KindDefaultSpec implements DefaultSpecification {

    private List<String> files;
    private List<KindPlaceholder> placeholders;

    public List<String> getFiles() {
        return files;
    }

    public List<KindPlaceholder> getPlaceholders() {
        return placeholders;
    }
}
