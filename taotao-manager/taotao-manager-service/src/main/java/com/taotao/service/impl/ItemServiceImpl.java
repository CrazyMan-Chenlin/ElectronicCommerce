package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author chenlin
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页信息
        if (page==null||page==0) { page = 1; }
        if (rows==null||rows==0) {rows = 30 ;}
        PageHelper.startPage(page,rows);
        //注入mapper
        //创建 TbItemExample对象
        TbItemExample tbItemExample = new TbItemExample();
        //查询所有数据
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        //获取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        //封装到EasyUIDataGridResult中
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
        easyUIDataGridResult.setRows(pageInfo.getList());
        return easyUIDataGridResult;
    }
    @Autowired
    TbItemDescMapper tbItemDescMapper;
    @Override
    public TaotaoResult saveItem(TbItem item, String desc) {
        // 1.生成商品id
        long itemId = IDUtils.genItemId();
        //2.补全TbItem对象的属性
        item.setId(itemId);
        //商品转态 1-正常 2-下架 3-删除
        item.setStatus((byte)1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //3.向商品表插入数据
        tbItemMapper.insert(item);
        //4.创建一个TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        //5.补全TbItemDesc的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);
        itemDesc.setCreated(date);
        //6.向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);
        //7.taotaoresult ok
        return TaotaoResult.ok();
    }
}
