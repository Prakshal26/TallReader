package com.readData.DataXML.commons;

import java.util.Map;

public class DataMapping {


    public static final String LEDGER = "Ledger";
    public static final String VOUCHER = "Voucher";
    public static final String GROUPMASTER = "GroupMaster";
    public static final String VOUCHERTRANSACTION = "Transaction";
    public static final String COSTCENTER = "CostCenter";
    public static final String VOUCHERTRANSACTIONALTERGUID = "TransactionAlterGuid";

    public static final Map<String,String> typeXMLMapper = Map.ofEntries(
            Map.entry(LEDGER,"Req_LedgerMaster.xml"),
            Map.entry(VOUCHER,"Req_VoucherMaster.xml"),
            Map.entry(GROUPMASTER,"Req_GroupMaster.xml"),
            Map.entry(VOUCHERTRANSACTION,"Req_Transaction.xml"),
            Map.entry(COSTCENTER,"Req_CostCenterMaster.xml"),
            Map.entry(VOUCHERTRANSACTIONALTERGUID,"Req_Transaction_Alter_Guid.xml")

    );
}
