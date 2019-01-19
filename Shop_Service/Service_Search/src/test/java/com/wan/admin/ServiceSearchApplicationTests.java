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
public class ServiceSearchApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void contextLoads() {
    }

    @Test
    public void insert() throws IOException, SolrServerException {
        for(int i = 0; i < 10; i++) {
            //创建一个document对象 -> 一个商品
            SolrInputDocument document = new SolrInputDocument();
            //设置字段
            document.setField("id", i + 1);

            if(i == 5){
                document.setField("gtitle", "小米手机" + i);
            } else {
                document.setField("gtitle", "华为手机" + i);
            }
            document.setField("ginfo", "手机中的战斗机");
            document.setField("gprice", 1999.9 + i);
            document.setField("gimage", "http://www.baidu.com");
            document.setField("gcount", 100 + i);

            //将该商品放入索引库
            solrClient.add(document);

        }
        solrClient.commit();
    }



    @Test
    public void update(){
        //创建一个document对象 -> 一个商品
        SolrInputDocument document = new SolrInputDocument();
        //设置字段
        document.setField("id", 11);
        document.setField("gtitle", "健力宝");
        document.setField("ginfo", "功能性饮料，饮料中的小霸王");
        document.setField("gprice", 3);
        document.setField("gimage", "http://www.baidu.com");
        document.setField("gcount", 1000);

        try {
            //将该商品放入索引库
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() throws IOException, SolrServerException {
//        solrClient.deleteById("1");
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }


    @Test
    public void query(){
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("gtitle:霸王 || ginfo:霸王");

        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();

            for(SolrDocument document : results){
                String id = (String) document.get("id");
                String gtitle = (String) document.get("gtitle");
                String ginfo = (String) document.get("ginfo");
                float gprice = (float) document.get("gprice");
                int gcount = (int) document.get("gcount");
                String gimage = (String) document.get("gimage");
                System.out.println(id + " " + gtitle + " " + ginfo + " " + gprice + " " + gcount + " " + gimage);
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

