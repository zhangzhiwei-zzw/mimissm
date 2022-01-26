package com.it.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.mapper.ProductInfoMapper;
import com.it.pojo.ProductInfo;
import com.it.pojo.ProductInfoExample;
import com.it.pojo.vo.ProductInfoVo;
import com.it.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //pageNum当前页码，pageSize每页大小
        PageHelper.startPage(pageNum,pageSize);
        //进行pageInfo的数据封装
        //进行有条件的查询，创建ProductInfoExample对象
        ProductInfoExample example=new ProductInfoExample();
        //设置排序，按主键降序
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合
        List<ProductInfo> list=productInfoMapper.selectByExample(example);
        //查询到的数据封装到pageInfo
        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list=productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
}
