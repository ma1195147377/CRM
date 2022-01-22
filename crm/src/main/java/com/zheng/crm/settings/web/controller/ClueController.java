package com.zheng.crm.settings.web.controller;

import com.zheng.crm.settings.domain.Activity;
import com.zheng.crm.settings.domain.Clue;
import com.zheng.crm.settings.domain.Tran;
import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.service.ActivityService;
import com.zheng.crm.settings.service.ClueService;
import com.zheng.crm.settings.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    private ClueService clueService;
    @Resource
    private ActivityService activityservice;
    @RequestMapping(value = "/getUserList.do", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){
        List<User> uList = clueService.getUserList();
        return uList;
    }
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map saveClue(HttpServletRequest request, Clue clue){
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Map map = clueService.saveClue(clue,createBy);
        return map;
    }
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET)
    public String detail(String id, HttpServletRequest request){
        Clue clue = clueService.detail(id);
        request.setAttribute("c",clue);
        return "/workbench/clue/detail.jsp";
    }
    @RequestMapping(value = "/getActivityListByClueId.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Activity> getActivityListByClueId(String clueId){
        List<Activity> aList = activityservice.getActivityListByClueId(clueId);
        return aList;
    }
    @RequestMapping(value = "/unbund.do",method = RequestMethod.POST)
    @ResponseBody
    public Map unbund(String id){
        Map map = clueService.unbund(id);
        return map;
    }
    @RequestMapping(value = "/getActivityListByNameAndNotByClueId.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String clueId,String aname){
        List<Activity> aList = clueService.getActivityListByNameAndNotByClueId(clueId,aname);
        return aList;
    }
    @RequestMapping(value = "/bund.do",method = RequestMethod.POST)
    @ResponseBody
    public Map bund(String cid,HttpServletRequest request){
        String[] aids = request.getParameterValues("aid");
        Map map = clueService.bund(cid,aids);
        return map;
    }
    @RequestMapping(value = "/getActivityListByName.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Activity> getActivityListByName(String aname){
        List<Activity> aList = clueService.getActivityListByName(aname);
        return aList;
    }
    @RequestMapping(value = "/convert.do",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView convert(Tran tran,String clueId,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        boolean success = clueService.convert(tran,clueId,createBy);
        if(success){
            mv.setViewName("redirect:/workbench/clue/index.jsp");
        }
        return mv;
    }
    //转换业务中最后的删除线索业务被注释掉了，请注意
    @RequestMapping(value = "/convert.do",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView convert(String clueId,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        boolean success = clueService.convert(null,clueId,createBy);
        if(success){
            mv.setViewName("redirect:/workbench/clue/index.jsp");
        }
        return mv;
    }
}
