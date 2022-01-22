package com.zheng.crm.settings.service.impl;

import com.zheng.crm.settings.dao.CustomerDao;
import com.zheng.crm.settings.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        List<String> list = customerDao.getCustomerName(name);
        return list;
    }
}
