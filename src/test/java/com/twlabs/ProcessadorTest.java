package com.twlabs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProcessadorTest {

    HandlerFiles processador = new ProcessadorXML(null);

    String TESTXML = "processador/xml/teste.xml";
    String TESTREPLACED = "processador/xml/";

    public URL getFileFromResources(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    private Document readFile(Path path) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document xml;

        try {
            builder = builderFactory.newDocumentBuilder();
            xml = builder.parse(path.toFile());
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("Erro ao ler o arquivo " + path.toString());
        }
        return xml;
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
    public void test_replace_just_one_tag(String query, String newValue) throws XPathExpressionException {
        String pathToReplaced = getFileFromResources(TESTREPLACED)+"replaced_file.xml";
        assertDoesNotThrow(() -> processador.replace(getFileFromResources(TESTXML).getPath(), query,
                newValue, pathToReplaced));

        Document doc = readFile(Paths.get(URI.create(getFileFromResources(TESTREPLACED)+"replaced_file.xml").getPath()));


        XPath  xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(query, doc, XPathConstants.NODESET);
        assertTrue(nodes.getLength() == 1, "Was expecting lenght equal 1 but it was: "+nodes.getLength());

    }


    @ParameterizedTest
    @CsvSource({"/project/dependencies/dependency/scope, Cookiecutter.scope.param"})
    public void test_replace_more_tags(String query, String newValue) throws XPathExpressionException {
        String pathToReplaced = getFileFromResources(TESTREPLACED)+"replaced_file.xml";
        assertDoesNotThrow(() -> processador.replace(getFileFromResources(TESTXML).getPath(), query,
                newValue, pathToReplaced));

        Document doc = readFile(Paths.get(URI.create(getFileFromResources(TESTREPLACED)+"replaced_file.xml").getPath()));


        XPath  xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(query, doc, XPathConstants.NODESET);
        assertTrue(nodes.getLength() == 2, "Was expecting lenght equal 2 but it was: "+nodes.getLength());

    }



    @ParameterizedTest
    @CsvSource({"/project/NotFound, param.artifactId"})
    public void test_replace_node_not_found(String query, String newValue) {
        String pathToReplaced = getFileFromResources(TESTREPLACED).toString();
        assertThrows(RuntimeException.class, () -> processador
                .replace(getFileFromResources(TESTXML).getPath(), query, newValue, pathToReplaced));
    }
}
