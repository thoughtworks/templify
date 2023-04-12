package com.twlabs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProcessadorXMLTest {


    String URI_XML = "target/template/pom.xml";
    String URI_CUTTER = "cutter.properties";

    private ProcessadorXML processador;

    @BeforeEach
    public void setup() throws Exception {
        Path path = Paths.get(URI_XML);
        processador = new ProcessadorXML(path);

    }

    @Test
    public void test_existe_xml() throws Exception {
        Path path = Paths.get(URI_XML);
        File arquivo = path.toFile();
        assertTrue(arquivo.exists());
    }


    @Test
    public void test_buscaParametro_valor_esperado() throws Exception {
        Map<String, String> esperado = new HashMap<>();
        esperado.put("/project/groupId", "com.twlabs");

        Map<String, String> resultado = processador.buscaParametro("/project/groupId");

        assertEquals(esperado, resultado);
    }


    @Test
    public void test_buscaParametro_Excepetion_path_nao_encontrado() {
        String path = "/project/fakeNews";
        assertThrows(XPathExpressionException.class, () -> {
            processador.buscaParametro(path);
        });
    }


    @Test
    public void test_parse_retornando_mapa_parametros() throws Exception {
        Map<String, String> paths = new HashMap<String, String>();
        paths.put("/project/artifactId", "");
        paths.put("/project/packaging", "");
        paths.put("/project/groupId", "");
        Map<String, String> esperado = new HashMap<String, String>();
        esperado.put("/project/artifactId", "cookiecutter-templater-maven-plugin");
        esperado.put("/project/packaging", "maven-plugin");
        esperado.put("/project/groupId", "com.twlabs");

        Map<String, String> resultado = processador.parse(paths);

        assertEquals(esperado, resultado);
    }

    @Test
    public void test_parse_Exception_path_nao_encontrado() {
        Map<String, String> path = new HashMap<String, String>();

        path.put("/project/artifactId", "");
        path.put("/project/fakeNews", "");

        assertThrows(XPathExpressionException.class, () -> {
            processador.parse(path);
        });


    }

}
