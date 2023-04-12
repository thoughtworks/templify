package com.twlabs;

import java.util.List;
import java.util.Map;

/**
 * Locator
 */
public interface Processador {

    List<String> find(String pathArquivo, String query);

    void replace(String pathArquivo, String query, String novoValor);

    void replace(String pathArquivo, Map<String, String> mapaQueryValor);

}
