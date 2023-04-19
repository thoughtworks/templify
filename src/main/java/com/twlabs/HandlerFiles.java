
package com.twlabs;

import java.util.Map;

/**
 * Locator
 */
public interface HandlerFiles {

    Map<String, String> find(String filePath, String query) throws HandlerFilesException;

    void replace(String filePath, String query, String newValue, String replacedValuesPath)
            throws HandlerFilesException;

    void replace(String filePath, Map<String, String> queryValueMap, String replacedValuesPath)
            throws HandlerFilesException;

}
