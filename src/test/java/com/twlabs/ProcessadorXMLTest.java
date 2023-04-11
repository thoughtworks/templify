package com.twlabs;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProcessadorXMLTest {


    String URI_XML = "target/template/pom.xml";
    String URI_CUTTER = "cutter.properties";


    @Test
    public void test_existe_xml() throws Exception {
        Path path = Paths.get(URI_XML);
        File arquivo = path.toFile();
        assertTrue(arquivo.exists());
    }


    @Test
    public void test_buscaParametro() throws Exception {


        ProcessadorXML processadorXml = new ProcessadorXML(Paths.get(URI_XML));
        Map<String, String> parametro = processadorXml.buscaParametro("/project/groupId");

        assertTrue("com.twlabs".equals(parametro.get("/project/groupId")));
        // assertTrue("cookiecutter-templater-maven-plugin".equals(parametros.get("artifactId")));
        // assertTrue("packaging".equals(parametros.get("packaging")));

    }

    @Test
    public void test_parse() throws Exception {
        Map<String, String> esperado = new HashMap<String, String>();

        esperado.put("/project/artifactId", "cookiecutter-templater-maven-plugin");
        esperado.put("/project/packaging", "maven-plugin");
        esperado.put("/project/groupId", "com.twlabs");


        ProcessadorXML processadorXML = new ProcessadorXML(Paths.get(URI_XML));
        Map<String, String> resultado = processadorXML.parse(esperado);

        assertTrue(esperado.equals(resultado));
    }

}
