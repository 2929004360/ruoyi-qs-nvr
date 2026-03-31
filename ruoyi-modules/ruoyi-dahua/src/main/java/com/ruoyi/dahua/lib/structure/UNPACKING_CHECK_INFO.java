package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 开包检查信息
 * @date 2021/9/9
 */
public class UNPACKING_CHECK_INFO extends NetSDKLib.SdkStructure {
    /**
     * 事件发生时间，带时区偏差的UTC时间，单位秒
     */
    public int              nUTC;
    /**
     * 事件时间毫秒数
     */
    public int              nUTCMS;
    /**
     * 表示包裹标识，用来唯一标识一个包裹
     */
    public byte[]           szPackageTag = new byte[32];
    /**
     * 关联图片
     */
    public RELATED_IMAGE[]  stuRelatedImage = new RELATED_IMAGE[64];
    /**
     * 关联图片数量
     */
    public int              nRelatedImageRetNum;
    /**
     * 报警时间（即拍摄违禁品时间），UTC时间，单位秒
     */
    public int              nAlarmTime;
    /**
     * 身份信息
     */
    public IDENTITY_INFO    stuIdentityInfo = new IDENTITY_INFO();
    /**
     * 违禁物品分类 已经废弃
     */
    public OneDimensionalInformation[] szContrabandClass = new OneDimensionalInformation[16];
    /**
     * 违禁物品个数
     */
    public int              nContrabandRetNum;
    /**
     * 处理方式  EM_PROCESS_MODE
     */
    public int              emProcessMode;
    /**
     * 检出人，即开包人员
     */
    public byte[]           szChecker = new byte[128];
    /**
     * 报警地点
     */
    public byte[]           szAlarmLocation = new byte[256];
    /**
     * 违禁物品分类  {@link com.ruoyi.dahua.lib.enumeration.EM_INSIDE_OBJECT_TYPE}
     */
    public int[]            emContrabandClass = new int[64];
    /**
     * 行程出发地
     */
    public byte[]           szPlaceOfDeparture = new byte[256];
    /**
     * 行程目的地
     */
    public byte[]           szDestination = new byte[256];
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[18688];

    public UNPACKING_CHECK_INFO() {
        for (int i = 0; i < 16; i++) {
            szContrabandClass[i] = new OneDimensionalInformation();
        }
        for (int i = 0; i < 64; i++) {
            stuRelatedImage[i] = new RELATED_IMAGE();
        }

    }
}

