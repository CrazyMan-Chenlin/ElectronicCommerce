package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    TaotaoResult updateContentCategory(Long id,String name){
        return contentCategoryService.updateContentCategory(id,name);
    }
    @RequestMapping(value = "/content/category/delete/",method = RequestMethod.POST)
    public TaotaoResult deleteContentCategory(Long id, Long parentId){
       return contentCategoryService.deleteContentContentCategory(id,parentId);
    }
    @RequestMapping(value = "/content/category/selectIsParentId/", method = RequestMethod.POST)
    Boolean selectIsParentId(long id){
        return contentCategoryService.selectIsParentId(id);
    }
}
