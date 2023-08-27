package com.twlabs.services.fs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    @Test
    public void test_copyDirectoryStructure(@TempDir Path tempDir) throws IOException {

        String pathToNewStructure =
                tempDir.toFile().getAbsolutePath() + File.separator + "CopyFolder";

        Files.createDirectory(
                Paths.get(tempDir.toFile().getAbsolutePath() + File.separator + "DirectoryToCopy"));

        // Act
        boolean fileNotExists = fileSystem.fileExists(pathToNewStructure);

        fileSystem.copyDirectoryStructure(tempDir.toFile(), new File(pathToNewStructure));

        boolean fileExists = fileSystem.fileExists(pathToNewStructure);

        // Assert
        assertFalse(fileNotExists);
        assertTrue(fileExists);


    }



}
