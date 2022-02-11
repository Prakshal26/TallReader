package com.readData.DataXML.Utility;

import com.readData.DataXML.models.Voucher;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoucherProcessor {

    public List<Voucher> processVoucher(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("VOUCHERTYPE");
        List<Voucher> voucherList = new ArrayList<>();
        for (int i=0;i<nodeList.getLength();i++) {
            Node voucherNode = nodeList.item(i);
            if(!voucherNode.hasAttributes()) continue;
            NodeList voucherNodeChildNodes = voucherNode.getChildNodes();
            Voucher voucher = new Voucher();
            voucher.setVOUCHERTYPENAME(voucherNode.getAttributes().getNamedItem("NAME").getNodeValue());
            for (int j=0;j<voucherNodeChildNodes.getLength();j++) {
                Node n = voucherNodeChildNodes.item(j);
                if(n.getNodeName().equals("GUID")) voucher.setGUID(n.getTextContent().trim());
                if(n.getNodeName().equals("PARENT")) voucher.setPARENT(n.getTextContent().trim());
                if(n.getNodeName().equals("NUMBERINGMETHOD")) voucher.setNUMBERINGMETHOD(n.getTextContent().trim());
                if(n.getNodeName().equals("ALTERID")) voucher.setALTERID(n.getTextContent().trim());

            }
            voucherList.add(voucher);
        }
        return voucherList;
    }
}
