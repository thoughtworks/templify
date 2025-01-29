package com.thoughtworks.kinds.handlers.plaintexthandler;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thoughtworks.kinds.api.FileHandlerException;
import com.thoughtworks.kinds.handlers.base.AbstractFileHandler;

/**
 * JsonHandler ->
 */
public class PlainTextHandler extends AbstractFileHandler {
    protected static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Override
    public Map<String, String> find(String filePath, String query) throws FileHandlerException {

        HashMap<String, String> result = new HashMap<>();

        try {
            String text = readFileAsString(filePath);
            List<Integer> resultIndex = searchWord(text, query);

            if (!resultIndex.isEmpty())
                result.put(query, String.valueOf(resultIndex.size()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void replace(String file, String key, String newValue) {
        try {
            String text = readFileAsString(file);
            String updatedContent = text.replaceAll(key, newValue);


            try (FileOutputStream outputStream = new FileOutputStream(file)) {

                byte[] strToBytes = updatedContent.getBytes();

                outputStream.write(strToBytes);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void replace(String filePath, Map<String, String> queryValueMap) {

        for (Map.Entry<String, String> entry : queryValueMap.entrySet()) {
            this.replace(filePath, entry.getKey(), entry.getValue());
        }
    }

    private List<Integer> searchWord(String string, String word) {
        List<Integer> results = new ArrayList<>();
        int indexOfWord = string.indexOf(word);
        while (indexOfWord != -1) {
            results.add(indexOfWord);
            indexOfWord = string.indexOf(word, indexOfWord + word.length());
        }
        return results;
    }


}
