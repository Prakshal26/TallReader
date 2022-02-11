package com.readData.DataXML.controller;

import com.readData.DataXML.Utility.TallyRequest;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetDataController {

    @Autowired
    TallyRequest tallyRequest;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    GroupMasterService groupMasterService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CostCenterService costCenterService;

    @Autowired
    Utility utility;

    @GetMapping("/getData")
    public String getLedgers(@RequestParam("type")String requestType) throws Exception {

        int count =0;
        switch (requestType) {
            case DataMapping.LEDGER:
                count = ledgerService.processContent(requestType);
                break;
            case DataMapping.VOUCHER:
                count = voucherService.processContent(requestType);
                break;
            case DataMapping.GROUPMASTER:
                count = groupMasterService.processContent(requestType);
                break;
            case DataMapping.VOUCHERTRANSACTION:
                count = transactionService.processContent(requestType);
                break;
            case DataMapping.COSTCENTER:
                count = costCenterService.processContent(requestType);
                break;
        }

        return "Inserted/Updated "+count+" "+requestType;
    }
}
