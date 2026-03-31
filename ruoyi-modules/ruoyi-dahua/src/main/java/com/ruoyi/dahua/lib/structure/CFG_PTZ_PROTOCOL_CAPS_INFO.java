package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.CFG_COMMON_STRING_32;
import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.MAX_PROTOCOL_NAME_LEN;

/**
 * @author 47081
 * @version 1.0
 * @description 获取云台能力集信息
 * @date 2020/11/9
 */
public class CFG_PTZ_PROTOCOL_CAPS_INFO extends NetSDKLib.SdkStructure {
    public int              nStructSize;
    /**
     * 是否支持云台水平摆动
     */
    public int              bPan;
    /**
     * 是否支持云台垂直摆动
     */
    public int              bTile;
    /**
     * 是否支持云台变倍
     */
    public int              bZoom;
    /**
     * 是否支持云台光圈调节
     */
    public int              bIris;
    /**
     * 是否支持预置点
     */
    public int              bPreset;
    /**
     * 是否支持清除预置点
     */
    public int              bRemovePreset;
    /**
     * 是否支持自动巡航线路
     */
    public int              bTour;
    /**
     * 是否支持清除巡航
     */
    public int              bRemoveTour;
    /**
     * 是否支持轨迹线路
     */
    public int              bPattern;
    /**
     * 是否支持自动水平摆动
     */
    public int              bAutoPan;
    /**
     * 是否支持自动扫描
     */
    public int              bAutoScan;
    /**
     * 是否支持辅助功能
     */
    public int              bAux;
    /**
     * 是否支持报警功能
     */
    public int              bAlarm;
    /**
     * 是否支持灯光, 内容见下面"stuPtzLightingControl"，该字段已废除使用
     */
    public int              bLight;
    /**
     * 是否支持雨刷
     */
    public int              bWiper;
    /**
     * 是否支持镜头翻转
     */
    public int              bFlip;
    /**
     * 是否支持云台内置菜单
     */
    public int              bMenu;
    /**
     * 是否支持云台按相对坐标定位
     */
    public int              bMoveRelatively;
    /**
     * 是否支持云台按绝对坐标定位
     */
    public int              bMoveAbsolutely;
    /**
     * 是否支持云台按三维坐标定位
     */
    public int              bMoveDirectly;
    /**
     * 是否支持云台复位
     */
    public int              bReset;
    /**
     * 是否支持获取云台运动状态及方位坐标
     */
    public int              bGetStatus;
    /**
     * 是否支持限位
     */
    public int              bSupportLimit;
    /**
     * 是否支持云台设备
     */
    public int              bPtzDevice;
    /**
     * 是否支持云台可视域
     */
    public int              bIsSupportViewRange;
    /**
     * 通道地址的最小值
     */
    public short            wCamAddrMin;
    /**
     * 通道地址的最大值
     */
    public short            wCamAddrMax;
    /**
     * 预览地址的最小值
     */
    public short            wMonAddrMin;
    /**
     * 预览地址的最大值
     */
    public short            wMonAddrMax;
    /**
     * 预置点的最小值
     */
    public short            wPresetMin;
    /**
     * 预置点的最大值
     */
    public short            wPresetMax;
    /**
     * 自动巡航线路的最小值
     */
    public short            wTourMin;
    /**
     * 自动巡航线路的最大值
     */
    public short            wTourMax;
    /**
     * 轨迹线路的最小值
     */
    public short            wPatternMin;
    /**
     * 轨迹线路的最大值
     */
    public short            wPatternMax;
    /**
     * 垂直速度的最小值
     */
    public short            wTileSpeedMin;
    /**
     * 垂直速度的最大值
     */
    public short            wTileSpeedMax;
    /**
     * 水平速度的最小值
     */
    public short            wPanSpeedMin;
    /**
     * 水平速度的最大值
     */
    public short            wPanSpeedMax;
    /**
     * 自动扫描的最小值
     */
    public short            wAutoScanMin;
    /**
     * 自动扫描的最大值
     */
    public short            wAutoScanMax;
    /**
     * 辅助功能的最小值
     */
    public short            wAuxMin;
    /**
     * 辅助功能的最大值
     */
    public short            wAuxMax;
    /**
     * 发送命令的时间间隔
     */
    public int              dwInterval;
    /**
     * 协议的类型，0-本地云台，1-远程云台
     */
    public int              dwType;
    /**
     * 协议的报警长度
     */
    public int              dwAlarmLen;
    /**
     * 近光灯组数量,0~4,为0时表示不支持
     */
    public int              dwNearLightNumber;
    /**
     * 远光灯组数量,0~4,为0时表示不支持
     */
    public int              dwFarLightNumber;
    /**
     * 支持的可视域数据获取方式掩码,从低位到高位依次数,目前支持，第1位:为1表示支持"ElectronicCompass" 电子罗盘方式
     */
    public int              dwSupportViewRangeType;
    /**
     * 支持的支持的焦距模式掩码,从低位到高位依次数,见{@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_FOCUS_MODE}
     */
    public int              dwSupportFocusMode;
    /**
     * 操作的协议名
     */
    public byte[]           szName = new byte[MAX_PROTOCOL_NAME_LEN];
    /**
     * 云台辅助功能名称列表
     */
    public Auxs[]           szAuxs = (Auxs[]) new Auxs().toArray(CFG_COMMON_STRING_32);
    /**
     * 云台转动角度范围，单位：度
     */
    public CFG_PTZ_MOTION_RANGE stuPtzMotionRange;
    /**
     * 灯光控制内容，该字段已废除使用
     */
    public CFG_PTZ_LIGHTING_CONTROL stuPtzLightingControl;
    /**
     * 是否支持预置点时间段配置的功能
     */
    public int              bSupportPresetTimeSection;
    /**
     * 是否支持云台变焦
     */
    public int              bFocus;
    /**
     * 区域扫描能力集
     */
    public CFG_PTZ_AREA_SCAN stuPtzAreaScan;
    /**
     * 隐私遮挡能力集
     */
    public CFG_PTZ_PRIVACY_MASKING stuPtzPrivacyMasking;
    /**
     * 图像测距能力集
     */
    public CFG_PTZ_MEASURE_DISTANCE stuPtzMeasureDistance;
    /**
     * 是否支持云台巡迹OSD叠加
     */
    public int              bSupportPtzPatternOSD;
    /**
     * 是否支持云台RS485检测OSD叠加
     */
    public int              bSupportPtzRS485DetectOSD;
    /**
     * 是否支持云台坐标叠加
     */
    public int              bSupportPTZCoordinates;
    /**
     * 是否支持云台变倍叠加
     */
    public int              bSupportPTZZoom;
    /**
     * 是否支持云台方向状态显示
     */
    public int              bDirectionDisplay;
    /**
     * 变倍最大值
     */
    public int              dwZoomMax;
    /**
     * 变倍最小值
     */
    public int              dwZoomMin;
    /**
     * 绝对控制云台能力，bMoveAbsolutely==TRUE 时有效
     */
    public CFG_PTZ_MOVE_ABSOLUTELY_CAP stuMoveAbsolutely;
    /**
     * stuMoveContinuously 字段是否有效
     */
    public int              bMoveContinuously;
    /**
     * 云台连续运动能力
     */
    public CFG_PTZ_MOVE_CONTINUOUSLY_CAPS stuMoveContinuously;
    /**
     * 云台不支持的转动方向个数
     */
    public int              nUnSupportDirections;
    /**
     * 云台不支持的转动方向,参考枚举{ @link EM_PTZ_UNSUPPORT_DIRECTION}
     */
    public int[]            emUnSupportDirections = new int[10];
    /**
     * 是否支持电子云台联动
     */
    public int              bSupportEptzLink;
    /**
     * 风扇能力,参见结构体定义 {@link CFG_PTZ_FAN_CAPS}
    */
    public CFG_PTZ_FAN_CAPS stuPtzFan = new CFG_PTZ_FAN_CAPS();
    /**
     * 视窗加热器能力,参见结构体定义 {@link CFG_WINDOW_HEATER_CAPS}
    */
    public CFG_WINDOW_HEATER_CAPS stuWindowHeater = new CFG_WINDOW_HEATER_CAPS();

    public CFG_PTZ_PROTOCOL_CAPS_INFO() {
    }
}

