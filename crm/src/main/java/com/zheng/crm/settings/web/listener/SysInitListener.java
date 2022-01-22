package com.zheng.crm.settings.web.listener;

import com.zheng.crm.settings.domain.DicValue;
import com.zheng.crm.settings.service.DicService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("上下文对象创建了");
        ServletContext application = event.getServletContext();
        DicService dao = WebApplicationContextUtils.getWebApplicationContext(application).getBean(DicService.class);
        Map<String, List<DicValue>> map = dao.getAll();

        Set<String> set = map.keySet();
        for(String key : set){
            application.setAttribute(key,map.get(key));

        }
        //解析properties文件
        Map<String,String> pMap = new HashMap<String,String>();

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){

            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);

            pMap.put(key, value);

        }

        application.setAttribute("pMap", pMap);
        System.out.println("监听器存放数据字典结束");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
