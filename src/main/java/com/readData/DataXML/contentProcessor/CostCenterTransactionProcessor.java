package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.models.CostCenterTransaction;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class CostCenterTransactionProcessor {

    public CostCenterTransaction processCostCenterInTransaction(Node n) {
        CostCenterTransaction costCenterTransaction = new CostCenterTransaction();
        for (int i=0;i<n.getChildNodes().getLength();i++) {
            Node c = n.getChildNodes().item(i);
            if(c.getNodeName().equals("CATEGORY")) costCenterTransaction.setCATEGORY(c.getTextContent().trim());
            if(c.getNodeName().equals("ISDEEMEDPOSITIVE")) costCenterTransaction.setISDEEMEDPOSITIVE(c.getTextContent().trim());
            if(c.getNodeName().equals("COSTCENTREALLOCATIONS.LIST")) {
                NodeList costCenterList = c.getChildNodes();
                for (int j=0;j<costCenterList.getLength();j++) {
                    Node costC = costCenterList.item(j);
                    if(costC.getNodeName().equals("NAME")) costCenterTransaction.setNAME(costC.getTextContent().trim());
                    if(costC.getNodeName().equals("AMOUNT")) costCenterTransaction.setAMOUNT(costC.getTextContent().trim());
                }
            }
        }
        return costCenterTransaction;
    }

    public List<CostCenterTransaction> processCostCenter(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("COSTCENTRE");
        List<CostCenterTransaction> costCenterTransactionList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node costCenterNode = nodeList.item(i);
            if(!costCenterNode.hasAttributes()) continue;
            NodeList costCenterNodeList = costCenterNode.getChildNodes();
            CostCenterTransaction costCenterTransaction = new CostCenterTransaction();
            costCenterTransaction.setNAME(costCenterNode.getAttributes().getNamedItem("NAME").getNodeValue());
            for (int j=0;j<costCenterNodeList.getLength();j++) {
                Node n = costCenterNodeList.item(j);
                if(n.getNodeName().equals("GUID")) costCenterTransaction.setGUID(n.getTextContent().trim());
                if(n.getNodeName().equals("PARENT")) costCenterTransaction.setPARENT(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) costCenterTransaction.setALTERID(n.getTextContent().trim());
            }
            costCenterTransactionList.add(costCenterTransaction);
        }
        return costCenterTransactionList;
    }
}
