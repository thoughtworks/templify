package com.twlabs;

import java.util.Map;
import org.w3c.dom.NodeList;

/**
 * Locator
 */
public interface HandlerFiles {

    NodeList find(String filePath, String query);

    void replace(String filePath, String query, String newValue, String replacedValuesPath);

    void replace(String filePath, Map<String, String> queryValueMap, String replacedValuesPath);

}
