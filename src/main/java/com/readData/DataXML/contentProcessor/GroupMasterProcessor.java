package com.readData.DataXML.contentProcessor;

import com.readData.DataXML.models.GroupMaster;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMasterProcessor {

    public List<GroupMaster> processGroupMaster(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("GROUP");
        List<GroupMaster> groupMasterList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node groupMasterNode = nodeList.item(i);
            if(!groupMasterNode.hasAttributes()) continue;
            NodeList groupMasterNodeChildNodes = groupMasterNode.getChildNodes();
            GroupMaster groupMaster = new GroupMaster();
            groupMaster.setGROUPNAME(groupMasterNode.getAttributes().getNamedItem("NAME").getNodeValue());
            for (int j=0;j<groupMasterNodeChildNodes.getLength();j++) {
                Node n = groupMasterNodeChildNodes.item(j);
                if(n.getNodeName().equals("GUID")) groupMaster.setGUID(n.getTextContent().trim());
                if(n.getNodeName().equals("PARENT")) groupMaster.setPARENT(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) groupMaster.setALTERID(n.getTextContent().trim());
            }
            groupMasterList.add(groupMaster);
        }
        return groupMasterList;
    }
}
