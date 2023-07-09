
package com.twlabs.interfaces;

import java.util.Map;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.model.Options;

/**
 * Locator
 */
public interface FileHandler {

    Map<String, String> find(String filePath, String query) throws FileHandlerException;

    void replace(String filePath, String query, String newValue) throws FileHandlerException;

    void replace(String filePath, Map<String, String> queryValueMap) throws FileHandlerException;

    void setOptions(Options opts) throws FileHandlerException;


}
