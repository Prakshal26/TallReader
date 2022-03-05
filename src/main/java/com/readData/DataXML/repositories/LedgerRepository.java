package com.readData.DataXML.repositories;

import com.readData.DataXML.models.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger,String>{

    Ledger findByName(String name);


    @Query(nativeQuery = true,value="SELECT guid from ledger")
    List<String> findAllGUID();

    void deleteByGuidIn(List<String> guids);
}
