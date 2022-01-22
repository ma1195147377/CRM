package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getValue(String code);
}
