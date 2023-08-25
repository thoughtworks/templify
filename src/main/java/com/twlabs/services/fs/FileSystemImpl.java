package com.twlabs.services.fs;

import java.io.IOException;
import org.codehaus.plexus.util.FileUtils;

public class FileSystemImpl implements FileSystem {

    @Override
    public boolean fileExists(String path) throws IOException {
    
        return FileUtils.fileExists(path);

    }

    @Override
    public void deleteDirectory(String path) throws IOException {
    
        FileUtils.deleteDirectory(path);
    }

}

