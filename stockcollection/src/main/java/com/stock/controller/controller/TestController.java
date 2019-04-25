package com.stock.controller.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/toTest1")
    public String toTest1(){
        return "test/test1";
    }


    @RequestMapping("/toTest2")
    public String toTest2(){
        return "test/test2";
    }


    @RequestMapping("/toTest3")
    public String toTest3(){
        return "diary_list";
    }



    @RequestMapping("/toIndex")
    public String toIndex(){
        return "web/index";
    }
}
