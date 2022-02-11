package com.readData.DataXML.Utility;

import com.readData.DataXML.models.BillAllocation;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class BillAllocationProcessor {

    public BillAllocation processBillAllocationList(Node node) {
        BillAllocation billAllocation = new BillAllocation();
        billAllocation.setBILLTYPE("Opening");
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("BILLDATE"))billAllocation.setBILLDATE(child.getTextContent().trim());
            if(child.getNodeName().equals("NAME"))billAllocation.setNAME(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLCREDITPERIOD"))billAllocation.setBILLCREDITPERIOD(child.getTextContent().trim());
            if(child.getNodeName().equals("ISADVANCE"))billAllocation.setISADVANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("AMOUNT"))billAllocation.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("OPENINGBALANCE"))billAllocation.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLTYPE"))billAllocation.setBILLTYPE(child.getTextContent().trim());
        }
        if(billAllocation.getNAME()==null) return null;
        return billAllocation;
    }

}
