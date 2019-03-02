package com.taotao.content.service;


import com.taotao.common.pojo.EasyUITreeNode;

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
}
