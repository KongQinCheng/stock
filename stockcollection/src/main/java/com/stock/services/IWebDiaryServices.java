package com.stock.services;

import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;

import java.util.List;

public interface IWebDiaryServices {

      /***
       * 获取指定用户的微博最新信息
       * @throws Exception
       */
      void  getWeiBoByUser() throws Exception;

      List<WebDiary> getDiaryAll();

      List<WebDiary> getDiaryByIndex(DiaryVo diaryVo);

      WebDiary getDiaryById(String id);

      void addToTable(WebDiary webDiary);

      void updateToTable(WebDiary webDiary);

      boolean isExitByText(String text,String date);



}
