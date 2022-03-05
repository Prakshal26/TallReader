package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.Utility.SharedContentParser;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.BillAllocationMaster;
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

import static com.readData.DataXML.Utility.SharedContentParser.*;

@Component
public class LedgerProcessor {

    @Autowired
    Utility utility;

    @Autowired
    BillAllocationMasterProcessor billAllocationMasterProcessor;

    private PaymentDetails processPayment(Node n) {

        PaymentDetails paymentDetails = new PaymentDetails();
        nodesIterator(n.getChildNodes(),(n1)->{
            if(haveTag(n1,"IFSCODE"))paymentDetails.setIFSCODE(processContent(n1));
            else if(haveTag(n1,"BANKNAME"))paymentDetails.setBANKNAME(processContent(n1));
            else if(haveTag(n1,"ACCOUNTNUMBER"))paymentDetails.setACCOUNTNUMBER(processContent(n1));
            else if(haveTag(n1,"PAYMENTFAVOURING"))paymentDetails.setPAYMENTFAVOURING(processContent(n1));
            else if(haveTag(n1,"TRANSACTIONNAME"))paymentDetails.setTRANSACTIONNAME(processContent(n1));
            else if(haveTag(n1,"SETASDEFAULT"))paymentDetails.setSETASDEFAULT(processContent(n1));
            else if(haveTag(n1,"DEFAULTTRANSACTIONTYPE"))paymentDetails.setDEFAULTTRANSACTIONTYPE(processContent(n1));
        });

        if(paymentDetails.getACCOUNTNUMBER()==null) return null;
        return paymentDetails;
    }

    public List<Ledger> processLedger(Document doc) throws TransformerException {

        NodeList nodeList = doc.getElementsByTagName("LEDGER");
        List<Ledger> ledgerList = new ArrayList<>();
        nodesIterator(nodeList,(n1)->{
            if(n1.hasAttributes()) {
                Ledger ledger = new Ledger();
                ledger.setName(SharedContentParser.getAttribute(n1,"NAME"));
                nodesIterator(n1.getChildNodes(),(n2)->{
                    if(haveTag(n2,"GUID")) ledger.setGuid(processContent(n2));
                    else if(haveTag(n2,"PARENT")) ledger.setPARENT(processContent(n2));
                    else if(haveTag(n2,"ADDRESS.LIST")) ledger.getADDRESS().add(utility.processContent(n2,"ADDRESS"));
                    else if(haveTag(n2,"MAILINGNAME.LIST")) ledger.getMAILINGNAME().add(utility.processContent(n2,"MAILINGNAME"));
                    else if(haveTag(n2,"EMAIL")) ledger.setEMAIL(processContent(n2));
                    else if(haveTag(n2,"PINCODE")) ledger.setPINCODE(processContent(n2));
                    else if(haveTag(n2,"INCOMETAXNUMBER")) ledger.setINCOMETAXNUMBER(processContent(n2));
                    else if(haveTag(n2,"COUNTRYNAME")) ledger.setCOUNTRYNAME(processContent(n2));
                    else if(haveTag(n2,"GSTREGISTRATIONTYPE")) ledger.setGSTREGISTRATIONTYPE(processContent(n2));
                    else if(haveTag(n2,"CREATEDBY")) ledger.setCREATEDBY(processContent(n2));
                    else if(haveTag(n2,"ALTEREDBY")) ledger.setALTEREDBY(processContent(n2));
                    else if(haveTag(n2,"BILLCREDITPERIOD")) ledger.setBILLCREDITPERIOD(processContent(n2));
                    else if(haveTag(n2,"EMAILCC")) ledger.setEMAILCC(processContent(n2));
                    else if(haveTag(n2,"LEDGERPHONE")) ledger.setLEDGERPHONE(processContent(n2));
                    else if(haveTag(n2,"LEDGERCONTACT")) ledger.setLEDGERCONTACT(processContent(n2));
                    else if(haveTag(n2,"LEDGERMOBILE")) ledger.setLEDGERMOBILE(processContent(n2));
                    else if(haveTag(n2,"PARTYGSTIN")) ledger.setPARTYGSTIN(processContent(n2));
                    else if(haveTag(n2,"LEDSTATENAME")) ledger.setLEDSTATENAME(processContent(n2));
                    else if(haveTag(n2,"ISBILLWISEON")) ledger.setISBILLWISEON(processContent(n2));
                    else if(haveTag(n2,"ISCOSTCENTRESON")) ledger.setISCOSTCENTRESON(processContent(n2));
                    else if(haveTag(n2,"ALTERID")) ledger.setALTERID(processContent(n2));
                    else if(haveTag(n2,"OPENINGBALANCE")) ledger.setOPENINGBALANCE(processContent(n2));
                    else if(haveTag(n2,"PAYMENTDETAILS.LIST")) {
                        PaymentDetails paymentDetails = processPayment(n2);
                        if (paymentDetails!=null) {
                            ledger.getPaymentDetails().add(paymentDetails);
                            ledger.getPaymentDetails().forEach(e -> e.setLedger(ledger));
                        }
                    }
                    else if(haveTag(n2,"BILLALLOCATIONS.LIST")) {
                        BillAllocationMaster billAllocationMaster =
                                billAllocationMasterProcessor.processBillAllocationList(n2);
                        if(billAllocationMaster!=null) {
                            ledger.getBillAllocationMasterDetails().add(billAllocationMaster);
                            ledger.getBillAllocationMasterDetails().forEach(e->e.setLedger(ledger));
                        }
                    }
                });
                ledgerList.add(ledger);
            }
        });
        return ledgerList;
    }
}
