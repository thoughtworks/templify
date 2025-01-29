package com.thoughtworks.kinds.handlers.plaintexthandler;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.github.javafaker.Faker;
import com.thoughtworks.kinds.api.FileHandler;
import com.thoughtworks.kinds.api.FileHandlerException;

/**
 * PlainTextHandlerTest
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PlainTextHandlerTest {

    FileHandler plainTextHandler = new PlainTextHandler();
    Faker faker = new Faker();
    final String teste_txt = "src/test/resources/processador/text/teste.txt";
    final String anyFile = "src/test/resources/processador/text/anyfile";

    @ParameterizedTest
    @CsvSource({"Unmatched", "SomeValue"})
    public void test_find_simple_types_must_not_return_values(String query)
            throws FileHandlerException {
        System.out
                .println("query: " + query + " == " + this.plainTextHandler.find(teste_txt, query));
        assertThat(this.plainTextHandler.find(teste_txt, query)).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"hobbies", "David", "football"})
    public void test_find_something_existing_must_return_result(String query)
            throws FileHandlerException {
        assertThat(this.plainTextHandler.find(teste_txt, query).size()).isEqualTo(1);
    }

    @Test
    public void test_replace_with_map() throws Exception {

        String nameQuery = "David";
        String newName = faker.name().fullName();

        String filename = faker.lorem().word().toLowerCase();

        final Path fileForTest = Files.createTempFile(filename, ".txt");
        FileUtils.copyFile(Paths.get(teste_txt).toFile(), fileForTest.toFile());

        this.plainTextHandler.replace(fileForTest.toAbsolutePath().toString(), nameQuery, newName);

        Map<String, String> findExistingContent =
                this.plainTextHandler.find(fileForTest.toAbsolutePath().toString(), nameQuery);
        Map<String, String> findUpdatedContent =
                this.plainTextHandler.find(fileForTest.toAbsolutePath().toString(), newName);

        assertThat(findExistingContent).isEmpty();
        assertThat(findUpdatedContent).isNotNull().isNotEmpty().containsKey(newName)
                .containsValue("2");
    }


    @Test
    public void test_replace_with_maps() throws Exception {

        String conditionStartPlaceholder =
                "#backstage-template-condition-infra-elk-condition-start";
        String conditionStartValue = "{% condition %}";

        String conditionEndPlaceholder = "#backstage-template-condition-infra-elk-condition-end";
        String conditionEndValue = "{% endif %}";

        Map<String, String> replaces = new HashMap<>();
        replaces.put(conditionStartPlaceholder, conditionStartValue);
        replaces.put(conditionEndPlaceholder, conditionEndValue);

        String filename = faker.lorem().word().toLowerCase();
        final Path fileForTest = Files.createTempFile(filename, ".someExt");
        FileUtils.copyFile(Paths.get(anyFile).toFile(), fileForTest.toFile());

        this.plainTextHandler.replace(fileForTest.toAbsolutePath().toString(), replaces);

        Map<String, String> previousPlaceholder1 = this.plainTextHandler
                .find(fileForTest.toAbsolutePath().toString(), conditionStartPlaceholder);
        Map<String, String> previousPlaceholder2 = this.plainTextHandler
                .find(fileForTest.toAbsolutePath().toString(), conditionEndPlaceholder);
        Map<String, String> updatedPlaceholder1 = this.plainTextHandler
                .find(fileForTest.toAbsolutePath().toString(), conditionStartValue);
        Map<String, String> updatedPlaceholder2 = this.plainTextHandler
                .find(fileForTest.toAbsolutePath().toString(), conditionEndValue);

        assertThat(previousPlaceholder1).isEmpty();
        assertThat(previousPlaceholder2).isEmpty();
        assertThat(updatedPlaceholder1).isNotNull().isNotEmpty().containsKey(conditionStartValue)
                .containsValue("1");
        assertThat(updatedPlaceholder2).isNotNull().isNotEmpty().containsKey(conditionEndValue)
                .containsValue("1");

    }
}
