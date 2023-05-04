package com.twlabs.exceptions;

import java.io.IOException;

public class FileHandlerException extends Exception {

    public FileHandlerException(String errorMessage) {
        super(errorMessage);
    }

    public FileHandlerException(String errorMessage, Exception e) {
        super(errorMessage, e);

    }
}

