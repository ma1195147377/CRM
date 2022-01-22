package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
