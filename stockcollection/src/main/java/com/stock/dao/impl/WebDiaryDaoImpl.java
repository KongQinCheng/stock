package com.stock.dao.impl;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.WebDiary;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IWebDiaryDao;
import com.stock.mapper.StockInfoMapper;
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
    public WebDiary getDiaryById(String id) {
        return webDiaryMapper.getDiaryById(id);
    }
}
