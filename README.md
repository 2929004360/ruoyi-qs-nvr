<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">泉视视频监控系统-nvr</h1>
<h4 align="center">基于rtsp，rtmp，onvif，海康sdk，海康isup，大华sdk系统，集成nvr视频管理</h4>

## 平台简介

泉视是一套全部开源的快速开发平台，毫无保留给个人及企业免费使用。

* 后端采用Spring Boot3、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，权限认证使用Redis。
* 流量控制框架选型Sentinel，分布式事务选型Seata。
* 流媒体ZLMediaKit

## 系统模块

~~~
com.ruoyi     
├── RuoYi-Cloud-Vue3-typescript              // 前端框架 [80]
├── ruoyi-gateway         // 网关模块 [8080]
├── ruoyi-auth            // 认证中心 [9200]
├── ruoyi-api             // 接口模块
│       └── ruoyi-api-system                          // 系统接口
│       └── ruoyi-api-dahua                           // 大华sdk接口
│       └── ruoyi-api-haikang                         // 海康sdk接口
│       └── ruoyi-api-haikang-isup                    // 海康isup接口
│       └── ruoyi-api-onvif                           // onvif接口
│       └── ruoyi-api-qs                              // 泉视接口
│       └── ruoyi-api-zlm                             // zlm接口
├── ruoyi-common          // 通用模块
│       └── ruoyi-common-core                         // 核心模块
│       └── ruoyi-common-datascope                    // 权限范围
│       └── ruoyi-common-datasource                   // 多数据源
│       └── ruoyi-common-log                          // 日志记录
│       └── ruoyi-common-redis                        // 缓存服务
│       └── ruoyi-common-seata                        // 分布式事务
│       └── ruoyi-common-security                     // 安全模块
│       └── ruoyi-common-sensitive                    // 数据脱敏
│       └── ruoyi-common-swagger                      // 系统接口
├── ruoyi-modules         // 业务模块
│       └── ruoyi-system                              // 系统模块 [9201]
│       └── ruoyi-gen                                 // 代码生成 [9202]
│       └── ruoyi-job                                 // 定时任务 [9203]
│       └── ruoyi-file                                // 文件服务 [9300]
│       └── ruoyi-dahua                               // 大华sdk服务 [9207]
│       └── ruoyi-haikang                             // 海康sdk服务 [9204]
│       └── ruoyi-haikang-isup                        // 海康isup服务 [9206]
│       └── ruoyi-onvif                               // onvif服务 [9208]
│       └── ruoyi-qs                                  // 泉视服务 [9205]
│       └── ruoyi-zlm                                 // zlm服务 [8090]
├── ruoyi-visual          // 图形化管理模块
│       └── ruoyi-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
~~~

## 内置功能

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3. 岗位管理：配置系统用户所属担任职务。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7. 参数管理：对系统动态配置常用参数。
8. 通知公告：系统通知公告信息发布维护。
9. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
18. 分屏监控：支持 1/4/6/9 屏，快速预览监控画面。
19. 电子地图：采用天地图，GIS全域可视，设备精准落图。
20. 设备管理：支持接入rtsp，rtmp，onvif，海康sdk，海康isup，大华sdk等设备直播和操作。
21. 行政区域：分类设备。
22. 业务分组：分类设备。
23. 录像计划：支持按时间录制设备。
24. 云端录像：支持播放和下载录像。
25. 媒体节点：支持多zlm，重启zlm等

## 后续计划

1. 支持更多协议，比如gb28181，jt808，推流，宇视sdk，天地伟业sdk，萤石，乐橙等。
2. 支持全部设国标级联，点播，云台等操作。
3. 做一个文档，视频等，0基础上手

## 演示图

<table>
    <tr>
        <td><img src="https://img.cdn1.vip/i/69dfba274564f_1776269863.webp"/></td>
        <td><img src="https://img.cdn1.vip/i/69dfb92b21084_1776269611.webp"/></td>
    </tr>
    <tr>
        <td><img src="https://img.cdn1.vip/i/69dfb9746ac0a_1776269684.webp"/></td>
        <td><img src="https://img.cdn1.vip/i/69dfb9842dc2e_1776269700.webp"/></td>
    </tr>
    <tr>
        <td><img src="https://img.cdn1.vip/i/69dfb990b40d3_1776269712.webp"/></td>
        <td><img src="https://img.cdn1.vip/i/69dfb9b3c3dc2_1776269747.webp"/></td>
    </tr>
    <tr>
        <td><img src="https://img.cdn1.vip/i/69dfb90785b7b_1776269575.webp"/></td>
        <td><img src="https://img.cdn1.vip/i/69dfb9e769f6c_1776269799.webp"/></td>
    </tr>
    <tr>
        <td><img src="https://img.cdn1.vip/i/69dfb9f426816_1776269812.webp"/></td>
        <td><img src="https://img.cdn1.vip/i/69dfba087dd08_1776269832.webp"/></td>
    </tr>
    <tr>
        <td><img src="https://img.cdn1.vip/i/69dfba12efc81_1776269842.webp"/></td>
        <td><img src="https://img.cdn1.vip/i/69dfba1d4cf50_1776269853.webp"/></td>
    </tr>
</table>