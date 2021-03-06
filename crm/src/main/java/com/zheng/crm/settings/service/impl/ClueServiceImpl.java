package com.zheng.crm.settings.service.impl;

import com.zheng.crm.settings.dao.*;
import com.zheng.crm.settings.domain.*;
import com.zheng.crm.settings.service.ClueService;
import com.zheng.crm.utils.DateTimeUtil;
import com.zheng.crm.utils.MD5Util;
import com.zheng.crm.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;
    @Resource
    private ActivityDao activityDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;

    public void setTranHistoryDao(TranHistoryDao tranHistoryDao) {
        this.tranHistoryDao = tranHistoryDao;
    }

    public void setTranDao(TranDao tranDao) {
        this.tranDao = tranDao;
    }

    public void setContactsActivityRelationDao(ContactsActivityRelationDao contactsActivityRelationDao) {
        this.contactsActivityRelationDao = contactsActivityRelationDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setCustomerRemarkDao(CustomerRemarkDao customerRemarkDao) {
        this.customerRemarkDao = customerRemarkDao;
    }

    public void setContactsDao(ContactsDao contactsDao) {
        this.contactsDao = contactsDao;
    }

    public void setContactsRemarkDao(ContactsRemarkDao contactsRemarkDao) {
        this.contactsRemarkDao = contactsRemarkDao;
    }

    public void setClueRemarkDao(ClueRemarkDao clueRemarkDao) {
        this.clueRemarkDao = clueRemarkDao;
    }

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    public void setClueDao(ClueDao clueDao) {
        this.clueDao = clueDao;
    }

    public void setClueActivityRelationDao(ClueActivityRelationDao clueActivityRelationDao) {
        this.clueActivityRelationDao = clueActivityRelationDao;
    }

    @Override
    public List<User> getUserList() {
        List<User> uList = clueDao.getUserList();
        return uList;
    }

    @Override
    public Map saveClue(Clue clue, String creatBy) {
        boolean flag = true;
        Map map = new HashMap();
        clue.setCreateBy(creatBy);
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setId(UUIDUtil.getUUID());
        int i = clueDao.saveClue(clue);
        if(i != 1){
            flag = false;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Clue detail(String id) {
        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public Map unbund(String id) {
        Map map = new HashMap();
        int i = clueActivityRelationDao.unbund(id);
        boolean flag = true;
        if(i!=1){
            flag = false;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(String clueId, String aname) {
        List<Activity> aList = activityDao.getActivityListByNameAndNotByClueId(clueId,aname);
        return aList;
    }

    @Override
    public Map bund(String cid, String[] aids) {
        Map map = new HashMap();
        boolean flag = true;
        for(String aid : aids){
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);
            int i = clueActivityRelationDao.bund(car);
            if(i!=1){
                flag = false;
            }
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> aList = activityDao.getActivityListByName(aname);
        return aList;
    }

    @Override
    public boolean convert(Tran tran, String clueId,String creatyBy) {
        boolean success = true;
        //1.????????????id??????????????????
        Clue clue = clueDao.getById(clueId);
        //2.??????????????????????????????????????????????????????????????????????????????????????????????????????
        String company = clue.getCompany();
        Customer cus = customerDao.getCustomerById(company);
        if(cus==null){
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setAddress(clue.getAddress());
            cus.setWebsite(clue.getWebsite());
            cus.setPhone(clue.getPhone());
            cus.setOwner(clue.getOwner());
            cus.setNextContactTime(clue.getNextContactTime());
            cus.setName(company);
            cus.setCreateBy(creatyBy);
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setContactSummary(clue.getContactSummary());

            //3.????????????
            int count = customerDao.save(cus);
            if(count!=1){
                success = false;
            }
        }
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setFullname(clue.getFullname());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setCustomerId(cus.getId());
        con.setCreateTime(DateTimeUtil.getSysTime());
        con.setCreateBy(creatyBy);
        con.setAppellation(clue.getAppellation());
        con.setAddress(clue.getAddress());
        //4.???????????????
        int count2 = contactsDao.save(con);
        if(count2!=1){
            success = false;
        }
        //??????????????????????????????????????????????????????
        //5.???????????????????????????????????????????????????
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        //?????????????????????????????????????????????????????????
        for(ClueRemark clueRemark : clueRemarkList) {
            String noteContent = clueRemark.getNoteContent();
            //6.????????????????????????
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setCreateBy(creatyBy);
            customerRemark.setCustomerId(cus.getId());
            customerRemark.setEditFlag("0");
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1) {
                success = false;
            }
            //6.???????????????????????????
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setCreateBy(creatyBy);
            contactsRemark.setContactsId(cus.getId());
            contactsRemark.setEditFlag("0");
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1) {
                success = false;
            }
        }
        //7.????????????????????????????????????????????????????????????
        //???????????????????????????????????????????????????????????????????????????????????????
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListById(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
            String activityId = clueActivityRelation.getActivityId();
            //?????????????????????????????????????????????
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());
            //???????????????????????????????????????
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1) {
                success = false;
            }
        }
        //8.????????????????????????????????????????????????
        if(tran != null){
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(cus.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(con.getId());
            tran.setId(UUIDUtil.getUUID());
            //????????????
            int count6 = tranDao.save(tran);
            if (count6 != 1) {
                success = false;
            }
            //?????????????????? ????????? ???????????????????????????????????????
            TranHistory th = new TranHistory();
            th.setCreateBy(creatyBy);
            th.setCreateTime(DateTimeUtil.getSysTime());
            th.setExpectedDate(tran.getExpectedDate());
            th.setId(UUIDUtil.getUUID());
            th.setMoney(tran.getMoney());
            th.setStage(tran.getStage());
            th.setTranId(tran.getId());
            //??????????????????
            int count7 = tranHistoryDao.save(th);
            if (count7 != 1) {
                success = false;
            }
        }
//        //9.??????????????????
//        for(ClueRemark clueRemark : clueRemarkList) {
//            int count8 =clueRemarkDao.delete(clueRemark);
//            if (count8 != 1) {
//                success = false;
//            }
//        }
//        //10.????????????????????????????????????
//        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
//            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
//            if (count9 != 1) {
//                success = false;
//            }
//        }
//        //11.????????????
//        int count10 = clueDao.delete(clueId);
//        if (count10 != 1) {
//            success = false;
//        }
        return success;
    }

}
