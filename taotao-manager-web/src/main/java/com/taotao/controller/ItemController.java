package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlin
 */
@RestController
public class ItemController {
    @Autowired
     private ItemService itemService;
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        //1.引入服务
        //2.注册服务
        //3.调用服务方法
        return itemService.getItemList(page,rows);
    }
    @RequestMapping("/item/save")
    public TaotaoResult saveItem(TbItem item, String desc) {
        return itemService.saveItem(item, desc);
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
    public TaotaoResult deleteItem(String ids){
        String[] idArray = ids.split(",");
        return itemService.deleteItem(idArray);
    }

    /**
     * 下架商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
    public TaotaoResult updateItemStatus(String[] ids){

        return itemService.updateItemStatus(ids,"1");
    }

    /**
     * 上架商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/instock",method = RequestMethod.POST)
    public TaotaoResult updateItemStatus2(String[] ids){

        return itemService.updateItemStatus(ids,"2");
    }

    /**
     * 加载商品描述
     * @param id
     * @return
     */
    @RequestMapping(value = "/rest/item/query/item/desc")
    public TaotaoResult selectItemDesc(long id){

        return itemService.selectItemDesc(id);
    }

    /**
     * 更新商品信息
     * @param
     * @return
     */
    @RequestMapping(value = "/rest/item/update")
    public TaotaoResult updateItem(TbItem tbItem,String desc){
        return itemService.updateItem(tbItem,desc);
    }

}
