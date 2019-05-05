package com.taotao.search.service.impl;
import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author chenlin
 */
@Service
public class SearchItemServiceImpl implements SearchItemService{
    @Autowired
    SearchDao searchDao;
    @Autowired
    SearchItemMapper searchItemMapper;
    @Autowired
    CloudSolrClient solrClient;
    @Override
    public TaotaoResult importAllItems() throws IOException, SolrServerException {
        List<SearchItem> searchItemList = searchItemMapper.getSearchItemList();
        SolrInputDocument document ;
        if (solrClient==null){
            System.out.println("yes");
        }
        for(SearchItem searchItem :searchItemList){
            document =  new SolrInputDocument();
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSellPoint());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getItemCatName());
            document.addField("item_desc",searchItem.getItemDesc());
            solrClient.add(document);
        }
        solrClient.commit();
        System.out.println("提交成功！");
        return TaotaoResult.ok(null);
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException, IOException {
        //1.定义一个搜索对象
        SolrQuery solrQuery = new SolrQuery();
        //2.设置查询条件
        solrQuery.setQuery(queryString);
        //3.设置分页
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        //4.设置默认搜索域
        solrQuery.set("df","item_keywords");
        //5.设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        //6.执行查询
        SearchResult search = searchDao.search(solrQuery);
        //7.统计总页数
        long recordCount = search.getRecordCount();
        long pageTotal = recordCount / rows;
        if (recordCount%rows!=0){
            pageTotal++;
        }
        search.setPageCount(pageTotal);
        return search;
    }

    @Override
    public TaotaoResult updateItemById(Long itemId) throws Exception {
        return searchDao.updateItemById(itemId);
    }
}
