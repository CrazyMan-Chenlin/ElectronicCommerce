package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenlin
 */
@RestController
public class ContentCategoryController {
    @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping(value="/content/category/list")
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
     return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    TaotaoResult createContentCategory(Long parentId,String name){
       return contentCategoryService.createContentCategory(parentId,name);
    }
}
