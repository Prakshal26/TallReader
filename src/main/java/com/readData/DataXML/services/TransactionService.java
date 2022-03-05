package com.readData.DataXML.services;

import com.readData.DataXML.contentProcessor.TransactionProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.dao.TransactionAlterAndGuid;
import com.readData.DataXML.models.Transaction;
import com.readData.DataXML.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionProcessor transactionProcessor;

    @Autowired
    Utility utility;

    @Transactional
    public int processContent(String requestType) throws Exception {

        Document doc = utility.processAndGiveDoc(requestType);

        List<TransactionAlterAndGuid> updatedAlterAndGuids = transactionProcessor.getAlterAndGuids(doc);

        List<TransactionAlterAndGuid> existingAlterAndGuids = transactionRepository.getAlterGuidIs();

        List<String> eligibleForDeletion = Optional.ofNullable(existingAlterAndGuids)
                .orElseGet(Collections::emptyList).stream()
                .map(TransactionAlterAndGuid::getGuid).filter(s ->{
                    if (s==null) return false;
                    else return updatedAlterAndGuids
                        .stream().noneMatch(e2->{
                            if(e2.getGuid()==null) return false;
                            return e2.getGuid()
                                .equals(s);});}).collect(Collectors.toList());


        List<String> eligibleForUpdate =Optional.ofNullable(updatedAlterAndGuids)
                .orElseGet(Collections::emptyList).stream()
                .filter(e->existingAlterAndGuids.stream()
                        .noneMatch(e::equals))
                .map(TransactionAlterAndGuid::getGuid)
                .collect(Collectors.toList());


        List<Transaction> transactionList = transactionProcessor.processTransaction(doc);
        transactionRepository.deleteByGuidIn(eligibleForDeletion);
        return transactionRepository.saveAll(transactionList).size();
    }
}
