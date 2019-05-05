package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenlin
 */
public interface SearchItemMapper {
    /**
     * 得到索引类表
     * @return
     */
    List<SearchItem> getSearchItemList();

    /**
     * 得到商品信息
     * @param itemId
     * @return
     */
    SearchItem getItemById(@Param("itemId") Long itemId);
 }
