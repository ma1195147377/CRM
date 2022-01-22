package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    List<Activity> getActivityListByNameAndNotByClueId(@Param("clueId") String clueId,@Param("aname") String aname);


    List<Activity> getActivityListByCondition(Map map);


    int getTotalByCondition(Map map);


    int save(Activity activity);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity detail(String id);


    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByName(String aname);
}
