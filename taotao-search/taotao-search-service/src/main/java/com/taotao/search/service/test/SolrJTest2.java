package com.taotao.search.service.test;

import com.taotao.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author chenlin
 */

@Component
public class SolrJTest2 {
    @Autowired
    CloudSolrClient solrClient;
    @Test
    public void addDocument() throws IOException, SolrServerException {
        if (solrClient == null) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
            solrClient = (CloudSolrClient) context.getBean("cloudSolrClient");
        }
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test001");
        document.addField("item_title", "测试商品3");
        document.addField("item_price", 123456);
        //把文档对象写入索引库
        solrClient.add(document);
        //提交
        solrClient.commit();
    }
}
