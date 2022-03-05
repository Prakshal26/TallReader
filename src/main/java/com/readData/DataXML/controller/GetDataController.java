package com.readData.DataXML.controller;

import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.exceptionManager.CustomException;
import com.readData.DataXML.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetDataController {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    GroupMasterService groupMasterService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CostCenterMasterService costCenterMasterService;

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
                count = costCenterMasterService.processContent(requestType);
                break;
            default:
                throw new CustomException("Invalid Query Type");
        }

        return "Inserted/Updated "+count+" "+requestType;
    }
}
