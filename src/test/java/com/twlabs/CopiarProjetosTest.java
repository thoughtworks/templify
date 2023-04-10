package com.twlabs;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class CopiarProjetosTest {

    @Test
    public void test_copiar_arquivos() {

        File resultado = new File("./target/template/pom.xml");

        assertTrue(resultado.exists() && resultado.isFile());
    }

}
