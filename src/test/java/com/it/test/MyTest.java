package com.it.test;

import com.it.mapper.ProductInfoMapper;
import com.it.pojo.ProductInfo;
import com.it.pojo.vo.ProductInfoVo;
import com.it.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Test
    public void testSelectCondition(){
        ProductInfoVo vo=new ProductInfoVo();
        vo.setPname("4");
        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(4000);
        List<ProductInfo> list=productInfoMapper.selectCondition(vo);
        for (ProductInfo info : list) {
            System.out.println(info);
        }
    }
//    @Test
//    public void testMD5(){
//        String mi= MD5Util.getMD5("123456");
//        System.out.println(mi);
//    }
}
