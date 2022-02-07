package com.readData.DataXML.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class GroupMaster {

    @Id
    String GUID;

    String GROUPNAME;
    String PARENT;
    String ALTERID;
}
