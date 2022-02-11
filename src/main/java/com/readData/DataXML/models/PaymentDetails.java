package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ledger_id")
    Ledger ledger;

    String IFSCODE;
    String BANKNAME;
    String ACCOUNTNUMBER;
    String PAYMENTFAVOURING;
    String TRANSACTIONNAME;
    String SETASDEFAULT;
    String DEFAULTTRANSACTIONTYPE;

}
