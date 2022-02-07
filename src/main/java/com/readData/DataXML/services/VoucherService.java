package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.Utility.VoucherProcessor;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.models.Voucher;
import com.readData.DataXML.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class VoucherService {

    VoucherRepository voucherRepository;

    @Autowired
    Utility utility;

    @Autowired
    VoucherProcessor voucherProcessor;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public int processContent(File targetFile) throws IOException, SAXException, ParserConfigurationException {

        List<Voucher> voucherList = voucherProcessor.processVoucher(utility.processDom(targetFile));
        return voucherRepository.saveAll(voucherList).size();
    }
}
