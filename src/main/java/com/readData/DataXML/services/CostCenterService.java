package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.contentProcessor.CostCenterMasterProcessor;
import com.readData.DataXML.models.CostCenterMaster;
import com.readData.DataXML.repositories.CostCenterRepository;
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
    CostCenterMasterProcessor costCenterProcessorMaster;

    public int processContent(String requestType) throws Exception {

        List<CostCenterMaster> costCenterMasterList = costCenterProcessorMaster.processCostCenter(utility.processAndGiveDoc(requestType));
        return costCenterRepository.saveAll(costCenterMasterList).size();
    }
}
