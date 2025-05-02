package com.thoughtworks.kinds.handlers.directoryhandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.regex.Pattern;

import com.thoughtworks.kinds.api.FileHandlerException;
import com.thoughtworks.kinds.handlers.base.AbstractFileHandler;

public class DirectoryHandler extends AbstractFileHandler {

    public Map<String, String> find(String directoryPath, String query) throws FileHandlerException {

        String path = Paths.get(directoryPath).resolve(query).toString();
        // directoryPath + File.separator + query;

        if (isDirectoryExists(path))
            return Map.of(query, path);

        if (Files.isRegularFile(Paths.get(path)))
            throw new FileHandlerException(
                    "Path is a FILE but NOT a DIRECTORY: " + path + "\nfor query: " + query
                            + " at filePath: " + directoryPath);
        throw new FileHandlerException(
                "Path not found: " + path + "\nfor query: " + query + " at filePath: " + directoryPath);
    }

    @Override
    public void replace(String filePath, String query, String newValue) throws FileHandlerException {
        Map<String, String> pathMapSource = find(filePath, query);
        String pathToReplace = filePath + File.separator + newValue;

        if (copyDirectory(pathMapSource.get(query), pathToReplace)) {
            String pathToBeDeleted = getDirectoryToBeDeleted(filePath, query);
            if (deleteDirectory(pathToBeDeleted))
                return;
        }

        throw new FileHandlerException("Error while replacing directory: " + pathMapSource.get(query));

    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap) throws FileHandlerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    protected boolean copyDirectory(String sourceDirectory, String targetDirectory)
            throws FileHandlerException {
        if (isDirectoryExists(sourceDirectory) == false) {
            throw new FileHandlerException("Source directory not found or is not a directory: " + sourceDirectory);
        }

        Path sourcePath = Paths.get(sourceDirectory);
        Path targetPath = Paths.get(targetDirectory);

        try {
            Files.walk(sourcePath).forEach(source -> {
                Path destination = targetPath.resolve(sourcePath.relativize(source));
                try {
                    if (Files.isDirectory(source)) {
                        if (!Files.exists(destination))
                            Files.createDirectories(destination);
                    } else {
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error while copying directory: " + source.toString() + " to "
                                    + destination.toString(),
                            e);
                }
            });
        } catch (IOException | RuntimeException e) {
            throw new FileHandlerException("Error while copying directory: " + sourcePath.toString()
                    + " to " + targetPath.toString(), e);
        }

        return true;

    }

    protected boolean isDirectoryExists(String sourcePath) {
        Path path = Paths.get(sourcePath);

        return Files.exists(path) && Files.isDirectory(path);
    }

    protected boolean deleteDirectory(String sourcePath) {

        if (isDirectoryExists(sourcePath)) {
            File rootDir = new File(sourcePath);
            File[] files = rootDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file.getAbsolutePath());
                    } else {
                        file.delete();
                    }
                }
            }
            if (rootDir.delete()) {
                return true;
            }
        }

        return false;
    }

    public String getDirectoryToBeDeleted(String sourcePath, String query) {

        String firstFolder = query.split(Pattern.quote(File.separator))[0];
        String pathResult = sourcePath + File.separator + firstFolder;

        return pathResult;

    }
}
