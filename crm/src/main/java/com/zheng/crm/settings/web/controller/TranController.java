package com.zheng.crm.settings.web.controller;

import com.zheng.crm.settings.dao.TranDao;
import com.zheng.crm.settings.domain.Tran;
import com.zheng.crm.settings.domain.TranHistory;
import com.zheng.crm.settings.domain.User;
import com.zheng.crm.settings.service.CustomerService;
import com.zheng.crm.settings.service.TranService;
import com.zheng.crm.settings.service.UserService;
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
@RequestMapping("/workbench/transaction")
public class TranController {
    @Resource
    private UserService userService;
    @Resource
    private TranService tranService;
    @Resource
    private CustomerService customerService;
    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String getUserList(HttpServletRequest request){
        List<User> list = userService.getUserList();
        request.setAttribute("uList",list);
        return "/workbench/transaction/save.jsp";
    }
    @RequestMapping(value = "/getCustomerName.do", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCustomerName(String name){
        List<String> list  = customerService.getCustomerName(name);

        return list;
    }
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ModelAndView save(Tran t,String customerName,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        t.setCreateBy(createBy);
        boolean flag = tranService.save(t,customerName);
        if(flag){
            mv.setViewName("redirect:/workbench/transaction/index.jsp");
        }
         return mv;
    }
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public String detail(String id,HttpServletRequest request){
        Tran t = tranService.detail(id);
        String stage = t.getStage();
        Map<String,String> pMap = (Map<String,String>)request.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);
        request.setAttribute("t",t);
        return "/workbench/transaction/detail.jsp";
    }
    @RequestMapping(value = "/getHistoryListByTranId.do", method = RequestMethod.GET)
    @ResponseBody
    public List<TranHistory> getHistoryListByTranId(String tranId,HttpServletRequest request){
        List<TranHistory> thList = tranService.getHistoryListByTranId(tranId);
        Map<String,String> pMap = (Map<String,String>)request.getServletContext().getAttribute("pMap");
        //将交易历史列表遍历
        for(TranHistory th : thList){
            //根据每条交易历史，取出每一个阶段
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);
        }
        return thList;
    }
    @RequestMapping(value = "/changeStage.do", method = RequestMethod.POST)
    @ResponseBody
    public Map changeStage(Tran t,HttpServletRequest request){
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        Map map = tranService.changeStage(t,editBy,request);
        return map;
    }
    @RequestMapping(value = "/getCharts.do", method = RequestMethod.GET)
    @ResponseBody
    public Map getCharts(){
        Map map = tranService.getCharts();
        return map;
    }
}
