package com.twlabs.services.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.twlabs.injetor.ContextDependencyInjection;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;

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

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        String buildDir = baseDir + "target/";
        String templateDir = buildDir + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withTemplateDir(templateDir);

        CreateTemplateRequest execute = task.execute(requestBuilder.build());

        assertNotNull(execute.getConfiguration());

    }
}
