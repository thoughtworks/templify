package com.twlabs.handlers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class YamlHandlerTest {

    FileHandler yamlHandler = new YamlHandler();


    final String test_yaml = "src/test/resources/processador/yaml/test.yml";

    @ParameterizedTest
    @CsvSource({"metadata.name, example", "kind, Deployment",
            "placeholders[0].query, /project/groupId",
            "placeholders[0].name, Cookiecutter.param.groupId",
            "placeholders[2].query, /project/dependencies/dependency/scope[text()='test']",
            "placeholders[2].name, Cookiecutter.replace.map.scopes"})
    public void test_find_simple_names_must_return_values(String query, String expected)
            throws FileHandlerException {
        assertThat(this.yamlHandler.find(test_yaml, query).get(query)).isNotNull().isNotEmpty()
                .isEqualTo(expected);
    }



    @ParameterizedTest
    @CsvSource({"src/test/resources/processador/yaml/empty.yml"})
    public void test_file_is_empty(String filePath) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.yamlHandler.find(filePath, "name"));
    }



    @ParameterizedTest
    @CsvSource({"metadata.notFound", "NotFound"})
    public void test_find_not_found(String query) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.yamlHandler.find(test_yaml, query));
    }



    @ParameterizedTest
    @CsvSource({"placeholders.query"})
    public void test_find_same_path_different_values(String query) throws FileHandlerException {
        assertThrows(FileHandlerException.class, () -> this.yamlHandler.find(test_yaml, query));
    }
}
