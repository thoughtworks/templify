package com.thoughtworks.services.fs;

import java.io.File;
import java.io.IOException;

/**
 * FileSystem
 */
public interface FileSystem {

    public boolean fileExists(String path) throws IOException;

    public void deleteDirectory(String path) throws IOException;

    public void copyDirectoryStructure(File fileOrigin, File fileDest) throws IOException; 

}
