package com.twlabs.exceptions;

/**
 * This class represents a custom exception that can be thrown by a file handler.
 * It extends the built-in Exception class.
 * 
 * The FileHandlerException class provides two constructors:
 *
 * * 1. FileHandlerException(String errorMessage): 
 *    - Creates a new FileHandlerException with the specified error message.
 *    - The error message should provide a clear and concise description of the exception.
 * 
 * * 2. FileHandlerException(String errorMessage, Exception e):
 *    - Creates a new FileHandlerException with the specified error message and a cause.
 *    - The error message should provide a clear and concise description of the exception.
 *    - The cause parameter is an exception that caused this exception to be thrown.
 * 
 * Example usage:
 * 
 * <pre>
 * try {
 *     // Code that may throw a FileHandlerException
 * } catch (FileHandlerException e) {
 *     // Handle the exception
 * }
 * </pre>
 *
 */
public class FileHandlerException extends Exception {

    public FileHandlerException(String errorMessage) {
        super(errorMessage);
    }

    public FileHandlerException(String errorMessage, Exception e) {
        super(errorMessage, e);

    }
}

