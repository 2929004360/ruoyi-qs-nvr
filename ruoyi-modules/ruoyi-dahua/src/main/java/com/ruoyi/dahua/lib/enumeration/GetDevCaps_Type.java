package com.ruoyi.dahua.lib.enumeration;
/**
 * @author 47081
 * @desc
 * @version 1.0.0
 * @date 2021/4/12
 */
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.structure.NET_IN_VIDEOIN_DAYNIGHT_CAPS;
import com.ruoyi.dahua.lib.structure.NET_OUT_VIDEOIN_DAYNIGHT_CAPS;
import com.sun.jna.Pointer;

/**
 * @author 47081
 * @description 设备能力类型,对应 {@link NetSDKLib#CLIENT_GetDevCaps(NetSDKLib.LLong, int, Pointer, Pointer,
 *     int)}的nType参数 {@see}注解为暂未实现的封装 {@link}注解对应已有实现封装
 * @date 2021/4/12
 * @version 1.0
 */
public enum GetDevCaps_Type {
  /** 电源时序器能力,pInBuf对应{@see NET_IN_CAP_SEQPOWER}, pOutBuf对应{@see NET_OUT_CAP_SEQPOWER} */
    NET_DEV_CAP_SEQPOWER(0x01, "电源时序器能力"),
  /** pInBuf对应NET_IN_ENCODE_CFG_CAPS pOutBuf对应 NET_OUT_ENCODE_CFG_CAPS */
    NET_ENCODE_CFG_CAPS(0x02, "设备编码配置对应能力"),
  /** pInBuf=NET_IN_VIDEOIN_FISHEYE_CAPS, pOutBuf=NET_OUT_VIDEOIN_FISHEYE_CAPS* */
    NET_VIDEOIN_FISHEYE_CAPS(0x03, "鱼眼能力"),
  /** pInBuf=NET_IN_COMPOSITE_CAPS, pOutBuf=NET_OUT_COMPOSITE_CAPS */
    NET_COMPOSITE_CAPS(0x04, "根据指定的窗口号预先获取融合后的能力集"),
  /** pInBuf=NET_IN_VIDEO_DETECT_CAPS, pOutBuf=NET_OUT_VIDEO_DETECT_CAPS */
    NET_VIDEO_DETECT_CAPS(0x05, "获取视频检测输入能力集"),
  /**
   * pInBuf对应{@link NetSDKLib.NET_IN_THERMO_GETCAPS}, pOutBuf对应{@link
   * NetSDKLib.NET_OUT_THERMO_GETCAPS}
   */
    NET_THERMO_GRAPHY_CAPS(0x06, "热成像摄像头属性能力"),
  /**
   * pInBuf对应{@link NetSDKLib.NET_IN_RADIOMETRY_GETCAPS}, pOutBuf对应{@link
   * NetSDKLib.NET_OUT_RADIOMETRY_GETCAPS}
   */
    NET_RADIOMETRY_CAPS(0x07, "热成像测温全局配置能力"),
  /** pInBuf = NET_IN_POS_GETCAPS,pOutBuf = NET_OUT_POS_GETCAPS */
    NET_POS_CAPS(0x08, "POS机能力"),
  /** pInBuf = NET_IN_USER_MNG_GETCAPS, pOutBuf = NET_OUT_USER_MNG_GETCAPS */
    NET_USER_MNG_CAPS(0x09, "用户管理能力"),
  /** pInBuf=NET_IN_MEDIAMANAGER_GETCAPS,pOutBuf=NET_OUT_MEDIAMANAGER_GETCAPS */
    NET_MEDIAMANAGER_CAPS(0x0a, "获取 VideoInput 的各个能力项"),
  /** pInBuf=NET_IN_MEDIA_VIDEOMOSAIC_GETCAPS,pOutBuf=NET_OUT_MEDIA_VIDEOMOSAIC_GETCAPS */
    NET_VIDEO_MOSAIC_CAPS(0x0b, "获取通道马赛克叠加能力"),
  /** pInBuf=NET_IN_SNAP_CFG_CAPS, pOutBuf= NET_OUT_SNAP_CFG_CAPS */
    NET_SNAP_CFG_CAPS(0x0c, "设备抓图配置对应能力"),
  /** pInBUf = NET_IN_VIDEOIN_CAPS, pOutBuf = NET_OUT_VIDEOIN_CAPS */
    NET_VIDEOIN_CAPS(0x0d, "设备视频输出能力"),
  /** pInBuf = NET_IN_FACEBOARD_CAPS, pOutBuf = NET_OUT_FACEBOARD_CAPS */
    NET_FACE_BOARD_CAPS(0x0e, "面板设备能力集"),
  /** pInBuf = NET_IN_EXTERNALSENSOR_CAPS, pOutBuf =NET_OUT_EXTERNALSENSOR_CAPS */
    NET_EXTERNALSENSOR_CAPS(0x0f, "外部传感器管理能力集"),
  /** pInBuf = NET_IN_VIDEO_IMAGECONTROL_CAPS, pOutBuf =NET_OUT_VIDEO_IMAGECONTROL_CAPS* */
    NET_VIDEO_IMAGECONTROL_CAPS(0x10, "图像旋转设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_EXPOSURE_CAPS, pOutBuf = NET_OUT_VIDEOIN_EXPOSURE_CAPS */
    NET_VIDEOIN_EXPOSURE_CAPS(0x11, "曝光设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_DENOISE_CAPS, pOutBuf = NET_OUT_VIDEOIN_DENOISE_CAPS */
    NET_VIDEOIN_DENOISE_CAPS(0x12, "降噪能力"),
  /** pInBuf = NET_IN_VIDEOIN_BACKLIGHT_CAPS, pOutBuf = NET_OUT_VIDEOIN_BACKLIGHT_CAPS */
    NET_VIDEOIN_BACKLIGHT_CAPS(0x13, "背光设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_WHITEBALANCE_CAPS, pOutBuf = NET_OUT_VIDEOIN_WHITEBALANCE_CAPS */
    NET_VIDEOIN_WHITEBALANCE_CAPS(0x14, "白平衡设置能力"),
  /**
   * pInBuf = {@link NET_IN_VIDEOIN_DAYNIGHT_CAPS}, pOutBuf = {@link NET_OUT_VIDEOIN_DAYNIGHT_CAPS}
   */
    NET_VIDEOIN_DAYNIGHT_CAPS(0x15, "球机机芯日夜设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_ZOOM_CAPS, pOutBuf = NET_OUT_VIDEOIN_ZOOM_CAPS */
    NET_VIDEOIN_ZOOM_CAPS(0x16, "变倍设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_FOCUS_CAPS, pOutBuf = NET_OUT_VIDEOIN_FOCUS_CAPS */
    NET_VIDEOIN_FOCUS_CAPS(0x17, "聚焦设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_SHARPNESS_CAPS, pOutBuf = NET_OUT_VIDEOIN_SHARPNESS_CAPS */
    NET_VIDEOIN_SHARPNESS_CAPS(0x18, "锐度设置能力"),
  /** pInBuf = NET_IN_VIDEOIN_COLOR_CAPS, pOutBuf = NET_OUT_VIDEOIN_COLOR_CAPS */
    NET_VIDEOIN_COLOR_CAPS(0x19, "图像设置能力"),
  /** pInBuf = NET_IN_GET_MASTERSLAVEGROUP_CAPS, pOutBuf = // NET_OUT_GET_MASTERSLAVEGROUP_CAPS */
    NET_GET_MASTERSLAVEGROUP_CAPS(0x1a, "获取跟踪业务能力"),
  /**
   * pInBuf = NET_IN_FACERECOGNITIONSERVER_CAPSBILITYQUERY, pOutBuf =
   * NET_OUT_FACERECOGNITIONSERVER_CAPSBILITYQUERY *
   */
    NET_FACERECOGNITIONSE_CAPS(0x1b, "目标识别服务器能力查询"),
  /** pInBuf = NET_IN_STORAGE_CAPS, pOutBuf = NET_OUT_STORAGE_CAPS */
    NET_STORAGE_CAPS(0x1c, "获取存储能力集"),
  /** pInBuf = NET_IN_VIDEOIN_RAWFRAME_CAPS, pOutBuf = // NET_OUT_VIDEOIN_RAWFRAME_CAPS */
    NET_VIDEOIN_RAWFRAME_CAPS(0x1d, "获取视频输入扩展能力集"),
  /** pInBuf = NET_IN_GET_COAXIAL_CONTROL_IO_CAPS, pOutBuf = NET_OUT_GET_COAXIAL_CONTROL_IO_CAPS */
    NET_COAXIAL_CONTROL_IO_CAPS(0x1e, "获取同轴IO控制能力"),
  /** pInBuf = NET_IN_GET_FACEINFO_CAPS, pOutBuf = NET_OUT_GET_FACEINFO_CAPS */
    NET_FACEINFO_CAPS(0x1f, "获得人脸门禁控制器能力集"),
  /** pInBuf = NET_IN_AC_CAPS, pOutBuf = NET_OUT_AC_CAPS */
    NET_ACCESSCONTROL_CAPS(0x20, "获取门禁能力"),
  /**
   * pInBuf = NET_IN_GET_COAXIAL_CONTROL_IO_STATUS, pOutBuf = NET_OUT_GET_COAXIAL_CONTROL_IO_STATUS*
   */
    NET_COAXIAL_CONTROL_IO_STATUS(0x21, "获取同轴IO白光灯和喇叭状态"),
  /** pInBuf =NET_IN_LIGHTINGCONTROL_CAPS,pOutBuf=NET_OUT_LIGHTINGCONTROL_CAPS */
    NET_LIGHTINGCONTROL_CAPS(0x22, "获取补光灯能力(IPC/SD使用)"),
  /** pInBuf =NET_IN_SUPPORT_GET_AUDIO_DECODE_CAPS,pOutBuf=NET_OUT_SUPPORT_GET_AUDIO_DECODE_CAPS* */
    NET_SUPPORT_GET_AUDIO_DECODE_CAPS(0x23, "获取设备是否支持音频解码能力获取"),
  /** pInBuf = NET_IN_UNIFIEDINFOCOLLECT_CAPS, pOutBuf = NET_OUT_UNIFIEDINFOCOLLECT_CAPS */
    NET_UNIFIEDINFOCOLLECT_CAPS(0x24, "获取设备是否支持DMSS专用协议能力"),
  /** pInBuf = NET_IN_EXALARMBOX_CAPS, pOutBuf = NET_OUT_EXALARMBOX_CAPS */
    NET_EXALARMBOX_CAPS(0x25, "获取某个扩展报警盒的能力集"),
  /** pInBuf = NET_IN_ALARMBOXMANAGER_CAPS, pOutBuf = NET_OUT_ALARMBOXMANAGER_CAPS */
    NET_ALARMBOXMANAGER_CAPS(0x26, "获取报警盒子管理器能力集"),
  /** pInBuf = NET_IN_GET_AUDIO_DETECT_CAPS, pOutBuf = NET_OUT_GET_AUDIO_DETECT_CAPS */
    NET_GET_AUDIO_DETECT_CAPS(0x27, "获取音频输入能力集"),
  /**
   * pInBuf = NET_IN_SUPPORT_FACEDB_DOWNLOAD_CAPS, pOutBuf = NET_OUT_SUPPORT_FACEDB_DOWNLOAD_CAPS
   */
    NET_SUPPORT_FACEDB_DOWNLOAD(0x28, "获取是否支持远程人脸库下载"),
  /** pInBuf = NET_IN_GET_LENS_FUNC_CAPS, pOutBuf = NET_OUT_GET_LENS_FUNC_CAPS */
    NET_GET_LENS_FUNC_CAPS(0x29, "获取角度调节能力集"),
  /** pInBuf = NET_IN_VIDEO_TALK_MANAGER_CAPS, pOutBuf = NET_OUT_VIDEO_TALK_MANAGER_CAPS */
    NET_VIDEO_TALK_MANAGER_CAPS(0x2a, "获取可视对讲能力"),
  /** pInBuf = NET_IN_VIDEO_IN_DEFOG_CAPS, pOutBuf = NET_OUT_VIDEO_IN_DEFOG_CAPS */
    NET_VIDEO_IN_DEFOG_CAPS(0x30, "获取视频透雾能力集"),
  /** pInBuf = NET_IN_DIAGNOSIS_CAPS, pOutBuf = NET_OUT_DIAGNOSIS_CAPS */
    NET_DIAGNOSIS_CAPS(0x31, "获取故障诊断能力"),
  /** pInBuf = NET_IN_LOWRATEWPAN_CAPS, pOutBuf = NET_OUT_LOWRATEWPAN_CAPS */
    NET_LOWRATEWPAN_CAPS(0x32, "获取低功率WPAN能力集"),
  /** pInBuf = NET_IN_GET_FILE_TRANSFER_CAPS, pOutBuf = NET_OUT_GET_FILE_TRANSFER_CAPS */
    NET_GET_FILE_TRANSFER_CAPS(0x33, "获取远程文件下载能力集"),
  /** pInBuf = NET_IN_AUDIO_OUTPUT_CAPS, pOutBuf = NET_OUT_AUDIO_OUTPUT_CAPS */
    NET_AUDIO_OUTPUT_CAPS(0x34, "获取音频输出能力"),
  /**
   * pInBuf = NET_IN_REMOTE_FACE_RECOGNITION_CAPS, pOutBuf = NET_OUT_REMOTE_FACE_RECOGNITION_CAPS*
   */
    NET_REMOTE_FACE_RECOGNITION_CAPS(0x35, "获取远程目标识别服务器能力"),
  /** pInBuf = NET_IN_REMOTE_COLLECT_DEVINFO_CAPS, pOutBuf = NET_OUT_REMOTE_COLLECT_DEVINFO_CAPS */
    NET_REMOTE_COLLECT_DEVINFO_CAPS(0x36, "获取远程设备信息采集能力"),
  /** pInBuf = NET_IN_MONITOR_WALL_CAPS_INFO, pOutBuf = NET_OUT_MONITOR_WALL_CAPS_INFO */
    NET_MONITOR_WALL_CAPS(0x37, "获取电视墙管理器实例能力集"),
  /** pInBuf = NET_IN_REMOTE_SPEAK_CAPS, pOutBuf = NET_OUT_REMOTE_SPEAK_CAPS */
    NET_REMOTE_SPEAK_CAPS(0x38, "获取前端音频文件路径和能力集"),
  /** pInBuf = NET_IN_WLAN_CAPS_INFO, pOutBuf = NET_OUT_WLAN_CAPS_INFO */
    NET_WLAN_CAPS(0x39, "获取WLAN能力集"),
  /** pInBuf = NET_IN_LOG_ONLINE_BACKUP_CAPS_INFO, pOutBuf = NET_OUT_LOG_ONLINE_BACKUP_CAPS_INFO */
    NET_LOG_ONLINE_BACKUP_CAPS(0x3A, "获取日志在线备份能力集"),
  /** pInBuf = NET_IN_RECEIPT_EVENT_CAPS_INFO, pOutBuf = NET_OUT_RECEIPT_EVENT_CAPS_INFO */
    NET_RECEIPT_EVENT_CAPS(0x3B, "获取事件回执能力集"),
  /**
   * pInBuf = NET_IN_ANALYSE_TASK_MANAGER_CAPS_INFO, pOutBuf =
   * NET_OUT_ANALYSE_TASK_MANAGER_CAPS_INFO
   */
    NET_ANALYSE_TASK_MANAGER_CAPS(0x3C, "获取智能任务分析能力集"),
  /** pInBuf = NET_IN_PERIPHERAL_CHIP_CAPS, pOutBuf = NET_OUT_PERIPHERAL_CHIP_CAPS */
    NET_PERIPHERAL_CHIP_CAPS(0x3D, "获取设备从片能力"),
  /**
   * pInBuf = NET_IN_CITIZEN_IDENTITY_MANAGER_CAPS, pOutBuf = NET_OUT_CITIZEN_IDENTITY_MANAGER_CAPS*
   */
    NET_CITIZEN_IDENTITY_MANAGER_CAPS(0x3E, "获取人证设备能力集"),
  /** pInBuf = NET_IN_AROUD_4G_PROBE_SEARCH_CAPS, pOutBuf = NET_OUT_AROUD_4G_PROBE_SEARCH_CAPS */
    NET_AROUD_4G_PROBE_SEARCH_CAPS(0x3F, "获取电子围栏手机采集能力"),
  /** pInBuf = NET_IN_GET_ADVERTIMENT_CAPS, pOutBuf = NET_OUT_GET_ADVERTIMENT_CAPS */
    NET_GET_ADVERTIMENT_CAPS(0x4A, "获取设备广告投放能力"),
  /** pInBuf = NET_IN_LIGHTING_MIX_SOLUTION_CAPS, pOutBuf = NET_OUT_LIGHTING_MIX_SOLUTION_CAPS */
    NET_VIDEOIN_LIGHTING_MIX_SOLUTION_CAPS(0x40, "混光灯补光方案能力");

  private int type;
  private String desc;

  GetDevCaps_Type(int type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public static GetDevCaps_Type getDevCapsType(int type) {
    for (GetDevCaps_Type cap : GetDevCaps_Type.values()) {
      if (cap.type == type) {
        return cap;
      }
    }
    return null;
  }
}

