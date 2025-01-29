package com.thoughtworks.kinds.api;

import java.io.Serializable;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;

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
    public void execute(KindHandlerCommand<S> command) throws RuntimeException;
}
