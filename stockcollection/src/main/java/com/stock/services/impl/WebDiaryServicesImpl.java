package com.stock.services.impl;

import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;
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
    public List<WebDiary> getDiaryByIndex(DiaryVo diaryVo) {
        return iWebDiaryDao.getDiaryByIndex(diaryVo);
    }

    @Override
    public WebDiary getDiaryById(String id) {
        return iWebDiaryDao.getDiaryById(id);
    }

    @Override
    public void addToTable(WebDiary webDiary) {
        iWebDiaryDao.addToTable(webDiary);
    }

    @Override
    public void updateToTable(WebDiary webDiary) {
        iWebDiaryDao.updateToTable(webDiary);
    }

    @Override
    public boolean isExitByText(String text,String date) {
        return  iWebDiaryDao.isExitByText(text, date);
    }
}
