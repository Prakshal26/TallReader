package com.readData.DataXML.services;

import com.readData.DataXML.Utility.Utility;
import com.readData.DataXML.contentProcessor.VoucherProcessor;
import com.readData.DataXML.models.Ledger;
import com.readData.DataXML.models.Voucher;
import com.readData.DataXML.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public int processContent(String requestType) throws Exception {

        List<Voucher> voucherList = voucherProcessor.processVoucher(utility.processAndGiveDoc(requestType));


        List<String> updatedGUID = voucherList.stream().map(Voucher::getGuid).collect(Collectors.toList());
        List<String> existingGUID = voucherRepository.findAllGUID();

        List<String> removedIDs = existingGUID.stream().filter(e->!updatedGUID.contains(e))
                .collect(Collectors.toList());

        voucherRepository.deleteByGuidIn(removedIDs);
        return voucherRepository.saveAll(voucherList).size();
    }
}
