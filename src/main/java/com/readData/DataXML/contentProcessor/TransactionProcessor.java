package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.dao.TransactionAlterAndGuid;
import com.readData.DataXML.exceptionManager.CustomException;
import com.readData.DataXML.models.*;
import com.readData.DataXML.services.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static com.readData.DataXML.Utility.SharedContentParser.*;

@Component
public class TransactionProcessor {

    @Autowired
    Utility utility;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    BillAllocationTransactionProcessor billAllocationTransactionProcessor;

    @Autowired
    CostCenterTransactionProcessor costCenterTransactionProcessor;


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

    private TransactionLedger processLegerAllocations(Node node) throws Exception {

        Ledger ledger = getLedger(node);
        TransactionLedger transactionLedger=new TransactionLedger();
        List<BillAllocationTransaction> billAllocationTransactionList = new ArrayList<>();
        List<CostCenterTransaction> costCenterTransactionList = new ArrayList<>();
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("BILLALLOCATIONS.LIST")) {
                BillAllocationTransaction billAllocationTransaction = billAllocationTransactionProcessor.processBillAllocationList(child);
                if (billAllocationTransaction !=null) {
                    billAllocationTransaction.setTransactionLedger(transactionLedger);
                    billAllocationTransactionList.add(billAllocationTransaction);
                }
            }
            if(child.getNodeName().equals("CATEGORYALLOCATIONS.LIST")) {
                costCenterTransactionList = costCenterTransactionProcessor.processCostCenterInTransaction(child);
                if (costCenterTransactionList!=null && costCenterTransactionList.size()>0) {
                    costCenterTransactionList.forEach(e->{
                        e.setTransactionLedger(transactionLedger);
                    });
                }
            }
            if(child.getNodeName().equals("AMOUNT")) transactionLedger.setAMOUNT(child.getTextContent().trim());
            if(child.getNodeName().equals("ISDEEMEDPOSITIVE")) transactionLedger.setISDEEMEDPOSITIVE(child.getTextContent().trim());

        }
        transactionLedger.setBillAllocationTransactionList(billAllocationTransactionList);
        transactionLedger.setCostCenterTransactionList(costCenterTransactionList);
        transactionLedger.setLedger(ledger);
        return transactionLedger;
    }

    public List<Transaction> processTransaction(Document doc) throws Exception {
        NodeList nodeList = doc.getElementsByTagName("VOUCHER");
        List<Transaction> transactionList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node transactionNode = nodeList.item(i);
            NodeList transactionNodeChildNodes = transactionNode.getChildNodes();
            Transaction transaction = new Transaction();
            List<TransactionLedger> transactionLedgers = new ArrayList<>();
            transaction.setTransactionLedgerList(transactionLedgers);
            for (int j=0;j<transactionNodeChildNodes.getLength();j++) {
                Node n = transactionNodeChildNodes.item(j);
                if(n.getNodeName().equals("DATE")) transaction.setDATE(n.getTextContent().trim());
                if(n.getNodeName().equals("GUID")) transaction.setGuid(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) transaction.setAlterId(n.getTextContent().trim());
                if(n.getNodeName().equals("NARRATION")) transaction.setNARRATION(n.getTextContent().trim());
                if(n.getNodeName().equals("VOUCHERTYPENAME")) transaction.setVOUCHERTYPENAME(n.getTextContent().trim());
                if(n.getNodeName().equals("VOUCHERNUMBER")) transaction.setVOUCHERNUMBER(n.getTextContent().trim());
                if(n.getNodeName().equals("PARTYLEDGERNAME")) transaction.setPARTYLEDGERNAME(n.getTextContent().trim());

                if(n.getNodeName().equals("ALLLEDGERENTRIES.LIST")) {
                    transactionLedgers.add(processLegerAllocations(n));
                    transactionLedgers.forEach(e->e.setTransaction(transaction));
                }
            }
            transactionList.add(transaction);
        }
        return transactionList;
    }

    public List<TransactionAlterAndGuid> getAlterAndGuids(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("VOUCHER");
        List<TransactionAlterAndGuid> transactionList = new ArrayList<>();

        nodesIterator(nodeList,(n)->{
            TransactionAlterAndGuid alterAndGuid = new TransactionAlterAndGuid();
            nodesIterator(n.getChildNodes(),(n2)->{
            if(haveTag(n2,"GUID")) alterAndGuid.setGuid(processContent(n2));
            else if(haveTag(n2,"ALTERID")) alterAndGuid.setAlterId(processContent(n2));
            });
            transactionList.add(alterAndGuid);
        });
        return transactionList;
    }
}

