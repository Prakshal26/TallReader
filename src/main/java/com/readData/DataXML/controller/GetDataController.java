package com.readData.DataXML.controller;

import com.readData.DataXML.Utility.TallyRequest;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.services.GroupMasterService;
import com.readData.DataXML.services.LedgerService;
import com.readData.DataXML.services.TransactionService;
import com.readData.DataXML.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.InputStream;

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
    Utility utility;

    @GetMapping("/getData")
    public String getLedgers(@RequestParam("type")String requestType) throws Exception {

        File targetFile = utility.processAndGiveFile(requestType);

        int count =0;
        switch (requestType) {
            case DataMapping.LEDGER:
                count = ledgerService.processContent(targetFile);
                break;
            case DataMapping.VOUCHER:
                count = voucherService.processContent(targetFile);
                break;
            case DataMapping.GROUPMASTER:
                count = groupMasterService.processContent(targetFile);
                break;
            case DataMapping.VOUCHERTRANSACTION:
                count = transactionService.processContent(targetFile);
                break;
        }

        return "Inserted/Updated "+count+" "+requestType;
    }
}
