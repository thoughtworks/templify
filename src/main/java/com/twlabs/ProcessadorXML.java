package com.twlabs;

import java.nio.file.Path;
import java.util.HashMap;
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
 * ProcessadorXML
 */
public class ProcessadorXML {



    private Document xml;

    public ProcessadorXML(Path path) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        xml = builder.parse(path.toFile());


    }


    public Map<String, String> buscaParametro(String path) throws XPathExpressionException {

        XPath xpath = XPathFactory.newInstance().newXPath();
        Map<String, String> map = new HashMap<String, String>();

        Node parametro = (Node) xpath.evaluate(path, xml, XPathConstants.NODE);
        if (parametro == null) {
            throw new XPathExpressionException("Prametro " + path + " não encontrado!!!");
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



}
