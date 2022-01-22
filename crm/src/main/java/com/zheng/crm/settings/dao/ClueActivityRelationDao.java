package com.zheng.crm.settings.dao;


import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {

    List<Activity> getActivityListByClueId(String clueId);

    int unbund(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getListById(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
