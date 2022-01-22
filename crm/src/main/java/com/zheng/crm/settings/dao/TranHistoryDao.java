package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory th);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
