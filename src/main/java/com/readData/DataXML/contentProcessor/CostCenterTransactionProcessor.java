package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.models.CostCenterTransaction;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.readData.DataXML.Utility.SharedContentParser.*;
import static com.readData.DataXML.Utility.SharedContentParser.haveTag;

@Component
public class CostCenterTransactionProcessor {

    public List<CostCenterTransaction> processCostCenterInTransaction(Node n) {

        List<CostCenterTransaction> costCenterTransactionList = new ArrayList<>();

        StringBuilder category= new StringBuilder();
        StringBuilder isDeemedPositive=new StringBuilder();
        nodesIterator(n.getChildNodes(),(n1)->{
            if(haveTag(n1,"CATEGORY")) category.append(processContent(n1));
            else if(haveTag(n1,"ISDEEMEDPOSITIVE")) isDeemedPositive.append(processContent(n1));
        });
        nodesIterator(n.getChildNodes(),(n1)->{
             if(haveTag(n1,"COSTCENTREALLOCATIONS.LIST")) {
                 CostCenterTransaction costCenterTransaction = new CostCenterTransaction();
                 costCenterTransaction.setCATEGORY(category.toString());
                 costCenterTransaction.setISDEEMEDPOSITIVE(isDeemedPositive.toString());
                nodesIterator(n1.getChildNodes(),(n2)->{
                    if(haveTag(n2,"NAME")) costCenterTransaction.setNAME(processContent(n2));
                    else if(haveTag(n2,"AMOUNT"))costCenterTransaction.setAMOUNT(processContent(n2));
                });
                costCenterTransactionList.add(costCenterTransaction);
            }
        });
        return costCenterTransactionList;
    }
}
