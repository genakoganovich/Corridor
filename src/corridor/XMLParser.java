package corridor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

class XMLParser {
    public static final String HEADER_TAG = "header";
    public static final String HEADER_SIZE_TAG = "header_size";
    public static final String CORRIDOR_TAG = "corridor";
    public static final String TABLE_HEADER_TAG = "table_header";
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
        Node nNode = findNodeByID(corridorID, XMLParser.CORRIDOR_TAG);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            headerSize = Integer.parseInt(getTextContentByTagName(eElement, XMLParser.HEADER_SIZE_TAG));
            header = getHeader(getAllTextContentByTagName(eElement, XMLParser.HEADER_TAG));
            tableHeaders = getAllTextContentByTagName(eElement, XMLParser.TABLE_HEADER_TAG);
        }
        return new Format(headerSize, header, tableHeaders);
    }
    private String getHeader(String[] allTextContent) {
        StringBuilder sb = new StringBuilder();
        for (int i  = 0; i < allTextContent.length - 1; i++) {
            sb.append(allTextContent[i]).append(System.lineSeparator());
        }
        sb.append(allTextContent[allTextContent.length - 1]);
        return sb.toString();
    }
    private String getTextContentByTagName(Element eElement, String tagName) {
        return eElement.getElementsByTagName(tagName).item(0).getTextContent();
    }
    private String[] getAllTextContentByTagName(Element eElement, String tagName) {
        NodeList nodeList = getNodeListByTagName(eElement, tagName);
        String[] res = new String[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            res[i] = nodeList.item(i).getTextContent();
        }
        return res;
    }
    private NodeList getNodeListByTagName(Element eElement, String tagName) {
        return eElement.getElementsByTagName(tagName);
    }
    private Node findNodeByID(String corridorID, String tagName) {
        NodeList nList = doc.getElementsByTagName(tagName);
        for (int index = 0; index < nList.getLength(); index++) {
            Node nNode = nList.item(index);
            if (nNode.getAttributes().item(0).getTextContent().equals(corridorID)) {
                return nNode;
            }
        }
        return null;
    }
    String[] getFormats(String format) {
        String[] res = null;
        try {
            NodeList nList = doc.getElementsByTagName(format);
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
