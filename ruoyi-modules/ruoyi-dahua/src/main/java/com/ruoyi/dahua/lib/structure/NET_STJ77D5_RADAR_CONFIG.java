package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * className：NET_STJ77D5_RADAR_CONFIG
 * description： 森思泰克77Ghz网络雷达配置
 * author：251589
 * createTime：2020/12/29 11:13
 *
 * @version v1.0
 */
public class NET_STJ77D5_RADAR_CONFIG extends NetSDKLib.SdkStructure {
    public int              nLaneNumber;                          // 车道个数, 范围1~4
    public int              nDetectMode;                          // 道路方向, 取值:17 来向, 18 去向, 19 双向
    public double           dbHeight;                             // 雷达安装高度 ,0.01m/bit; 范围0~25
    public double           dbHorizonShift;                       // 水平偏移, 0.2m/bit; 范围-25~25
    public int              nLaneCount;                           // 车道个数
    public double[]         dbLaneWidth = new double[4];          // 车道1-4宽度, 0.01m/bit; 范围2~5。第一个元素是车道1宽度, 以此类推
    public double           dbStopLine;                           // 停止线距离, 0.1m/bit; 范围20~60
    public int              nSceneMode;                           // 雷达工作场景模式, 0:未知, 1:主路模式, 2:路口模式
    public double           dbShiftAngle;                         // 角度修正 ,0.1/bit; 范围-12~12
    public double           dbLengthwayShiftDistance;             // 纵向距离修正, 0.2m/bit; 范围-25~25
    public double           dbSensitive;                          // 灵敏度设置, 0.1/bit; 范围0~3
    public int              nIDset;                               // ID设置; 范围0~4
    public int              nWorkMode;                            // 雷达工作模式, 0:调试模式 1:工作模式
    public int              nRadarFlowTime;                       // 雷达流量统计时间间隔, 单位:s
    public int              nRadarFlowSwitch;                     // 雷达流量输出开关, 0:未知, 1:开启, 2:关闭
    public int              nNonMotorDiscern;                     // 非机动车/行人识别控制开关, 0:未知, 1:开启, 2:关闭
    public int              nVehicleDistinguish;                  // 车型分别能力;范围-10~10, 负值:大车门限降低, 正值:大车门限升高
    public int              nStopTargetDisappearTime;             // 停止目标消失时间,1s/bit, 0:关闭, 非0时:停止目标消失的时间
    public int              nStartLane;                           // 雷达的起始车道号, 取值范围: 0~7
    public int              nRadarLanNumber;                      // 雷达支持的车道数目, 取值范围:1~8
    public int              nVirtualCoilTriggerCount;             // 虚拟线圈个数
    public int[]            nVirtualCoilTrigger = new int[4];     // 虚拟线圈触发方式: 1:头线圈, 2:尾线圈, 3:头尾线圈, 90:线圈,不统计
    public int              nVirtualCoilDistanceCount;            // 虚拟线圈个数
    public double[]         dbVirtualCoilDistance = new double[4]; // 虚拟线圈起始位置(下边沿,y向距离雷达距离), 单位:m
    public int              nVirtualCoilLengthCount;              // 虚拟线圈个数
    public double[]         dbVirtualCoilLength = new double[4];  // 虚拟线圈长度, 单位:m
    public double           dbCameraToRoadEndDistance;            // 相机到车道监控面路口距离, 单位:m, 0.1m/bit(精确到小数点后一位,步长0.1m)
    public double           dbCameraToStopLane;                   // 相机到停止线距离, 单位:m, 0.1m/bit(精确到小数点后一位,步长0.1m)
    public int[]            nLaneDirection = new int[4];          // 车道方向,
    public int              nLaneDirectionCount;                  // 车道个数
    /**
     * 字节对齐
    */
    public byte[]           szReserved = new byte[4];
    /**
     * 渠化隔离带宽度
    */
    public double           dbCanalizeDivideIslandWidth;
    /**
     * 渠化道路水平偏移，单位米(m)，渠化道路相对正常道路偏移距离，左负右正
    */
    public double           dbCanalizeHorizontalOffset;
    /**
     * 渠化道路个数，1-16
    */
    public int              nCanalizeLaneNumber;
    /**
     * 渠化道路实际返回数量
    */
    public int              nCanalizeLaneWidthNum;
    /**
     * 渠化道路宽度，0.01m/bit，范围0~20。数组下标对应渠化道路号，渠化道路号取值取自于Radar CanalizeLaneNumber，从1开始，例如：,第一个元素是渠化道路1宽度，,第二个元素是渠化道路2宽度，,第三个元素是渠化道路3宽度，,第四个元素是渠化道路4宽度
    */
    public double[]         dbCanalizeLaneWidth = new double[8];
    /**
     * 渠化上边界，单位米(m)
    */
    public double           dbCanalizeUpperBoundary;
    /**
     * 渠化下边界，单位米(m)
    */
    public double           dbCanalizeLowerBoundary;
    /**
     * 道路渠化开关渠化开启：0x55AA55AA（1437226410）渠化关闭：0xAA55AA55（2857740885）以十进制下发数据
    */
    public int              nCanalizeSwitch;
    /**
     * 雷达和相机的数据交互方式 -1:未知 0：串口 1：网络
    */
    public int              nDataMode;
    /**
     * 正常道路隔离带宽度
    */
    public double           dbDivideIslandWidth;
    /**
     * 正常道路/渠化道路隔离带位置，0-16(0表示没有隔离带,1表示隔离带在1车道的右侧)
    */
    public int              nDivideIslandLocation;
    /**
     * 通过网络传输数据使能项（字段不扩展，此款雷达也只适配特定相机，所以这里不考虑后续扩展，直接使用bool类型） true：雷达使用网络传输数据给相机false：雷达不使用网络传输数据给相机，此时，DataMode字段也无效
    */
    public int              bNetEnable;
    public byte[]           bReserved = new byte[888];            // 保留字节
}

