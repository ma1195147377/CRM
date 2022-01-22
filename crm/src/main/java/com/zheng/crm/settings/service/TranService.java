package com.zheng.crm.settings.service;

import com.zheng.crm.settings.domain.Tran;
import com.zheng.crm.settings.domain.TranHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran t,String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    Map changeStage(Tran t, String editBy, HttpServletRequest request);

    Map getCharts();
}
