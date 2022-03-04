package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.contentProcessor.LedgerProcessor;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.repositories.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;


import java.util.List;

@Service
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    @Autowired
    LedgerProcessor ledgerProcessor;

    @Autowired
    Utility utility;

    @Autowired
    public LedgerService(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    public int processContent(String requestType) throws Exception {
       Document doc = utility.processAndGiveDoc(requestType);

       List<Ledger> ledgerList = ledgerProcessor.processLedger(doc);
       return ledgerRepository.saveAll(ledgerList).size();
    }

    public Ledger findByName(String name) {
       return ledgerRepository.findByName(name);
    }
}
