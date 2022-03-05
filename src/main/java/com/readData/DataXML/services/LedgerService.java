package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.contentProcessor.LedgerProcessor;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.repositories.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public int processContent(String requestType) throws Exception {

       List<Ledger> ledgerList = ledgerProcessor.processLedger(utility.processAndGiveDoc(requestType));

       List<String> updatedGUID = ledgerList.stream().map(Ledger::getGuid).collect(Collectors.toList());
       List<String> existingGUID = ledgerRepository.findAllGUID();

       List<String> removedIDs = existingGUID.stream().filter(e->!updatedGUID.contains(e))
               .collect(Collectors.toList());

      ledgerRepository.deleteByGuidIn(removedIDs);
       return ledgerRepository.saveAll(ledgerList).size();
    }

    public Ledger findByName(String name) {
       return ledgerRepository.findByName(name);
    }
}
