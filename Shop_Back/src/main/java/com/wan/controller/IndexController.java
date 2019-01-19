package com.wan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 万星明
 * @Date 2019/1/18
 */
@Controller
public class IndexController {

    /**
     * 跳转到任意页面的请求
     * @param page
     * @return
     */
    @RequestMapping("/topage/{page}")
    public String toPage(@PathVariable("page") String page){
        return page;
    }

}
