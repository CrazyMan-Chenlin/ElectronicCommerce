package com.taotao.content.service;


import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author chenlin
 */
public interface ContentCategoryService {
    /**
     * 获得ContentCategoryList
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    /**
     * 父节点的id
     * 新增节点的名称
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult createContentCategory(long parentId,String name);
}
