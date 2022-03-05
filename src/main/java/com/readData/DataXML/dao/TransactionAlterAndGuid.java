package com.readData.DataXML.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionAlterAndGuid {

    String guid;
    String alterId;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TransactionAlterAndGuid)) return false;
        TransactionAlterAndGuid t = (TransactionAlterAndGuid) obj;
        return this.getGuid().equals(t.getGuid()) && this.getAlterId().equals(t.getAlterId());
    }
}
