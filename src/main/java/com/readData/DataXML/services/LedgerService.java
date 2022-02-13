package com.readData.DataXML.services;

import com.readData.DataXML.Utility.LedgerProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.repositories.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<Ledger> ledgerList = ledgerProcessor.processLedger(utility.processAndGiveDoc(requestType));
        ledgerRepository.deleteAll();
       return ledgerRepository.saveAll(ledgerList).size();
    }

    public Ledger findByName(String name) {
       return ledgerRepository.findByName(name);
    }
}
