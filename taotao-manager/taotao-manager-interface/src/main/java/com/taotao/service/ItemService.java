package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * @author chenlin
 */
public interface ItemService {
    /**
     * 根据当前页码和行数进行分页
     * @param page
     * @param row
     * @return
     */
     EasyUIDataGridResult getItemList(Integer page,Integer row);

    /**
     * 根据商品的基础数据和商品的描述信息插入商品
     * @param item
     * @param desc
     * @return
     */
     TaotaoResult saveItem(TbItem item,String desc);

    /**
     * 根据id删除数据
     * @param ids
     * @return
     */
     TaotaoResult deleteItem(String[] ids);

    /**
     * 修改商品状态
     * @param ids
     * @param status
     * @return
     */
     TaotaoResult updateItemStatus(String[] ids,String status);

    /**
     * 查询商品描述
     * @param id
     * @return
     */
     TaotaoResult selectItemDesc(long id);

    /**
     * 更新商品信息
     * @param tbItem
     * @param desc
     * @return
     */
     TaotaoResult updateItem(TbItem tbItem,String desc);
}
