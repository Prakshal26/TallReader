package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class CostCenter {

    @Id
    String GUID;
    String NAME;
    String PARENT;
    String ALTERID;
}
