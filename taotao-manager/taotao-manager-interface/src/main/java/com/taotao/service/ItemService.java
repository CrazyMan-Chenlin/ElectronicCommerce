package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;

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
}
