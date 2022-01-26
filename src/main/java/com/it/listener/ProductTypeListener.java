package com.it.listener;

import com.it.pojo.ProductType;
import com.it.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手工从Spring容器中去除ProductType...对象
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService= (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList=productTypeService.getAll();
        //放入全局的作用域中
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
