package com.readData.DataXML.repositories;

import com.readData.DataXML.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {


    @Query(nativeQuery = true,value="SELECT guid from voucher")
    List<String> findAllGUID();

    void deleteByGuidIn(List<String> guids);
}
