package com.twlabs;


import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import org.junit.jupiter.api.DisplayName;

@MavenJupiterExtension
public class CookieCutterMojoIT {



    @MavenTest
    @DisplayName("Build da aplicação deve ocorrer com sucesso")
    public void configuracao_basica_build_test(MavenExecutionResult resultado) {
        assertThat(resultado).isSuccessful();
    }

}
