package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  RFID 电子车牌标签信息 
* @date 2022/10/13 14:39:18
*/
public class NET_RFIDELETAG_INFO extends NetSDKLib.SdkStructure {
/** 
卡号
*/
    public			byte[]         szCardID = new byte[16];
/** 
卡号类型, 0:交通管理机关发行卡, 1:新车出厂预装卡
*/
    public			int            nCardType;
/** 
卡号省份 {@link com.ruoyi.dahua.lib.enumeration.EM_CARD_PROVINCE}
*/
    public			int            emCardPrivince;
/** 
车牌号码
*/
    public			byte[]         szPlateNumber = new byte[32];
/** 
出厂日期
*/
    public			byte[]         szProductionDate = new byte[16];
/** 
车辆类型 {@link com.ruoyi.dahua.lib.enumeration.EM_CAR_TYPE}
*/
    public			int            emCarType;
/** 
功率,单位：千瓦时，功率值范围0~254；255表示该车功率大于可存储的最大功率值
*/
    public			int            nPower;
/** 
排量,单位：百毫升，排量值范围0~254；255表示该车排量大于可存储的最大排量值
*/
    public			int            nDisplacement;
/** 
天线ID，取值范围:1~4
*/
    public			int            nAntennaID;
/** 
号牌种类 {@link com.ruoyi.dahua.lib.enumeration.EM_PLATE_TYPE}
*/
    public			int            emPlateType;
/** 
检验有效期，年-月
*/
    public			byte[]         szInspectionValidity = new byte[16];
/** 
逾期未年检标志, 0:已年检, 1:逾期未年检
*/
    public			int            nInspectionFlag;
/** 
强制报废期，从检验有效期开始，距离强制报废期的年数
*/
    public			int            nMandatoryRetirement;
/** 
车身颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_CAR_COLOR_TYPE}
*/
    public			int            emCarColor;
/** 
核定载客量，该值<0时：无效；此值表示核定载客，单位为人
*/
    public			int            nApprovedCapacity;
/** 
此值表示总质量，单位为百千克；该值<0时：无效；该值的有效范围为0~0x3FF，0x3FF（1023）表示数据值超过了可存储的最大值
*/
    public			int            nApprovedTotalQuality;
/** 
过车时间
*/
    public NET_TIME_EX      stuThroughTime = new NET_TIME_EX();
/** 
使用性质 {@link com.ruoyi.dahua.lib.enumeration.EM_USE_PROPERTY_TYPE}
*/
    public			int            emUseProperty;
/** 
发牌代号，UTF-8编码
*/
    public			byte[]         szPlateCode = new byte[8];
/** 
号牌号码序号，UTF-8编码
*/
    public			byte[]         szPlateSN = new byte[16];
/** 
标签(唯一标识), UTF-8编码
*/
    public			byte[]         szTID = new byte[64];
/** 
保留字节,留待扩展.
*/
    public			byte[]         bReserved = new byte[40];

public			NET_RFIDELETAG_INFO(){
}
}

