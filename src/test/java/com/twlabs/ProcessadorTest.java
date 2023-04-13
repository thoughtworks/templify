package com.twlabs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.NodeList;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProcessadorTest {

    HandlerFiles processador = new ProcessadorXML(null);

    String TESTXML = "processador/xml/teste.xml";
    String TESTREPLACED = "processador/xml/";

    public URL getFileFromResources(String path) {
        return getClass().getClassLoader().getResource(path);
    }


    @ParameterizedTest
    @CsvSource({"/project/artifactId, project-to-test",
            "/project/groupId, org.apache.maven.plugin.my.unit"})
    public void test_find(String query, String value) {
        NodeList nodes = processador.find(getFileFromResources(TESTXML).getPath(), query);

        Map<String, String> actual = new HashMap<String, String>();
        actual.put(query, value);

        Map result = new HashMap<String, String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            result.put(query, nodes.item(i).getTextContent());
        }
        assertTrue(result.equals(actual), "Result has: \n" + result);
    }

    @ParameterizedTest
    @CsvSource({"project/notFound", "notfound/groupId"})
    public void test_find_not_found(String query) {
        NodeList nodes = processador.find(getFileFromResources(TESTXML).getPath(), query);

        assertNull(nodes.item(0));

    }


    @ParameterizedTest
    @CsvSource({"/project/artifactId, param.artifactId",
            "/project/groupId, Cookiecutter.test.param"})
    public void test_replace(String query, String newValue) {
        String pathToReplaced = getFileFromResources(TESTREPLACED) + "replaced_file.xml";
        assertDoesNotThrow(() -> processador.replace(getFileFromResources(TESTXML).getPath(), query,
                newValue, pathToReplaced));
    }

    @ParameterizedTest
    @CsvSource({"/project/NotFound, param.artifactId"})
    public void test_replace_node_not_found(String query, String newValue) {
        String pathToReplaced = getFileFromResources(TESTREPLACED) + "replaced_file.xml";
        assertThrows(RuntimeException.class,() -> processador.replace(getFileFromResources(TESTXML).getPath(), query,
                newValue, pathToReplaced));
    }
}
