<div align="center">

<br />
<img alt="logo" src="images/logo.png" width="150" height="150">

<br />
<br />

# 🔥 泉视视频监控系统-NVR

**下一代企业级智能视频监控解决方案**

<br />

<p align="center">
  <a href="https://github.com/2929004360/ruoyi-qs-nvr">
    <img alt="GitHub Stars" src="https://img.shields.io/github/stars/2929004360/ruoyi-qs-nvr?style=social&logo=github" height="24">
  </a>
  <a href="https://github.com/2929004360/ruoyi-qs-nvr/fork">
    <img alt="GitHub Forks" src="https://img.shields.io/github/forks/2929004360/ruoyi-qs-nvr?style=social&logo=github" height="24">
  </a>
</p>

<p align="center">
  <a href="https://github.com/2929004360/ruoyi-qs-nvr/releases">
    <img src="https://img.shields.io/github/v/release/2929004360/ruoyi-qs-nvr?style=flat-square&logo=github&color=orange" alt="Release" height="24">
  </a>
  <a href="https://github.com/2929004360/ruoyi-qs-nvr/blob/main/LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-yellow.svg?style=flat-square" alt="License" height="24">
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square&logo=springboot" height="24">
  <img src="https://img.shields.io/badge/Vue-3.x-blue?style=flat-square&logo=vue.js" height="24">
  <img src="https://img.shields.io/badge/Element%20Plus-2.x-409eff?style=flat-square&logo=element" height="24">
  <img src="https://img.shields.io/badge/ECharts-5.x-c71e23?style=flat-square&logo=apache" height="24">
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql" height="24">
  <img src="https://img.shields.io/badge/Redis-6.0-red?style=flat-square&logo=redis" height="24">
</p>

<p align="center">
  <a href="./README.md">English</a> | 
  <a href="./README.zh-CN.md">中文</a>
</p>

<br />

</div>

---

## 📜 项目简介

泉视视频监控系统是一套全部开源的企业级一站式视频监控解决方案，毫无保留给个人及企业免费使用。

- 🔗 **后端**：采用 Spring Boot 3、Spring Cloud & Alibaba
- 📦 **注册配置**：Nacos 注册中心、配置中心
- 🔒 **安全认证**：Redis 权限认证
- ⚡ **流量控制**：Sentinel 流量控制
- 📊 **分布式事务**：Seata 分布式事务
- 🎬 **流媒体**：ZLMediaKit 专业流媒体服务器
- 🎮 **播放器**：EasyPlayer 高性能播放器

---

## 🌟 核心亮点

<div align="center">
  <table>
    <tr>
      <td align="center" style="padding: 20px; border: 2px solid #3b82f6; border-radius: 12px; margin: 10px;">
        <h3>🔗 国标GB28181级联</h3>
        <p>支持上下级平台级联，目录推送，远程点播/回放/云台</p>
      </td>
      <td align="center" style="padding: 20px; border: 2px solid #10b981; border-radius: 12px; margin: 10px;">
        <h3>🚗 部标JT808/1078</h3>
        <p>车载设备接入，位置跟踪，车辆控制，轨迹回放</p>
      </td>
    </tr>
    <tr>
      <td align="center" style="padding: 20px; border: 2px solid #f59e0b; border-radius: 12px; margin: 10px;">
        <h3>🎮 多协议兼容</h3>
        <p>20+协议支持，海康/大华SDK一网打尽</p>
      </td>
      <td align="center" style="padding: 20px; border: 2px solid #ef4444; border-radius: 12px; margin: 10px;">
        <h3>📡 流媒体集群</h3>
        <p>支持多ZLMediaKit节点，负载均衡，高可用</p>
      </td>
    </tr>
  </table>
</div>

---

## 🌟 项目地址

