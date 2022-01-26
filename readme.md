# 搭建SSM项目的步骤
1、新建maven工程
2、修改pom文件
3、添加依赖
4、新建jdbc.properties到resources目录下
5、新建applicationContext_dao.xml文件，进行数据访问层的配置
6、新建applicationContext_service.xml文件，进行业务逻辑的配置
7、新建springmvc.xml文件，配置springmvc框架
8、新建sqlMapConfing.xml文件，进行分页插件的配置
9、使用逆向工程生成pojo和mapper文件
10、开发业务逻辑层，实现登录判断