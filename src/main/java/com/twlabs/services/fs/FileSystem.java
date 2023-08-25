package com.twlabs.services.fs;

import java.io.IOException;

/**
 * FileSystem
 */
public interface FileSystem {

    public boolean fileExists(String path) throws IOException;

    public void deleteDirectory(String path) throws IOException;

}
