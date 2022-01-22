package com.zheng.crm.settings.service;

import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.ActivityRemark;
import com.zheng.crm.settings.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    public boolean save(Activity activity,String createBy);

    PaginationVO<Activity> pageList(Map map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    Map<String, Object> update(Activity activity, String updateBy);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    Map<String, Object> deleteRemark(String id);

    Map<String, Object> saveRemark(ActivityRemark activityRemark, String createBy);

    Map<String, Object> updateRemark(ActivityRemark activityRemark, String updateBy);

    List<Activity> getActivityListByClueId(String clueId);


}
