package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;

/**
 * @author chenlin
 */
public interface ItemParamService {
    /**
     * 根据当前页码和行数进行分页
     * @param page
     * @param row
     * @return
     */
    EasyUIDataGridResult getItemParamList(Integer page, Integer row);
}
