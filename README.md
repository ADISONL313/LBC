# LBC(Left-Behind children)
基于web的留守儿童爱心网站;Web based caring website for left behind children;
#主要功能模块
  #管理员
    用户管理、新闻管理、捐赠管理、求助管理、志愿者管理
  #普通用户
    新闻模块、登录/注册/修改密码、捐赠模块、求助模块、志愿者模块、个人中心
#个人毕业设计 graduation design
#项目整体采用前后端分离的模式开发 MVC.
#技术栈如下
1.后端基于springboot3.4.0，采用了MinIO作为文件服务，Mysql8.0.37作为数据库，redis作为数据库中间件
2.前端基于vue3，使用element-plus快速构建页面，pinia作为前端数据持久层，axios实现网络请求
#环境配置
1.java19或以上版本
2.node.js 18或以上版本
3.npm包管理工具10.0及以上
4.前端依赖安装npm install
5.启动前端项目 npm run dev
#后端在back分支，前端在master
