package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;
import com.stock.services.IWebDiaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/web/diary")
public class WebDiaryController {


    @Autowired
    IWebDiaryServices iWebDiaryServices;

    //日记列表页
    @RequestMapping("/toDiaryList")
    public String toDiaryList(){
        return "web/diary/diary_list";
    }
    //日记详情页
    @RequestMapping("/toDiaryInfo")
    public String toDiaryInfo(){
        return "web/diary/diary_info";
    }


    @PostMapping(value = "/getDiaryAll",consumes = "application/json")
    @ResponseBody
    public String getDiaryAll(){
        List<WebDiary> diaryAll = iWebDiaryServices.getDiaryAll();
        String jsonStr = JSON.toJSONString( diaryAll );
        return jsonStr;
    }


    /***
     * 从指定位置，获取指定条数的日记信息
     * @param diaryVo
     * @return
     */
    @PostMapping(value = "/getDiaryByIndex",consumes = "application/json")
    @ResponseBody
    public String getDiaryByIndex(@RequestBody DiaryVo diaryVo){
        List<WebDiary> diaryAll = iWebDiaryServices.getDiaryByIndex(diaryVo);
        String jsonStr = JSON.toJSONString( diaryAll );
        return jsonStr;
    }


    @RequestMapping(value = "/getDiaryById")
    @ResponseBody
    public String getDiaryById( String id ){
        WebDiary webDiary = iWebDiaryServices.getDiaryById(id);
        String jsonStr = JSON.toJSONString(webDiary);
        return jsonStr;
    }





}
