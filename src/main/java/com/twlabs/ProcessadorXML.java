package com.twlabs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.dom4j.XPathException;
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
        System.out.println(path.toString());
        System.out.println(path.toFile());
        xml = builder.parse(path.toFile());


    }


    public Map<String, String> buscaParametro(String path) throws Exception {

        XPath xpath = XPathFactory.newInstance().newXPath();
        Node parametro = (Node) xpath.evaluate(path, xml, XPathConstants.NODE);


        Map<String, String> map = new HashMap<String, String>();

        map.put(path, parametro.getTextContent());


        return map;
    }


    public Map<String, String> parse(Map<String, String> path) throws XPathExpressionException {

        XPath xpath = XPathFactory.newInstance().newXPath();

        Map<String, String> map = new HashMap<String, String>();
        path.forEach((chave, valor) -> {
            try {
                Node parametroTmp = (Node) xpath.evaluate(chave, xml, XPathConstants.NODE);
                map.put(chave, parametroTmp.getTextContent());


            } catch (XPathExpressionException e) {
                System.err.println("Xablau na hora de iterar o mapa de nós");

            }
        });

        return map;
    }



}
