package com.readData.DataXML.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillAllocationMaster {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ledger_guid")
    Ledger ledger;

    String BILLDATE;
    String NAME;
    String BILLCREDITPERIOD;
    String ISADVANCE;
    String OPENINGBALANCE;
    String BILLTYPE;
}
