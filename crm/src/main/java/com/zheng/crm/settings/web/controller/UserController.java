package com.zheng.crm.settings.web.controller;


import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.exception.LoginException;
import com.zheng.crm.settings.exception.UserException;
import com.zheng.crm.settings.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController {
    @Resource
    private UserService service;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map login(HttpServletRequest request, User user) throws UserException {
        String ip = request.getRemoteAddr();
        Map<String, Object> map = new HashMap();
        User tuser = service.login(user.getLoginAct(), user.getLoginPwd());
        request.getSession().setAttribute("user", tuser);
        if (tuser != null) {
            map.put("success", true);
            map.put("msg", "");
            System.out.println(tuser);
        } else {
            throw new LoginException("该用户不存在，请再次尝试");
        }
        return map;
    }


}
