package com.readData.DataXML.repositories;
import com.readData.DataXML.models.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LedgerRepository extends JpaRepository<Ledger,Long>{

    public Ledger findByName(String name);
}
