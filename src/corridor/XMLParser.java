package corridor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

class XMLParser {
    private Document doc;
    XMLParser() {
        try {
            File fXmlFile = new File("format.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Format parse(String corridorID) {
        int headerSize = 0;
        String header = null;
        String[] tableHeaders = null;
        Node nNode = findNodeByID(corridorID);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            headerSize = Integer.parseInt(
                    eElement.getElementsByTagName("header_size").item(0).getTextContent());
            header = eElement.getElementsByTagName("header").item(0).getTextContent();

            NodeList childrenList = eElement.getElementsByTagName("table_header");
            tableHeaders = new String[childrenList.getLength()];
            for (int i = 0; i < childrenList.getLength(); i++) {
                tableHeaders[i] = childrenList.item(i).getTextContent();
            }
        }
        return new Format(headerSize, header, tableHeaders);
    }
    private Node findNodeByID(String corridorID) {
        NodeList nList = doc.getElementsByTagName("corridor");
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);
            if (nNode.getAttributes().item(0).getTextContent().equals(corridorID)) {
                return nNode;
            }
        }
        return null;
    }
    String[] getCorridors() {
        String[] res = null;
        try {
            NodeList nList = doc.getElementsByTagName("corridor");
            res = new String[nList.getLength()];
            for (int index = 0; index < nList.getLength(); index++) {
                res[index] = nList.item(index).getAttributes().item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