- Gitee:
  - [ruoyi-qs-nvr](https://gitee.com/tangwenzhaoaini/ruoyi-qs-nvr) 后端地址
  - [ruoyi-qs-nvr-ui](https://gitee.com/tangwenzhaoaini/ruoyi-qs-nvr-ui) 前端地址

- GitHub:
  - [ruoyi-qs-nvr](https://github.com/2929004360/ruoyi-qs-nvr) 后端地址
  - [ruoyi-qs-nvr-ui](https://github.com/2929004360/ruoyi-qs-nvr-ui) 前端地址

---

## 📦 功能模块

### 🎬 1. 实时视频监控
- ✅ **多屏预览**：支持 1/4/6/9/12/16/25 屏自由切换，满足各种监控场景
- ✅ **分屏控制**：一键清空、保存布局、恢复布局、全屏模式
- ✅ **智能云台**：上下左右方向控制、变倍、聚焦、光圈调节、预置位巡航
- ✅ **语音对讲**：双向语音对讲，支持单路音频开关
- ✅ **截图抓拍**：实时画面快照保存
- ✅ **多码流切换**：普清、高清、超清三档码流自由切换
- ✅ **轮巡预览**：支持自动轮巡，可配置轮巡时间
- ✅ **布局管理**：支持自定义布局配置，本地存储持久化

### 🔗 2. 国标GB28181级联
- ✅ **平台级联**：支持上级平台注册（信令安全传输、心跳保活）
- ✅ **目录推送**：支持按平台、按分组、按区域推送设备目录
- ✅ **通道关联**：灵活配置需要向上级级联的通道
- ✅ **自动推送**：支持自动推送通道到上级平台
- ✅ **手动注册/注销**：支持手动控制级联注册状态
- ✅ **目录查询**：上级平台可查询下级设备目录
- ✅ **远程点播**：上级平台可远程点播实时视频
- ✅ **远程回放**：上级平台可远程回放历史录像
- ✅ **远程云台**：上级平台可远程控制云台
- ✅ **状态监控**：实时监控级联平台的在线状态
- ✅ **TCP/UDP传输**：支持TCP、UDP两种传输协议

### 🚗 3. 部标JT808/JT1078车载监控
- ✅ **车载设备接入**：完整支持JT808-2019协议、JT1078-2016协议
- ✅ **实时视频**：1~8路车载摄像头实时监控
- ✅ **位置跟踪**：实时GPS/北斗位置上报，实时位置查询
- ✅ **轨迹回放**：历史轨迹回放，支持时间轴拖动
- ✅ **临时跟踪**：临时位置跟踪控制，可设置跟踪间隔
- ✅ **区域管理**：支持圆形区域、矩形区域、多边形区域设置
- ✅ **路线管理**：行驶路线设置、删除、查询
- ✅ **车辆控制**：远程车辆控制（车门锁、车窗、油路控制）
- ✅ **终端参数**：查询和设置终端参数（8103/8104/8106/8107）
- ✅ **终端控制**：远程控制终端（8105）
- ✅ **报警处理**：人工确认报警消息（8203）
- ✅ **链路检测**：服务器向终端发起链路检测（8204）
- ✅ **文本下发**：8300文本信息下发到终端
- ✅ **事件设置**：事件处理参数设置（8301）
- ✅ **提问下发**：8302提问下发，支持选择题
- ✅ **信息点播**：8303信息点播菜单设置
- ✅ **信息服务**：8304信息服务（新闻、天气预报）
- ✅ **电话回拨**：8400电话回拨，设置电话本
- ✅ **行驶记录仪**：8700/8701/8702行驶记录仪数据采集和参数下发
- ✅ **摄像头控制**：8801立即拍照、8804开始录音、8802/8803/8805多媒体检索上传
- ✅ **终端升级**：8108下发终端升级包，支持远程OTA升级
- ✅ **音视频属性**：9003查询终端音视频属性
- ✅ **录像查询**：车载终端录像文件查询和回放

### 📡 4. 流媒体管理
- ✅ **多节点部署**：支持多个ZLMediaKit流媒体服务节点
- ✅ **负载均衡**：流媒体服务节点负载均衡
- ✅ **节点监控**：实时监控流媒体服务节点状态、负载情况
- ✅ **流信息查询**：查询指定应用和流的详细信息
- ✅ **节点管理**：添加、删除、修改流媒体服务节点
- ✅ **连接测试**：测试流媒体服务节点的连接状态
- ✅ **服务重启**：支持远程重启流媒体服务节点
- ✅ **拉流播放**：支持RTSP/RTMP拉流转FLV/WS-FLV/HLS播放
- ✅ **RTP推流**：支持GB28181和大华设备的RTP推流
- ✅ **流关闭**：支持手动关闭指定流
- ✅ **录像加载**：录像文件加载生成播放地址
- ✅ **推流地址**：自动生成设备推流地址

### 🗺️ 5. 电子地图
- ✅ **设备落图**：支持天地图、腾讯地图，设备精准落位
- ✅ **定位导航**：点击地图设备快速定位和跳转
- ✅ **区域管理**：行政区域划分，按区域管理设备
- ✅ **热力图**：设备在线状态热力图展示
- ✅ **轨迹回放**：移动设备历史轨迹回放
- ✅ **在线状态**：地图上实时显示设备在线、离线状态
- ✅ **设备分组**：按业务分组展示和管理设备

### 📹 6. 录像回放
- ✅ **云端录像**：7×24小时不间断录像存储
- ✅ **录像计划**：灵活配置录像计划（按时间、按设备）
- ✅ **录像检索**：按行政区域、业务分组、时间段检索录像
- ✅ **回放控制**：倍速播放、快进快退、时间轴拖动
- ✅ **录像下载**：支持MP4格式录像文件下载
- ✅ **多种回放**：支持海康SDK、大华SDK、GB28181、ONVIF等多种协议回放
- ✅ **时间轴**：可视化录像时间轴，直观展示录像时段

### 🚨 7. 智能告警
- ✅ **告警检测**：移动侦测、视频遮挡、越界检测、智能分析告警
- ✅ **告警推送**：实时告警消息推送通知
- ✅ **告警联动**：联动云台转向、联动录像、联动弹窗提示
- ✅ **告警查询**：历史告警记录查询和统计
- ✅ **告警统计**：告警数据可视化统计分析
- ✅ **告警处理**：告警确认、告警处理、告警归档

### 🏢 8. 设备管理
- ✅ **多协议接入**：20+协议支持（RTSP/RTMP/FLV/HLS/WS-FLV/ONVIF/GB28181/JT808/JT1078/海康SDK/海康ISUP/大华SDK/推流）
- ✅ **设备分组**：行政区域划分、业务分组管理
- ✅ **状态监控**：在线/离线/异常状态实时监控
- ✅ **批量操作**：批量配置、批量升级、批量重启
- ✅ **设备巡检**：自动巡检、健康检测、故障预警
- ✅ **设备配置**：远程配置设备参数
- ✅ **设备搜索**：ONVIF设备自动搜索和发现
- ✅ **设备信息**：设备详细信息展示和编辑

### 📊 9. 数据大屏
- ✅ **实时监控**：设备在线率、录像状态实时展示
- ✅ **统计分析**：告警统计、录像统计、设备接入统计
- ✅ **趋势图表**：设备接入趋势、告警趋势、流量趋势
- ✅ **大屏适配**：完美适配4K/8K超大屏显示
- ✅ **暗黑模式**：支持深色/浅色主题切换
- ✅ **数据可视化**：丰富的ECharts图表展示

### 👥 10. 系统管理
- ✅ **用户管理**：用户账号管理、角色分配
- ✅ **角色管理**：角色权限配置、菜单权限控制
- ✅ **菜单管理**：系统菜单配置
- ✅ **部门管理**：组织架构管理
- ✅ **岗位管理**：岗位信息管理
- ✅ **字典管理**：数据字典维护
- ✅ **参数设置**：系统参数配置
- ✅ **通知公告**：系统公告发布
- ✅ **日志管理**：操作日志、登录日志
- ✅ **服务监控**：服务器CPU、内存、磁盘监控
- ✅ **缓存监控**：Redis缓存状态监控
- ✅ **在线用户**：在线用户管理和踢人

---

## 🔧 协议支持

### 主流协议
| 协议 | 版本 | 状态 | 说明 |
|------|------|------|------|
| RTSP | - | ✅ 完整支持 | 实时流协议 |
| RTMP | - | ✅ 完整支持 | 推流协议 |
| HTTP-FLV | - | ✅ 完整支持 | FLV 直播流 |
| HLS | - | ✅ 完整支持 | HLS 切片流 |
| WebSocket-FLV | - | ✅ 完整支持 | WebSocket FLV |

### 设备SDK
| 厂商 | SDK | 状态 | 说明 |
|------|-----|------|------|
| 海康威视 | 设备网络 SDK | ✅ 完整支持 | 播放、云台、回放 |
| 海康威视 | ISUP Ehome | ✅ 完整支持 | 主动注册 |
| 大华 | DH SDK | ✅ 完整支持 | 播放、云台、回放 |

### 行业标准
| 标准 | 版本 | 状态 | 说明 |
|------|------|------|------|
| ONVIF | - | ✅ 完整支持 | 设备发现、PTZ、事件 |
| GB28181 | 2016/2022 | ✅ 完整支持 | 国标级联、点播、云台、回放 |
| JT808 | 2019 | ✅ 完整支持 | 部标 808 车载终端 |
| JT1078 | 2016 | ✅ 完整支持 | 部标 1078 视频传输 |

---

## 📁 项目结构

```
com.ruoyi
├── ruoyi-qs-nvr-ui       // 前端框架 [80]
├── ruoyi-qs-nvr-app      // 前端app框架 暂未开发
├── ruoyi-gateway         // 网关模块 [8080]
├── ruoyi-auth            // 认证中心 [9200]
├── ruoyi-api             // 接口模块
│       └── ruoyi-api-system                          // 系统接口
│       └── ruoyi-api-dahua                           // 大华sdk接口
│       └── ruoyi-api-gb28181                         // 国标28181接口
│       └── ruoyi-api-haikang                         // 海康sdk接口
│       └── ruoyi-api-haikang-isup                    // 海康isup接口
│       └── ruoyi-api-jt1078                          // 部标808和部标1078接口
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
│       └── ruoyi-gb28181                             // 国标28181服务 [9209]
│       └── ruoyi-jt1078                              // 部标808和部标1078服务 [9210]
│       └── ruoyi-dahua                               // 大华sdk服务 [9207]
│       └── ruoyi-haikang                             // 海康sdk服务 [9204]
│       └── ruoyi-haikang-isup                        // 海康isup服务 [9206]
│       └── ruoyi-onvif                               // onvif服务 [9208]
│       └── ruoyi-qs                                  // 泉视服务 [9205]
│       └── ruoyi-zlm                                 // zlm服务 [8090]
├── ruoyi-visual          // 图形化管理模块
│       └── ruoyi-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
```

---

## 📋 后续计划

### 已实现
- ✅ RTSP、RTMP、ONVIF、FLV、HLS、视频文件
- ✅ 海康SDK、海康ISUP、大华SDK
- ✅ GB28181、JT808、JT1078
- ✅ 推流设备接入

### 规划中
- 🔄 支持更多协议（宇视SDK、天地伟业SDK、萤石、乐橙等）

---

## 📄 授权协议

本项目自有代码使用宽松的 MIT 协议，在保留版权信息的情况下可以自由应用于各自商用、非商业的项目。

但是本项目也使用了一些其他的开源代码，在商用的情况下请自行替代或剔除；由于使用本项目而产生的商业纠纷或侵权行为一概与本项目及开发者无关，请自行承担法律风险。

在使用本项目代码时，也应该在授权协议中同时表明本项目依赖的第三方库的协议。

---

## 📺 视频教程

- [ruoyi-qs-nvr 项目介绍](https://www.bilibili.com/video/BV1HCdzBQE4c/)
- [ruoyi-qs-nvr 项目启动](https://www.bilibili.com/video/BV11RdBBsEsY/)
- [ruoyi-qs-nvr 设备接入-RTSP设备](https://www.bilibili.com/video/BV187oTBVEjw/)
- [ruoyi-qs-nvr 设备接入-RTMP设备](https://www.bilibili.com/video/BV1gLoTB1E91/)
- [ruoyi-qs-nvr 设备接入-FLV设备](https://www.bilibili.com/video/BV1gLoTB1Esd/)
- [ruoyi-qs-nvr 设备接入-HLS设备](https://www.bilibili.com/video/BV18LoTByEoR/)
- [ruoyi-qs-nvr 设备接入-ONVIF设备](https://www.bilibili.com/video/BV11CoKBiEoz/)
- [ruoyi-qs-nvr 设备接入-视频文件设备](https://www.bilibili.com/video/BV12CoKBiE7a/)
- [ruoyi-qs-nvr 设备接入-海康SDK设备](https://www.bilibili.com/video/BV11CoKBiEyz/)
- [ruoyi-qs-nvr 设备接入-海康ISUP设备](https://www.bilibili.com/video/BV12koKBTE7n/)
- [ruoyi-qs-nvr 设备接入-大华SDK设备](https://www.bilibili.com/video/BV12koKBTEku/)
- [ruoyi-qs-nvr 设备接入-PUSH设备](https://www.bilibili.com/video/BV14koKBMEzp/)

---

## 🎨 界面预览

<div align="center">
  <table>
    <tr>
      <td><img src="images/1.png" width="100%"/></td>
      <td><img src="images/2.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/3.png" width="100%"/></td>
      <td><img src="images/4.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/5.png" width="100%"/></td>
      <td><img src="images/6.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/7.png" width="100%"/></td>
      <td><img src="images/8.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/9.png" width="100%"/></td>
      <td><img src="images/10.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/11.png" width="100%"/></td>
      <td><img src="images/12.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/13.png" width="100%"/></td>
      <td><img src="images/14.png" width="100%"/></td>
    </tr>
    <tr>
      <td><img src="images/15.png" width="100%"/></td>
      <td><img src="images/16.png" width="100%"/></td>
    </tr>
  </table>
</div>

---

## 💰 付费社群

也可以自愿有偿加入知识星球咨询：

<div align="center">
  <img src="images/xingqui.jpg" width="50%" height="50%">
</div>

---

## 💖 致谢支持

感谢以下优秀的开源项目和作者：

- [ZLMediaKit](https://docs.zlmediakit.com/zh/) - 专业级流媒体服务器
- [EasyPlayer](https://www.tsingsee.com/) - 高性能播放器
- [RuoYi Cloud](https://doc.ruoyi.vip/ruoyi-cloud/) - 优秀的微服务框架
- [JT808-Server](https://gitee.com/yezhihao/jt808-server) - 部标808/1078框架
- [WVP-GB28181-PRO](https://gitee.com/pan648540858/wvp-GB28181-pro) - 国标级联平台
- 感谢作者[胖虎](https://gitee.com/daofuli) 提供技术支持

---

<div align="center">
  如果这个项目对你有帮助，请给个 Star ⭐ 支持一下！

  <br/>
  <br/>

  Made with ❤️ by 泉视团队
</div>