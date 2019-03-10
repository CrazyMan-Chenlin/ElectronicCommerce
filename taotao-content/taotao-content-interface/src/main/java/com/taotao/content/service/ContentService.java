package com.taotao.content.service;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import java.util.List;

/**
 * @author chenlin
 */
public interface ContentService {
    /**
     * 查询内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getContentList(long categoryId, Integer page, Integer rows);

    /**
     * 保存内容
     * @param tbContent
     * @return
     */
    TaotaoResult saveContent(TbContent tbContent);

    /**
     * 通过categoryId来获取内容列表
     * @param categoryId
     * @return
     */
    List<TbContent> getContentListByCategoryId(long categoryId);
}
