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


    String POM = ".target/template/pom.xml";

    @MavenTest
    @DisplayName("Build da aplicação deve ocorrer com sucesso")
    public void configuracao_basica_build_test(MavenExecutionResult resultado) {
        // validando se build foi executado com sucesso
        assertThat(resultado).isSuccessful();

        assertThat(resultado).isSuccessful().out().info()
                .contains("Brace yourself! Iniciando o cookiecutter-templater-maven-plugin!!");

        // maven-jar-plugin vai emitir um warning pq não estamos adicionando conteudo no jar
        assertThat(resultado).isSuccessful().out().warn()
                .contains("JAR will be empty - no content was marked for inclusion!");


        File resultadoPom = new File(POM);

        assertTrue(resultadoPom.exists() && resultadoPom.isFile(), "Arquivo pom.xml não foi copiado!!");

    }

}
