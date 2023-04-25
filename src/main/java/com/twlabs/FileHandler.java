
package com.twlabs;

import java.util.Map;
/**
 * Locator
 */
public interface FileHandler {

    Map<String, String> find(String filePath, String query) throws FileHandlerException;

    void replace(String filePath, String query, String newValue)
            throws FileHandlerException;

    void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException;

}
