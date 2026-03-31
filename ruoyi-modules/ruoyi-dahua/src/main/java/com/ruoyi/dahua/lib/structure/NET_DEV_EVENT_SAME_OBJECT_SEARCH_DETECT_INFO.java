package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib.NET_MSG_OBJECT;
import com.ruoyi.dahua.lib.NetSDKLib.SCENE_IMAGE_INFO;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

/**
 * @author 421657
 * @description 事件类型 EVENT_IVS_SAME_OBJECT_SEARCH_DETECT (按图索骥物品检测事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/10/30 10:58:12
 */
public class NET_DEV_EVENT_SAME_OBJECT_SEARCH_DETECT_INFO extends SdkStructure {
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 0:脉冲, 1:开始, 2:停止
     */
    public int              nAction;
    /**
     * / 扩展协议字段
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * / 事件发生时间, 带时区偏差的UTC时间, 单位秒
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * / 事件编号
     */
    public int              nEventID;
    /**
     * / 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * / 智能事件所属大类
     */
    public byte[]           szClass = new byte[16];
    /**
     * / 相对事件时间戳,单位毫秒
     */
    public double           dbPTS;
    /**
     * / 事件组ID，同一辆车抓拍过程内GroupID相同。
     */
    public int              nGroupID;
    /**
     * / CountInGroup一个事件组内应有的抓拍张数
     */
    public int              nCountInGroup;
    /**
     * / 一个事件组内的抓拍序号，从1开始
     */
    public int              nIndexInGroup;
    /**
     * / 物体信息列表数组有效个数
     */
    public int              nObjectsNum;
    /**
     * / 物体信息列表
     */
    public NET_MSG_OBJECT[] stuObjects = new NET_MSG_OBJECT[64];
    /**
     * / 算法名称
     */
    public byte[]           szAlgName = new byte[128];
    /**
     * / 物品类型
     */
    public byte[]           szObjectType = new byte[32];
    /**
     * / 全景图
     */
    public SCENE_IMAGE_INFO stuImageInfo = new SCENE_IMAGE_INFO();
    /**
     * / 自定义报警ID
     */
    public int              nAlarmId;
    /**
     * / ObjectType物品类型id
     */
    public int              nObjectTypeID;
    /**
     * / 规则使用的按图索骥算法id
     */
    public int              nAlgId;
    /**
     * / 字节对齐
     */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_SAME_OBJECT_SEARCH_DETECT_INFO() {
        for (int i = 0; i < stuObjects.length; i++) {
            stuObjects[i] = new NET_MSG_OBJECT();
        }
    }
}

