package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.IGoodsDao;
import com.wan.pojo.Goods;
import com.wan.service.IGoodService;
import com.wan.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/1/17
 */
@Service
public class GoodsServiceImpl implements IGoodService {

    @Autowired
    private IGoodsDao goodsDao;

    @Reference
    private ISearchService searchService;


    /**
     * 查询全部商品
     * @return
     */
    @Override
    public List<Goods> findAllGoods() {
        return goodsDao.selectList(null);//查询条件为空,即全部查询
    }

    /**
     * 插入商品信息,并同步到索引库
     * @param goods
     * @return
     */
    @Override
    @Transactional
    public Goods insertGoods(Goods goods) {
        //保存到数据库中
        goodsDao.insert(goods);
        //同步商品到索引库
        searchService.insertGoodsIndexed(goods);
        return goods;
    }

}
