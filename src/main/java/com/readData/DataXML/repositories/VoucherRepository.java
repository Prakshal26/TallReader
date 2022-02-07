package com.readData.DataXML.repositories;

import com.readData.DataXML.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {
}
