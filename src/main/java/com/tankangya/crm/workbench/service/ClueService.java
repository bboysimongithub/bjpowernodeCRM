package com.tankangya.crm.workbench.service;

import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.entity.Clue;
import com.tankangya.crm.workbench.entity.ClueRemark;
import com.tankangya.crm.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUserList();

    PaginationVO<Clue> getPageList(Map<String,Object> map);

    boolean saveClue(Clue clue);

    boolean deleteClue(String[] para);

    Map<String, Object> editClue(String id);

    boolean updateClue(Clue clue);

    Clue detail(String id);

    List<ClueRemark> getClueRemark(String id);

    boolean deleteClueRemark(String id);

    boolean updateClueRemark(ClueRemark clueRemark);

    boolean saveClueRemark(ClueRemark clueRemark);

    boolean deleteCLueActivityRelationById(String id);

    boolean saveClueActicityRaletionBttn(String[] id, String clueId);

    boolean saveConvert(String clueId, String createBy, Tran tran);
}
