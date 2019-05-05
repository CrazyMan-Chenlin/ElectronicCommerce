package com.taotao.controller;

import com.mchange.v1.util.ArrayUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author chenlin
 */
@RestController
public class SearchController {
    @Autowired
    SearchItemService searchItemService;

    @RequestMapping("/index/add")
    public TaotaoResult addIndex() throws IOException, SolrServerException {
        return searchItemService.importAllItems();

    }
}
