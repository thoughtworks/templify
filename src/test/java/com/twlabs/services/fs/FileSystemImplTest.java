package com.twlabs.services.fs;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * FileSystemTest
 */
public class FileSystemImplTest {

    FileSystem fileSystem = new FileSystemImpl();

    @Test
    public void test_FileExists(@TempDir Path tempDir) throws IOException {


        boolean exists = fileSystem.fileExists(tempDir.toFile().getAbsolutePath());

        assertTrue(exists);
    }


}
