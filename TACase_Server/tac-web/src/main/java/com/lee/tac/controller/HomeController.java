package com.lee.tac.controller;

import com.lee.tac.service.ViewServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by zhaoli on 2018/4/8
 */

@Controller
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    ViewServiceImpl viewServiceImpl;

    @RequestMapping("/")
    public String index(){
        viewServiceImpl.addPageView();
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

}