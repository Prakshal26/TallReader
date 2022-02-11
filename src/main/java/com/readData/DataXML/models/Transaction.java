package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "transaction", orphanRemoval = true)
    List<BillAllocation> billAllocation = new ArrayList<>();

}
