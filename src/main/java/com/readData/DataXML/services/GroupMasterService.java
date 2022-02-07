package com.readData.DataXML.services;

import com.readData.DataXML.Utility.GroupMasterProcessor;
import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.models.GroupMaster;
import com.readData.DataXML.models.Voucher;
import com.readData.DataXML.repositories.GroupMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GroupMasterService {

    @Autowired
    GroupMasterRepository groupMasterRepository;

    @Autowired
    Utility utility;

    @Autowired
    GroupMasterProcessor groupMasterProcessor;

    public int processContent(File targetFile) throws IOException, SAXException, ParserConfigurationException {

        List<GroupMaster> groupMasterList = groupMasterProcessor.processGroupMaster(utility.processDom(targetFile));
       return groupMasterRepository.saveAll(groupMasterList).size();
    }
}
