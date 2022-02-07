package com.readData.DataXML.commons;

import java.util.Map;

public class DataMapping {


    public static final String LEDGER = "Ledger";
    public static final String VOUCHER = "Voucher";
    public static final String GROUPMASTER = "GroupMaster";
    public static final String VOUCHERTRANSACTION = "Transaction";

    public static final Map<String,String> typeXMLMapper = Map.ofEntries(
            Map.entry(LEDGER,"LedMaster.xml"),
            Map.entry(VOUCHER,"VchTypMaster.xml"),
            Map.entry(GROUPMASTER,"GroupMaster.xml"),
            Map.entry(VOUCHERTRANSACTION,"Vchtran.xml")
    );
}
