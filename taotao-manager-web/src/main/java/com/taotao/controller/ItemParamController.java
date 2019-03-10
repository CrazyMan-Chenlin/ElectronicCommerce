package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlin
 */
@RestController
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    @RequestMapping(value="/item/param/list",method = RequestMethod.GET)
    public EasyUIDataGridResult getItemParamList(Integer page,Integer rows){
        return itemParamService.getItemParamList(page,rows);
    }
}
