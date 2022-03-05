package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CostCenterMaster {

    @Id
    String guid;
    String NAME;
    String PARENT;
    String ALTERID;
    String CATEGORY;
    String ISDEEMEDPOSITIVE;
    String AMOUNT;
}
