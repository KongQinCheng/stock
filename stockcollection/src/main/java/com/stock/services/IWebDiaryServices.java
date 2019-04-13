package com.stock.services;

import com.stock.bean.po.WebDiary;

import java.util.List;

public interface IWebDiaryServices {

      List<WebDiary> getDiaryAll();

      WebDiary getDiaryById(String id);

}
