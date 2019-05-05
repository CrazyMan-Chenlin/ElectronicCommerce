package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * @author chenlin
 */
public interface SearchItemService {

    /**
     * 导入数据到索引库
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    TaotaoResult importAllItems() throws IOException, SolrServerException;

    /**
     * 搜索功能
     * @param queryString
     * @param page
     * @param rows
     * @return
     */
    SearchResult search(String queryString,Integer page,Integer rows) throws SolrServerException, IOException;

    /**
     * 该方法用于更新索引库
     * @param itemId
     * @return
     * @throws Exception
     */
    TaotaoResult updateItemById(Long itemId) throws Exception;
}
