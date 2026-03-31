package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

public class NET_IDCARD_INFO extends NetSDKLib.SdkStructure {
    /**
     * 证件消息类型,请参考EM_IDCARD_MSG_TYPE
     */
    public  int             emIDCardMsgType;                      
    // EM_IDCARD_MSG_TYP_QUERY：除 nEventGroupID 和 szRoomNo （在上一层）字段不用填，其余必填
    // EM_IDCARD_MSG_TYPE_ISSUE_PERMISSION：nEventGroupID 、szRoomNo（在上一层）及 szCitizenName 字段必填，其余可为空
    /**
     * 姓名
     */
    public byte[]           szCitizenName = new byte[32];
    /**
     * 性别,请参考EM_CITIZENIDCARD_SEX_TYPE
     */
    public  int             emSexType;
    public  int             nMZ;
    /**
     * 生日(年月日有效)
     */
    public NET_TIME         stuBirthday;
    /**
     * 地址
     */
    public byte[]           szAddress = new byte[128];
    /**
     * 证件号
     */
    public byte[]           szCitizenID = new byte[32];
    /**
     * 签发机关
     */
    public byte[]           szAuthority = new byte[48];
    /**
     * 有效期限起始日期(年月日有效)
     */
    public NET_TIME         stuBeginValidTime;
    /**
     * 有效期限截止日期(年月日有效)
     */
    public NET_TIME         stuEndValidTime;
    /**
     * 事件组ID,用于标示不同事件的对同一动作的关联
     */
    public int              nEventGroupID;
    /**
     * 预留字段
     */
    public byte[]           byReserved = new byte[1024];
}

