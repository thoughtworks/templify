package com.twlabs.kinds.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Kind is an interface that represents a generic kind of object.
 * 
 * @param <S> the type of the kind specification
 * @param <M> the type of the metadata associated with the kind
 */
public interface Kind<S extends Serializable> {

    /**
     * Executes the kind with the given kind specifications.
     * 
     * @param kindSpec the list of kind specifications
     * @throws RuntimeException if there is an error during execution
     */
    public void execute() throws RuntimeException;


    /**
     * Retrieves the metadata associated with the kind.
     * 
     * @return the metadata associated with the kind
     */
    public Optional<Map<String, String>> getMetadata();

    /**
     * Retrieves the list of kind specifications.
     * 
     * @return the list of kind specifications
     */
    public List<S> getSpecs();

    /**
     * Retrieves the name of the kind.
     * 
     * @return the name of the kind
     */
    public String getName();

}
