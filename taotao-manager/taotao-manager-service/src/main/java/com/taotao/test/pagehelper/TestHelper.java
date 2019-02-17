package com.taotao.test.pagehelper;

import com.github.pagehelper.PageHelper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author chenlin
 */
public class TestHelper {
    @Test
    public void testHelper(){
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //获取mapper的代理对象
        TbItemMapper bean = applicationContext.getBean(TbItemMapper.class);
        //设置分页
        PageHelper.startPage(1,3);
        //设置查询条件
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItems = bean.selectByExample(tbItemExample);
        List<TbItem> tbItems1 = bean.selectByExample(tbItemExample);
        System.out.println(tbItems.size());
        System.out.println(tbItems1.size());
        for(TbItem tbItem :tbItems){
            System.out.println(tbItem.getId()+tbItem.getTitle());
        }
    }
}
