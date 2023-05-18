package com.twlabs.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

/**
 * JavaHandler
 */
public class JavaHandler implements FileHandler {

    public Map<String, String> find(String filePath, String query) throws FileHandlerException {

        String transformQuery = query;
        if (!query.startsWith("{{") && !query.endsWith("}}")) {
            transformQuery = query.replace(".", File.separator);
        }
        Path path = Paths.get(filePath + File.separator + transformQuery);


        Map<String, String> pathsMap = new HashMap<>();

        if (Files.isDirectory(path)) {
            pathsMap.put(query, transformQuery);
        } else {
            throw new FileHandlerException("Path not found: " + path + "\nfor query: " + query
                    + " at filePath: " + filePath);
        }

        return pathsMap;

    }



    public void replace(String file, String query, String newValue) throws FileHandlerException {
        Path classPath = Paths.get(file + File.separator + find(file, query).get(query));

        try {


            boolean filesModified = modifyFileContents(classPath, query, newValue);


            boolean filesMoved = moveFiles(classPath, query, newValue);


            boolean directoryRemoved = removePackageDirectory(file, query);


            if (!(filesMoved && filesModified && directoryRemoved)) {

                throw new FileHandlerException("Could not replace " + query + " in " + file);
            }


        } catch (FileHandlerException e) {
            throw new FileHandlerException("Error while replacing file: " + file, e);
        }

    }



    private boolean modifyFileContents(Path classPath, String oldDir, String newContent)
            throws FileHandlerException {
        AtomicBoolean filesModified = new AtomicBoolean(false);

        try {
            Files.walk(classPath).filter(Files::isRegularFile).forEach(regularFile -> {
                try {
                    String fileContent = new String(Files.readAllBytes(regularFile));

                    if (fileContent.contains(oldDir)) {
                        fileContent = fileContent.replaceAll(oldDir, newContent);
                        Files.write(regularFile, fileContent.getBytes());
                        filesModified.set(true);
                    }


                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error while modify files contents: " + classPath.toString(), e);
                }

            });
        } catch (IOException | RuntimeException e) {
            throw new FileHandlerException("Error while walking file: " + classPath.toString(), e);
        }

        return filesModified.get();

    }



    private boolean moveFiles(Path classPath, String oldDir, String newDir)
            throws FileHandlerException {
        AtomicBoolean filesMoved = new AtomicBoolean(false);

        try {
            Files.walk(classPath).filter(Files::isRegularFile).forEach(regularFile -> {
                try {
                    String packageName = getPackageName(regularFile.toAbsolutePath());

                    if (packageName.contains(newDir)) {
                        String newPath = regularFile.toAbsolutePath().toString()
                                .replace(oldDir.replace(".", File.separator), newDir);

                        Files.createDirectories(Paths.get(newPath).getParent());
                        Files.move(regularFile, Paths.get(newPath));
                        filesMoved.set(true);
                    }
                } catch (IOException | FileHandlerException e) {
                    throw new RuntimeException("Error while moving file: " + regularFile.toString(),
                            e);
                }
            });

        } catch (IOException | RuntimeException e) {
            throw new FileHandlerException("Error while walking file: " + classPath.toString(), e);
        }

        return filesMoved.get();

    }



    public boolean removePackageDirectory(String classPath, String oldDir)
            throws FileHandlerException {

        // get first folder to walk
        String firstFolder = find(classPath, oldDir).get(oldDir).split(File.separator)[0];

        Path dirToDelete = Paths.get(classPath + File.separator + firstFolder);

        try {
            if (Files.isDirectory(dirToDelete)) {
                Files.walk(dirToDelete).sorted(Comparator.reverseOrder()).map(Path::toFile)
                        .forEach(File::delete);
            }
            return true;
        } catch (IOException e) {
            throw new FileHandlerException(
                    "It was not possible to remove the directory: " + dirToDelete.toString(), e);
        }
    }



    private static String getPackageName(Path filePath) throws IOException, FileHandlerException {
        String content = Files.readString(filePath);
        Pattern pattern = Pattern.compile("(?mi)^\\s*package\\s+([a-z0-9_\\.]+)\\s*;");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new FileHandlerException("content: " + content + "\natcher: " + matcher.group()
                + " Package not found in " + filePath);
    }



    @Override
    public void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException {
        for (Map.Entry<String, String> entry : queryValueMap.entrySet()) {
            replace(filePath, entry.getKey(), entry.getValue());
        }
    }


}
