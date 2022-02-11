package com.readData.DataXML.repositories;

import com.readData.DataXML.models.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCenterRepository extends JpaRepository<CostCenter,String> {
}
