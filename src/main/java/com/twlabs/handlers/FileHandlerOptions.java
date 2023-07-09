package com.twlabs.handlers;

import java.util.HashMap;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.model.Options;

/**
 * Abstract class to handle options for file handlers.
 *
 * Example of usage:
 * <pre>
 *
 * class SomeFileHandler extends FileHandlerOptions implements FileHandler { }
 *
 * </pre>
 *
 * Common options are attributes of Options, for ex placeholder suffix and preffix
 * Any different options must be added to extension map that is a <String, String>
 *
 * @see com.twlabs.model.Options 
 *
 */
abstract class FileHandlerOptions {

    // FIX - Default settings must be in some other place.
    private static final String DEFAULT_PREFIX = "{{";
    /**
     * Default prefix for file handlers.
     */
    private static final String DEFAULT_SUFFIX = "}}";

    /**
     * Default suffix for file handlers.
     */

    public Options options;
    /**
     * Options for file handlers.
     */

    public void setOptions(Options opt) throws FileHandlerException {
    /**
     * Set options for file handlers.
     * @param opt Options for file handlers.
     * @throws FileHandlerException
     */
        this.options = opt;
    }

    public Options getOptions() {
    /**
     * Get options for file handlers.
     * @return Options for file handlers.
     */
        if( this.options == null ) 
            this.options = new Options(DEFAULT_PREFIX, DEFAULT_SUFFIX, new HashMap<String, String>());
        return this.options;
    }

}
