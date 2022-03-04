package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.models.BillAllocationTransaction;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class BillAllocationTransactionProcessor {

    public BillAllocationTransaction processBillAllocationList(Node node) {
        BillAllocationTransaction billAllocationTransaction = new BillAllocationTransaction();
        billAllocationTransaction.setBILLTYPE("Opening");
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("BILLDATE")) billAllocationTransaction.setBILLDATE(child.getTextContent().trim());
            if(child.getNodeName().equals("NAME")) billAllocationTransaction.setNAME(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLCREDITPERIOD")) billAllocationTransaction.setBILLCREDITPERIOD(child.getTextContent().trim());
            if(child.getNodeName().equals("ISADVANCE")) billAllocationTransaction.setISADVANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("AMOUNT")) billAllocationTransaction.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("OPENINGBALANCE")) billAllocationTransaction.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLTYPE")) billAllocationTransaction.setBILLTYPE(child.getTextContent().trim());
        }
        if(billAllocationTransaction.getNAME()==null) return null;
        return billAllocationTransaction;
    }

}
