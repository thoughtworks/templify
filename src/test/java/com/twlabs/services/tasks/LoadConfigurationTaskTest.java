package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.twlabs.injetor.ContextDependencyInjection;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;
import com.twlabs.services.logger.RunnerLogger;

/**
 * LoadConfigurationTaskTest
 */
public class LoadConfigurationTaskTest {

    private static final String BUILD_TEMPLATE_DIR = "/template";

    LoadConfigurationTask task = new LoadConfigurationTask();
    Injector injector = Guice.createInjector(new ContextDependencyInjection());

    @ParameterizedTest
    @CsvSource(value = {
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_generic_java_project/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_json_handler_empty_pom/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_new_settings_v2/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_default_pom_file/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_generics_xml_files/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_generics_yml_files/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_java/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_json_file/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_throw_unsupported_file_type/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_running_with_existing_template_directory/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_using_custom_placeholder_settings/",
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_using_default_placeholder_settings/",})
    public void test_configuration_default(String baseDir) {
        injector.injectMembers(task);


        RunnerLogger mockLogger = Mockito.mock(RunnerLogger.class);

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        String buildDir = baseDir + "target/";
        String templateDir = buildDir + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withTemplateDir(templateDir)
                .withLogger(mockLogger);

        CreateTemplateRequest execute = task.execute(requestBuilder.build());

        assertNotNull(execute.getConfiguration());
        Mockito.verify(mockLogger, Mockito.times(2)).warn(Mockito.anyString());

    }



    @ParameterizedTest
    @CsvSource(value = {
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/configuracao_basica_build_test/",})
    public void test_cofiguration_exception(String baseDir) {
        injector.injectMembers(task);

        RunnerLogger mockLogger = Mockito.mock(RunnerLogger.class);
        CreateTemplateRequest mockRequest = Mockito.mock(CreateTemplateRequest.class);
        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder(mockRequest);

        String buildDir = baseDir + "target/";
        String templateDir = buildDir + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withTemplateDir(templateDir)
                .withLogger(mockLogger);

        Mockito.doReturn(null).when(mockRequest).getConfigFilePath();
        Mockito.doReturn(mockLogger).when(mockRequest).getLogger();

        assertThrows(RuntimeException.class, () -> {
            task.execute(requestBuilder.build());
        });

        Mockito.verify(mockLogger).error(Mockito.anyString());



    }
}
