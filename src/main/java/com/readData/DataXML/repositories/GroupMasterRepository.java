package com.readData.DataXML.repositories;

import com.readData.DataXML.models.GroupMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupMasterRepository extends JpaRepository<GroupMaster,String> {


    @Query(nativeQuery = true,value="SELECT guid from group_master")
    List<String> findAllGUID();

    void deleteByGuidIn(List<String> guids);
}
