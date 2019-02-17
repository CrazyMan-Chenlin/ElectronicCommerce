package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author chenlin
 */
public interface ItemCatService {
    /**
     * 根据父节点id 查询该节点的子类目列表
     * @param parentId
     * @return
     */
     List<EasyUITreeNode> getItemCatList(Long parentId);
}
