package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    String GUID;
    String DATE;
    String NARRATION;
    String VOUCHERTYPENAME;
    String VOUCHERNUMBER;
    String PARTYLEDGERNAME;

}
