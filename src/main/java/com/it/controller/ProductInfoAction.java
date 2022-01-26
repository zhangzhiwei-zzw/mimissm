package com.it.controller;

import com.github.pagehelper.PageInfo;
import com.it.pojo.ProductInfo;
import com.it.pojo.vo.ProductInfoVo;
import com.it.service.ProductInfoService;
import com.it.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE=5;
    //业务逻辑对象
    String saveFileName="";
    @Autowired
    ProductInfoService productInfoService;
    //显示全部商品，不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list=productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
    //显示第1页的5条数据
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("prodVo");
        if(vo!=null){
            info=productInfoService.splitPageVo((ProductInfoVo)vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }else {
            //得到第一页的数据
            info=productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.setAttribute("info",info);
        return "product";
    }
    //ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //取得当前page参数页面的数据
        PageInfo info=productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取生成文件名UUID+.jpg
        saveFileName= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中存储的路径
        String  path=request.getServletContext().getRealPath("/image_big");
        //转存储
        try {
            System.out.println(path+File.separator+saveFileName);
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        //返回客户端json对象
        JSONObject object=new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }
    @RequestMapping("/save")
    public String save(ProductInfo productInfo,HttpServletRequest request){
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());
        int num=-1;
        try {
            num=productInfoService.save(productInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","增加成功");
        }else {
            request.setAttribute("msg","增加失败");
        }
        //清空saveFileName内容，为了下次增加或者修改
        saveFileName="";
        //增加成功后重新访问数据库，跳转回分页显示
        return "forward:/prod/split.action";
    }
    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo,Model model,HttpSession session){
        ProductInfo info=productInfoService.getByID(pid);
        model.addAttribute("prod",info);
        session.setAttribute("prodVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //因为Ajax的图片上传，则saveFileName有值，反之则无
        if(!saveFileName.isEmpty()){
            info.setpImage(saveFileName);
        }
        int num=-1;
        try {
            num=productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","更新成功");
        }else {
            request.setAttribute("msg","更新失败");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }
    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request,ProductInfoVo vo){
        int num=-1;
        try{
            num = productInfoService.delete(pid);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","删除成功");
            request.getSession().setAttribute("deleteProdVo",vo);
        }else {
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info=null;
        Object vo=request.getSession().getAttribute("vo");
        if(vo!=null){
            info=productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }else {
            info=productInfoService.splitPage(1,PAGE_SIZE);
        }
        //取得第一页所有数据
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    //批量删除商品的功能
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //将上传字符串截开
        String []ps=pids.split(",");
        try{
            int num= productInfoService.deleteBatch(ps);
            if(num>0){
                request.setAttribute("msg","批量删除成功！");
            }else {
                request.setAttribute("msg","批量删除失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("msg","商品不可删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }
    //多条件查询
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list=productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
