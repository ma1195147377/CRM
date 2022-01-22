package com.zheng.crm.settings.service.impl;

import com.zheng.crm.settings.dao.ActivityDao;
import com.zheng.crm.settings.dao.UserDao;
import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.service.UserService;
import com.zheng.crm.settings.vo.PaginationVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User login(String loginAct,String loginPwd){
        Map<String,String> map = new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = dao.login(map);
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> list = dao.getUserList();
        return list;
    }


}
