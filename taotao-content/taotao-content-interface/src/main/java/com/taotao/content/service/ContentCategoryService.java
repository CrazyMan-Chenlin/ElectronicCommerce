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

    /**
     * 本身的节点
     * 修改的节点名称
     * @param id
     * @param name
     * @return
     */
    TaotaoResult updateContentCategory(long id,String name);

    /**
     * 根据id删除数据，通过父节点id决定是否将父节点变为子节点
     * @param id
     * @param parentId
     * @return
     */
    TaotaoResult deleteContentContentCategory(long id,long parentId);

    /**
     * 判断是否是父节点
     * @param id
     * @return
     */
   Boolean selectIsParentId(long id);
}
