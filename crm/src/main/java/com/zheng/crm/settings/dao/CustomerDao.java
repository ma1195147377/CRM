package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerById(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);


}
