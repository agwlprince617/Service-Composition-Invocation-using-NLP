import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXML {
    public static void main(String[] args) {
        try {
            // Parse an XML file into a Document object
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("api_description.xml");

            // Extract data from the Document object using the right data structures
            NodeList apiNodes = doc.getElementsByTagName("api");
            HashMap<String, Api> apis = new HashMap<>();
            for (int i = 0; i < apiNodes.getLength(); i++) {
                Node apiNode = apiNodes.item(i);
                String id = apiNode.getAttributes().getNamedItem("id").getNodeValue();
                String description = null, url = null, input = null, output = null;
                NodeList childNodes = apiNode.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        String nodeName = childNode.getNodeName();
                        String nodeValue = childNode.getFirstChild().getNodeValue();
                        if (nodeName.equals("description")) {
                            description = nodeValue;
                        } else if (nodeName.equals("url")) {
                            url = nodeValue;
                        } else if (nodeName.equals("input")) {
                            input = nodeValue;
                        } else if (nodeName.equals("output")) {
                            output = nodeValue;
                        }
                    }
                }
                Api api = new Api(id, description, url, input, output);
                apis.put(id, api);
            }

            // Print out the extracted data
            for (Api api : apis.values()) {
                System.out.println("ID: " + api.getId());
                System.out.println("Description: " + api.getDescription());
                System.out.println("Url: " + api.getUrl());
                System.out.println("Input: " + api.getInput());
                System.out.println("Output: " + api.getOutput());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Api {
    private String id;
    private String description;
    private String url;
    private String input;
    private String output;

    public Api(String id, String description, String url, String input, String output) {
        this.id = id;
        this.description = description;
        this.url = url;
        this.input = input;
        this.output = output;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

}