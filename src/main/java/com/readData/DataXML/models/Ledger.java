package com.readData.DataXML.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Ledger {

    @Id
    String GUID;
    String name;
    String MAILINGNAME;

    String address;
    String EMAIL;
    String PINCODE;
    String INCOMETAXNUMBER;
    String GSTREGISTRATIONTYPE;
    String PARENT;
    String BILLCREDITPERIOD;
    String EMAILCC;
    String LEDGERPHONE;
    String LEDGERCONTACT;
    String LEDGERMOBILE;
    String PARTYGSTIN;
    String ISBILLWISEON;
    String ISCOSTCENTRESON;
    String ISTDSAPPLICABLE;
    String ALTERID;
    String OPENINGBALANCE;
    String BALANCE;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ledger",orphanRemoval = true)
    List<PaymentDetails> paymentDetails = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ledger",orphanRemoval = true)
    List<BillAllocation> billAllocationDetails = new ArrayList<>();

}
