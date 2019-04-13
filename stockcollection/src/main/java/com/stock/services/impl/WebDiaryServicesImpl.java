package com.stock.services.impl;

import com.stock.bean.po.WebDiary;
import com.stock.dao.IWebDiaryDao;
import com.stock.services.IWebDiaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebDiaryServicesImpl implements IWebDiaryServices {

    @Autowired
    IWebDiaryDao iWebDiaryDao;

    @Override
    public List<WebDiary> getDiaryAll() {
        return iWebDiaryDao.getDiaryAll();
    }

    @Override
    public WebDiary getDiaryById(String id) {
        return iWebDiaryDao.getDiaryById(id);
    }
}
