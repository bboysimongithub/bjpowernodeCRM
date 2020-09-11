package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    List<Clue> getPageList(Map<String, Object> map);

    int saveClue(Clue clue);

    int getPageListCount(Map<String, Object> map);

    Clue editClue(String id);

    int updateClue(Clue clue);

    int deleteClue(String[] para);

    Clue detail(String id);

    Clue getClueById(String clueId);

    int delete(String clueId);
}
