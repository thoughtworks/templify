package com.twlabs;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;

@MavenJupiterExtension
public class CookieCutterMojoIT {

    FileHandler handler = new XMLHandler();

    String POM =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/configuracao_basica_build_test/project/target/template/pom.xml";

    String json_handler_test =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_json_handler_empty_pom/project/target/template/env.json";
    String templateDir_default_pom =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_replace_default_pom_file/project/target/template";

    String templateDir_generics_xmls =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_replace_generics_xml_files/project/target/template";

    String templateDir_generics_ymls =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_replace_generics_yml_files/project/target/template";

    String unsupportFileType =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_replace_throw_unsupported_file_type/project/target/template";

    String template_json =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_replace_json_file/project/target/template";


    String template_java =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_replace_java/project/target/template";

    @MavenTest
    public void configuracao_basica_build_test(MavenExecutionResult result) {

        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        File resultadoPom = new File(POM);

        assertTrue(resultadoPom.exists() && resultadoPom.isFile(),
                "pom.xml was not copied to the template folder.");

    }


    @MavenTest
    public void test_json_handler_empty_pom(MavenExecutionResult result)
            throws IOException, FileHandlerException {

        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        File resultadoJson = new File(json_handler_test);

        assertTrue(resultadoJson.exists() && resultadoJson.isFile(),
                "env.json was not copied to the template folder.");


    }



    @MavenTest
    public void test_replace_default_pom_file(MavenExecutionResult result)
            throws IOException, FileHandlerException {
        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");


        final Path fileTemplatePom = Paths.get(templateDir_default_pom + "/pom.xml");


        String groupIdQuery = "/project/groupId";
        String groupIdNewName = "{{Cookiecutter.param.groupId}}";

        String artifactIdQuery = "/project/artifactId";
        String artifactIdNewName = "{{Cookiecutter.test.replace.map.artifactId}}";

        String scopesQuery =
                "/project/dependencies/dependency/scope[text()='{{Cookiecutter.replace.map.scopes}}']";
        String scopesNewName = "{{Cookiecutter.replace.map.scopes}}";

        Map<String, String> actual =
                handler.find(fileTemplatePom.toAbsolutePath().toString(), artifactIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(artifactIdNewName);


        actual = handler.find(fileTemplatePom.toAbsolutePath().toString(), groupIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(groupIdNewName);


        actual = handler.find(fileTemplatePom.toAbsolutePath().toString(), scopesQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(scopesNewName);

    }


    @MavenTest
    public void test_replace_generics_xml_files(MavenExecutionResult result)
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

        String authorQuery = "/bookstore/book/author[text()='{{Cookiecutter.kurtCagle}}']";
        String autorNewName = "{{Cookiecutter.kurtCagle}}";

        String yearQuery = "/bookstore/book/year[text()='{{Cookiecutter.NewYear}}']";
        String yearNewName = "{{Cookiecutter.NewYear}}";

        actual = handler.find(fileTemplateGeneric2.toAbsolutePath().toString(), authorQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(autorNewName);


        actual = handler.find(fileTemplateGeneric2.toAbsolutePath().toString(), yearQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(yearNewName)
                .doesNotContainValue("2005");

    }

    @MavenTest
    public void test_replace_generics_yml_files(MavenExecutionResult result)
            throws FileHandlerException {

        FileHandler yamlHandler = new YamlHandler();
        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        final Path fileTemplateGeneric1 =
                Paths.get(templateDir_generics_ymls + "/yamls/generic1.yml");


        String fileQuery = "mappings[0].file";
        String fileNewName = "{{newFile}}";

        String groupIdQuery = "mappings[0].placeholders[0].query";
        String groupIdQueryNewName = "{{Cookiecutter.query.project.groupId}}";

        String groupIdQueryName = "mappings[0].placeholders[0].name";
        String groupIdNewName = "{{Cookiecutter.replace.map.groupId}}";

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
    public void test_replace_java(MavenExecutionResult result) throws FileHandlerException {
        FileHandler javaHandler = new JavaHandler();
        assertThat(result).isSuccessful();
        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");


        String classpathTemplate_java = template_java + "/src/main/java";
        String packageQuery = "com.myPackage";
        String packageNewName = "{{cookiecutter.package}}";

        Map<String, String> filePathMap = javaHandler.find(classpathTemplate_java, packageNewName);

        assertFalse(filePathMap.containsKey(packageQuery),
                "Directory " + filePathMap.get(packageQuery) + " was not moved");
        assertTrue(Files.isDirectory(Paths.get(classpathTemplate_java + "/" + packageNewName)),
                "It was not found directory: " + packageNewName + " on path: "
                        + classpathTemplate_java);
    }



    @MavenTest
    public void test_replace_json_file(MavenExecutionResult result)
            throws IOException, FileHandlerException {

        FileHandler jsonHandler = new JsonHandler();

        assertThat(result).isSuccessful();


        final Path fileTemplatePom = Paths.get(template_json + "/jsons/test.json");


        String nameQuery = "$['name']";
        String nameNewName = "{{Cookiecutter.name}}";

        String ageQuery = "$['age']";
        String ageNewName = "{{Cookiecutter.age}}";


        Map<String, String> actual =
                jsonHandler.find(fileTemplatePom.toAbsolutePath().toString(), ageQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(ageNewName);


        actual = jsonHandler.find(fileTemplatePom.toAbsolutePath().toString(), nameQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(nameNewName);
    }



    @MavenTest
    public void test_replace_throw_unsupported_file_type(MavenExecutionResult result) {

        assertThat(result).isFailure().out().plain().contains(
                "Caused by: java.lang.IllegalArgumentException: Unsupported file type: pom.unsupported");


    }



}
