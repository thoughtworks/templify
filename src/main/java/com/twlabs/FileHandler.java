
package com.twlabs;

import java.util.Map;
import com.twlabs.FileHandlerException;
/**
 * Locator
 */
public interface FileHandler {

    Map<String, String> find(String filePath, String query) throws FileHandlerException;

    void replace(String filePath, String query, String newValue, String replacedValuesPath)
            throws FileHandlerException;

    void replace(String filePath, Map<String, String> queryValueMap, String replacedValuesPath)
            throws FileHandlerException;

}
