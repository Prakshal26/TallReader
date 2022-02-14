package com.readData.DataXML.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class Utility {

    @Autowired
    TallyRequest tallyRequest;

    private Document processDom(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(stream));
        doc.getDocumentElement().normalize();
        
        return doc;
    }
    public String processContent(Node node, String tag) {
        NodeList nl = node.getChildNodes();
        StringBuilder address = new StringBuilder();
        for(int i=0;i< nl.getLength();i++) {
            if(nl.item(i).getNodeName().equals(tag))address.append(nl.item(i).getTextContent().trim()).append(" ");
        }
        return address.toString().trim();
    }

    public Document processAndGiveDoc(String requestType) throws Exception {

        InputStream response = tallyRequest.makeRequest(requestType);
        String text = new String(response.readAllBytes(), StandardCharsets.UTF_8);
        text = "<?xml version=\"1.1\"?>"+text;

        InputStream inputStream = new ByteArrayInputStream(text.getBytes());

//        File targetFile = new File("response.xml");
//        FileUtils.copyInputStreamToFile(response, targetFile);
//        response.close();
        //return  response;
        return this.processDom(inputStream);
    }
}
