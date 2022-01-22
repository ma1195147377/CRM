package com.zheng.crm.settings.service.impl;

import com.zheng.crm.settings.dao.ActivityDao;
import com.zheng.crm.settings.dao.ActivityRemarkDao;
import com.zheng.crm.settings.dao.UserDao;
import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.ActivityRemark;
import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.service.ActivityService;
import com.zheng.crm.settings.vo.PaginationVO;
import com.zheng.crm.utils.DateTimeUtil;
import com.zheng.crm.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ActivityRemarkDao activityRemarkDao;
    @Resource
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setDao(ActivityDao dao) {
        this.activityDao = dao;
    }

    public void setAcrdao(ActivityRemarkDao acrdao) {
        this.activityRemarkDao = acrdao;
    }

    @Override
    public boolean save(Activity activity,String createBy) {
        boolean flag = true;
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(createBy);
        int i = activityDao.save(activity);
        if(i != 1){
            flag = false;
        }
        return flag;
    }
    @Override
    public PaginationVO<Activity> pageList(Map map) {
        int total = activityDao.getTotalByCondition(map);
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        PaginationVO<Activity> vo =new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受影响的行数
        int count2 = activityRemarkDao.deleteByAids(ids);
        if(count1!=count2){
            flag = false;
        }
        int count3 = activityDao.delete(ids);
        if(count3!= ids.length){
            flag = false;
        }
        //删除市场活动
        return flag;
    }
    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map = new HashMap<String,Object>();
        List<User> uList = userDao.getUserList();
        Activity activity = activityDao.getById(id);
        map.put("uList",uList);
        map.put("a",activity);
        return map;
    }

    @Override
    public Map<String, Object> update(Activity activity, String updateBy) {
        Map<String, Object> map = new HashMap<String, Object>();
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(updateBy);
        boolean flag = true;
        int i = activityDao.update(activity);
        if(i!=1){
            flag = false;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> list= activityRemarkDao.getRemarkListByAid(activityId);
        return list;
    }

    @Override
    public Map<String, Object> deleteRemark(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = activityRemarkDao.deleteRemark(id);
        boolean flag = true;
        if(i != 1){
            flag = false;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Map<String, Object> saveRemark(ActivityRemark activityRemark, String createBy) {
        Map<String, Object> map = new HashMap<String, Object>();
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        int i = activityRemarkDao.saveRemark(activityRemark);
        boolean flag = true;
        if(i != 1){
            flag = false;
            activityRemark = null;
        }
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }
    @Override
    public Map<String, Object> updateRemark(ActivityRemark activityRemark, String updateBy) {
        Map<String, Object> map = new HashMap<String, Object>();
        activityRemark.setEditBy(updateBy);
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("1");
        int i = activityRemarkDao.updateRemark(activityRemark);
        boolean flag = true;
        if(i != 1){
            flag = false;
            activityRemark = null;
        }
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList = activityDao.getActivityListByClueId(clueId);
        return aList;
    }
}
