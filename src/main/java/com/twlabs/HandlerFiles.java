
package com.twlabs;

import java.util.Map;

/**
 * Locator
 */
public interface HandlerFiles {

    Map<String, String> find(String filePath, String query);

    void replace(String filePath, String query, String newValue, String replacedValuesPath);

    void replace(String filePath, Map<String, String> queryValueMap, String replacedValuesPath);

}
