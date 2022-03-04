package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.contentProcessor.VoucherProcessor;
import com.readData.DataXML.models.Voucher;
import com.readData.DataXML.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int processContent(String requestType) throws Exception {

        List<Voucher> voucherList = voucherProcessor.processVoucher(utility.processAndGiveDoc(requestType));
       // voucherRepository.deleteAll();
        return voucherRepository.saveAll(voucherList).size();
    }
}
