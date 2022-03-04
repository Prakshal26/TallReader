package com.readData.DataXML.services;

import com.readData.DataXML.contentProcessor.GroupMasterProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.GroupMaster;
import com.readData.DataXML.repositories.GroupMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMasterService {

    @Autowired
    GroupMasterRepository groupMasterRepository;

    @Autowired
    Utility utility;

    @Autowired
    GroupMasterProcessor groupMasterProcessor;

    public int processContent(String requestType) throws Exception {

        List<GroupMaster> groupMasterList = groupMasterProcessor.processGroupMaster(utility.processAndGiveDoc(requestType));
       // groupMasterRepository.deleteAll();
       return groupMasterRepository.saveAll(groupMasterList).size();
    }
}
