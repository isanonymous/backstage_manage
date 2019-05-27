# backstage_manage
后台管理



## 1. 首次启动会自动执行sql，
记得==项目启动之后==把`src/main/resources/application.properties`中的spring.datasource.initialization-mode ***=always*** 改成 ***=never*** 

### 1.2 redis

默认主机为 127.0.0.1 

默认端口 ( 6379 )

## 2. 访问
默认访问 http://localhost:8881

## 3. 用户名和密码

用户名默认为: 11

密码默认为: 123456789

> 偷懒没有写，所以只能在数据库手动添加/修改

