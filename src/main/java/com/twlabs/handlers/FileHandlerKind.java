package com.twlabs.handlers;

import java.util.HashMap;
import java.util.Map;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

/**
 * Abstract class to handle options for file handlers.
 *
 * Example of usage:
 * 
 * <pre>
 *
 * class SomeFileHandler extends FileHandlerOptions implements FileHandler {
 * }
 *
 * </pre>
 *
 * Common options are attributes of Options, for ex placeholder suffix and preffix Any different
 * options must be added to extension map that is a <String, String>
 *
 * @see com.twlabs.model.Metadata
 *
 */
abstract class FileHandlerKind implements FileHandler {

    public Map<String, Object> metadata = new HashMap<String, Object>();
    public Map<String, Object> spec = new HashMap<String, Object>();

    /**
     * Options for file handlers.
     */

    public void configure(Map<String, Object> metadata, Map<String, Object> spec) throws FileHandlerException {
        this.metadata = metadata;
        this.spec = spec;
    }


}
