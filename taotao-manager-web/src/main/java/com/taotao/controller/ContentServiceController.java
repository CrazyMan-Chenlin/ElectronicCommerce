package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlin
 */
@RestController
public class ContentServiceController {

    @Autowired
    ContentService contentService;
    @RequestMapping(value = "/content/query/list",method = RequestMethod.GET)
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows){
    return  contentService.getContentList(categoryId,page,rows);
    }
    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    public TaotaoResult saveContent(TbContent tbContent){
        return contentService.saveContent(tbContent);
    }
}
