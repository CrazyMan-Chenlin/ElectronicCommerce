package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author chenlin
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;


    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria =tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        List<EasyUITreeNode> list = new ArrayList<>();
        for (TbContentCategory tbContentCategory :tbContentCategories){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
            easyUITreeNode.setText(tbContentCategory.getName());
            list.add(easyUITreeNode);
        }
        return list;
    }

    @Override
    public TaotaoResult createContentCategory(long parentId, String name) {
        //1.构建对象，补全其他属性
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setUpdated(tbContentCategory.getCreated());
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //2.插入ContentCategory表中的数据
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        //判断如果要添加的父节点本身为叶子节点，本身要变为父节点
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (parent.getIsParent() == false){
            parent.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
        }
        //主键返回：再插入数据后，返回到id到原有的对象中
        //3.返回taotaoResult 包含内容分类的id,需要进行主键返回
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        //1.根据提供的id查询该条目
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        System.out.println(tbContentCategory.getIsParent());
        //2.修改名称
        tbContentCategory.setName(name);
        //3.更新
        tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        //返回数据
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult deleteContentContentCategory(long id,long parentId) {
        //1.根据id删除数据
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        //2.根据父节点id查询是否还有其他兄弟节点
        //3.设置查询条件
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria =tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //4.查询
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        if (tbContentCategories.size()==0){
            //1.查询父节点
            TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            //2.修改属性
            tbContentCategory.setIsParent(false);
            //3.更新
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
            //4.返回
            return TaotaoResult.ok(null);
        }else{
            return TaotaoResult.ok(null);
        }
    }
    @Override
    public Boolean selectIsParentId(long id) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
       return tbContentCategory.getIsParent();
    }
}
