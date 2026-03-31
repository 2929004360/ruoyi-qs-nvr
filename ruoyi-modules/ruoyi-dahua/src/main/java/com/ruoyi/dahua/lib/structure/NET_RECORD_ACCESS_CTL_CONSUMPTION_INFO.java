package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description 消费记录信息
* @date 2022/03/08 17:16:39
*/
public class NET_RECORD_ACCESS_CTL_CONSUMPTION_INFO extends NetSDKLib.SdkStructure {
/** 
结构体大小
*/
    public			int            dwSize;
/** 
记录集编号,只读
*/
    public			int            nRecNo;
/** 
上报事件数据序列号从1开始自增
*/
    public			int            nBlockId;
/** 
刷卡时间
*/
    public NET_TIME         stuCreateTime = new NET_TIME();
/** 
卡号
*/
    public			byte[]         szCardNo = new byte[16];
/** 
卡命名
*/
    public			byte[]         szCardName = new byte[128];
/** 
卡类型  {@link NetSDKLib.NET_ACCESSCTLCARD_TYPE}
*/
    public			int            emCardType;
/** 
用户ID
*/
    public			byte[]         szUserID = new byte[12];
/** 
事件类型   {@link NetSDKLib.NET_ENUM_DIRECTION_ACCESS_CTL}
*/
    public			int            emType;
/** 
消费结果  {@link com.ruoyi.dahua.lib.enumeration.EM_CONSUMPTION_STATUS}
*/
    public			int            emStatus;
/** 
开门方式   {@link NetSDKLib.NET_ACCESS_DOOROPEN_METHOD}
*/
    public			int            emMethod;
/** 
门号
*/
    public			int            nDoor;
/** 
读卡器ID
*/
    public			byte[]         szReaderID = new byte[32];
/** 
消费失败的原因,配合 emStatus 使用
 // 0x00 没有错误
 // 0x10 未授权
 // 0x11 卡挂失或注销
 // 0x12 没有该门权限
 // 0x13 开门模式错误
 // 0x14 有效期错误
 // 0x15 防反潜模式
 // 0x16 胁迫报警未打开
 // 0x17 门常闭状态
 // 0x18 AB互锁状态
 // 0x19 巡逻卡
 // 0x1A 设备处于闯入报警状态
 // 0x20 时间段错误
 // 0x21 假期内开门时间段错误
 // 0x30 需要先验证有首卡权限的卡片
 // 0x40 卡片正确,输入密码错误
 // 0x41 卡片正确,输入密码超时
 // 0x42 卡片正确,输入信息错误
 // 0x43 卡片正确,输入信息超时
 // 0x44 信息正确,输入密码错误
 // 0x45 信息正确,输入密码超时
 // 0x50 组合开门顺序错误
 // 0x51 组合开门需要继续验证
 // 0x60 验证通过,控制台未授权
 // 0x61 卡片正确,人脸错误
 // 0x62 卡片正确,人脸超时
 // 0x63 重复进入
 // 0x64 未授权,需要后端平台识别
 // 0x65 体温过高
 // 0x66	未戴口罩
 // 0x67 健康码获取失败
 // 0x68 黄码禁止通行
 // 0x69 红码禁止通行
 // 0x6a 健康码无效
 // 0x6b 绿码验证通过
 // 0x70 获取健康码信息
 // 0x71 校验证件信息（平台下发对应证件号的校验结果）
*/
    public			int            nErrorCode;
/** 
目标识别相似度,开门方式为人脸开门时有效
*/
    public			int            nSimilarity;
/** 
人脸抠图保存的本地路径
*/
    public			byte[]         szSnapFaceURL = new byte[128];
/**
记录类型    {@link com.ruoyi.dahua.lib.enumeration.EM_CONSUMPTION_RECORD_TYPE}
*/
    public			int            emRecordType;
/** 
消费金额。单位（分）101 = 1.01 元
*/
    public			int            nConsumptionAmount;
/** 
充值金额。单位（分）101 = 1.01 元
*/
    public			int            nRechargeAmount;
/** 
交易流水号
*/
    public			byte[]         szTransactionSerialNumber = new byte[128];

public NET_RECORD_ACCESS_CTL_CONSUMPTION_INFO(){
this.dwSize=this.size();
}
}

