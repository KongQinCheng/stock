package com.stock.controller.web;


import com.alibaba.fastjson.JSON;
import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.StockSearchVo;
import com.stock.services.IWebDiaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/diary")
public class WebDiaryController {


    @Autowired
    IWebDiaryServices iWebDiaryServices;

    @PostMapping(value = "/getDiaryAll",consumes = "application/json")
    @ResponseBody
    public String getDiaryAll(){
        List<WebDiary> diaryAll = iWebDiaryServices.getDiaryAll();
        String jsonStr = JSON.toJSONString( diaryAll );
        return jsonStr;
    }

    @RequestMapping(value = "/getDiaryById")
    @ResponseBody
    public String getDiaryById( String id ){
        WebDiary webDiary = iWebDiaryServices.getDiaryById(id);
        String jsonStr = JSON.toJSONString( webDiary);
        return jsonStr;
    }
}
