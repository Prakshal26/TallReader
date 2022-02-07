package com.readData.DataXML.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Component
public class Utility {

    @Autowired
    TallyRequest tallyRequest;

    public Document processDom(File targetFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(targetFile);
        doc.getDocumentElement().normalize();
        return doc;
    }
    public String processContent(Node node, String tag) {
        NodeList nl = node.getChildNodes();
        StringBuilder address = new StringBuilder();
        for(int i=0;i< nl.getLength();i++) {
            if(nl.item(i).getNodeName().equals(tag))address.append(nl.item(0).getTextContent().trim());
        }
        return address.toString();
    }

    public File processAndGiveFile(String requestType) throws IOException {
        // InputStream response = tallyRequest.makeRequest(requestType);

        File targetFile = new File(tallyRequest.createRequest(requestType));
        //  FileUtils.copyInputStreamToFile(response, targetFile);
        // response.close();
        return  targetFile;
    }
}
