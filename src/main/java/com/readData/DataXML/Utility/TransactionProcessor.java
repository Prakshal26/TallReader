package com.readData.DataXML.Utility;

import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.exceptionManager.CustomException;
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

    @Autowired
    BillAllocationProcessor billAllocationProcessor;


    private Ledger getLedger(Node node) throws Exception {

        NodeList nl = node.getChildNodes();
        String st ="";
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("LEDGERNAME")) st = child.getTextContent().trim();
        }
        if (!st.equals("")) {
            Ledger ledger =  ledgerService.findByName(st);
            if (ledger==null) {
                ledgerService.processContent(DataMapping.LEDGER);
                ledger = ledgerService.findByName(st);
            }
            if(ledger==null) throw new CustomException("Ledger is Not Available"); else return ledger;
        } else {
            throw new CustomException("Empty Ledger name in Voucher Transaction");
        }
    }

    private List<BillAllocation> processLegerAllocations(Node node) throws IOException, ParserConfigurationException, SAXException {

        List<BillAllocation> billAllocationList = new ArrayList<>();
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("BILLALLOCATIONS.LIST")) {
                BillAllocation billAllocation = billAllocationProcessor.processBillAllocationList(child);
                if (billAllocation!=null) {
                    billAllocationList.add(billAllocation);
                }
            }
        }
        if(billAllocationList.size()>0) return billAllocationList; else return null;
    }

    public List<Transaction> processTransaction(Document doc) throws Exception {
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
                    Ledger ledger = getLedger(n);
                    List<BillAllocation> billAllocationList = processLegerAllocations(n);
                    if(billAllocationList!=null) {
                        billAllocationList.forEach(e->e.setLedger(ledger));
                        billAllocationList.forEach(e->e.setTransaction(transaction));
                        transaction.getBillAllocation().addAll(billAllocationList);
                    }
                }
            }
            transactionList.add(transaction);
        }
        return transactionList;
    }

}

