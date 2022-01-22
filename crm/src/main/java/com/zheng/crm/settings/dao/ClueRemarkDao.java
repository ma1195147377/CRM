package com.zheng.crm.settings.dao;

import com.zheng.crm.settings.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);
}
