package com.stock.dao;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;

import java.util.List;

public interface IWebDiaryDao {
    List<WebDiary> getDiaryAll();

    List<WebDiary> getDiaryByIndex(DiaryVo diaryVo);

    WebDiary getDiaryById(String id);

    void addToTable(WebDiary webDiary);

    void updateToTable(WebDiary webDiary);

    boolean isExitByText(String text,String date);

}
