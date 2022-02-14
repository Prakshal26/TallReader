package com.readData.DataXML.Utility;

import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.models.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LedgerProcessor {

    @Autowired
    Utility utility;

    @Autowired
    BillAllocationProcessor billAllocationProcessor;

    private PaymentDetails processPayment(Node node) {
        PaymentDetails paymentDetails = new PaymentDetails();
        NodeList nl = node.getChildNodes();
        for(int i=0;i< nl.getLength();i++) {
            Node child=nl.item(i);
            if(child.getNodeName().equals("IFSCODE"))paymentDetails.setIFSCODE(child.getTextContent().trim());
            if(child.getNodeName().equals("BANKNAME"))paymentDetails.setBANKNAME(child.getTextContent().trim());
            if(child.getNodeName().equals("ACCOUNTNUMBER"))paymentDetails.setACCOUNTNUMBER(child.getTextContent().trim());
            if(child.getNodeName().equals("PAYMENTFAVOURING"))paymentDetails.setPAYMENTFAVOURING(child.getTextContent().trim());
            if(child.getNodeName().equals("TRANSACTIONNAME"))paymentDetails.setTRANSACTIONNAME(child.getTextContent().trim());
            if(child.getNodeName().equals("SETASDEFAULT"))paymentDetails.setSETASDEFAULT(child.getTextContent().trim());
            if(child.getNodeName().equals("DEFAULTTRANSACTIONTYPE"))paymentDetails.setDEFAULTTRANSACTIONTYPE(child.getTextContent().trim());
        }
        if(paymentDetails.getACCOUNTNUMBER()==null) return null;
        return paymentDetails;
    }

    public List<Ledger> processLedger(Document doc) throws TransformerException {

        NodeList nodeList = doc.getElementsByTagName("LEDGER");
        List<Ledger> ledgerList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node ledgerNode = nodeList.item(i);
            if(!ledgerNode.hasAttributes()) continue;
            NodeList ledgerChildList = ledgerNode.getChildNodes();
            Ledger ledger = new Ledger();
            ledger.setName(ledgerNode.getAttributes().getNamedItem("NAME").getNodeValue());
            for (int j=0;j<ledgerChildList.getLength();j++) {
                Node n = ledgerChildList.item(j);
                if(n.getNodeName().equals("GUID")) ledger.setGUID(n.getTextContent().trim());
                if(n.getNodeName().equals("PARENT")) ledger.setPARENT(n.getTextContent().trim());
                if(n.getNodeName().equals("ADDRESS.LIST")) ledger.getADDRESS().add(utility.processContent(n,"ADDRESS"));
                if(n.getNodeName().equals("MAILINGNAME.LIST")) ledger.getMAILINGNAME().add(utility.processContent(n,"MAILINGNAME"));
                if(n.getNodeName().equals("EMAIL")) ledger.setEMAIL(n.getTextContent().trim());
                if(n.getNodeName().equals("PINCODE")) ledger.setPINCODE(n.getTextContent().trim());
                if(n.getNodeName().equals("INCOMETAXNUMBER")) ledger.setINCOMETAXNUMBER(n.getTextContent().trim());
                if(n.getNodeName().equals("COUNTRYNAME")) ledger.setCOUNTRYNAME(n.getTextContent().trim());
                if(n.getNodeName().equals("GSTREGISTRATIONTYPE")) ledger.setGSTREGISTRATIONTYPE(n.getTextContent().trim());
                if(n.getNodeName().equals("CREATEDBY")) ledger.setCREATEDBY(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTEREDBY")) ledger.setALTEREDBY(n.getTextContent().trim());
                if(n.getNodeName().equals("BILLCREDITPERIOD")) ledger.setBILLCREDITPERIOD(n.getTextContent().trim());
                if(n.getNodeName().equals("EMAILCC")) ledger.setEMAILCC(n.getTextContent().trim());
                if(n.getNodeName().equals("LEDGERPHONE")) ledger.setLEDGERPHONE(n.getTextContent().trim());
                if(n.getNodeName().equals("LEDGERCONTACT")) ledger.setLEDGERCONTACT(n.getTextContent().trim());
                if(n.getNodeName().equals("LEDGERMOBILE")) ledger.setLEDGERMOBILE(n.getTextContent().trim());
                if(n.getNodeName().equals("PARTYGSTIN")) ledger.setPARTYGSTIN(n.getTextContent().trim());
                if(n.getNodeName().equals("LEDSTATENAME")) ledger.setLEDSTATENAME(n.getTextContent().trim());
                if(n.getNodeName().equals("ISBILLWISEON")) ledger.setISBILLWISEON(n.getTextContent().trim());
                if(n.getNodeName().equals("ISCOSTCENTRESON")) ledger.setISCOSTCENTRESON(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) ledger.setALTERID(n.getTextContent().trim());
                if(n.getNodeName().equals("OPENINGBALANCE")) ledger.setOPENINGBALANCE(n.getTextContent().trim());
              //  if(n.getNodeName().equals("NAME.LIST")) ledger.getMAILINGNAME().add(utility.processContent(n,"MAILINGNAME"));
                if(n.getNodeName().equals("PAYMENTDETAILS.LIST")&&processPayment(n)!=null) {
                    ledger.getPaymentDetails().add(processPayment(n));
                    ledger.getPaymentDetails().forEach(e->e.setLedger(ledger));
                }
                if(n.getNodeName().equals("BILLALLOCATIONS.LIST")&&billAllocationProcessor.processBillAllocationList(n)!=null) {
                    ledger.getBillAllocationDetails().add(billAllocationProcessor.processBillAllocationList(n));
                    ledger.getBillAllocationDetails().forEach(e->e.setLedger(ledger));
                }
            }
            ledgerList.add(ledger);
        }
        return ledgerList;
    }
}
