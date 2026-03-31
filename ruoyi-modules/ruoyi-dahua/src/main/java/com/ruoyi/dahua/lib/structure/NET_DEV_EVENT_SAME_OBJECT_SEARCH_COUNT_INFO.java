package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib.NET_MSG_OBJECT_EX2;
import com.ruoyi.dahua.lib.NetSDKLib.SCENE_IMAGE_INFO;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;

/**
 * @author 421657
 * @description 事件类型 EVENT_IVS_SAME_OBJECT_SEARCH_COUNT(按图索骥物品计数事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/10/30 10:46:13
 */
public class NET_DEV_EVENT_SAME_OBJECT_SEARCH_COUNT_INFO extends SdkStructure {
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * / 扩展协议字段
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
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
     * / 事件时间毫秒数
     */
    public int              nUTCMS;
    /**
     * / 事件编号，用来唯一标志一个事件
     */
    public int              nEventID;
    /**
     * / GroupID事件组ID，同一物体抓拍过程内GroupID相同
     */
    public int              nGroupID;
    /**
     * / CountInGroup一个事件组内的抓拍张数
     */
    public int              nCountInGroup;
    /**
     * / IndexInGroup一个事件组内的抓拍序号，从1开始
     */
    public int              nIndexInGroup;
    /**
     * / 物体信息列表实际数量
     */
    public int              nObjectsNum;
    /**
     * / 物体信息列表，最大支持64个物体
     */
    public NET_MSG_OBJECT_EX2[] stObjects = new NET_MSG_OBJECT_EX2[64];
    /**
     * / 算法名称
     */
    public byte[]           szAlgName = new byte[128];
    /**
     * / 物品类型
     */
    public byte[]           szObjectType = new byte[32];
    /**
     * / ObjectType物品类型id
     */
    public int              nObjectTypeID;
    /**
     * / 自定义报警ID，事件上报平台时使用
     */
    public int              nAlarmId;
    /**
     * / 全景图
     */
    public SCENE_IMAGE_INFO stImage = new SCENE_IMAGE_INFO();
    /**
     * / 规则使用的按图索骥算法id
     */
    public int              nAlgId;
    /**
     * / 物体个数
     */
    public int              nCount;
    /**
     * / 预留字节
     */
    public byte[]           szReserverd = new byte[1020];

    public NET_DEV_EVENT_SAME_OBJECT_SEARCH_COUNT_INFO() {
        for (int i = 0; i < stObjects.length; i++) {
            stObjects[i] = new NET_MSG_OBJECT_EX2();
        }
    }
}

