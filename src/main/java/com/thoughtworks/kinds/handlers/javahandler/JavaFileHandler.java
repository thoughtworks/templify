package com.thoughtworks.kinds.handlers.javahandler;

import com.thoughtworks.kinds.handlers.plaintexthandler.PlainTextHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.thoughtworks.kinds.api.FileHandler;
import com.thoughtworks.kinds.api.FileHandlerException;
import com.thoughtworks.kinds.handlers.base.AbstractFileHandler;

/**
 * This class represents a JavaFileHandler, which is a type of FileHandler that implements the
 * FileHandlerOptions interface.
 * 
 * The JavaFileHandler class provides functionality for handling Java files, including reading,
 * writing, and manipulating Java code.
 * 
 * This class extends the FileHandlerOptions class, which provides additional options and settings
 * for file handling.
 * 
 * Usage:
 * 
 * <pre>
 * JavaFileHandler handler = new JavaFileHandler();
 * </pre>
 * 
 * Note: This class assumes that the Java files being handled are valid and well-formed.
 * 
 * @see AbstractFileHandler
 * @see FileHandler
 */
public class JavaFileHandler extends AbstractFileHandler {


    private static final String DEFAULT_VALUE_PREFIX = "{{";
    private static final String DEFAULT_VALUE_SUFFIX = "}}";
    private static final String PREFIX_KEY = "prefix";
    private static final String SUFFIX_KEY = "suffix";



    public String findDir(String filePath, String query) throws FileHandlerException {

        // FIX must remove coupling with metadata
        String suffix = (String) this.metadata.getOrDefault(SUFFIX_KEY, DEFAULT_VALUE_SUFFIX);
        String prefix = (String) this.metadata.getOrDefault(PREFIX_KEY, DEFAULT_VALUE_PREFIX);

        String queryToTransform = query;
        if (!query.startsWith(prefix) && !query.endsWith(suffix)) {
            queryToTransform = query.replace(".", File.separator);
        }

        return this.find(filePath, queryToTransform).get(queryToTransform);
    }



    public Map<String, String> find(String filePath, String query) throws FileHandlerException {

        String transformQuery = query;
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
        Path classPath = Paths.get(file + File.separator + findDir(file, query));

        try {

            boolean filesModified = modifyFileContents(classPath, query, newValue);

            boolean filesMoved = moveFiles(classPath, query, newValue);

            boolean directoryRemoved = removePackageDirectory(file, query);

            if (!(filesMoved && filesModified && directoryRemoved)) {
                throw new FileHandlerException(
                        "Could not replace " + query + "to" + newValue + " in path: " + file);
            }

        } catch (FileHandlerException e) {
            throw new FileHandlerException("Error while replacing for path: " + file
                    + "with package: " + query + " to be replaced for: " + newValue, e);
        }

    }



    private boolean modifyFileContents(Path classPath, String oldContent, String newContent)
            throws FileHandlerException {
        AtomicBoolean filesModified = new AtomicBoolean(false);

        Map<String, String> getAllJavaFiles = findJavaFiles(classPath.toString());
        List<Path> javaFiles = new ArrayList<Path>();
        for (Map.Entry<String, String> entry : getAllJavaFiles.entrySet()) {
            javaFiles.add(Paths.get(entry.getValue()));

        }

        List<Path> resultBeforeChange = findJavaFilesWithMatchContent(javaFiles, oldContent);

        replaceJavaFilesContent(resultBeforeChange, oldContent, newContent);

        List<Path> resultAfterChange = findJavaFilesWithMatchContent(javaFiles, newContent);

        if (resultBeforeChange.size() == resultAfterChange.size()) {
            filesModified.set(true);
        }

        /**
         * try { Files.walk(classPath).filter(Files::isRegularFile).forEach(regularFile -> { try {
         * String fileContent = new String(Files.readAllBytes(regularFile));
         * 
         * if (fileContent.contains(oldContent)) { fileContent = fileContent.replaceAll(oldContent,
         * newContent); Files.write(regularFile, fileContent.getBytes()); filesModified.set(true); }
         * 
         * 
         * } catch (IOException e) { throw new RuntimeException( "Error while modify files contents:
         * " + classPath.toString(), e); }
         * 
         * }); } catch (IOException | RuntimeException e) { throw new FileHandlerException("Error
         * while walking file: " + classPath.toString(), e);
         * 
         * }
         */

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
        String firstFolder = findDir(classPath, oldDir).split(Pattern.quote(File.separator))[0];

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
        // Pattern pattern = Pattern.compile("(?mi)^\\s*package\\s+([a-z0-9_\\.\\{\\}]+)\\s*;");
        Pattern pattern = Pattern.compile("(?mi)^\\s*package\\s+([\\p{all}]+)\\s*;");
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



    protected static Map<String, String> findJavaFiles(String baseDir, String originalDir)
            throws FileHandlerException {
        Map<String, String> javaFiles = new HashMap<>();
        File rootDir = new File(baseDir);

        if (!rootDir.exists()) {
            throw new FileHandlerException(
                    "Java Directory not found: " + rootDir.getAbsolutePath());
        }

        if (!rootDir.isDirectory()) {
            throw new FileHandlerException(
                    "Java Directory is not a directory: " + rootDir.getAbsolutePath());
        }

        File[] files = rootDir.listFiles();

        if (files == null) {
            throw new FileHandlerException(
                    "Java Directory is not accessible: " + rootDir.getAbsolutePath());
        }

        for (File file : files) {
            if (file.isDirectory()) {
                javaFiles.putAll(findJavaFiles(file.getAbsolutePath(), originalDir));
            } else if (file.getName().endsWith(".java")) {
                try {
                    String absolutePath = file.getCanonicalPath();
                    String relativePath = absolutePath
                            .substring(new File(originalDir).getCanonicalPath().length() + 1);
                    javaFiles.put(relativePath, absolutePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return javaFiles;
    }


    protected Map<String, String> findJavaFiles(String baseDir) throws FileHandlerException {
        return findJavaFiles(baseDir, baseDir);

    }



    protected Map<String, String> findJavaFileContent(Path javaFile, String query)
            throws FileHandlerException {
        PlainTextHandler plainText = new PlainTextHandler();
        return plainText.find(javaFile.toFile().getAbsolutePath(), query);
    }

    protected void replaceJavaFileContent(Path javaFile, String query, String value) {
        PlainTextHandler plainText = new PlainTextHandler();
        plainText.replace(javaFile.toFile().getAbsolutePath(), query, value);

    }



    protected List<Path> findJavaFilesWithMatchContent(List<Path> javaFilesList,
            String match) {
        List<Path> javaFiles = new ArrayList<Path>();
        for (Path javaFilePath : javaFilesList) {
            try {
                Map<String, String> content = findJavaFileContent(javaFilePath, match);
                if (!content.containsValue("0") && content.containsKey(match)) {
                    javaFiles.add(javaFilePath);
                }

            } catch (FileHandlerException e) {
                throw new RuntimeException(e);
            }
        }
        return javaFiles;
    }



    protected void replaceJavaFilesContent(List<Path> javaFilesPaths, String query,
            String replace) {
        for (Path javaFilePath : javaFilesPaths) {
            replaceJavaFileContent(javaFilePath, query, replace);
        }
    }



}
