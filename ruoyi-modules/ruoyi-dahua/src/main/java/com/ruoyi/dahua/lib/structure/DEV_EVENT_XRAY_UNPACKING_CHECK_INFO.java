package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 291189
 * @version 1.0
 * @description  事件类型 EVENT_IVS_XRAY_UNPACKING_CHECK (X光开包检查事件)对应的数据块描述信息
 * @date 2021/9/9
 */

public class DEV_EVENT_XRAY_UNPACKING_CHECK_INFO extends NetSDKLib.SdkStructure {
 /** 通道号*/
    public			int            nChannelID;
 /** 事件动作,0表示脉冲事件*/
    public			int            nAction;
 /** 事件名称*/
    public			byte[]         szName = new byte[NetSDKLib.NET_EVENT_NAME_LEN];
 /** 智能事件所属大类*/
    public			byte[]         szClass = new byte[16];
 /** 相对事件时间戳(单位是毫秒)*/
    public			double         PTS;
 /** 开包检查信息*/
    public UNPACKING_CHECK_INFO stuCheckInfo = new UNPACKING_CHECK_INFO();
 /** 事件ID*/
    public			int            nEventID;
 /** 预留字节*/
    public			byte[]         szReserved = new byte[1024];
}

