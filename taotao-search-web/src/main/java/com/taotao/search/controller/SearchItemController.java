package com.taotao.search.controller;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author chenlin
 */
@Controller
public class SearchItemController {
    @Value("${rows}")
    Integer rows;
    @Autowired
    SearchItemService searchItemService;
    @RequestMapping(value = "/search")
    public String search(@RequestParam("q")String queryString, @RequestParam(defaultValue = "1")Integer page, Model model) throws SolrServerException, IOException {
        queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
        SearchResult result = searchItemService.search(queryString, page, rows);
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", result.getPageCount());
        model.addAttribute("itemList", result.getSearchItemList());
        model.addAttribute("page", page);
       return "search";
    }
}
