package com.twlabs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.io.TempDir;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class LeitorDeArquivosTest {

    private Path diretorioTeste;

    @BeforeEach
    public void setUp(@TempDir Path diretorioTemporario) {

        this.diretorioTeste = diretorioTemporario;

    }


    @Test
    public void test_LerLinhas() throws IOException {

        Path arquivo = Files.createFile(this.diretorioTeste.resolve("text.txt"));
        List<String> linhasControle = List.of("Linha 1", "Linha 2", "Linha 3");
        Files.write(arquivo, linhasControle);
        LeitorDeArquivos leitorArquivo = new LeitorDeArquivos();

        List<String> linhasExperimento = leitorArquivo.lerLinhas(arquivo);


        assertEquals(linhasControle, linhasExperimento);

    }
}
