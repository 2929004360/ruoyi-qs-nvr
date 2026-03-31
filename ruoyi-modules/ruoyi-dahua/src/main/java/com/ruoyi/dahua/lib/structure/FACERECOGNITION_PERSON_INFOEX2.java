package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 人员信息扩展结构体2
*/
public class FACERECOGNITION_PERSON_INFOEX2 extends NetSDKLib.SdkStructure
{
    /**
     * 备注信息
    */
    public byte[]           szComment = new byte[100];
    /**
     * 8字节对齐
    */
    public byte[]           szReserved1 = new byte[4];
    /**
     * 人员预约开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuAppointmentStartTime = new NetSDKLib.NET_TIME();
    /**
     * 人员预约结束时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuAppointmentEndTime = new NetSDKLib.NET_TIME();
    /**
     * 允许到达楼层, 已废弃, 建议使用szAllowFloorsEx
    */
    public byte[]           szAllowFloors = new byte[32];
    /**
     * 姓名的扩展字段
    */
    public byte[]           szNameEX = new byte[128];
    /**
     * 人员信息号码，工号，或其他编号的扩展字段
    */
    public byte[]           szIDEX = new byte[128];
    /**
     * 是否使用修改时间
    */
    public int              bUseModifyTime;
    /**
     * 是否使用注册库入库时间
    */
    public int              bUseRegisterStorageTime;
    /**
     * 平台最近一次修改注册库人员信息的时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuModifyTime = new NetSDKLib.NET_TIME();
    /**
     * 注册库入库时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuRegisterStorageTime = new NetSDKLib.NET_TIME();
    /**
     * 允许到达楼层
    */
    public byte[]           szAllowFloorsEx = new byte[64];
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[616];

    public FACERECOGNITION_PERSON_INFOEX2() {
    }
}

