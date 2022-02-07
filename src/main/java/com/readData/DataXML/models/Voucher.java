package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Voucher {


    String VOUCHERTYPENAME;

    @Id
    String GUID;
    String PARENT;
    String NUMBERINGMETHOD;
    String ALTERID;
}
