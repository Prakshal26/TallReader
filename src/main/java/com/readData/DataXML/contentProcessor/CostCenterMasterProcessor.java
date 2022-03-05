package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.models.CostCenterMaster;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class CostCenterMasterProcessor {

    public List<CostCenterMaster> processCostCenter(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("COSTCENTRE");
        List<CostCenterMaster> costCenterMasterList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node costCenterNode = nodeList.item(i);
            if(!costCenterNode.hasAttributes()) continue;
            NodeList costCenterNodeList = costCenterNode.getChildNodes();
            CostCenterMaster costCenterMaster = new CostCenterMaster();
            costCenterMaster.setNAME(costCenterNode.getAttributes().getNamedItem("NAME").getNodeValue());
            for (int j=0;j<costCenterNodeList.getLength();j++) {
                Node n = costCenterNodeList.item(j);
                if(n.getNodeName().equals("GUID")) costCenterMaster.setGuid(n.getTextContent().trim());
                if(n.getNodeName().equals("PARENT")) costCenterMaster.setPARENT(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) costCenterMaster.setALTERID(n.getTextContent().trim());
            }
            costCenterMasterList.add(costCenterMaster);
        }
        return costCenterMasterList;
    }
}
