package com.taotao.portal.controller;
import com.taotao.portal.pojo.AdvNode;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenlin
 */
@Controller
public class PageController {

    @Autowired
    ContentService contentService;
      @Value("${categoryId}")
      long categoryId;
      @Value("${height}")
      String height;
      @Value("${width}")
      String width;
       @Value("${widthB}")
      String widthB;
    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> contentListByCategoryId = contentService.getContentListByCategoryId(categoryId);
        List<AdvNode> advNodes = new ArrayList<>();
        AdvNode advNode;
        for (TbContent tbContent : contentListByCategoryId){
         advNode = new AdvNode();
         advNode.setSrcB(tbContent.getPic());
         advNode.setHeight(height);
         advNode.setAlt(tbContent.getSubTitle());
         advNode.setHeightB(height);
         advNode.setHref(tbContent.getUrl());
         advNode.setSrc(tbContent.getPic2());
         advNode.setWidth(width);
         advNode.setWidthB(widthB);
         advNodes.add(advNode);
        }
        model.addAttribute("ad1", JsonUtils.objectToJson(advNodes));
        return "index";
    }
}
