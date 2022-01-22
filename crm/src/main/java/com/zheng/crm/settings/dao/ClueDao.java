package com.zheng.crm.settings.dao;


import com.zheng.crm.settings.domain.Clue;
import com.zheng.crm.settings.domain.User;

import java.util.List;

public interface ClueDao {
    List<User> getUserList();

    int saveClue(Clue clue);

    Clue detail(String id);


    Clue getById(String clueId);

    int delete(String clueId);
}
