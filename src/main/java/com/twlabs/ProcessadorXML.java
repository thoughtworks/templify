package com.twlabs;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * ProcessadorXML bom cidadao
 */
public class ProcessadorXML implements Processador {

    // Loc
    private Document xml;

    public ProcessadorXML(Path path) throws RuntimeException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = builderFactory.newDocumentBuilder();
            xml = builder.parse(path.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo " + path.toString());
        }
    }


    public Map<String, String> buscaParametro(String path) throws XPathExpressionException {

        XPath xpath = XPathFactory.newInstance().newXPath();
        Map<String, String> map = new HashMap<String, String>();

        Node parametro = (Node) xpath.evaluate(path, xml, XPathConstants.NODE);

        if (parametro == null) {
            throw new XPathExpressionException("Parametro " + path + " não encontrado!!!");
        }

        map.put(path, parametro.getTextContent());

        return map;
    }


    public Map<String, String> parse(Map<String, String> paths) throws XPathExpressionException {

        Map<String, String> map = new HashMap<String, String>();

        for (String path : paths.keySet()) {
            map.put(path, buscaParametro(path).get(path));
        }

        return map;
    }



    @Override
    public void replace(String path, String query, String novoValor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }


    @Override
    public void replace(String path, Map<String, String> mapaQueryValor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }


    @Override
    public List<String> find(String pathArquivo, String query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }



}
