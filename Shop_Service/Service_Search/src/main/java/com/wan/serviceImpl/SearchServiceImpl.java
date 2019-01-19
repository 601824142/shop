package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wan.pojo.Goods;
import com.wan.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/1/17
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;//注入查询客户端


    /**
     * 关键词查询
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> findGoodsByIndexed(String keyword) {
        SolrQuery solrQuery = new SolrQuery();//新建一个查询索引库

        //如果传进来的查询条件,关键词为空
        if (keyword == "") {
            solrQuery.setQuery("*:*");//设置查询关键词为全部
        } else {
            //否则设置标题关键词,描述关键词
            solrQuery.setQuery("gtitle:" + keyword + "|| ginfo:" + keyword);
        }

        //创建一个商品集合
        List<Goods> goodsList = new ArrayList<>();
        try {
            //调用查询客户端,传入查询索引
            QueryResponse query = solrClient.query(solrQuery);
            //得到查询结果集合
            SolrDocumentList results = query.getResults();
            //遍历查询结果集合
            for (SolrDocument document : results) {
                String id = (String) document.get("id");//取出id
                String gtitle = (String) document.get("gtitle");//取出标题
                String ginfo = (String) document.get("ginfo");//取出详情
                float gprice = (float) document.get("gprice");//取出价格
                int gcount = (int) document.get("gcount");//取出剩余数量
                String gimage = (String) document.get("gimage");//取出图片路径

                //将属性转为一个商品对象
                Goods goods = new Goods(Integer.parseInt(id), gtitle, ginfo, gcount,
                                                    0, 0, gprice, gimage);
                //加入集合中
                goodsList.add(goods);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //将查询的相关商品集合返回
        return goodsList;
    }


    /**
     * 添加插入的商品到查询索引库
     * @param goods
     * @return
     */
    @Override
    public int insertGoodsIndexed(Goods goods) {
        //创建一个solr索引库输入对象
        SolrInputDocument solrDocument = new SolrInputDocument();
        //设置该对象的各类属性
        solrDocument.setField("id", goods.getId());
        solrDocument.setField("gtitle", goods.getTitle());
        solrDocument.setField("ginfo", goods.getGinfo());
        solrDocument.setField("gimage", goods.getGimage());
        solrDocument.setField("gcount", goods.getGcount());
        solrDocument.setField("gprice", goods.getPrice());

        try {
            //将该索引对象添加到查询客户端
            solrClient.add(solrDocument);
            //查询客户端提交
            solrClient.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
