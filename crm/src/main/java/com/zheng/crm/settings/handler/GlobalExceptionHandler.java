package com.zheng.crm.settings.handler;

import com.zheng.crm.settings.exception.LoginException;
import com.zheng.crm.settings.exception.UserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    //针对LoginException类型的异常进行处理，注解无参则表示处理其他类型的异常
    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map doLoginException(Exception e){
        String msg = e.getMessage();
        Map<String, Object> map = new HashMap();
        map.put("success", false);
        map.put("msg",msg);
        return map;
    }
}
