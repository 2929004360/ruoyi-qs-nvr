package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_HELMET_EVENT_TYPE;

/**
 * @author ： 260611
 * @description ： 事件类型 EVENT_IVS_HELMET_DETECTION (安全帽检测事件) 对应的数据块描述信息
 * @since ： Created in 2022/03/08 15:25
 */

public class DEV_EVENT_HELMET_DETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲 1:开始 2:停止
     */
    public int              nAction;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 智能事件所属大类,{@link NetSDKLib.EM_CLASS_TYPE}
     */
    public int              emClassType;
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 规则编号
     */
    public int              nRuleID;
    /**
     * 目标ID
     */
    public int              nObjectID;
    /**
     * 事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo = new NetSDKLib.NET_EVENT_FILE_INFO();
    /**
     * 触发事件类型，{@link EM_HELMET_EVENT_TYPE}
     */
    public int              emHelmetEventType;
    /**
     * 人体图片
     */
    public NetSDKLib.HUMAN_IMAGE_INFO stuHumanImage = new NetSDKLib.HUMAN_IMAGE_INFO();
    /**
     * 全景广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
}

