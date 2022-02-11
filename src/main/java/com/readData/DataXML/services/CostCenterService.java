package com.readData.DataXML.services;

import com.readData.DataXML.Utility.CostCenterProcessor;
import com.readData.DataXML.Utility.GroupMasterProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.CostCenter;
import com.readData.DataXML.models.GroupMaster;
import com.readData.DataXML.repositories.CostCenterRepository;
import com.readData.DataXML.repositories.GroupMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostCenterService {

    @Autowired
    CostCenterRepository costCenterRepository;

    @Autowired
    Utility utility;

    @Autowired
    CostCenterProcessor costCenterProcessor;

    public int processContent(String requestType) throws Exception {

        List<CostCenter> costCenterList = costCenterProcessor.processCostCenter(utility.processAndGiveDoc(requestType));
        return costCenterRepository.saveAll(costCenterList).size();
    }
}
