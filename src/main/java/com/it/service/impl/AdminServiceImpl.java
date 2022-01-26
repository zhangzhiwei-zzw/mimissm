package com.it.service.impl;

import com.it.mapper.AdminMapper;
import com.it.pojo.Admin;
import com.it.pojo.AdminExample;
import com.it.service.AdminService;
import com.it.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    //在业务逻辑层中，一定会有数据访问层的对象
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户到数据库中查询相应的用户对象

        //如果有对象，则一定要创建AdminExample对象，封装条件
        AdminExample example=new AdminExample();
        //添加条件
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size()>0){
            Admin admin=list.get(0);
            //如果查询到用户在进行密码的比对
            String mipwd= MD5Util.getMD5(pwd);
            if(mipwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
