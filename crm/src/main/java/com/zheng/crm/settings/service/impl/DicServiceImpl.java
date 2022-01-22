package com.zheng.crm.settings.service.impl;

import com.zheng.crm.settings.dao.DicTypeDao;
import com.zheng.crm.settings.dao.DicValueDao;
import com.zheng.crm.settings.domain.DicType;
import com.zheng.crm.settings.domain.DicValue;
import com.zheng.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Resource
    private DicValueDao dicValueDao;
    @Resource
    private DicTypeDao dicTypeDao;

    public void setDicValueDao(DicValueDao dicValueDao) {
        this.dicValueDao = dicValueDao;
    }

    public void setDicTypeDao(DicTypeDao dicTypeDao) {
        this.dicTypeDao = dicTypeDao;
    }

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String,List<DicValue>> map = new HashMap<String,List<DicValue>>();
        List<DicType> dtList = dicTypeDao.getType();
        for (DicType dt : dtList){
            String code = dt.getCode();
            List<DicValue> dvList = dicValueDao.getValue(code);
            map.put(code+"List",dvList);
        }
        return map;
    }
}
