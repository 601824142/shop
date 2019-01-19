package com.wan.admin;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

    //自动注入索引库
    @Autowired
    private SolrClient solrClient;

    /**
     * 添加索引库测试
     */
    @Test
    public void insertGoodsToSolr() {
        //创建Document对象(一个文档对象代表一个商品对象)
        SolrInputDocument document = new SolrInputDocument();
        //设置字段和属性
        document.setField("id", 1);
        document.setField("gtitle", "小米笔记本");
        document.setField("ginfo", "笔记本中的性价比");
        document.setField("gprice", 7000);
        document.setField("gimage", "http://www.baidu.com");
        document.setField("gcount", 20);

        try {
            //将该商品放入索引库
            solrClient.add(document);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改索引库商品测试
     */
    @Test
    public void updateGoodsToSolr() {
        //创建Document对象(一个文档对象代表一个商品对象)
        SolrInputDocument document = new SolrInputDocument();
        //设置字段和属性
        document.setField("id", 1);
        document.setField("gtitle", "小米笔记本(我的最爱)");
        document.setField("ginfo", "买不起啊");
        document.setField("gprice", 7000);
        document.setField("gimage", "http://www.baidu.com");
        document.setField("gcount", 20);

        try {
            //将该商品放入索引库
            solrClient.add(document);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试删除索引库中的内容
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void deleteGoodsToSolr() throws IOException, SolrServerException {
        //通过ID删除
//        solrClient.deleteById("2");
        //删除全部
        solrClient.deleteByQuery("*:*");
        //提交索引库
        solrClient.commit();
    }


    /**
     * 测试查询索引库中内容
     */
    @Test
    public void queryGoodsToSolr(){
        //创建查询索引库对象
        SolrQuery solrQuery = new SolrQuery();

//        solrQuery.setQuery("*:*");//设置查询条件(查询全部)
//        solrQuery.setQuery("gtitle:华为 || ginfo:华为");//或查询
//        solrQuery.setQuery("gtitle:华为 && ginfo:华为");//与查询
        solrQuery.setQuery("gtitle:华为");


        try {
            //通过客户端进行查询(将查询索引库对象作为参数传入),返回查询回复对象
            QueryResponse queryResponse = solrClient.query(solrQuery);
            //通过回复对象,得到查询结果文档对象Document的集合
            SolrDocumentList results = queryResponse.getResults();
            //遍历文档对象集合
            for (SolrDocument document : results){
                //通过文档对象取得属性
                String id = (String) document.get("id");
                String gtitle = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                float gprice = (float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage = (String) document.get("gimage");
                System.out.println(id + " " + gtitle + " " + ginfo + " " + gprice + " " + gcount + " " + gimage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

