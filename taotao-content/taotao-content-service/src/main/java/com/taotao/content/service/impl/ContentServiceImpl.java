package com.taotao.content.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.content.service.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
/**
 * @author chenlin
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    JedisClient jedisClient;
    @Autowired
    TbContentMapper tbContentMapper;
    @Value(value = "${CONTENT_KEY}")
    String CONTENT_KEY;
    @Override
    public EasyUIDataGridResult getContentList(long categoryId, Integer page, Integer rows) {
        //设置分页信息
        if (page==null||page==0) { page = 1;}
        if (rows==null||rows==0) { rows = 20;}
        PageHelper.startPage(page,rows);
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(pageInfo.getList());
        easyUIDataGridResult.setTotal((int)pageInfo.getTotal());
        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult saveContent(TbContent tbContent) {
        //同步缓存
        try {
            jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
            System.out.println("同步缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbContent.setCreated(new Date());
        tbContent.setUpdated(tbContent.getCreated());
        tbContentMapper.insertSelective(tbContent);
        return TaotaoResult.ok(null);
    }

    @Override
    public List<TbContent> getContentListByCategoryId(long categoryId) {
        //查询缓存
        try {
            String hget = jedisClient.hget(CONTENT_KEY, String.valueOf(categoryId));
            if (hget!=null){
                System.out.println("查询缓存");
                return JsonUtils.jsonToList(hget,TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);
        //向缓存添加数据，捕获异常，防止缓存异常，影响程序进行
        try {
            jedisClient.hset(CONTENT_KEY,String.valueOf(categoryId), JsonUtils.objectToJson(tbContents));
            System.out.println("添加缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  tbContents;
    }
}
