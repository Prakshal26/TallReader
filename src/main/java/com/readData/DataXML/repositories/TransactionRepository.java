package com.readData.DataXML.repositories;

import com.readData.DataXML.dao.TransactionAlterAndGuid;
import com.readData.DataXML.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT new com.readData.DataXML.dao.TransactionAlterAndGuid(t.guid,t.alterId) FROM Transaction t")
    List<TransactionAlterAndGuid> getAlterGuidIs();

    void deleteByGuidIn(List<String> guids);
}
