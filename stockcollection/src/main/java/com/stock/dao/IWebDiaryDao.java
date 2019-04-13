package com.stock.dao;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.WebDiary;

import java.util.List;

public interface IWebDiaryDao {
    List<WebDiary> getDiaryAll();

    WebDiary getDiaryById(String id);

}
