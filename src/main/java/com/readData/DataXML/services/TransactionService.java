package com.readData.DataXML.services;

import com.readData.DataXML.Utility.TransactionProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.Transaction;
import com.readData.DataXML.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionProcessor transactionProcessor;

    @Autowired
    Utility utility;

    public int processContent(String requestType) throws Exception {

        List<Transaction> transactionList = transactionProcessor.processTransaction(utility.processAndGiveDoc(requestType));
        transactionRepository.deleteAll();
        return transactionRepository.saveAll(transactionList).size();
    }
}
