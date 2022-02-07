package com.readData.DataXML.Utility;

import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.models.BillAllocation;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.models.Transaction;
import com.readData.DataXML.services.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionProcessor {

    @Autowired
    Utility utility;

    @Autowired
    LedgerService ledgerService;

    private BillAllocation processBillAllocationList(Node node) {
        BillAllocation billAllocation = new BillAllocation();
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("NAME"))billAllocation.setNAME(child.getTextContent().trim());
            if(child.getNodeName().equals("AMOUNT"))billAllocation.setOPENINGBALANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("ISADVANCE"))billAllocation.setISADVANCE(child.getTextContent().trim());
            if(child.getNodeName().equals("BILLTYPE"))billAllocation.setBILLTYPE(child.getTextContent().trim());
        }
        if(billAllocation.getBILLDATE()==null) return null;
        return billAllocation;
    }

    private void processLegerAllocations(Node node) throws IOException, ParserConfigurationException, SAXException {

        NodeList nl = node.getChildNodes();
        String st ="";
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("LEDGERNAME")) st = child.getTextContent().trim();
        }

        if (!st.equals("")) {
           Ledger ledger =  ledgerService.findByName(st);
           if (ledger==null) {
               ledgerService.processContent(utility.processAndGiveFile(DataMapping.LEDGER));
           }
        } else {
            throw new RuntimeException("Empty Ledger name in Voucher Transaction");
        }

        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
//            if(child.getNodeName().equals("BILLALLOCATIONS.LIST")&&processBillAllocationList(child)!=null) {
//                ledger.getBillAllocationDetails().add(processBillAllocationList(child));
//                ledger.getBillAllocationDetails().forEach(e->e.setLedger(ledger));
//            }
        }

    }

    public List<Transaction> processTransaction(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("VOUCHER");
        List<Transaction> transactionList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node transactionNode = nodeList.item(i);
            NodeList transactionNodeChildNodes = transactionNode.getChildNodes();
            Transaction transaction = new Transaction();
            for (int j=0;j<transactionNodeChildNodes.getLength();j++) {
                Node n = transactionNodeChildNodes.item(j);
                if(n.getNodeName().equals("DATE")) transaction.setDATE(n.getTextContent().trim());
                if(n.getNodeName().equals("GUID")) transaction.setGUID(n.getTextContent().trim());
                if(n.getNodeName().equals("NARRATION")) transaction.setNARRATION(n.getTextContent().trim());
                if(n.getNodeName().equals("VOUCHERTYPENAME")) transaction.setVOUCHERTYPENAME(n.getTextContent().trim());
                if(n.getNodeName().equals("VOUCHERNUMBER")) transaction.setVOUCHERNUMBER(n.getTextContent().trim());
                if(n.getNodeName().equals("PARTYLEDGERNAME")) transaction.setPARTYLEDGERNAME(n.getTextContent().trim());

                if(n.getNodeName().equals("ALLLEDGERENTRIES.LIST")) {

                }
            }
            transactionList.add(transaction);
        }
        return transactionList;
    }

}

