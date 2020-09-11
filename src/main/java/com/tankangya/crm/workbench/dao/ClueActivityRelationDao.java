package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int deleteCLueActivityRelationById(String id);

    int saveClueActicityRaletionBttn(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getClueActivityRelation(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
