package com.twlabs.mojos;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.twlabs.mojos.TemplifyIT;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.FileHandlerException;
import com.twlabs.kinds.handlers.javahandler.JavaFileHandler;
import com.twlabs.kinds.handlers.jsonhandler.JsonFileHandler;
import com.twlabs.kinds.handlers.xmlhandler.XmlFileHandler;
import com.twlabs.kinds.handlers.yamlhandler.YamlFileHandler;

@MavenJupiterExtension
public class TemplifyIT {

    FileHandler handler = new XmlFileHandler();

    String fixturesFolder = "./target/maven-it/com/twlabs/mojos/TemplifyIT/";

    String POM = fixturesFolder + "test_basic_usage_example/project/target/template/pom.xml";

    String json_handler_test =
            fixturesFolder + "basic_json_example/project/target/template/env.json";

    String templateDir_default_pom =
            fixturesFolder + "basic_default_options_example/project/target/template";

    String templateDir_generics_xmls =
            fixturesFolder + "basic_xml_example/project/target/template";

    String templateDir_generics_ymls =
            fixturesFolder + "basic_yml_example/project/target/template";

    String unsupportFileType =
            fixturesFolder + "test_replace_throw_unsupported_file_type/project/target/template";

    String template_json = fixturesFolder + "test_replace_json_file/project/target/template";


    String template_java = fixturesFolder + "basic_java_example/project/target/template";

    String template_java_project = fixturesFolder + "java_project_example/project/target/template";


    String template_custom_placeholder =
            fixturesFolder + "test_using_custom_placeholder_settings/project/target/template";



    @MavenTest
    public void test_basic_usage_example(MavenExecutionResult result) {

        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting Templify maven plugin!!");
        File resultadoPom = new File(POM);

        assertTrue(resultadoPom.exists() && resultadoPom.isFile(),
                "pom.xml was not copied to the template folder.");

    }

    @MavenTest
    public void basic_json_example(MavenExecutionResult result)
            throws IOException, FileHandlerException {

        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting Templify maven plugin!!");

        File resultadoJson = new File(json_handler_test);

        assertTrue(resultadoJson.exists() && resultadoJson.isFile(),
                "env.json was not copied to the template folder.");

        assertThat(result).isSuccessful().out().info()
                .contains("Producing KindHandlerEvent: JsonHandler");
        assertThat(result).isSuccessful().out().warn()
                .contains("Replace: $['name'] with: {{Cookiecutter.name}}");
    }

