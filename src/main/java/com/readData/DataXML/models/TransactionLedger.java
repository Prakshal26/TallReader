package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TransactionLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String AMOUNT;
    String ISDEEMEDPOSITIVE;

    @OneToOne
    Ledger ledger;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    Transaction transaction;

    @OneToMany(mappedBy = "transactionLedger",cascade = CascadeType.ALL,orphanRemoval = true)
    List<BillAllocationTransaction> billAllocationTransactionList = new ArrayList<>();

    @OneToMany(mappedBy = "transactionLedger",cascade = CascadeType.ALL,orphanRemoval = true)
    List<CostCenterTransaction> costCenterTransactionList = new ArrayList<>();
}
