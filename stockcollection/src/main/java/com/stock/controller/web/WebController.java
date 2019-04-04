package com.stock.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class WebController {

    //首页
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/toMain")
    public String toMain(){
        return "main";
    }

    @RequestMapping("/toEma")
    public String toEma(){
        return "ema";
    }

    @RequestMapping("/toTest")
    public String toTest(){
        return "macd";
    }


    @RequestMapping("/toMacd")
    public String toMacd(){
        return "macd";
    }


    @RequestMapping("/toNominate")
    public String toNominate(){
        return "nominate";
    }

    @RequestMapping("/toNewStock")
    public String toNewStock(){
        return "stock_new_list";
    }





}
