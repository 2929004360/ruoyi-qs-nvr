package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 计划表信息
 * @date 2021/09/28
 */
public class NET_LANE_PLAN_LIST_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  可变车道指示牌号
     */
    public int              nVarlaneId;
    /**
     *  可变车道编号（车道配置页面编号）
     */
    public int              nVarlaneNumber;
    /**
     *  保持时间（分钟）0-1440
     */
    public int              nKeepTime;
    /**
     *  1：固定方案； 2：自适应双向切换； 3：定向切换
     */
    public int              nControlMode;
    /**
     *  当前属性 1：左转2：直行（潮汐）3：右转4：掉头5：禁行（匝道）6：通行（匝道）7：反向（潮汐）
     */
    public int              nCurrentVarlaneProperty;
    /**
     *  属性计划表个数
     */
    public int              nPropertyPlanListNum;
    /**
     *  属性计划表
     */
    public NET_PROPERTY_PLAN_LIST_INFO[] stuPropertyPlanInfo = (NET_PROPERTY_PLAN_LIST_INFO[]) new NET_PROPERTY_PLAN_LIST_INFO().toArray(7);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

