package com.twlabs.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.github.javafaker.Faker;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class XMLHandlerTest {

    FileHandler processador = new XMLHandler();
    Faker faker = new Faker();


    final String teste_xml = "src/test/resources/processador/xml/teste.xml";


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



    private Path fileForTest() throws IOException {
        String fileName = faker.lorem().word().toLowerCase();
        final Path fileForTest = Files.createTempFile(fileName, ".xml");
        FileUtils.copyFile(Paths.get(teste_xml).toFile(), fileForTest.toFile());
        return fileForTest;

    }



    @ParameterizedTest
    @CsvSource({"/project/artifactId, project-to-test",
            "/project/groupId, org.apache.maven.plugin.my.unit",
            "/project/dependencies/dependency/scope[text() = 'no_test'], no_test",
            "/project/dependencies/dependency/scope[text() = 'test'], test"})

    public void test_find_one_value(String query, String value)
            throws FileHandlerException, IOException {
        final Path fileForTest = fileForTest();
        Map<String, String> result =
                processador.find(fileForTest.toAbsolutePath().toString(), query);

        Map<String, String> actual = new HashMap<String, String>();
        actual.put(query, value);

        assertTrue(result.equals(actual),
                "Result has: \n" + result + "\n Actual has: \n" + actual + "\n------");
    }



    @ParameterizedTest
    @CsvSource({"project/notFound", "notfound/groupId",
            "project/dependencies/dependency/scope[text()='NOT_FOUND']"})
    public void test_find_not_found(String query) throws FileHandlerException, IOException {
        final Path fileForTest = fileForTest();
        Map<String, String> nodes =
                processador.find(fileForTest.toAbsolutePath().toString(), query);
        assertTrue(nodes.isEmpty());
    }



    @ParameterizedTest
    @CsvSource({"project/dependencies/dependency/scope"})
    public void test_find_found_same_path_different_values(String query) throws IOException {
        final Path fileForTest = fileForTest();
        assertThrows(FileHandlerException.class,
                () -> processador.find(fileForTest.toAbsolutePath().toString(), query));

    }



    private boolean checkExpectedLenght(String query, int expectedLenght, Path fileToTest)
            throws XPathExpressionException {
        Document doc = readFile(fileToTest);
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(query, doc, XPathConstants.NODESET);
        if (nodes.getLength() == expectedLenght) {
            return true;
        }

        throw new RuntimeException("Was expecting lenght equal " + expectedLenght + " but it was: "
                + nodes.getLength());
    }



    @ParameterizedTest
    @CsvSource({"/project/artifactId, param.artifactId",
            "/project/groupId, Cookiecutter.test.param",
            "/project/dependencies/dependency/scope[text()='no_test'], Cookiecutter.test.change.one.scope"})
    public void test_replace_just_one_tag(String query, String newValue)
            throws XPathExpressionException, IOException, FileHandlerException {
        final Path originalFile = fileForTest();


        processador.replace(originalFile.toAbsolutePath().toString(), query, newValue);

        // change the valeu of scopes, it is a more complex xpath :)
        if (query.equals("/project/dependencies/dependency/scope[text()='no_test']")) {
            query = "/project/dependencies/dependency/scope[text()='Cookiecutter.test.change.one.scope']";
        }

        assertTrue(checkExpectedLenght(query, 1, originalFile.toAbsolutePath()), "Values mismatch");

        Map<String, String> results =
                processador.find(originalFile.toAbsolutePath().toString(), query);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newValue);
    }



    @ParameterizedTest
    @CsvSource({"/project/dependencies/dependency/scope[text()='test'], Cookiecutter.scope.param"})
    public void test_replace_more_tags(String query, String newValue)
            throws XPathExpressionException, IOException, FileHandlerException {
        final Path originalFile = fileForTest();

        final String newQuery = "/project/dependencies/dependency/scope[text()='" + newValue + "']";
        processador.replace(originalFile.toAbsolutePath().toString(), query, newValue);

        assertTrue(checkExpectedLenght(newQuery, 2, originalFile), "Values mismatch");
        Map<String, String> results =
                processador.find(originalFile.toAbsolutePath().toString(), newQuery);
        assertThat(results).isNotNull().isNotEmpty().containsValue(newValue);
    }



    @ParameterizedTest
    @CsvSource({"/project/NotFound, param.artifactId"})
    public void test_replace_node_not_found(String query, String newValue) throws IOException {
        final Path originalFile = fileForTest();
        assertThrows(FileHandlerException.class, () -> processador
                .replace(originalFile.toAbsolutePath().toString(), query, newValue));
    }



    @Test
    public void test_replace_with_map() throws IOException, FileHandlerException {
        final Path originalFile = fileForTest();

        String groupIdQuery = "/project/groupId";
        String groupIdNewName = "Cookiecutter.test.replace.map.groupId";

        String artifactIdQuery = "/project/artifactId";
        String artifactIdNewName = "Cookiecutter.test.replace.map.artifactId";

        String scopesQuery = "/project/dependencies/dependency/scope[text()='test']";
        String scopesNewName = "Cookiecutter.replace.map.scopes";

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(groupIdQuery, groupIdNewName);
        queryMap.put(artifactIdQuery, artifactIdNewName);
        queryMap.put(scopesQuery, scopesNewName);

        processador.replace(originalFile.toAbsolutePath().toString(), queryMap);

        Map<String, String> result =
                processador.find(originalFile.toAbsolutePath().toString(), artifactIdQuery);

        printFileResult(originalFile.toAbsolutePath());
        assertThat(result).isNotNull().isNotEmpty().containsValue(artifactIdNewName);

        result = processador.find(originalFile.toAbsolutePath().toString(), groupIdQuery);
        assertThat(result).isNotNull().isNotEmpty().containsValue(groupIdNewName);


    }

    private void printFileResult(Path path) throws IOException {
        FileReader file = new FileReader(path.toAbsolutePath().toString());
        BufferedReader reader = new BufferedReader(file);
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
        file.close();
    }

}
