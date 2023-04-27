package com.twlabs;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import org.junit.jupiter.api.DisplayName;

@MavenJupiterExtension
public class CookieCutterMojoIT {


    String POM =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/configuracao_basica_build_test/project/target/template/pom.xml";

    String json_handler_test =
            "./target/maven-it/com/twlabs/CookieCutterMojoIT/test_json_handler_empty_pom/project/target/template/env.json";



    @MavenTest
    @DisplayName("Build da aplicação deve ocorrer com sucesso")
    public void configuracao_basica_build_test(MavenExecutionResult result) {

        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        File resultadoPom = new File(POM);

        assertTrue(resultadoPom.exists() && resultadoPom.isFile(),
                "pom.xml was not copied to the template folder.");

    }


    @MavenTest
    @DisplayName("Build da aplicação deve ocorrer com sucesso")
    public void test_json_handler_empty_pom(MavenExecutionResult result) {

        assertThat(result).isSuccessful();

        assertThat(result).isSuccessful().out().info()
                .contains("Brace yourself! starting cookiecutter-templater-maven-plugin!!");

        File resultadoJson = new File(json_handler_test);

        assertTrue(resultadoJson.exists() && resultadoJson.isFile(),
                "env.json was not copied to the template folder.");

    }


}
