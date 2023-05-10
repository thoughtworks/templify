package com.twlabs.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

/**
 * JavaHandler
 */
public class JavaHandler implements FileHandler {

    @Override
    public Map<String, String> find(String filePath, String query) throws FileHandlerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");

    }

    @Override
    public void replace(String file, String query, String newValue) throws FileHandlerException {

        Path classPath = Paths.get(file);


        try {
            Files.walk(classPath).filter(Files::isRegularFile).forEach(regulaFile -> {
                try {
                    String packageName = getPackageName(regulaFile.toAbsolutePath());

                    String fileContent = new String(Files.readAllBytes(regulaFile));

                    fileContent = fileContent.replaceAll(query, newValue);
                    Files.write(regulaFile, fileContent.getBytes());

                    if (packageName.contains(query)) {
                        String newPath =
                                regulaFile.toAbsolutePath().toString().replace(query, newValue);
                        Files.createDirectories(Paths.get(newPath).getParent());
                        Files.move(regulaFile, Paths.get(newPath));
                    }
                } catch (IOException e) {
                    throw new RuntimeException("replace file exception: "+regulaFile.toString(), e);
                }
            });
        } catch (IOException e) {
            throw new FileHandlerException("Error while walking file: " + classPath.toString(), e);
        }

        removePackageDirectory(classPath, query);



    }


    public void removePackageDirectory(Path classPath, String oldDir) throws FileHandlerException {

        // get first folder to walk
        String firstFolder = oldDir.replace(".", File.separator).split(File.separator)[0];

        Path dirToDelete = classPath.resolve(firstFolder);

        if (Files.isDirectory(dirToDelete)) {
            try {
                Files.walk(dirToDelete).sorted(Comparator.reverseOrder()).map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new FileHandlerException(
                        "It was not possible to remove the directory: " + dirToDelete.toString(),
                        e);
            }
        }
    }



    private static String getPackageName(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        Pattern pattern = Pattern.compile("(?mi)^\\s*package\\s+([a-z0-9_\\.]+)\\s*;");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("content: " + content + "\natcher: " + matcher.group()
                + " Package not found in " + filePath);
    }




    @Override
    public void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }



}
