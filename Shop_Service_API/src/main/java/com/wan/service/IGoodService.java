package com.wan.service;

import com.wan.pojo.Goods;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/1/17
 */
public interface IGoodService {

    //查询全部商品
    List<Goods> findAllGoods();

    //添加商品
    Goods insertGoods(Goods goods);

}
