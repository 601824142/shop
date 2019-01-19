package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Goods;
import com.wan.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/1/19
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    //注入搜索服务接口
    @Reference
    private ISearchService searchService;

    @RequestMapping("/goodsList")
    public String search(String keyword, Model model){
        //调用接口进行关键词查询
        List<Goods> goodsList = searchService.findGoodsByIndexed(keyword);
        System.out.println("已进入关键词"+keyword+"查询:"+goodsList);
        //添加页面
        model.addAttribute("goodslist",goodsList);
        //返回搜索结果页面
        return "searchlist";
    }

}
