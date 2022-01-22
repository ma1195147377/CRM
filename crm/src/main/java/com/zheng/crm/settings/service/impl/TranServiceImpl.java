package com.zheng.crm.settings.service.impl;

import com.zheng.crm.settings.dao.CustomerDao;
import com.zheng.crm.settings.dao.TranDao;
import com.zheng.crm.settings.dao.TranHistoryDao;
import com.zheng.crm.settings.domain.Customer;
import com.zheng.crm.settings.domain.Tran;
import com.zheng.crm.settings.domain.TranHistory;
import com.zheng.crm.settings.service.TranService;
import com.zheng.crm.utils.DateTimeUtil;
import com.zheng.crm.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Resource
    private CustomerDao customerDao;

    public void setTranHistoryDao(TranHistoryDao tranHistoryDao) {
        this.tranHistoryDao = tranHistoryDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setTranDao(TranDao tranDao) {
        this.tranDao = tranDao;
    }

    @Override
    public boolean save(Tran t,String customerName) {
        boolean flag = true;
        t.setId(UUIDUtil.getUUID());
        t.setCreateTime(DateTimeUtil.getSysTime());
        Customer cus = customerDao.getCustomerById(customerName);

        if(cus==null){
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateBy(t.getCreateBy());
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());
            int count1 = customerDao.save(cus);
            if(count1!=1){
                flag=false;
            }
        }
        //把名字放到到交易中并添加
        t.setCustomerId(cus.getId());
        int count2 = tranDao.save(t);
        if(count2!=1){
            flag=false;
        }
        //创建交易历史并添加
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        t.setCreateBy(t.getCreateBy());
        t.setCreateTime(DateTimeUtil.getSysTime());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());

        int count3 = tranHistoryDao.save(th);
        if(count3!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(tranId);
        return thList;
    }

    @Override
    public Map changeStage(Tran t, String editBy, HttpServletRequest request) {
        Map map = new HashMap();
        t.setEditBy(editBy);
        t.setEditTime(DateTimeUtil.getSysTime());
        boolean flag = true;
        int i = tranDao.changeStage(t);
        if(i!=1){
            flag = false;
        }
        //生成交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(editBy);
        th.setMoney(t.getMoney());
        th.setStage(t.getStage());
        th.setExpectedDate(t.getExpectedDate());
        th.setTranId(t.getId());
        int count = tranHistoryDao.save(th);
        if(count!=1){
            flag = false;
        }
        Map<String,String> pMap = (Map<String,String>)request.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(t.getStage()));
        //生成交易历史
        map.put("success",flag);
        map.put("t",t);
        return map;
    }

    @Override
    public Map getCharts() {
        //取得total
        int total = tranDao.getTotal();
        //取得dataList
        List<Map<String,Object>> dataList = tranDao.getCharts();
        //将total和dataList保存到map中
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("dataList", dataList);

        //返回map
        return map;
    }
}
