package com.zheng.crm.settings.service;

import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public interface UserService {
    User login(String loginAct,String loginPwd);

    List<User> getUserList();



}
