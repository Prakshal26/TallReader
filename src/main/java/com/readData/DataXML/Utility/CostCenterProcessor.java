package com.readData.DataXML.Utility;

import com.readData.DataXML.models.CostCenter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class CostCenterProcessor {
    public List<CostCenter> processCostCenter(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("COSTCENTRE");
        List<CostCenter> costCenterList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node costCenterNode = nodeList.item(i);
            if(!costCenterNode.hasAttributes()) continue;
            NodeList costCenterNodeList = costCenterNode.getChildNodes();
            CostCenter costCenter = new CostCenter();
            costCenter.setNAME(costCenterNode.getAttributes().getNamedItem("NAME").getNodeValue());
            for (int j=0;j<costCenterNodeList.getLength();j++) {
                Node n = costCenterNodeList.item(j);
                if(n.getNodeName().equals("GUID")) costCenter.setGUID(n.getTextContent().trim());
                if(n.getNodeName().equals("PARENT")) costCenter.setPARENT(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) costCenter.setALTERID(n.getTextContent().trim());
            }
            costCenterList.add(costCenter);
        }
        return costCenterList;
    }
}
