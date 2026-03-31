package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 访客信息
*/
public class NET_CUSTOM_VISITOR_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 访客姓名
    */
    public byte[]           szVisitorName = new byte[128];
    /**
     * 访客电话
    */
    public byte[]           szVisitorTel = new byte[32];
    /**
     * 访问开始时间
    */
    public byte[]           szVisitorStartTime = new byte[32];
    /**
     * 访问结束时间
    */
    public byte[]           szVisitorEndTime = new byte[32];
    /**
     * 访客事由
    */
    public byte[]           szVisitorAccessFor = new byte[256];
    /**
     * 访客证件号
    */
    public byte[]           szVisitorCitizenID = new byte[32];
    /**
     * 被访者姓名
    */
    public byte[]           szRespondentName = new byte[128];
    /**
     * 被访者电话
    */
    public byte[]           szRespondentTel = new byte[32];
    /**
     * 被访者单位
    */
    public byte[]           szRespondentCompany = new byte[128];
    /**
     * 被访者部门
    */
    public byte[]           szRespondentSection = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_CUSTOM_VISITOR_INFO() {
    }
}

