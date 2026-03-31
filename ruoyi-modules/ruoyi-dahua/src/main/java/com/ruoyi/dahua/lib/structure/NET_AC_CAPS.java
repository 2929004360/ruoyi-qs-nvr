package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  AC服务能力集 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public class NET_AC_CAPS extends NetSDKLib.SdkStructure {
/** 
支持的门禁通道数量
*/
    public			int            nChannels;
/** 
是否支持门禁报警日志记录在记录集中
*/
    public			int            bSupAccessControlAlarmRecord;
/** 
AccessControlCustomPassword记录集中密码的保存方式,0:明文,默认值0, 1:MD5
*/
    public			int            nCustomPasswordEncryption;
/** 
是否支持信息功能,0:未知，默认,1:不支持, 2:支持
*/
    public			int            nSupportFingerPrint;
/** 
是否支持卡片鉴权
*/
    public			int            bHasCardAuth;
/** 
是否支持目标识别鉴权
*/
    public			int            bHasFaceAuth;
/** 
是否只支持单门授权(发卡)
*/
    public			int            bOnlySingleDoorAuth;
/** 
是否支持授权异步返回
*/
    public			int            bAsynAuth;
/** 
是否支持人卡分离方案
*/
    public			int            bUserlsoLate;
/** 
机器数据下发插入最大数量
*/
    public			int            nMaxInsertRate;
/** 
假日计划能力
*/
    public			NET_SPECIAL_DAYS_SCHEDULE_CAPS stuSpecialDaysSchedule = new NET_SPECIAL_DAYS_SCHEDULE_CAPS();
/** 
设备支持的开锁模式组合数量
*/
    public			int            nUnlockModes;
/** 
设备支持的开锁模式组合
*/
    public			int[]          emUnlockModes = new int[128];
/** 
支持安全帽功能 {@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_HELMET_TYPE}
*/
    public			int            emSupportHelmetType;
/** 
支持人名长度限制
*/
    public			int            nUserNameMaxLen;
/** 
支持闸机业务组件（以前闸机是挂在门禁设备作为子设备存在，闸机相关配置写在门禁里面。现在将闸机业务从门禁业务划分出来。）
*/
    public			int            bSupportASGManager;
/** 
是否支持眼睛识别鉴权
*/
    public			int            bHasIrisAuth;
    /**
     * 是否支持用户单个门绑定多个假日计划
    */
    public int              bSupportDoorMultiSection;
    /**
     * 门禁设备是否支持采集用户信息
    */
    public int              bSupportCollectUserInfo;
    /**
     * 是否支持时间计划数据库方案
    */
    public int              bSupportTimeScheduleDB;
    /**
     * 是否支持出门方式配置（若支持，与进门方式Method可分开配置）
    */
    public int              bSupportOutDoorMethodSetting;
/** 
保留字节
*/
    public			byte[]         byReserved = new byte[224];

public NET_AC_CAPS(){
}
}

