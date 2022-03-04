package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BillAllocationTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_ledger_id")
    TransactionLedger transactionLedger;

    String BILLDATE;
    String NAME;
    String BILLCREDITPERIOD;
    String ISADVANCE;
    String OPENINGBALANCE;
    String BILLTYPE;
}