    @MavenTest
    public void basic_default_options_example(MavenExecutionResult result)
            throws IOException, FileHandlerException {
        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting Templify maven plugin!!");


        final Path fileTemplatePom = Paths.get(templateDir_default_pom + "/pom.xml");


        String groupIdQuery = "/project/groupId";
        String groupIdNewName = "{{cookiecutter.param.groupId}}";

        String artifactIdQuery = "/project/artifactId";
        String artifactIdNewName = "{{cookiecutter.test.replace.map.artifactId}}";

        String scopesQuery =
                "/project/dependencies/dependency/scope[text()='{{cookiecutter.replace.map.scopes}}']";
        String scopesNewName = "{{cookiecutter.replace.map.scopes}}";

        Map<String, String> actual =
                handler.find(fileTemplatePom.toAbsolutePath().toString(), artifactIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(artifactIdNewName);


        actual = handler.find(fileTemplatePom.toAbsolutePath().toString(), groupIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(groupIdNewName);


        actual = handler.find(fileTemplatePom.toAbsolutePath().toString(), scopesQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(scopesNewName);

    }


    @MavenTest
    public void basic_xml_example(MavenExecutionResult result)
            throws FileHandlerException {
        assertThat(result).isSuccessful();

        final Path fileTemplateGeneric1 =
                Paths.get(templateDir_generics_xmls + "/xmls/generic_1.xml");


        String headingQuery = "/note/heading";
        String headingNewName = "{{New Reminder}}";


        Map<String, String> actual =
                handler.find(fileTemplateGeneric1.toAbsolutePath().toString(), headingQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(headingNewName);


        final Path fileTemplateGeneric2 =
                Paths.get(templateDir_generics_xmls + "/xmls/complex/generic_2.xml");

        String authorQuery = "/bookstore/book/author[text()='{{cookiecutter.kurtCagle}}']";
        String autorNewName = "{{cookiecutter.kurtCagle}}";

        String yearQuery = "/bookstore/book/year[text()='{{cookiecutter.NewYear}}']";
        String yearNewName = "{{cookiecutter.NewYear}}";

        actual = handler.find(fileTemplateGeneric2.toAbsolutePath().toString(), authorQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(autorNewName);


        actual = handler.find(fileTemplateGeneric2.toAbsolutePath().toString(), yearQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(yearNewName)
                .doesNotContainValue("2005");

    }

    @MavenTest
    public void basic_yml_example(MavenExecutionResult result)
            throws FileHandlerException {

        FileHandler yamlHandler = new YamlFileHandler();
        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting Templify maven plugin!!");

        final Path fileTemplateGeneric1 =
                Paths.get(templateDir_generics_ymls + "/yamls/generic1.yml");


        String fileQuery = "mappings[0].file";
        String fileNewName = "{{newFile}}";

        String groupIdQuery = "mappings[0].placeholders[0].query";
        String groupIdQueryNewName = "{{cookiecutter.query.project.groupId}}";

        String groupIdQueryName = "mappings[0].placeholders[0].name";
        String groupIdNewName = "{{cookiecutter.replace.map.groupId}}";

        Map<String, String> actual =
                yamlHandler.find(fileTemplateGeneric1.toAbsolutePath().toString(), fileQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(fileNewName);


        actual = yamlHandler.find(fileTemplateGeneric1.toAbsolutePath().toString(), groupIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(groupIdQueryNewName);


        actual = yamlHandler.find(fileTemplateGeneric1.toAbsolutePath().toString(),
                groupIdQueryName);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(groupIdNewName);


    }


    @MavenTest
    public void basic_java_example(MavenExecutionResult result) throws FileHandlerException {
        JavaFileHandler javaHandler = new JavaFileHandler();
        assertThat(result).isSuccessful();
        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting Templify maven plugin!!");

        String classpathTemplate_java = template_java + "/src/main/java";
        String packageQuery = "com.myPackage";
        String packageNewName = "{{cookiecutter.package}}";

        Map<String, String> filePathMap = javaHandler.find(classpathTemplate_java, packageNewName);
        // src/main/java+/{{cookiecutter.package}}

        assertFalse(filePathMap.containsKey(packageQuery),
                "Directory " + filePathMap.get(packageQuery) + " was not moved");

        assertTrue(Files.isDirectory(Paths.get(classpathTemplate_java + "/" + packageNewName)),
                "It was not found directory: " + packageNewName + " on path: "
                        + classpathTemplate_java);
    }


    @MavenTest
    public void test_replace_json_file(MavenExecutionResult result)
            throws IOException, FileHandlerException {

        FileHandler jsonHandler = new JsonFileHandler();

        assertThat(result).isSuccessful();


        final Path fileTemplatePom = Paths.get(template_json + "/jsons/test.json");


        String nameQuery = "$['name']";
        String nameNewName = "{{cookiecutter.name}}";

        String ageQuery = "$['age']";
        String ageNewName = "{{cookiecutter.age}}";


        Map<String, String> actual =
                jsonHandler.find(fileTemplatePom.toAbsolutePath().toString(), ageQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(ageNewName);


        actual = jsonHandler.find(fileTemplatePom.toAbsolutePath().toString(), nameQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(nameNewName);
    }


    @MavenTest
    public void test_replace_throw_unsupported_file_type(MavenExecutionResult result) {
        assertThat(result).isFailure().out().error()
                .anyMatch(msg -> msg.contains("Unsupported kind handler received"));
    }


    @MavenTest
    public void java_project_example(MavenExecutionResult result) throws FileHandlerException {
        JavaFileHandler javaHandler = new JavaFileHandler();
        assertThat(result).isSuccessful();
        assertThat(result).isSuccessful().out().info().anyMatch(msg -> msg
                .contains("Brace yourself! starting Templify maven plugin!!"));

        assertThat(result).isSuccessful().out().info().anyMatch(msg -> msg
                .contains("Executing JavaHandlerKind."));

        String classpathTemplate_java = template_java_project + "/src/main/java";
        String packageQuery = "br.com.client.sfc.datalake.sfcdatatransferdatalake";
        String packageNewName = "{{cookiecutter.package}}";

        Map<String, String> filePathMap = javaHandler.find(classpathTemplate_java, packageNewName);

        assertFalse(filePathMap.containsKey(packageQuery),
                "Directory " + filePathMap.get(packageQuery) + " was not moved");
        assertTrue(Files.isDirectory(Paths.get(classpathTemplate_java + "/" + packageNewName)),
                "It was not found directory: " + packageNewName + " on path: "
                        + classpathTemplate_java);

        assertThat(result).isSuccessful().out().warn().anyMatch(msg -> msg
                .contains("Replace: " + packageQuery + " with: " + packageNewName));



    }


    @MavenTest
    public void test_running_with_existing_template_directory(MavenExecutionResult result) {
        assertThat(result).isSuccessful().out().info().anyMatch(
                msg -> msg.contains("Old template directory was found and it was removed!!"));

    }


    @MavenTest
    public void test_using_default_placeholder_settings(MavenExecutionResult result) {
        assertThat(result).isSuccessful().out().warn().anyMatch(msg -> msg
                .contains("Using default placeholder settings!! -> Prefix:{{ and Suffix: }}"));

    }



    @MavenTest
    public void test_using_custom_placeholder_settings(MavenExecutionResult result)
            throws FileHandlerException {

        String prefix = "_{{";
        String suffix = "}}";

        assertThat(result).isSuccessful().out().warn()
                .anyMatch(msg -> msg.contains("Using custom placeholder settings!! -> Prefix:"
                        + prefix + " and Suffix: " + suffix));

        // JavaFileHandler javaHandler = new JavaFileHandler();
        assertThat(result).isSuccessful();
        assertThat(result).isSuccessful().out().info().anyMatch(msg -> msg
                .contains("Brace yourself! starting Templify maven plugin!!"));


        String classpathTemplate_java = template_custom_placeholder + "/src/main/java";
        // String packageQuery = "com.myPackage";
        String packageNewName = prefix + "cookiecutter.package" + suffix;

        assertTrue(Files.isDirectory(Paths.get(classpathTemplate_java + "/" + packageNewName)),
                "It was not found directory: " + packageNewName + " on path: "
                        + classpathTemplate_java);
    }

    @MavenTest
    public void test_using_custom_placeholder_settings_and_default_handler(
            MavenExecutionResult result) {

        String prefix = "{%";
        String suffix = "%}";
        String type = "unknown";

        assertThat(result).isSuccessful().out().warn()
                .contains("Using custom placeholder settings!! -> Prefix:" + prefix
                        + " and Suffix: " + suffix);

        assertThat(result).isSuccessful().out().warn()
                .contains("Replace: #backstage-template-condition-infra-elk-condition-end with: "
                        + prefix + " endif " + suffix);

        assertThat(result).isSuccessful();
        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting Templify maven plugin!!");

        assertThat(result).isSuccessful().out().info()
                .contains("Producing KindHandlerEvent: PlainTextHandler");

    }



}
