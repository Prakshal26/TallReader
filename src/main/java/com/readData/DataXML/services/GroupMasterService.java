package com.readData.DataXML.services;

import com.readData.DataXML.contentProcessor.GroupMasterProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.GroupMaster;
import com.readData.DataXML.repositories.GroupMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMasterService {

    @Autowired
    GroupMasterRepository groupMasterRepository;

    @Autowired
    Utility utility;

    @Autowired
    GroupMasterProcessor groupMasterProcessor;

    @Transactional
    public int processContent(String requestType) throws Exception {

       List<GroupMaster> groupMasterList = groupMasterProcessor.processGroupMaster(utility.processAndGiveDoc(requestType));

        List<String> updatedGUID = groupMasterList.stream().map(GroupMaster::getGuid).collect(Collectors.toList());
        List<String> existingGUID = groupMasterRepository.findAllGUID();

        List<String> removedIDs = existingGUID.stream().filter(e->!updatedGUID.contains(e))
                .collect(Collectors.toList());

        groupMasterRepository.deleteByGuidIn(removedIDs);
       return groupMasterRepository.saveAll(groupMasterList).size();
    }
}
