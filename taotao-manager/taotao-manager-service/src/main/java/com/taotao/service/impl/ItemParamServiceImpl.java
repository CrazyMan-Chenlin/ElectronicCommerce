package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author chenlin
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    TbItemParamMapper tbItemParamMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;
    /**
     * 模仿写法
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
        //设置分页信息
        if (page==null||page==0) { page = 1;}
        if (rows==null||rows==0) { rows = 30;}
        PageHelper.startPage(page,rows);
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParams);
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
        List<TbItemParam> list = pageInfo.getList();
        easyUIDataGridResult.setRows(list);
        return easyUIDataGridResult;
    }
}
