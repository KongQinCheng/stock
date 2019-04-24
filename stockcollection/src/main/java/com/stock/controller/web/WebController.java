package com.stock.controller.web;


import com.alibaba.fastjson.JSON;
import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.controller.init.TimedTask;
import com.stock.services.IWebDiaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class WebController {


    //首页
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "web/index";
    }

    @RequestMapping("/web/toHome")
    public String toHome(){
        return "web/home";
    }
    //日记列表页
    @RequestMapping("/web/toDiaryList")
    public String toDiaryList(){
        return "web/diary_list";
    }
    //日记详情页
    @RequestMapping("/web/toDiaryInfo")
    public String toDiaryInfo(){
        return "web/diary_info";
    }

    //初始化失败的时候执行
    @RequestMapping("/web/init")
    public void init() throws Exception {
        timedTask.getStockInfo();
    }


    @Autowired
    IWebDiaryServices iWebDiaryServices;

    @Autowired
    TimedTask timedTask;


    @PostMapping(value = "/diary/getDiaryAll",consumes = "application/json")
    @ResponseBody
    public String getDiaryAll(){
        List<WebDiary> diaryAll = iWebDiaryServices.getDiaryAll();
        String jsonStr = JSON.toJSONString( diaryAll );
        return jsonStr;
    }
    @PostMapping(value = "/diary/getDiaryByIndex",consumes = "application/json")
    @ResponseBody
    public String getDiaryByIndex(@RequestBody DiaryVo diaryVo){
        List<WebDiary> diaryAll = iWebDiaryServices.getDiaryByIndex(diaryVo);
        String jsonStr = JSON.toJSONString( diaryAll );
        return jsonStr;
    }


    @RequestMapping(value = "/diary/getDiaryById")
    @ResponseBody
    public String getDiaryById( String id ){
        WebDiary webDiary = iWebDiaryServices.getDiaryById(id);
        String jsonStr = JSON.toJSONString( webDiary);
        return jsonStr;
    }
}
