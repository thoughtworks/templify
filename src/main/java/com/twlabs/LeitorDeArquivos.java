package com.twlabs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * LeitorDeArquivos
 */
public class LeitorDeArquivos {

    public List<String> lerLinhas(Path pathArquivo) throws IOException {
        return Files.readAllLines(pathArquivo, StandardCharsets.UTF_8);
    }
}
