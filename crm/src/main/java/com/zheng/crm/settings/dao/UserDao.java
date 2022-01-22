package com.zheng.crm.settings.dao;



import com.zheng.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Author 北京动力节点
 */
public interface UserDao {


    User login(Map<String, String> map);

    List<User> getUserList();


}
