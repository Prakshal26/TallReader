package com.readData.DataXML.services;

import com.readData.DataXML.Utility.LedgerProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.repositories.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
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

    public int processContent(File targetFile) throws IOException, SAXException, ParserConfigurationException {

        List<Ledger> ledgerList = ledgerProcessor.processLedger(utility.processDom(targetFile));
       return ledgerRepository.saveAll(ledgerList).size();
    }

    public Ledger findByName(String name) {
       return ledgerRepository.findByName(name);
    }
}
