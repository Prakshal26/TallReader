package com.readData.DataXML.services;

import com.readData.DataXML.Utility.TransactionProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.GroupMaster;
import com.readData.DataXML.models.Transaction;
import com.readData.DataXML.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionProcessor transactionProcessor;

    @Autowired
    Utility utility;

    public int processContent(File targetFile) throws IOException, SAXException, ParserConfigurationException {

        List<Transaction> transactionList = transactionProcessor.processTransaction(utility.processDom(targetFile));
        System.out.println(transactionList.size());
        return transactionRepository.saveAll(transactionList).size();
    }
}
