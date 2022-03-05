package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class CostCenterTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String NAME;
    String CATEGORY;
    String ISDEEMEDPOSITIVE;
    String AMOUNT;

    @ManyToOne
    @JoinColumn(name = "transaction_ledger_id")
    TransactionLedger transactionLedger;

}
