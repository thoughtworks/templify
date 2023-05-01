package com.twlabs;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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


        final Path fileTemplate = Paths.get(templateDir_default_pom + "/pom.xml");


        String groupIdQuery = "/project/groupId";
        String groupIdNewName = "Cookiecutter.param.groupId";

        String artifactIdQuery = "/project/artifactId";
        String artifactIdNewName = "${Cookiecutter.test.replace.map.artifactId}";

        String scopesQuery =
                "/project/dependencies/dependency/scope[text()='${Cookiecutter.replace.map.scopes}']";
        String scopesNewName = "${Cookiecutter.replace.map.scopes}";

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(groupIdQuery, groupIdNewName);
        queryMap.put(artifactIdQuery, artifactIdNewName);
        queryMap.put(scopesQuery, scopesNewName);


        Map<String, String> actual =
                handler.find(fileTemplate.toAbsolutePath().toString(), artifactIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(artifactIdNewName);


        actual = handler.find(fileTemplate.toAbsolutePath().toString(), groupIdQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(groupIdNewName);


        actual = handler.find(fileTemplate.toAbsolutePath().toString(), scopesQuery);
        assertThat(actual).isNotNull().isNotEmpty().containsValue(scopesNewName);

    }

}
