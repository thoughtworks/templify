package com.thoughtworks.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import com.thoughtworks.config.PlaceholderSettings;
import com.thoughtworks.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.thoughtworks.services.logger.JavaLogger;
import com.thoughtworks.services.logger.MavenLogger;
import com.thoughtworks.services.logger.RunnerLogger;

/**
 * CreateTemplateRequestTest
 */
public class CreateTemplateCommandTest {

    @ParameterizedTest
    @CsvSource({
            "/maven-templify.yml",
    })
    @DisplayName("CreateTemplateCommand.getMaventemplifyYml() must return maven-templify.yml")
    public void test_get_maven_templify_yml_should_be_maven_templify_yml(String ymlFile) {
        assertEquals(ymlFile, CreateTemplateCommand.getMaventemplifyYml());
    }


    @ParameterizedTest
    @CsvSource({
            "fool/DestDir",
    })
    public void test_builder_must_set_base_dir(String destDir) {

        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        assertEquals(requestBuilder.withDestDir(destDir), requestBuilder);
        assertEquals(destDir, requestBuilder.build().getDestDir());
    }


    @ParameterizedTest
    @CsvSource({
            "foo/BuildDir",
    })
    public void test_builder_must_set_build_dir(String buildDir) {

        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        assertEquals(requestBuilder.withBuildDir(buildDir), requestBuilder);
        assertEquals(buildDir, requestBuilder.build().getBuildDir());
    }

    @ParameterizedTest
    @CsvSource({
            "match, replace",
    })
    public void test_builder_must_set_the_placeholder(String placeholderMatch,
            String placeholderReplace) {

        PlaceholderSettings placeholder =
                new PlaceholderSettings(placeholderMatch, placeholderReplace);
        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();
        assertEquals(requestBuilder.withPlaceholder(placeholder), requestBuilder);
        assertEquals(placeholder, requestBuilder.build().getPlaceholder());


    }

    @Test
    public void test_builder_must_set_the_maven_logger() {

        MavenLogger logger = Mockito.mock(MavenLogger.class);
        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        assertEquals(requestBuilder.withLogger(logger), requestBuilder);
        assertEquals(logger, requestBuilder.build().getLogger());
    }


    @Test
    public void test_builder_must_set_the_custom_logger() {

        Log log = Mockito.mock(Log.class);
        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        assertEquals(requestBuilder, requestBuilder.withLogger(log));
        assertEquals(MavenLogger.class, requestBuilder.build().getLogger().getClass());

    }


    @Test
    void test_get_logger_null_should_create_runner_logger() {

        RunnerLogger nullLogger = null;
        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();

        assertEquals(requestBuilder, requestBuilder.withLogger(nullLogger));
        assertEquals(JavaLogger.class, requestBuilder.build().getLogger().getClass());
    }



}
