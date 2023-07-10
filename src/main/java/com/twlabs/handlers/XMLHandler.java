package com.twlabs.handlers;

import java.io.FileOutputStream;
import java.io.IOException;
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
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.interfaces.FileHandler;

/**
 * This class represents an XML file handler that implements the FileHandler interface and extends the FileHandlerOptions class.
 * It provides methods to handle XML files, such as reading, writing, and manipulating XML data.
 * 
 * Example usage:
 * 
 * <pre>
 * XMLHandler xmlHandler = new XMLHandler();
 * </pre>
 * 
 * Note: This class assumes that the xml files being handled are valid and well-formed.
 * 
 * @see FileHandlerKind
 * @see FileHandler
 */
public class XMLHandler extends FileHandlerKind implements FileHandler {


    private Document readFile(Path path) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document xml;

        try {
            builder = builderFactory.newDocumentBuilder();
            xml = builder.parse(path.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo " + path.toString(), e);
        }
        return xml;
    }



    @Override
    public Map<String, String> find(String pathFile, String query) throws FileHandlerException {
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
                    throw new FileHandlerException(
                            "We have same nodes paths with difrentes values. Adjust your Xpath. \n Query Error: "
                                    + query);
                } // if equals do nothing
            }
        } catch (XPathExpressionException e) {
            throw new FileHandlerException("Was not found any nodes with: " + query);
        }
        return nodeMap;
    }


    public void replace(String path, String query, String newValue) throws FileHandlerException {
        // Load original content
        Document originalDocument = readFile(Paths.get(path));
        XPath xpath = XPathFactory.newInstance().newXPath();
        // Find and change value of Nodes
        Map<String, String> findNodeMap = find(path, query);

        boolean notFound = true;
        for (Map.Entry<String, String> entryNode : findNodeMap.entrySet()) {


            NodeList originalNodes;
            try {
                originalNodes = (NodeList) xpath.evaluate(entryNode.getKey(), originalDocument,
                        XPathConstants.NODESET);

                // OriginalDocument.getElementsByTagName(entryNode.getKey());
                for (int j = 0; j < originalNodes.getLength(); j++) {
                    Node originalNode = originalNodes.item(j);
                    if (entryNode.getValue().equals(originalNode.getTextContent())) {
                        originalNode.setTextContent(newValue);
                        notFound = false;
                    }
                }

            } catch (XPathExpressionException e) {
                throw new FileHandlerException("Was not found any nodes with: " + query);
            }
        }
        if (notFound) {
            throw new FileHandlerException(
                    "It was not possible to make replace: " + query + " NOT FOUND");
        }
        // Making a copy

        saveChanges(originalDocument, path);
    }

    private void saveChanges(Document doc, String filePath) throws FileHandlerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;

        try {
            transformer = transformerFactory.newTransformer();
            FileOutputStream outputStream = new FileOutputStream(filePath);

            transformer.transform(new DOMSource(doc), new StreamResult(outputStream));

        } catch (TransformerConfigurationException e) {
            throw new FileHandlerException(
                    "Was not possible to make a instance of a transformer!!!");
        } catch (TransformerException e) {
            throw new FileHandlerException(
                    "It aws not possible to save the changes on " + filePath);
        } catch (IOException e) {
            throw new FileHandlerException("It was not possible do to colse Stream: " + filePath);
        }
    }


    @Override
    public void replace(String filePath, Map<String, String> queryValueMap)
            throws FileHandlerException {

        for (Map.Entry<String, String> entry : queryValueMap.entrySet()) {
            this.replace(filePath, entry.getKey(), entry.getValue());
        }
    }
}
