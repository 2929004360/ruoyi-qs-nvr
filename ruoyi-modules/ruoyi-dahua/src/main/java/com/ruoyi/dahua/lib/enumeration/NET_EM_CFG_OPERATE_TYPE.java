package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.structure.NET_CFG_HEALTH_CODE_INFO;
import com.ruoyi.dahua.lib.structure.NET_CFG_VSP_GAYS_SERVER_INFO;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * 配置枚举值列表 对应接口: 获取配置 {@link NetSDKLib#CLIENT_GetConfig(NetSDKLib.LLong, int, int, Pointer, int,
 * int, Pointer)} 下发配置 {@link NetSDKLib#CLIENT_SetConfig(NetSDKLib.LLong, int, int, Pointer, int,
 * int, IntByReference, Pointer)}
 */
public class NET_EM_CFG_OPERATE_TYPE {
    public static final int   NET_EM_CFG_SNAP_MODE = 0;             // 抓图模式配置,对应结构体 NET_SNAP_MODE
    public static final int   NET_EM_CFG_DEV_CAR_COACH = 1;         // 铁路记录配置, 对应结构体 NET_DEV_CAR_COACH_INFO
    public static final int   NET_EM_CFG_YUEQING_SUPPLYLIGHTING = 2; // 乐清外接灯光配置, 对应结构体 NET_YUEQING_SUPPLYLIGHTING_INFO
    public static final int   NET_EM_CFG_MEDIA_GLOBAL = 3;          // 媒体组件全局配置, 对应结构体 NET_MEDIA_GLOBAL_INFO
    public static final int   NET_EM_CFG_PARKINGSPACELIGHT_STATE = 5; // 车位状态对应的车位指示灯, 对应结构体 NET_PARKINGSPACELIGHT_STATE_INFO
    public static final int   NET_EM_CFG_COAXIAL_LIGHT = 6;         // 白光灯视频通道配置, 对应结构体 NET_CFG_COAXIAL_LIGHT_INFO
    public static final int   NET_EM_CFG_VIDEO_OUT = 7;             // 视频输出配置, 对应结构体 NET_CFG_VIDEO_OUT_INFO
    public static final int   NET_EM_CFG_MEDIA_ENCRYPT = 9;         // 媒体数据加密配置,对应结构体 NET_MEDIA_ENCRYPT_INFO,与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_COUNTRY = 10;              // 国家/地区配置, 对应结构体 NET_CFG_COUNTRY_INFO,与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_VIDEOSTANDARD = 11;        // 视频制式配置, 对应结构体 NET_CFG_VIDEOSTANDARD_INFO,与通道不相关,通道号需要填成-1, 目前设备不支持配置SECAM制式
    public static final int   NET_EM_CFG_SERIAL_PUSH_LOG = 12;      // 串口服务器推送日志配置， 对应结构体 NET_CFG_SERIALPUSHLOG_INFO，与通道不相关需要填成-1
    public static final int   NET_EM_CFG_PRESET_SNAP_PICTURE_NUM = 13; // 预置点抓拍图片数量配置,对应结构体 NET_CFG_PRESET_SNAP_PICTURE_NUM
    public static final int   NET_EM_CFG_DOWNLOAD_ENCRYPT = 14;     // 下载数据加密配置, 对应结构体 NET_DOWNLOAD_ENCRYPT_INFO,与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_LIFT_CONTROL_OPTION = 15;  // 梯控功能全局配置，对应结构体 NET_CFG_LIFTCONTROL_OPTION,与通道不相关,通道号需要填成-1
    /**
     * 健康码使能配置，对应结构体{@link NET_CFG_HEALTH_CODE_INFO}, 与通道不相关,通道号需要填成-1
     */
    public static final int   NET_EM_CFG_HEALTH_CODE = 16;          // 健康码使能配置，对应结构体 NET_CFG_HEALTH_CODE_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_RTMP = 17;                 // RTMP 配置，对应结构体 NET_CFG_RTMP_INFO，与通道不相关,通道号需要填成
    public static final int   NET_EM_CFG_ACCESSCONTROL_MEASURE_TEMP = 20; // 门禁测温配置, 对应结构体 NET_CFG_ACCESSCONTROL_MEASURE_TEMP_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_QR_CODE = 26;              // 配置二维码有效时间, 对应的结构体 NET_CFG_QR_CODE_INFO , 通道号需要填成-1
    /**
     * /// 动态检测报警配置, 对应结构体 NET_CFG_MOTION_DETECT_INFO
    */
    public static final int   NET_EM_CFG_MOTION_DETECT = 38;
    public static final int   NET_EM_CFG_TRAFFIC_LAMP_CONFIG = 45;  // 时间间隔配置, 对应结构体 NET_CFG_TRAFFIC_LAMP_CONFIG_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_DISABLE_BEEP_LINKAGE = 47; // 防蜂鸣联动项使能配置, 对应结构体 NET_CFG_DISABLE_BEEP_LINKAGE_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_AREA_ARM_MODE = 48;        // Area布撤防配置, 对应结构体 NET_CFG_AREA_ARM_MODE_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_CONTROL_CASE = 61;         // 控制智能机箱开关配置, 对应结构体 NET_CFG_CONTROL_CASE_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_LAMP_EXTERNAL_DEVICE = 62; // 智慧灯杆项外设配置, 对应结构体 NET_CFG_LAMP_EXTERNAL_DEVICE_INFO, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_AUTO_PAN = 71;             // 水平旋转设置配置, 对应结构体 NET_CFG_AUTO_PAN_INFO
    /**
     * /// 休眠策略配置, 对应结构体 NET_CFG_SLEEPING_CONTROL_INFO, 与通道不相关,通道号需要填成-1
    */
    public static final int   NET_EM_CFG_SLEEPING_CONTROL = 74;
    /**
     *  录像/图片存储点配置, 对应结构体 NET_CFG_RECORD_STORAGE_POINT_INFO
    */
    public static final int   NET_EM_CFG_RECORD_STORAGE_POINT = 99;
    /*********OSD叠加相关配置*************************************************************************************************/
    public static final int   NET_EM_CFG_CHANNELTITLE = 1000;       // 叠加通道标题属性配置，对应结构体 NET_OSD_CHANNEL_TITLE,其中结构体中的emOsdBlendType为必填参数
    public static final int   NET_EM_CFG_TIMETITLE = 1001;          // 叠加时间标题属性配置，对应结构体 NET_OSD_TIME_TITLE,其中结构体中的emOsdBlendType为必填参数
    public static final int   NET_EM_CFG_CUSTOMTITLE = 1002;        // 叠加自定义标题属性配置，对应结构体 NET_OSD_CUSTOM_TITLE,其中结构体中的stuCustomTitle.emOsdBlendType为必填参数
    public static final int   NET_EM_CFG_CUSTOMTITLETEXTALIGN = 1003; // 叠加自定义标题对齐方式属性配置，对应结构体 NET_OSD_CUSTOM_TITLE_TEXT_ALIGN
    public static final int   NET_EM_CFG_OSDCOMMINFO = 1004;        // 叠加公共属性配置，对应结构体 NET_OSD_COMM_INFO
    public static final int   NET_EM_CFG_OSD_PTZZOOM = 1005;        // 变倍叠加配置，对应结构体 NET_OSD_PTZZOOM_INFO
    public static final int   NET_EM_CFG_GPSTITLE = 1006;           // 叠加GPS标题显示配置，对应结构体 NET_OSD_GPS_TITLE
    public static final int   NET_EM_CFG_OSD_NUMBERSTATPLAN = 1007; // 人数统计计划叠加OSD配置, 支持NumberStatPlan算法大类时(球机)使用，对应结构体NET_OSD_NUMBER_STATPLAN
    public static final int   NET_EM_CFG_GPSSTARNUM_OSD = 1008;     // GPS搜星数OSD配置, 需求, 对应结构体 NET_CFG_GPSSTARNUM_OSD_INFO
    public static final int   NET_EM_CFG_PICTURETITLE = 1009;       // 图片标题,对应结构体 NET_OSD_PICTURE_TITLE
    public static final int   NET_EM_CFG_OSD_FACEFLOWSTAT = 1010;   // 人脸数量统计OSD配置，对应结构体 NET_OSD_FACEFLOWSTAT_INFO
    public static final int   NET_EM_CFG_PTZ_PRESET = 1011;         // 预置点信息叠加,对应结构体 NET_OSD_CFG_PTZ_PRESET
    public static final int   NET_EM_CFG_PTZ_PATTERN = 1012;        // 显示巡迹百分比叠加配置,对应结构体 NET_OSD_CFG_PTZ_PATTERN
    public static final int   NET_EM_CFG_PTZ_RS485_DETECT = 1013;   // 显示RS485检测结果的叠加配置,对应结构体 NET_OSD_CFG_PTZ_RS485_DETECT
    public static final int   NET_EM_CFG_PTZ_COORDINATES = 1014;    // 显示云台坐标的叠加配置,对应结构体 NET_OSD_CFG_PTZ_COORDINATES
    public static final int   NET_EM_CFG_PTZ_DIRECTION = 1015;      // 显示云台方向的叠加配置,对应结构体 NET_OSD_CFG_PTZ_DIRECTION
    public static final int   NET_EM_CFG_TEMPERATURE = 1016;        // 显示环境温度的叠加配置,对应结构体 NET_OSD_CFG_TEMPERATURE
    public static final int   NET_EM_CFG_COVERS = 1017;             // 区域覆盖,对应结构体 NET_OSD_CFG_COVERS
    public static final int   NET_EM_CFG_USER_DEF_TITLE = 1018;     // 用户自定义OSD标题，对应结构体 NET_OSD_USER_DEF_TITLE  DHOP专用
    public static final int   NET_EM_CFG_SNAP_OSD = 1019;           // 抓拍OSD配置, 对应结构体 NET_CFG_SNAP_OSD
    /**
     * /// 视频OSD国标模式配置， 对应结构体NET_CFG_GBMODE_INFO
    */
    public static final int   NET_EM_CFG_GBMODE = 1021;
    /*********encode 相关配置*************************************************************************************************/
    public static final int   NET_EM_CFG_ENCODE_VIDEO = 1100;       // 编码视频格式属性配置，对应结构体 NET_ENCODE_VIDEO_INFO
    public static final int   NET_EM_CFG_ENCODE_VIDEO_PACK = 1101;  // 编码视频格式打包模式配置，对应结构体 NET_ENCODE_VIDEO_PACK_INFO
    public static final int   NET_EM_CFG_ENCODE_VIDEO_SVC = 1102;   // 编码视频格式SVC配置，对应结构体 NET_ENCODE_VIDEO_SVC_INFO
    public static final int   NET_EM_CFG_ENCODE_VIDEO_PROFILE = 1103; // 编码视频格式profile配置，对应结构体 NET_ENCODE_VIDEO_PROFILE_INFO
    public static final int   NET_EM_CFG_ENCODE_AUDIO_COMPRESSION = 1104; // 编码音频压缩格式配置，对应结构体 NET_ENCODE_AUDIO_COMPRESSION_INFO
    public static final int   NET_EM_CFG_ENCODE_AUDIO_INFO = 1105;  // 编码音频格式配置，对应结构体 NET_ENCODE_AUDIO_INFO
    public static final int   NET_EM_CFG_ENCODE_SNAP_INFO = 1106;   // 编码抓图配置，对应结构体 NET_ENCODE_SNAP_INFO
    public static final int   NET_EM_CFG_ENCODE_SNAPTIME = 1107;    // 编码抓图时间相关配置，对应结构体 NET_ENCODE_SNAP_TIME_INFO
    public static final int   NET_EM_CFG_ENCODE_CHANNELTITLE = 1108; // 通道名称配置，对应结构体 NET_ENCODE_CHANNELTITLE_INFO
    /**********音频相关配置***************************************************************************************************/
    public static final int   NET_EM_CFG_AUDIOIN_SOURCE = 1200;     // 音频输入类型配置，对应结构体 NET_ENCODE_AUDIO_SOURCE_INFO
    public static final int   NET_EM_CFG_AUDIOIN_DENOISE = 1201;    // 音频降噪配置，对应结构体 NET_AUDIOIN_DENOISE_INFO
    public static final int   NET_EM_CFG_AUDIOIN_VOLUME = 1202;     // 音频输入音量配置，对应结构体 NET_AUDIOIN_VOLUME_INFO
    public static final int   NET_EM_CFG_AUDIOOUT_VOLUME = 1203;    // 音频输出音量配置，对应结构体 NET_AUDIOOUT_VOLUME_INFO
    public static final int   NET_EM_CFG_AUDIOOUT_MODE = 1204;      // 音频输出模式配置, 对应结构体 NET_AUDIOOUT_MODE_INFO
    public static final int   NET_EM_CFG_LOOPPLAYBACK_AUDIOALARM = 1205; // 音频循环播放报警配置，对应结构体 NET_LOOPPLAYBACK_AUDIOALARM_INFO,与通道不相关,通道号需要填成-1
    /**********videoin 相关配置***********************************************************************************************/
    public static final int   NET_EM_CFG_VIDEOIN_SWITCHMODE = 1300; // 切换模式配置，对应结构体 NET_VIDEOIN_SWITCH_MODE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_COLOR = 1301;      // 视频输入颜色配置，对应结构体 NET_VIDEOIN_COLOR_INFO
    public static final int   NET_EM_CFG_VIDEOIN_IMAGE_OPT = 1302;  // 图像属性配置，对应结构体 NET_VIDEOIN_IMAGE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_STABLE = 1303;     // 图像防抖配置，对应结构体 NET_VIDEOIN_STABLE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_IRISAUTO = 1304;   // 自动光圈配置，对应结构体 NET_VIDEOIN_IRISAUTO_INFO
    public static final int   NET_EM_CFG_VIDEOIN_IMAGEENHANCEMENT = 1305; // 图像增强配置，对应结构体 NET_VIDEOIN_IMAGEENHANCEMENT_INFO
    public static final int   NET_EM_CFG_VIDEOIN_EXPOSURE_NORMAL = 1306; // 曝光通用属性配置，对应结构体 NET_VIDEOIN_EXPOSURE_NORMAL_INFO
    public static final int   NET_EM_CFG_VIDEOIN_EXPOSURE_OTHER = 1307; // 其他曝光属性配置，对应结构体 NET_VIDEOIN_EXPOSURE_OTHER_INFO
    public static final int   NET_EM_CFG_VIDEOIN_EXPOSURE_SHUTTER = 1308; // 曝光快门配置，对应结构体 NET_VIDEOIN_EXPOSURE_SHUTTER_INFO
    public static final int   NET_EM_CFG_VIDEOIN_BACKLIGHT = 1309;  // 背光配置，对应结构体 NET_VIDEOIN_BACKLIGHT_INFO
    public static final int   NET_EM_CFG_VIDEOIN_INTENSITY = 1310;  // 场景自适应对比度强度配置，对应结构体 NET_VIDEOIN_INTENSITY_INFO
    public static final int   NET_EM_CFG_VIDEOIN_LIGHTING = 1311;   // 补光灯配置，对应结构体 NET_VIDEOIN_LIGHTING_INFO
    public static final int   NET_EM_CFG_VIDEOIN_DEFOG = 1312;      // 透雾配置，对应结构体 NET_VIDEOIN_DEFOG_INFO
    public static final int   NET_EM_CFG_VIDEOIN_FOCUSMODE = 1313;  // 聚焦模式配置，对应结构体 NET_VIDEOIN_FOCUSMODE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_FOCUSVALUE = 1314; // 聚焦信息配置，对应结构体 NET_VIDEOIN_FOCUSVALUE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_WHITEBALANCE = 1315; // 白平衡配置，对应结构体 NET_VIDEOIN_WHITEBALANCE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_DAYNIGHT = 1316;   // 日夜模式配置，对应结构体 NET_VIDEOIN_DAYNIGHT_INFO
    public static final int   NET_EM_CFG_VIDEOIN_DAYNIGHT_ICR = 1317; // 日夜模式ICR切换类型配置，对应结构体 NET_VIDEOIN_DAYNIGHT_ICR_INFO
    public static final int   NET_EM_CFG_VIDEOIN_SHARPNESS = 1318;  // 锐度配置，对应结构体 NET_VIDEOIN_SHARPNESS_INFO
    public static final int   NET_EM_CFG_VIDEOIN_COMM_DENOISE = 1319; // 通用降噪配置，对应结构体 NET_VIDEOIN_DENOISE_INFO
    public static final int   NET_EM_CFG_VIDEOIN_3D_DENOISE = 1320; // 3D 降噪配置，对应结构体 NET_VIDEOIN_3D_DENOISE_INFO
    /***********庭审相关配置*****************************************************************************************/
    public static final int   NET_EM_CFG_ENCODE_PLAN = 1400;        // 刻录光盘编码计划, 对应结构体 NET_ENCODE_PLAN_INFO
    /**
     * 合成通道配置, 对应结构体{@link com.ruoyi.dahua.lib.structure.NET_COMPOSE_CHANNEL_INFO}
     */
    public static final int   NET_EM_CFG_COMPOSE_CHANNEL = 1401;    // 合成通道配置, 对应结构体 NET_COMPOSE_CHANNEL_INFO
    /**
     * 刻录参数, 对应结构体 {@link com.ruoyi.dahua.lib.structure.NET_BURN_PARAM_INFO}
     */
    public static final int   NET_EM_CFG_BURN_PARAM = 1402;         // 刻录参数, 对应结构体 NET_BURN_PARAM_INFO
    public static final int   NET_EM_CFG_COMPOSE_PLAN = 1403;       // 合成通道的预案分割配置, 对应结构体 NET_COMPOSE_PLAN_INFO
    /**********报警网关相关配置**************************************************************************************/
    public static final int   NET_EM_CFG_ALARM_SOUND = 1500;        // 报警网关语音配置, 对应结构体 NET_ALARM_SOUND_INFO
    public static final int   NET_EM_CFG_FIRE_WARNINGMODE = 1503;   // 火灾预警模式, 对应结构体 NET_FIREWARNING_MODE_INFO
    public static final int   NET_EM_CFG_FIRE_WARNING = 1504;       // 火灾预警配置(与火灾预警模式配置配合使用), 对应的结构体 NET_FIRE_WARNING_INFO
    /***********热成像相关配置******************************************************************************************/
    public static final int   NET_EM_CFG_HOT_COLD_SPOT_WARNING = 1505; // 热冷点报警配置, 对应的结构体 NET_HOT_COLD_SPOT_WARNING_INFO
    /**********网络应用相关配置**************************************************************************************/
    public static final int   NET_EM_CFG_ACCESS_POINT = 1600;       // 用于WiFi服务端配置(热点功能), 对应结构体 NET_NETAPP_ACCESSPOINT
    /**************安全基线需求**************************************************************************************/
    public static final int   NET_EM_CFG_NAS = 1700;                // NAS 配置, 对应结构体 NET_NAS_INFO
    public static final int   NET_EM_CFG_PPPOE = 1701;              // PPPOE 配置，对应结构体 NET_PPPOE_INFO
    public static final int   NET_EM_CFG_EMAIL = 1702;              // Email 配置，对应结构体 NET_EAMIL_INFO
    public static final int   NET_EM_CFG_DDNS = 1703;               // DDNS 配置，对应结构体 NET_DDNS_INFO
    /**************SCADA配置需求**************************************************************************************/
    public static final int   NET_EM_CFG_SCADA_PROTOCOLS_MANAGER = 1800; // 协议管理配置，对应结构体 NET_SCADA_PROTOCOLS_MANAGER
    public static final int   NET_EM_CFG_SCADA_DEVICEINFO_CFG = 1801; // 设备信息配置，对应结构体 NET_SCADA_DEVICEINFO_CFG
    /**************NetApp配置需求*************************************************************************************/
    public static final int   NET_EM_CFG_NETAPP_LINK_LAYER_VPN = 1900; // 链路层VPN设置,对应结构体 NET_NETAPP_LINK_LAYER_VPN_CFG
    public static final int   NET_EM_CFG_NETAPP_COMMUNICATION_LIST = 1902; // 通讯录配置, 对应结构体 NET_NETAPP_COMMUNICATION_LIST_CFG, 与通道不相关,通道号需要填成-1
    /**************中国铁塔平台接入***********************************************************************************/
    public static final int   NET_EM_CFG_VSP_CHINA_TOWER = 2000;    // 安徽治超平台接入配置,对应结构体 NET_VSP_CHINA_TOWER
    /**********智能相关配置*******************************************************************************************/
    public static final int   NET_EM_CFG_STEREO_CALIBRATE = 2100;   // 双目标定结果, 对应结构体NET_STEREO_CALIBRATE_INFO
    public static final int   NET_EM_CFG_STEREO_CALIBRATEMATRIX_MULTISENSOR = 2101; // 多目相机标定配置CalibrateMatrix(MultiSensor), 对应的结构体 NET_MULTI_SENSOR_INFO
    public static final int   NET_EM_CFG_CROWDDISTRIMAP_CALIBRATE = 2102; // 人群分布图标定配置, 对应的结构体 NET_CROWDDISTRIMAP_CALIBRATE_INFO
    public static final int   NET_EM_CFG_TRAFFIC_NOPASSING = 2103;  // 交通禁行配置, 对应的结构体 NET_TRAFFIC_NOPASSING_INFO
    public static final int   NET_EM_CFG_FIGHT_CALIBRATE = 2104;    // 打架标定配置, 对应的结构体 NET_FIGHT_CALIBRATE_INFO
    public static final int   NET_EM_CFG_FACE_RECOGNITION_ALARM = 2105; // 目标识别联动报警通道配置, 对应结构体 NET_FACE_RECOGNITION_ALARM_INFO
    public static final int   NET_EM_CFG_STEREO_CALIBRATEMATRIX_MULTIMODE = 2106; // 灵瞳相机的标定方式 CalibrateMatrix(MultiMode), 对应的结构体 NET_CALIBRATEMATRIX_MULTIMODE_INFO
    public static final int   NET_EM_CFG_AUTO_SNAP_SCHEDULE = 2107; // 智能养殖定时抓图配置, 对应结构体 NET_CFG_AUTO_SNAP_SCHEDULE_INFO
    public static final int   NET_EM_CFG_PARKING_TIMEOUT_DETECT = 2108; // 停车超时检测配置, 对应结构体 NET_CFG_PARKING_TIMEOUT_DETECT
    /**
     * /// 智能动态检测配置, 对应结构体 NET_CFG_SMART_MOTION_DETECT
    */
    public static final int   NET_EM_CFG_SMART_MOTION_DETECT = 2109;
    /**
     * /// 课堂行为分析规则配置,对应结构体CFG_CLASSROOM_BEHAVIOR_INFO
    */
    public static final int   NET_EM_CFG_CLASSROOM_BEHAVIOR = 2120;
    /**
     * /// 开关门检测配置,对应结构体 NET_CFG_DOOR_STATE_DETECTION_INFO
    */
    public static final int   NET_EM_CFG_DOOR_STATE_DETECTION = 2122;
    /**
     * /// 反复开关门检测配置,对应结构体 NET_CFG_OPEN_CLOSE_DOOR_DETECTION_INFO
    */
    public static final int   NET_EM_CFG_OPEN_CLOSE_DOOR_DETECTION = 2123;
    /**
     * /// 电梯困人检测配置,对应结构体 NET_CFG_TRAPPED_IN_LIFT_DETECTION_INFO
    */
    public static final int   NET_EM_CFG_TRAPPED_IN_LIFT_DETECTION = 2124;
    /**********雷达配置***********************************************************************************************/
    public static final int   NET_EM_CFG_RADAR = 2200;              // 雷达配置，对应结构体 DEV_RADAR_CONFIG
    public static final int   NET_EM_CFG_RADAR_RFIDCARD = 2201;     // 雷达RFID卡片信息配置，与通道不相关,通道号需要填成-1,对应结构体 NET_CFG_RADAR_RFIDCARD_INFO
    /**********视频对讲电话通用配置***********************************************************************************/
    public static final int   NET_EM_CFG_VTH_PASSWORD = 2300;       // 视频对讲电话通用配置,对应结构体 NET_CFG_VTH_PASSWORD_INFO
    public static final int   NET_EM_CFG_REGISTAR = 2301;           // 注册服务器配置,对应结构体 NET_CFG_REGISTAR_INFO
    public static final int   NET_EM_CFG_SIP = 2302;                // sip配置, 对应结构体 NET_CFG_SIPSERVER_INFO
    /**********镜头前遮挡盖配置***************************************************************************************/
    public static final int   NET_EM_CFG_AELENSMASK = 2400;         // 镜头前遮挡盖配置，对应结构体NET_CFG_AELENSMASK_INFO
    public static final int   NET_EM_CFG_ULTRASONIC = 2500;         // 超声波配置，对应结构体NET_CFG_ULTRASONIC_INFO
    /**********报警主机相关配置***************************************************************************************/
    public static final int   NET_EM_CFG_ARMSCHEDULE = 2600;        // 报警计划配置，对应结构体 NET_CFG_ARMSCHEDULE_INFO
    public static final int   NET_EM_CFG_RECORDEXTRA = 3610;        // 录像辅码流录像配置, 对应结构体 NET_CFG_RECORDEXTRA_INFO
    /**********录像抓图功能相关配置***********************************************************************************/
    public static final int   NET_EM_CFG_TIME_RECORDBACKUP_RESTORE = 3613; // 录像定时自动备份配置, NET_CFG_TIME_RECORDBACKUP_RESTORE_INFO; 对应通道为-1
    /**********视频诊断相关配置***************************************************************************************/
    public static final int   NET_EM_VIDEODIAGNOSIS_PROJECT = 3700; // 视频诊断计划配置, 对应结构体NET_CFG_VIDEODIAGNOSIS_PROJECT_INFO
    /***********车载相关配置******************************************************************************************/
    public static final int   NET_EM_CFG_POSITIONREPORTPOLICY = 3800; // 车载GPS位置信息上报策略配置, 对应结构体 NET_CFG_POSITIONREPORTPOLICY_INFO
    public static final int   NET_EM_CFG_VEHICLE_WORKTIMESCHEDULE = 3801; // 车载工作计划配置，对应结构体 NET_CFG_VEHICLE_WORKTIMESCHEDULE_INFO
    public static final int   NET_EM_CFG_VEHICLE_LOAD = 3802;       // 荷载人数配置, 对应结构体 NET_CFG_VEHICLE_LOAD_INFO
    /***********门禁相关配置******************************************************************************************/
    public static final int   NET_EM_CFG_ACCESSCTL_BLACKLIST = 3900; // 门禁禁止名单报警配置，对应结构体 NET_CFG_ACCESSCTL_BLACKLIST
    public static final int   NET_EM_CFG_ACCESSCTL_BLACKLIST_LINK = 3901; // 门禁禁止名单报警联动配置,对应结构体 NET_CFG_ALARM_MSG_HANDLE
    /**
     * 门禁节假日组配置, 对应结构体 {@link com.ruoyi.dahua.lib.structure.NET_CFG_ACCESSCTL_SPECIALDAY_GROUP_INFO}
     */
    public static final int   NET_EM_CFG_ACCESSCTL_SPECIALDAY_GROUP = 3902; // 门禁节假日组配置, 对应结构体 NET_CFG_ACCESSCTL_SPECIALDAY_GROUP_INFO
    /**
     * 门禁节假日计划配置, 对应结构体 NET_CFG_ACCESSCTL_SPECIALDAYS_SCHEDULE_INFO
     */
    public static final int   NET_EM_CFG_ACCESSCTL_SPECIALDAYS_SCHEDULE = 3903; // 门禁节假日计划配置, 对应结构体 NET_CFG_ACCESSCTL_SPECIALDAYS_SCHEDULE_INFO
    /**
     * 在离线开门认证模式配置，对应结构体NET_CFG_ACCESSCTL_AUTH_MODE
     */
    public static final int   NET_EM_CFG_ACCESSCTL_AUTH_MODE = 3904; // 在离线开门认证模式配置，对应结构体NET_CFG_ACCESSCTL_AUTH_MODE
    public static final int   NET_EM_CFG_ACS_FACE_RECOGNITION_SCHEME = 3905; //  门禁目标识别相关配置,对应结构体 NET_CFG_ACS_FACE_RECOGNITION_SCHEME
    /**
     * 广告禁用时段配置, 对应结构体 {@link com.ruoyi.dahua.lib.structure.NET_CFG_FORBIDDEN_ADVERT_PLAY}, 通道无关，通道号填-1
     */
    public static final int   NET_EM_CFG_FORBIDDEN_ADVERT_PLAY = 3906; // 广告禁用时段配置, 对应结构体 NET_CFG_FORBIDDEN_ADVERT_PLAY, 通道无关，通道号填-1
    /**
     * 门禁设备当前模式配置, 对应结构体 {@link com.ruoyi.dahua.lib.structure.NET_CFG_BGY_CUSTOMERCFG}, 通道无关，通道号填-1
     */
    public static final int   NET_EM_CFG_BGY_CUSTOMERCFG = 3907;    // 门禁设备当前模式配置, 对应结构体 NET_CFG_BGY_CUSTOMERCFG, 通道无关，通道号填-1
    public static final int   NET_EM_CFG_ACCESSCTL_KEYBINDINGINFOCFG = 3908; // 不同数字按钮对应的信息配置, 对应结构体 NET_CFG_ACCESSCTL_KEYBINDINGINFOCFG, 通道无关，通道号填-1
    public static final int   NET_EM_CFG_HOST_DEVICE = 3909;        // 主机、从机设置配置, 对应结构体 NET_CFG_HOST_DEVICE, 通道无关，通道号填-1
    public static final int   NET_EM_CFG_VTH_ALPA_CLOUD = 3910;     // 室内机Alpa客户云服务器配置, 对应结构体 NET_CFG_VTH_ALPA_CLOUD, 通道无关， 通道号填-1
    public static final int   NET_EM_CFG_VTH_FETCHINFO = 3911;      // 室内机广告信息配置，对应结构体 NET_CFG_VTH_FETCHINFO， 通道无关， 通道号填-1
    public static final int   NET_EM_CFG_VIDEOTALK_SIGNLIGHT = 3917; // 签名灯，可视对讲使用配置，对应结构体 NET_CFG_VIDEOTALK_SIGNLIGHT  通道无关 通道号填-1
    public static final int   NET_EM_CFG_BSCCUSTOM = 3919;          // 门禁配置汇总 对应结构体 NET_CFG_BSCCUSTOM 通道无关 通道号填-1
    public static final int   NET_EM_CFG_CONSUMPTION_STRATEGY = 3921; 
    // 消费相关策略, 对应结构体 NET_CFG_CONSUMPTION_STRATEGY_INFO, 通道无关 通道号填-1
    public static final int   NET_EM_CFG_WIEGAND = 3922;            
    // 韦根配置, 对应结构体 NET_CFG_WIEGAND_INFO, 通道无关 通道号填-1
    /**
     * /// 门禁设备显示配置, 对应结构体 NET_CFG_ACCESS_DISPLAY_INFO, 通道无关, 通道号填-1
    */
    public static final int   NET_EM_CFG_ACCESS_DISPLAY = 3925;
    public static final int   NET_EM_CFG_CUSTOMER_DEV_SETTING = 3931; // 门禁提供客户的"系统设置"配置, 对应结构体 NET_CFG_CUSTOMER_DEV_SETTING_INFO 通道无关， 通道号填-1
    public static final int   NET_EM_CFG_USER_PASS_DATA_COUNT_CLEAR = 3934; 
    // 定期通行人数清除功能配置, 对应结构体 NET_EM_CFG_USER_PASS_DATA_COUNT_CLEAR_INFO 通道无关, 通道号填-1
    /***********配置************************************************************************************************/
    public static final int   NET_EM_CFG_SERIALNOWHITETABLE = 4000; // 前端序列号允许名单下发至NVR 配置, 对应结构体 NET_CFG_SERIALNOWHITETABLE_INFO
    public static final int   NET_EM_CFG_VSP_LXSJ = 4004;           // 灵信视觉平台接入配置 对应结构体 NET_CFG_VSP_LXSJ_INFO，配置较大，建议内存new出来
    public static final int   NET_EM_CFG_FILE_HOLD_DAYS = 4010;     // 录像保存天数配置, 对应结构体NET_CFG_FILE_HOLD_DAYS_INFO
    public static final int   NET_EM_CFG_CARD_MNG = 4016;           // 单片机兼容cpu卡和ic卡功能切换,对应结构体 NET_CFG_CARD_MNG_INFO
    public static final int   NET_EM_CFG_FINANCIAL_CABINET_CONFIG = 4020; // 金融业务库配置 对应结构体 NET_CFG_FINANCIAL_CABINET_CONFIG_INFO
    /**
     * /// 电梯楼层报警相关配置, 对应结构体 NET_CFG_ELEVATOR_ALARM_CONFIG_INFO, 与通道不相关，通道号需要填成-1
    */
    public static final int   NET_EM_CFG_ELEVATOR_ALARM_CONFIG = 4026;
    /**
     * /// 智慧杆Camera配置, 对应结构体 NET_CFG_POLE_CAMERA_INFO
    */
    public static final int   NET_EM_CFG_POLE_CAMERA = 4033;
    /**
     * /// 车位状态指示灯控制配置, 对应结构体 NET_CFG_PARKING_SPACE_LIGHT_CONTROL_INFO, 与通道不相关, 通道号需要填成-1
    */
    public static final int   NET_EM_CFG_PARKING_SPACE_LIGHT_CONTROL = 4034;
    /**
     * /// 停车场语音播报配置, 对应结构体 NET_CFG_PARK_SPACE_VOICE_BROADCAST_INFO
    */
    public static final int   NET_EM_CFG_PARK_SPACE_VOICE_BROADCAST = 4035;
    /***********雷球联动配置************************************************************************************************/
    //在调用 CLIENT_SetConfig 时,restart字段无效
    public static final int   NET_EM_CFG_RADAR_DEVLIST = 6010;      // 雷达设备列表配置, 对应结构体NET_CFG_RADAR_DEVLIST_INFO
    public static final int   NET_EM_CFG_RADAR_SCREEN_RULE = 6012;  // 雷达屏幕显示规则配置, 对应结构体NET_CFG_RADAR_SCREEN_RULE_INFO
    public static final int   NET_EM_CFG_MINIRADAR_DECT_PARAM = 6016; // Mini雷达探测参数配置, 对应结构体 NET_CFG_MINIRADAR_DECT_PARAM_INFO
    public static final int   NET_EM_CFG_MINIRADAR_INDOOR_ALARM = 6017; // Mini雷达报警配置, 对应结构体 NET_CFG_MINIRADAR_INDOOR_ALARM_INFO
    public static final int   NET_EM_CFG_MINIRADAR_COMP_INFO = 6018; // Mini雷达角度补偿配置, 对应结构体 NET_CFG_MINIRADAR_COMP_INFO
    public static final int   NET_EM_CFG_MINIRADAR_INSTALL_INFO = 6019; // Mini雷达安装信息配置, 对应结构体 NET_CFG_MINIRADAR_INSTALL_INFO
    public static final int   NET_EM_CFG_MINIRADAR_REGION_DETECT = 6020; // Mini雷达探测区域配置, 对应结构体 NET_CFG_MINIRADAR_REGION_DETECT_INFO
    /**
     * /// 云台水平旋转组扫描 对应结构体 NET_CFG_HORIZONTAL_ROTATION_GROUP_SCAN_INFO 
    */
    public static final int   NET_EM_CFG_AUTOSCAN = 7002;
    public static final int   NET_EM_CFG_PTZ_MOVEMENT = 7008;       // 云台动作配置, 对应结构体 NET_CFG_PTZ_MOVEMENT_INFO
    /***********第三方配置************************************************************************************************/
    public static final int   NET_EM_CFG_VSP_PAAS = 9000;           // 乐橙云注册配置修改, 对应结构体 NET_CFG_VSP_PAAS_INFO
    /**
     * 平台接入配置(国标服务端), 对应结构体 {@link NET_CFG_VSP_GAYS_SERVER_INFO} ,对应通道为-1
     */
    public static final int   NET_EM_CFG_VSP_GAYS_SERVER = 9001;
    public static final int   NET_EM_CFG_VSP_CO_SIGN_SERVER = 9002; // 协同签名服务器配置,对应结构体NET_CFG_VSP_CO_SIGN_SERVER_INFO,对应通道为-1
    public static final int   NET_EM_CFG_VSP_GAVI = 9003;           // 公安视频图像信息应用系统接入配置,对应结构体NET_CFG_VSP_GAVI_INFO,对应通道为-1
    /**
     * /// ONVIF服务配置, 对应结构体 NET_CFG_VSP_ONVIF_INFO, 对应通道为-1
    */
    public static final int   NET_EM_CFG_VSP_ONVIF = 9004;
    /***********道闸配置************************************************************************************************/
    public static final int   NET_EM_CFG_TRAFFICSTROBE = 9100;      // 道闸配置, 对应结构体 NET_CFG_TRAFFICSTROBE_INFO
	/***********布撤防配置************************************************************************************************/
    public static final int   NET_EM_CFG_DISABLE_LINKAGE = 9300;    // 一键撤防配置,对应结构体 NET_CFG_DISABLE_LINKAGE, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_DISABLE_LINKAGE_TIME_SECTION = 9301; // 周期撤防联动项功能总开关, 对应结构体 NET_CFG_DISABLE_LINKAGE_TIME_SECTION, 与通道不相关,通道号需要填成-1
    public static final int   NET_EM_CFG_REMOTE_DISABLE_LINKAGE_TIME_SECTION_SYNC = 9303; //  远程通道是否同步本地端"周期一键布撤防"配置, 对应结构体 NET_CFG_REMOTE_DISABLE_LINKAGE_TIME_SECTION_SYNC_INFO, 与通道不相关,接口通道号需要填成-1
    /***********Traffic相关配置***************************************************************************************************/
    public static final int   NET_EM_CFG_TRAFFIC_LATTICE_SCREEN = 10000; // 点阵屏显示信息配置, 对应结构体 NET_CFG_TRAFFIC_LATTICE_SCREEN_INFO
    public static final int   NET_EM_CFG_TRAFFIC_VOICE_BROADCAST = 10001; // 智能交通语音播报配置， 对应结构体 NET_CFG_TRAFFIC_VOICE_BROADCAST_INFO
    public static final int   NET_EM_CFG_SCENE_SNAP_SHOT_WITH_RULE2 = 10002; // 场景抓拍设置， 对应结构体 NET_CFG_SCENE_SNAP_SHOT_WITH_RULE2_INFO
    public static final int   NET_EM_CFG_SENSOR_MAINTAIN = 11100;   // 热成像机芯维护配置,对应结构体 NET_CFG_SENSOR_MAINTAIN
    public static final int   NET_EM_CFG_TILT_LIMIT = 11101;        // 激光测距俯仰角限制,对应结构体 NET_CFG_TILT_LIMIT
    public static final int   NET_EM_CFG_RADIO_REGULATOR = 11102;   // 人体测温标准黑体配置, 对应结构体 NET_CFG_RADIO_REGULATOR，通道号不能为-1
    public static final int   NET_EM_CFG_HUMAN_THERM_CALIBRATE = 11103; // 人体测温标定信息, 对应结构体 NET_CFG_HUMAN_THERM_CALLBRATE_INFO ，通道号必须是-1
    public static final int   NET_EM_CFG_TELESCOPE_OPTIONS = 11104; // 望远镜配置,对应结构体 NET_CFG_TELESCOPE_OPTIONS_INFO
    public static final int   NET_EM_CFG_ENVIRONMENT_THERM_INFO = 11105; // 环境温度信息(热成像内部配置,只可读,不可设),通道号需要填热成像通道号  对应结构体 NET_CFG_ENVIRONMENT_THERM_INFO
    public static final int   NET_EM_CFG_THERMOMETRY_SHIELD = 11106; // 测温屏蔽区配置 对应结构体 NET_CFG_THERMOMETRY_SHIELD_INFO
    /***********录播默认配置******************************************************************************************/
    public static final int   NET_EM_CFG_COURSE_RECORD_DEFAULT_CONFIG = 12100; // 录播默认配置, 对应结构体 NET_CFG_COURSE_RECORD_DEFAULT_CONFIG
    public static final int   NET_EM_CFG_COURSE_RECORD_UPLOAD = 12101; // 录播设备支持录像文件上传配置, 对应结构体 NET_CFG_COURSE_RECORD_UPLOAD
    /***********硬盘相关配置******************************************************************************************/
    public static final int   NET_EM_CFG_NAS_DIRECTORY = 12302;     // 共享文件夹信息配置, 对应结构体 NET_CFG_NAS_DIRECTORY
    /**
     * /// 声音配置, 对应结构体 NET_CFG_SOUND
    */
    public static final int   NET_EM_CFG_SOUND = 12600;
    /***********DHRS相关配置******************************************************************************************/
    public static final int   NET_EM_CFG_DHRS = 12700;              // DH485串口点阵屏配置, 对应结构体 NET_CFG_DHRS
    /***********信号机相关配置******************************************************************************************/
    public static final int   NET_EM_CFG_RTSC_SCH_CROSSLIST = 14000; // 路口数据列表配置, 对应结构体NET_CFG_RTSC_SCH_CROSSLIST_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_CHANNELS = 14001; // 通道表, 对应结构体NET_CFG_RTSC_SCH_CHANNELS_INFO
    public static final int   NET_EM_CFG_RTSC_SCH_PLANS = 14002;    // 周期方案表,  对应结构体NET_CFG_RTSC_SCH_PLANS_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_MANAGE = 14003;   // 红绿灯调度计划列表, 对应结构体 NET_CFG_RTSC_SCH_MANAGE_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_VEHICLE_DETECTORLIST = 14004; // 检测器列表，对应结构体 NET_CFG_RTSC_SCH_VEHICLE_DETECTORLIST_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_PEDDETECTOR_LIST = 14005; // 行人检测器列表，对应结构体 NET_CFG_RTSC_SCH_PEDDETECTOR_LIST_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_PEDDESTRAIN_PRIORITY = 14006; // 行人优先智能化配置，对应结构体 NET_CFG_RTSC_SCH_PEDDESTRAIN_PRIORITY_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_OVERFLOW_CONTROL = 14007; // 溢出控制列表，对应结构体 NET_CFG_RTSC_SCH_OVERFLOW_CONTROL_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_NIGHTASK = 14008; // 夜间请求，对应结构体 NET_CFG_RTSC_SCH_NIGHTASK_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_OPTIMIZES = 14009; // 饱和度与单点自适应智能化配置，对应结构体 NET_CFG_RTSC_SCH_OPTIMIZES_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_VARLANELIST = 14010; // 可变车道列表，对应结构体 NET_CFG_RTSC_SCH_VARLANELIST_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_SCH_VARLANE_MANAGEMENT = 14011; // 可变车道调度计划列表，对应结构体 NET_CFG_RTSC_SCH_VARLANE_MANAGEMENT_INFO, 与通道无关, 通道号需填成-1
    public static final int   NET_EM_CFG_RTSC_TEMP_SCH = 14012;     // 临时方案配置，对应结构体 NET_CFG_RTSC_TEMP_SCH_INFO, 与通道无关, 通道号需填成-1
}

