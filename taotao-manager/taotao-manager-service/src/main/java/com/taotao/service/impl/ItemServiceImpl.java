package com.taotao.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.jedis.JedisClientCluster;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;
/**
 * @author chenlin
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name="topicDestination")
    private Destination topicDestination;
    @Autowired
    private JedisClientCluster jedisCluster;
    @Value("${ItemInfoKey}")
    private  String itemInfoKey;
    @Value("${ItemInfoKeyExpire}")
    private  Integer itemInfoKeyExpire;
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
    public TaotaoResult sendMessage(TbItem item, String desc){
        Long itemId = this.saveItem(item, desc);
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(itemId + "");
            }
        });
        return TaotaoResult.ok(null);
    }

    @Override
    public TbItem getItemById(Long itemId) {
        // 添加缓存的原则是，不能够影响现在有的业务逻辑
        // 查询缓存
        try {
            if (itemId != null) {
                // 从缓存中查询
                String jsonString = jedisCluster.get(itemInfoKey + ":" + itemId + ":BASE");
                if (StringUtils.isNotBlank(jsonString)) {
                    // 不为空则直接返回
                    jedisCluster.expire(itemInfoKey + ":" + itemId + ":BASE", itemInfoKeyExpire);
                    return JsonUtils.jsonToPojo(jsonString, TbItem.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        // 添加缓存
        try {
            // 注入redisjedisCluster
            if (tbItem != null){
                jedisCluster.set(itemInfoKey + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
                jedisCluster.expire(itemInfoKey + ":" + itemId + ":BASE", itemInfoKeyExpire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        // 查询缓存
        try {
            if (itemId != null) {
                // 从缓存中查询
                String jsonString = jedisCluster.get(itemInfoKey + ":" + itemId + ":DESC");
                if (StringUtils.isNotBlank(jsonString)) {
                    // 不为空则直接返回
                    jedisCluster.expire(itemInfoKey + ":" + itemId + ":DESC", itemInfoKeyExpire);
                    return JsonUtils.jsonToPojo(jsonString, TbItemDesc.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
           // 添加缓存
        try {
            // 注入redisjedisCluster
            if (tbItemDesc != null){
                jedisCluster.set(itemInfoKey + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
                jedisCluster.expire(itemInfoKey + ":" + itemId + ":DESC", itemInfoKeyExpire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }
    @Override
    public Long saveItem(TbItem item, String desc) {
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
        return itemId;
    }

    @Override
    public TaotaoResult deleteItem(String[] ids) {
        for (int i = 0; i <ids.length ; i++) {
            tbItemMapper.deleteByPrimaryKey(Long.parseLong(ids[i]));
            tbItemDescMapper.deleteByPrimaryKey(Long.parseLong(ids[i]));
        }
        return TaotaoResult.ok(null);
    }

    @Override
    public TaotaoResult updateItemStatus(String[] ids,String sta) {
        for (int i = 0; i <ids.length ; i++) {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(ids[i]));
            tbItem.setStatus(Byte.parseByte(sta));
            tbItemMapper.updateByPrimaryKey(tbItem);
        }
        return TaotaoResult.ok(null);
    }

    @Override
    public TaotaoResult selectItemDesc(long id) {
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult updateItem(TbItem tbItem, String desc) {
        tbItem.setUpdated(new Date());
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(tbItem.getUpdated());
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        return TaotaoResult.ok(null);
    }
}
