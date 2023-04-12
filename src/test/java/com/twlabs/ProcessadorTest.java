package com.twlabs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProcessadorTest {

    Processador processador = new ProcessadorXML(null);

    public String getFileFromResources(){
       return getClass().getClassLoader().getResource("processador/xml/teste.xml").getPath();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/project/artifactId", "/project/version"})
    public void buscar(String valorTeste) {
        List<String> find = processador.find(getFileFromResources(), valorTeste);
        assertNotNull(find);
    }

    @Test
    public void replace() {
        assertDoesNotThrow(() -> processador.replace("path", "query", "novo_valor"));
        new File("processador/tmp/xml/teste.xml");

    }
}
