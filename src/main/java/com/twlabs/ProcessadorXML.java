package com.twlabs;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
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
    public Map<String, String> find(String pathFile, String query) {
        Document xml = readFile(Paths.get(pathFile));
        XPath xpath = XPathFactory.newInstance().newXPath();

        Map<String, String> nodeMap = new HashMap<>();
        NodeList nodes;
        try {
            nodes = (NodeList) xpath.evaluate(query, xml, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (nodeMap.get(query) == null) {
                    nodeMap.put(query, node.getTextContent());
                } else if (!(nodeMap.get(query).equals(node.getTextContent()))) {
                    throw new RuntimeException(
                            "We have same nodes paths with difrentes values. Adjust your Xpath. \n Query Error: "
                                    + query);
                } // if equals do nothing
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new RuntimeException("Was not found any nodes with: " + query);
        }
        return nodeMap;
    }


    public void replace(String path, String query, String newValue, String replaceValuePath) {
        // Load original content
        Document originalDocument = readFile(Paths.get(path));
        XPath xpath = XPathFactory.newInstance().newXPath();

        // Find and change value of Nodes
        Map<String, String> findNodeMap = find(path, query);

        System.out.println("Query replace: " + query);
        boolean notFound = true;
        for (Map.Entry<String, String> entryNode : findNodeMap.entrySet()) {


            NodeList originalNodes;
            try {
                originalNodes = (NodeList) xpath.evaluate(entryNode.getKey(), originalDocument,
                        XPathConstants.NODESET);
                // OriginalDocument.getElementsByTagName(entryNode.getKey());
                for (int j = 0; j < originalNodes.getLength(); j++) {
                    Node originalNode = originalNodes.item(j);
                    System.out.println("entryNode: " + entryNode.getKey() + " --- "
                            + originalNode.getNodeName());
                    if (entryNode.getValue().equals(originalNode.getTextContent())) {
                        originalNode.setTextContent("${{" + newValue + "}}");
                        notFound = false;
                    }
                }



            } catch (XPathExpressionException e) {
                e.printStackTrace();
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



        boolean isFirst = true;
        Iterator<String> iterator = queryValueMap.keySet().iterator();

        while (iterator.hasNext()) {
            String query = iterator.next();
            if (!isFirst) {
                replace(URI.create(replacedValuesPath).getPath(), query, queryValueMap.get(query),
                        replacedValuesPath);


            } else {
                replace(filePath, query, queryValueMap.get(query), replacedValuesPath);
                isFirst = false;
            }

        }


    }



}
