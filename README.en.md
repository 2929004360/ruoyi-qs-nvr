<p align="center">
  <img alt="logo" src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/logo.png" width="100" height="100">
</p>

<div align="center">

[English](./README.md) | [中文](./README.zh-CN.md)

</div>

<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Quanshi Video Surveillance System - NVR</h1>
<h4 align="center">Based on RTSP, RTMP, ONVIF, Hikvision SDK, Hikvision ISUP, Dahua SDK, GB28181, JT808, JT1078, integrated NVR video management</h4>

## Platform Introduction

Quanshi is a fully open-source rapid development platform, freely available for personal and enterprise use.

* Backend uses Spring Boot 3, Spring Cloud & Alibaba.
* Service registry and configuration center using Nacos, authentication using Redis.
* Traffic control framework using Sentinel, distributed transaction using Seata.
* Streaming media using ZLMediaKit.
* Player using EasyPlayer.

## Documentation

* [ZLMediaKit](https://docs.zlmediakit.com/zh/) Streaming Media Server
* [RuoYi Cloud](https://doc.ruoyi.vip/ruoyi-cloud/) RuoYi Cloud Documentation
* [RuoYi QS NVR](http://1.12.55.122:5173/) Project Documentation

## Project Address
* Gitee:
  * [ruoyi-qs-nvr](https://gitee.com/tangwenzhaoaini/ruoyi-qs-nvr) Backend Address
  * [ruoyi-qs-nvr-ui](https://gitee.com/tangwenzhaoaini/ruoyi-qs-nvr-ui) Frontend Address

* GitHub:
  * [ruoyi-qs-nvr](https://github.com/2929004360/ruoyi-qs-nvr) Backend Address
  * [ruoyi-qs-nvr-ui](https://github.com/2929004360/ruoyi-qs-nvr-ui) Frontend Address

## System Modules

~~~
com.ruoyi
├── ruoyi-qs-nvr-ui       // Frontend Framework [80]
├── ruoyi-qs-nvr-app      // Frontend App Framework (Under Development)
├── ruoyi-gateway         // Gateway Module [8080]
├── ruoyi-auth            // Authentication Center [9200]
├── ruoyi-api             // API Module
│       └── ruoyi-api-system                          // System API
│       └── ruoyi-api-dahua                           // Dahua SDK API
│       └── ruoyi-api-gb28181                         // GB28181 API
│       └── ruoyi-api-haikang                         // Hikvision SDK API
│       └── ruoyi-api-haikang-isup                    // Hikvision ISUP API
│       └── ruoyi-api-jt1078                          // JT808 and JT1078 API
│       └── ruoyi-api-onvif                           // ONVIF API
│       └── ruoyi-api-qs                              // Quanshi API
│       └── ruoyi-api-zlm                             // ZLM API
├── ruoyi-common          // Common Module
│       └── ruoyi-common-core                         // Core Module
│       └── ruoyi-common-datascope                    // Data Scope
│       └── ruoyi-common-datasource                   // Multi Datasource
│       └── ruoyi-common-log                          // Logging
│       └── ruoyi-common-redis                        // Redis Service
│       └── ruoyi-common-seata                        // Distributed Transaction
│       └── ruoyi-common-security                     // Security Module
│       └── ruoyi-common-sensitive                    // Data Desensitization
│       └── ruoyi-common-swagger                      // System API
├── ruoyi-modules         // Business Module
│       └── ruoyi-system                              // System Module [9201]
│       └── ruoyi-gen                                 // Code Generator [9202]
│       └── ruoyi-job                                 // Scheduled Task [9203]
│       └── ruoyi-file                                // File Service [9300]
│       └── ruoyi-gb28181                             // GB28181 Service [9209]
│       └── ruoyi-jt1078                              // JT808 and JT1078 Service [9210]
│       └── ruoyi-dahua                               // Dahua SDK Service [9207]
│       └── ruoyi-haikang                             // Hikvision SDK Service [9204]
│       └── ruoyi-haikang-isup                        // Hikvision ISUP Service [9206]
│       └── ruoyi-onvif                               // ONVIF Service [9208]
│       └── ruoyi-qs                                  // Quanshi Service [9205]
│       └── ruoyi-zlm                                 // ZLM Service [8090]
├── ruoyi-visual          // Visualization Module
│       └── ruoyi-visual-monitor                      // Monitoring Center [9100]
├──pom.xml                // Common Dependencies
~~~

## Built-in Features

1. User Management: Users are system operators, this function mainly completes system user configuration.
2. Department Management: Configure system organization (company, department, group), tree structure display supports data permissions.
3. Position Management: Configure positions that users belong to.
4. Menu Management: Configure system menus, operation permissions, button permission identifiers, etc.
5. Role Management: Role menu permission allocation, set role data range permissions by organization.
6. Dictionary Management: Maintain some relatively fixed data that is frequently used in the system.
7. Parameter Management: Dynamically configure common parameters for the system.
8. Notification Announcement: System notification announcement information release and maintenance.
9. Operation Log: System normal operation log recording and query; system exception information log recording and query.
10. Login Log: System login log recording and query including login exceptions.
11. Online Users: Current active user status monitoring in the system.
12. Scheduled Tasks: Online (add, modify, delete) task scheduling including execution result logs.
13. Code Generation: Frontend and backend code generation (Java, HTML, XML, SQL) supports CRUD download.
14. System Interface: Automatically generate related API interface documentation based on business code.
15. Service Monitoring: Monitor current system CPU, memory, disk, stack and other related information.
16. Online Builder: Drag form elements to generate corresponding HTML code.
17. Connection Pool Monitoring: Monitor current system database connection pool status, can analyze SQL to find system performance bottlenecks.
18. Split Screen Monitoring: Supports 1/4/6/9 screens, quick preview of surveillance footage.
19. Electronic Map: Uses Tianditu, GIS full-domain visualization, device precise mapping, achieving device precise positioning.
20. Device Management: Supports access to RTSP, RTMP, ONVIF, FLV, HLS, video files, Hikvision SDK, Hikvision ISUP, Dahua SDK, GB28181, JT808, JT1078, push stream and other device live streaming and operations.
21. Administrative Region: Devices can be divided by different regions.
22. Business Group: Devices can be divided by different businesses.
23. Recording Plan: Supports all recordings, supports recording devices by time, supports 24-hour recording.
24. Cloud Recording: Supports all protocol recordings, can play and download recordings.
25. Media Nodes: Supports multiple ZLM, restart ZLM, etc.
26. Recording Playback: Supports cloud recordings obtained by administrative region and business group, and view playback.

## Future Plans

* Implemented
  * RTSP, RTMP, ONVIF, FLV, HLS, video files, Hikvision SDK, Hikvision ISUP, Dahua SDK, GB28181, JT808, JT1078, push stream and other device additions

* Not Implemented
  * Support more protocols, such as Uniview SDK, Tiandy SDK, EZVIZ, LeChange, etc.
  * Support full GB28181 cascade, VOD, PTZ and other operations.

## License

The project's own code uses the permissive MIT license, and can be freely applied to commercial and non-commercial projects while retaining copyright information.
However, this project also sporadically uses some other open-source code. Please replace or eliminate it yourself in commercial situations; commercial disputes or infringements arising from the use of this project are unrelated to this project and its developers,
please bear legal risks yourself. When using this project code, you should also indicate the licenses of the third-party libraries that this project depends on in the license agreement.

## Video Tutorials

* [RuoYi QS NVR Project Introduction](https://www.bilibili.com/video/BV1HCdzBQE4c/)
* [RuoYi QS NVR Project Startup](https://www.bilibili.com/video/BV11RdBBsEsY/)
* [RuoYi QS NVR Device Access - RTSP Device](https://www.bilibili.com/video/BV187oTBVEjw/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - RTMP Device](https://www.bilibili.com/video/BV1gLoTB1E91/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - FLV Device](https://www.bilibili.com/video/BV1gLoTB1Esd/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - HLS Device](https://www.bilibili.com/video/BV18LoTByEoR/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - ONVIF Device](https://www.bilibili.com/video/BV11CoKBiEoz/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - Video File Device](https://www.bilibili.com/video/BV12CoKBiE7a/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - Hikvision SDK Device](https://www.bilibili.com/video/BV11CoKBiEyz/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - Hikvision ISUP Device](https://www.bilibili.com/video/BV12koKBTE7n/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - Dahua SDK Device](https://www.bilibili.com/video/BV12koKBTEku/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - Push Stream Device](https://www.bilibili.com/video/BV14koKBMEzp/?spm_id_from=333.1387.homepage.video_card.click)
* [RuoYi QS NVR Device Access - GB28181 - Preparing to Record Video]()
* [RuoYi QS NVR Device Access - JT808 and JT1078 - Preparing to Record Video]()

## Demo Images

<table>
    <tr>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/1.png"/></td>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/3.png"/></td>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/5.png"/></td>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/6.png"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/7.png"/></td>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/8.png"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/9.png"/></td>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/10.png"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/11.png"/></td>
        <td><img src="https://raw.giteeusercontent.com/tangwenzhaoaini/ruoyi-qs-nvr/raw/master/images/12.png"/></td>
    </tr>
</table>

## Acknowledgments

* Thank you to [Tsingsee](https://www.tsingsee.com/) for providing such a great player
* Thank you to [Xia Chu](https://docs.zlmediakit.com/zh/) for providing such a great open-source streaming media service framework.
* Thank you to [RuoYi](https://doc.ruoyi.vip/ruoyi-cloud/) for open-sourcing such a great framework.
* Thank you to [Jian Qi Jin](https://gitee.com/yezhihao/jt808-server) for open-sourcing such a great JT808 and JT1078 framework.
* Thank you to [panll](https://gitee.com/pan648540858/wvp-GB28181-pro) for open-sourcing such a great GB28181 framework.
* Thank you to [Pang Hu](https://gitee.com/daofuli) for providing technical support.
