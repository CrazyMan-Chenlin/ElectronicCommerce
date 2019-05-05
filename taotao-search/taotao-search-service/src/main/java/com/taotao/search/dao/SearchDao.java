package com.taotao.search.dao;
import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenlin
 */
@Repository
public class SearchDao {
  @Autowired
   private CloudSolrClient solrClient;
   @Autowired
    SearchItemMapper mapper;
    public SearchResult search(SolrQuery query) throws SolrServerException, IOException {
        //1.查询索引库
        QueryResponse response = solrClient.query(query);
        //2.获取商品列表
        SolrDocumentList results = response.getResults();
        //取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //3.封装商品列表
        List<SearchItem> searchItems = new ArrayList<>();
        SearchItem searchItem ;
        for (SolrDocument solrDocument : results){
            searchItem = new SearchItem();
            searchItem.setId((String)solrDocument.get("id"));
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String hightLight;
            if (list !=null && list.size() > 0){
                hightLight = list.get(0);
            }else{
                hightLight = solrDocument.get("item_title").toString();
            }
            searchItem.setTitle(hightLight);
            searchItem.setSellPoint(solrDocument.get("item_sell_point").toString());
            searchItem.setImage(solrDocument.get("item_image").toString());
            searchItem.setItemCatName(solrDocument.get("item_category_name").toString());
            searchItem.setPrice((long)solrDocument.get("item_price"));
            searchItems.add(searchItem);
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setSearchItemList(searchItems);
        searchResult.setRecordCount(results.getNumFound());
        return searchResult;
    }
    public TaotaoResult updateItemById(Long itemId) throws Exception {
        //1.调用mapper中的方法
        SearchItem searchItem = mapper.getItemById(itemId);
        //2.创建solrinputdocument
        SolrInputDocument document;
        //3.向文档中添加域
        document =  new SolrInputDocument();
        document.addField("item_title",searchItem.getTitle());
        document.addField("item_sell_point",searchItem.getSellPoint());
        document.addField("item_price",searchItem.getPrice());
        document.addField("item_image",searchItem.getImage());
        document.addField("item_category_name",searchItem.getItemCatName());
        document.addField("item_desc",searchItem.getItemDesc());
        //4.添加文档到索引库中
        solrClient.add(document);
        //5.提交
        solrClient.commit();
        return TaotaoResult.ok();
    }

}
