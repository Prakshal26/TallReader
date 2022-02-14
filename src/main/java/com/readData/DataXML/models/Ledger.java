package com.readData.DataXML.models;

import com.readData.DataXML.convertors.StringListConverter;
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
    String PARENT;

    @Convert(converter = StringListConverter.class)
    List<String> ADDRESS = new ArrayList<>();

    //@ElementCollection(targetClass=String.class
    @Convert(converter = StringListConverter.class)
    List<String> MAILINGNAME = new ArrayList<>();

    String EMAIL;
    String PINCODE;
    String INCOMETAXNUMBER;
    String COUNTRYNAME;
    String GSTREGISTRATIONTYPE;

    String CREATEDBY;
    String ALTEREDBY;
    String BILLCREDITPERIOD;
    String EMAILCC;
    String LEDGERPHONE;
    String LEDGERCONTACT;
    String LEDGERMOBILE;
    String PARTYGSTIN;
    String LEDSTATENAME;
    String ISBILLWISEON;
    String ISCOSTCENTRESON;
    String ALTERID;
    String OPENINGBALANCE;

//    @Convert(converter = StringListConverter.class)
//    List<String> NAME = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ledger",orphanRemoval = true)
    List<PaymentDetails> paymentDetails = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ledger",orphanRemoval = true)
    List<BillAllocation> billAllocationDetails = new ArrayList<>();

}
