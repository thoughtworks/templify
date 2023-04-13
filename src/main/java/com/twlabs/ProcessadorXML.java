package com.twlabs;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ProcessadorXML bom cidadao
 */
public class ProcessadorXML implements HandlerFiles {


    public ProcessadorXML(Path path) throws RuntimeException {}


    private Document readFile(Path path) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document xml;

        try {
            builder = builderFactory.newDocumentBuilder();
            xml = builder.parse(path.toFile());
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("Erro ao ler o arquivo " + path.toString());
        }
        return xml;
    }



    @Override
    public NodeList find(String pathFile, String query) {
        Document xml = readFile(Paths.get(pathFile));
        XPath xpath = XPathFactory.newInstance().newXPath();

        NodeList nodes;
        try {
            nodes = (NodeList) xpath.evaluate(query, xml, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException("Was not found any nodes with: " + query);
        }
        return nodes;
    }


    public void replace(String path, String query, String newValue, String replaceValuePath) {
        // Load original content
        Document originalDocument = readFile(Paths.get(path));

        // Find and change value of Nodes
        NodeList findNodeList = find(path, query);
        boolean notFound = true;
        for (int i = 0; i < findNodeList.getLength(); i++) {
            Node findNode = findNodeList.item(i);

            // Iterate over the elements with the same name off the node
            NodeList originalNodes = originalDocument.getElementsByTagName(findNode.getNodeName());
            for (int j = 0; j < originalNodes.getLength(); j++) {
                Node originalNode = originalNodes.item(j);
                if (findNode.getNodeName().equals(originalNode.getNodeName())
                        && findNode.getTextContent().equals(originalNode.getTextContent())) {
                    notFound = false;
                }
            }

        }

        if (notFound) {
            throw new RuntimeException(
                    "It was not possible to make replace: " + query + " NOT FOUND");
        }
        // Making a copy
        saveChanges(originalDocument, replaceValuePath);
    }

    private void saveChanges(Document doc, String filePath) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc),
                    new StreamResult(new File(URI.create(filePath))));
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void replace(String filePath, Map<String, String> queryValueMap,
            String replacedValuesPath) {
        // TODO Auto-generated method stub

    }



}
