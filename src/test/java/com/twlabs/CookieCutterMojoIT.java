package com.twlabs;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import com.twlabs.exceptions.FileHandlerException;

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

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

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
        String autorNewName ="{{Cookiecutter.kurtCagle}}";

        String yearQuery = "/bookstore/book/year[text()='{{Cookiecutter.NewYear}}']";
        String yearNewName = "{{Cookiecutter.NewYear}}";

        actual = handler.find(fileTemplateGeneric2.toAbsolutePath().toString(), authorQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(autorNewName);


        actual = handler.find(fileTemplateGeneric2.toAbsolutePath().toString(), yearQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(yearNewName)
                .doesNotContainValue("2005");



    }



}
