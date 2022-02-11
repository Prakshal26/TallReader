package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BillAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ledger_id")
    Ledger ledger;

    String BILLDATE;
    String NAME;
    String BILLCREDITPERIOD;
    String ISADVANCE;
    String OPENINGBALANCE;
    String BILLTYPE;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    Transaction transaction;
}
