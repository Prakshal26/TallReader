package com.readData.DataXML.repositories;

import com.readData.DataXML.models.CostCenterMaster;
import com.readData.DataXML.models.CostCenterTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCenterRepository extends JpaRepository<CostCenterMaster,String> {
}
