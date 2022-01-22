package com.zheng.crm;

import com.zheng.crm.settings.domain.User;
import com.zheng.crm.utils.MD5Util;

public class test {
    public static void main(String[] args) {
        String test = "123456";
        String rel = MD5Util.getMD5(test);
        System.out.println(rel);
        User u=null;
        boolean flag;
        User user = new User();
        User user1 = new User();
        User user2=null;

    }

}
