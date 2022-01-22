package com.zheng.crm.settings.service;

import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.Clue;
import com.zheng.crm.settings.domain.Tran;
import com.zheng.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUserList();

    Map saveClue(Clue clue,String creatBy);

    Clue detail(String id);

    Map unbund(String id);

    List<Activity> getActivityListByNameAndNotByClueId(String clueId, String aname);

    Map bund(String cid, String[] aids);

    List<Activity> getActivityListByName(String aname);

    boolean convert(Tran tran, String clueId,String creatBy);
}
