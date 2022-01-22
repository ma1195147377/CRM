package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran tran);

    Tran detail(String id);

    int changeStage(Tran t);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
