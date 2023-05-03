package com.twlabs.handlers;

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
    @CsvSource({"metadata.name"})
    public void test_find_simple_names_must_return_values(String query)
            throws FileHandlerException {
        assertThat(this.yamlHandler.find(test_yaml, query)).isNotNull().isNotEmpty();
    }

}
