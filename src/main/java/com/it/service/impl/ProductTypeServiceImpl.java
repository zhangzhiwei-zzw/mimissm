package com.it.service.impl;

import com.it.mapper.ProductTypeMapper;
import com.it.pojo.ProductType;
import com.it.pojo.ProductTypeExample;
import com.it.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    //数据访问层对象
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
