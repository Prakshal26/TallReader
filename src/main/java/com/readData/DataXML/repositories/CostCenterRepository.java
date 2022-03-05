package com.readData.DataXML.repositories;

import com.readData.DataXML.models.CostCenterMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CostCenterRepository extends JpaRepository<CostCenterMaster,String> {


    @Query(nativeQuery = true,value="SELECT guid from cost_center")
    List<String> findAllGUID();

    void deleteByGuidIn(List<String> guids);
}
