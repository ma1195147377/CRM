package com.zheng.crm.settings.web.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.ActivityRemark;
import com.zheng.crm.settings.domain.Clue;
import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.exception.LoginException;
import com.zheng.crm.settings.exception.UserException;
import com.zheng.crm.settings.service.ActivityService;
import com.zheng.crm.settings.service.UserService;
import com.zheng.crm.settings.vo.PaginationVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
    @Resource
    private UserService service;
    @Resource
    private ActivityService activityservice;
    @RequestMapping(value = "/getUserList.do", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){
        List<User> list = service.getUserList();
        for(User user : list){
            System.out.println(user);
        }
        return list;
    }
    @RequestMapping(value = "/save.do",method = RequestMethod.POST)
    @ResponseBody
    public Map saveActivity(HttpServletRequest request,Activity activity){
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        boolean flag = activityservice.save(activity,createBy);
        Map map = new HashMap();
        if(flag){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }
    @RequestMapping(value = "/pageList.do",method = RequestMethod.GET)
    @ResponseBody
    public PaginationVO pageList(String pageNo, String pageSize,Activity activity){
        int pageNum = Integer.valueOf(pageNo);
        int pageSizes = Integer.valueOf(pageSize);
        int skipCount = (pageNum-1)*pageSizes;
        Map map = new HashMap();
        map.put("skipCount",skipCount);
        map.put("pageSizes",pageSizes);
        map.put("activity",activity);
        PaginationVO<Activity> vo = activityservice.pageList(map);
        return vo;
    }
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public Map delete(HttpServletRequest request){
        String[] ids = request.getParameterValues("id");
        boolean flag =activityservice.delete(ids);
        Map map = new HashMap();
        map.put("success",flag);
        return map;
    }
    @RequestMapping(value = "/getUserListAndActivity.do",method = RequestMethod.GET)
    @ResponseBody
    public Map getUserListAndActivity(String id){

        Map<String,Object> map = activityservice.getUserListAndActivity(id);
        return map;
    }
    @RequestMapping(value = "/update.do",method = RequestMethod.POST)
    @ResponseBody
    public Map updateActivity(HttpServletRequest request,Activity activity){
        String updateBy = ((User) request.getSession().getAttribute("user")).getName();
        Map<String,Object> map = activityservice.update(activity,updateBy);
        return map;
    }
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET)
    public String detail(String id,HttpServletRequest request){
        Activity activity = activityservice.detail(id);
        request.setAttribute("a",activity);
        return "/workbench/activity/detail.jsp";
    }
    @RequestMapping(value = "/getRemarkListByAid.do",method = RequestMethod.GET)
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String activityId){
        List<ActivityRemark> arList = activityservice.getRemarkListByAid(activityId);
        return arList;
    }
    @RequestMapping(value = "/deleteRemark.do",method = RequestMethod.POST)
    @ResponseBody
    public Map deleteRemark(String id){
        Map<String,Object> map = activityservice.deleteRemark(id);
        return map;
    }
    @RequestMapping(value = "/saveRemark.do",method = RequestMethod.POST)
    @ResponseBody
    public Map saveRemark(ActivityRemark activityRemark,HttpServletRequest request){
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Map<String,Object> map = activityservice.saveRemark(activityRemark,createBy);
        return map;
    }
    @RequestMapping(value = "/updateRemark.do",method = RequestMethod.POST)
    @ResponseBody
    public Map updateRemark(ActivityRemark activityRemark,HttpServletRequest request){
        String updateBy = ((User) request.getSession().getAttribute("user")).getName();
        Map<String,Object> map = activityservice.updateRemark(activityRemark,updateBy);
        return map;
    }
}
