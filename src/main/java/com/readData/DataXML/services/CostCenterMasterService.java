package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.contentProcessor.CostCenterMasterProcessor;
import com.readData.DataXML.models.CostCenterMaster;
import com.readData.DataXML.models.Voucher;
import com.readData.DataXML.repositories.CostCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostCenterMasterService {

    @Autowired
    CostCenterRepository costCenterRepository;

    @Autowired
    Utility utility;

    @Autowired
    CostCenterMasterProcessor costCenterProcessorMaster;

    @Transactional
    public int processContent(String requestType) throws Exception {

        List<CostCenterMaster> costCenterMasterList = costCenterProcessorMaster.processCostCenter(utility.processAndGiveDoc(requestType));

        List<String> updatedGUID = costCenterMasterList.stream().map(CostCenterMaster::getGuid).collect(Collectors.toList());
        List<String> existingGUID = costCenterRepository.findAllGUID();

        List<String> removedIDs = existingGUID.stream().filter(e->!updatedGUID.contains(e))
                .collect(Collectors.toList());

        costCenterRepository.deleteByGuidIn(removedIDs);
        return costCenterRepository.saveAll(costCenterMasterList).size();
    }
}
