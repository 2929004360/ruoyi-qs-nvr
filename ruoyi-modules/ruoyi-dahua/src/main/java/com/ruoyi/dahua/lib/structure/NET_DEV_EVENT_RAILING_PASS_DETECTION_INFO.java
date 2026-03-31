package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 隔栏传物事件 (对应 DH_ALARM_RAILING_PASS_DETECTION)
*/
public class NET_DEV_EVENT_RAILING_PASS_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号,从0开始
    */
    public int              nChannelID;
    /**
     * 事件动作, 0表示脉冲事件, 1表示开始,  2表示结束
    */
    public int              nEventAction;
    /**
     * 智能事件规则编号,用于标示哪个规则触发的事件。
    */
    public int              nRuleID;
    /**
     * 事件发生的时间,标准的（不带时区偏差的）UTC时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 事件组ID，同一物体抓拍过程内GroupID相同
    */
    public int              nGroupID;
    /**
     * 一个事件组内的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号
    */
    public int              nIndexInGroup;
    /**
     * 规则被触发次数
    */
    public int              nCount;
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 事件编号
    */
    public int              nEventID;
    /**
     * 配置的规则线，包含一个起点、一个终点坐标，归一化到[0,8192)区间,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectLine = new NET_POINT_EX[2];
    /**
     * 配置的检测区,多边形中每个顶点的坐标归一化到[0,8192)区间,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 多边形中顶点个数
    */
    public int              nDetectRegionPointNum;
    /**
     * 检测到的目标信息个数
    */
    public int              nObjectsCount;
    /**
     * 检测到的目标信息,参见结构体定义 {@link NET_DETECTION_OBJECT_INFO}
    */
    public NET_DETECTION_OBJECT_INFO[] stuObjects = new NET_DETECTION_OBJECT_INFO[100];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_DEV_EVENT_RAILING_PASS_DETECTION_INFO() {
        for(int i = 0; i < stuDetectLine.length; i++){
            stuDetectLine[i] = new NET_POINT_EX();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NET_DETECTION_OBJECT_INFO();
        }
    }
}

