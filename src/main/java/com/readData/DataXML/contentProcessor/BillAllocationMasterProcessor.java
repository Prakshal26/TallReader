package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.models.BillAllocationMaster;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class BillAllocationMasterProcessor {

    public BillAllocationMaster processBillAllocationList(Node node) {
        BillAllocationMaster billAllocationMaster = new BillAllocationMaster();
        billAllocationMaster.setBILLTYPE("Opening");
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("BILLDATE")) billAllocationMaster.setBILLDATE(child.getTextContent().trim());
            if(child.getNodeName().equals("NAME")) billAllocationMaster.setNAME(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLCREDITPERIOD")) billAllocationMaster.setBILLCREDITPERIOD(child.getTextContent().trim());
            if(child.getNodeName().equals("ISADVANCE")) billAllocationMaster.setISADVANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("AMOUNT")) billAllocationMaster.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("OPENINGBALANCE")) billAllocationMaster.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLTYPE")) billAllocationMaster.setBILLTYPE(child.getTextContent().trim());
        }
        if(billAllocationMaster.getNAME()==null) return null;
        return billAllocationMaster;
    }
}
