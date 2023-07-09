
package com.twlabs.interfaces;

import java.util.Map;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.model.Options;

/**
 * Interface to be implemented by the file handler class.
 */
public interface FileHandler {

    /**
     * Finds all occurrences of a given query in a file specified by the file path.
     *
     * @param filePath The path to the file to be searched.
     * @param query The string to be searched for in the file.
     * @return A map containing the finded query values.
     * @throws FileHandlerException If there is an error accessing or reading the file.
     */
    Map<String, String> find(String filePath, String query) throws FileHandlerException;


    /**
     * Replaces all occurrences of a given query with a new value in a specified file.
     *
     * @param filePath The path of the file to be modified.
     * @param query The string to be replaced.
     * @param newValue The new value to replace the query with.
     * @throws FileHandlerException If an error occurs while accessing or modifying the file.
     */
    void replace(String filePath, String query, String newValue) throws FileHandlerException;


    /**
     * Replaces the values in a file with the given query value map.
     *
     * @param filePath The path of the file to be replaced.
     * @param queryValueMap A map containing the query values to be replaced in the file.
     * @throws FileHandlerException If an error occurs while replacing the values in the file.
     */
    void replace(String filePath, Map<String, String> queryValueMap) throws FileHandlerException;


    /**
     * Sets the options for the file handler.
     *
     * @param opts the options to set for the file handler
     * @throws FileHandlerException if an error occurs while setting the options
     */
    void setOptions(Options opts) throws FileHandlerException;



}
