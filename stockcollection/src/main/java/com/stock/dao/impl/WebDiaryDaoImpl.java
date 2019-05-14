package com.stock.dao.impl;

import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;
import com.stock.dao.IWebDiaryDao;
import com.stock.mapper.WebDiaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebDiaryDaoImpl implements IWebDiaryDao {

    @Autowired
    WebDiaryMapper webDiaryMapper;

    @Override
    public List<WebDiary> getDiaryAll() {
        return webDiaryMapper.getDiaryAll();
    }

    @Override
    public List<WebDiary> getDiaryByIndex(DiaryVo diaryVo) {
        return webDiaryMapper.getDiaryByIndex(diaryVo);
    }

    @Override
    public WebDiary getDiaryById(String id) {
        return webDiaryMapper.getDiaryById(id);
    }

    @Override
    public void addToTable(WebDiary webDiary) {
        webDiaryMapper.addToTable(webDiary);
    }

    @Override
    public void updateToTable(WebDiary webDiary) {
        webDiaryMapper.updateToTable(webDiary);
    }

    @Override
    public boolean isExitByText(String text,String date) {
        if (webDiaryMapper.isExitByText(text, date)>0)
            return true;
        return false;
    }
}
