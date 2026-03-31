package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 事件类型 EVENT_IVS_USERMANAGER_FOR_TWSDK (用户信息上报事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/07/28 14:34:03
 */
public class NET_DEV_EVENT_USERMANAGER_FOR_TWSDK_INFO extends NetSDKLib.SdkStructure
{
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 0:脉冲
	 */
    public int              nAction;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
	/**
	 * 扩展协议字段
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 用户ID
	 */
    public byte[]           szUserID = new byte[9];
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved1 = new byte[7];
	/**
	 * 用户名
	 */
    public byte[]           szUserName = new byte[64];
	/**
	 * 用户类型
	 * 0: 为普通用户
	 * 1: 为黑名单用户（黑名单会报黑名单事件AccessControlBlacklist）
	 * 2: 为来宾用户
	 * 3: 为巡逻用户
	 * 4: 为VIP用户
	 * 5: 伤障用户
	 */
    public int              nUserType;
	/**
	 * 使用次数
	 */
    public int              nUseTime;
	/**
	 * 用户权限 0:未知 1: 管理员 2: 普通用户
	 */
    public int              nAuthority;
	/**
	 * 有效的的时间段数目
	 */
    public int              nTimeSectionNum;
	/**
	 * 时段
	 */
    public int[]            nTimeSections = new int[64];
	/**
	 * 有效期
	 */
    public NET_TIME         stuValidTo = new NET_TIME();
	/**
	 * 假日计划
	 */
    public int[]            nSpecialDaysSchedule = new int[64];
	/**
	 * 假日计划表示数量
	 */
    public int              nSpecialDaysScheduleNum;
	/**
	 * 消息类型 0: 未知 1: 人员新增消息 2: 人员修改消息 3:人员删除消息（删除时仅UserID有效）
	 */
    public int              nType;
	/**
	 * 密码
	 */
    public byte[]           szPassword = new byte[9];
	/**
	 * 字节对齐
	 */
    public byte[]           szReserved2 = new byte[7];
	/**
	 * 特征值
	 */
    public BYTE_2048[]      szFaceList = new BYTE_2048[5];
	/**
	 * 特征值数量
	 */
    public int              nFaceListNum;
	/**
	 * 卡片数量
	 */
    public int              nCardListNum;
	/**
	 * 卡片
	 */
    public BYTE_32[]        szCardList = new BYTE_32[5];
	/**
	 * 信息
	 */
    public BYTE_256[]       szFingerList = new BYTE_256[5];
	/**
	 * 信息数量
	 */
    public int              nFingerListNum;
	/**
	 * 是否删除所有用户（true:删除所有用户数据，其余字段无效）
	 */
    public int              bDelAllUser;
	/**
	 * 有效期开始时间
	 */
    public NET_TIME         stuValidFrom = new NET_TIME();
	/**
	 * 设备序列号
	 */
    public byte[]           szSN = new byte[32];
	/**
	 * 用户总数量
	 */
    public int              nUserCount;
	/**
	 * 信息总数量
	 */
    public int              nFingerCount;
	/**
	 * 目标总数量
	 */
    public int              nFaceCount;
	/**
	 * 卡片总数量
	 */
    public int              nCardCount;
	/**
	 * 用户信息图片信息
	 */
    public NET_USERMANAGER_IMAGE_INFO[] stuImageInfo = new NET_USERMANAGER_IMAGE_INFO[5];
	/**
	 * 用户信息图片信息个数
	 */
    public int              nImageInfoCount;
	/**
	 * 预留字节
	 */
    public byte[]           szReserved = new byte[572];

	public NET_DEV_EVENT_USERMANAGER_FOR_TWSDK_INFO() {
		for (int i = 0; i < stuImageInfo.length; i++) {
			stuImageInfo[i] = new NET_USERMANAGER_IMAGE_INFO();
		}

		for(int i=0;i<szFaceList.length;i++){
			szFaceList[i]=new BYTE_2048();
		}

		for(int i=0;i<szCardList.length;i++){
			szCardList[i]=new BYTE_32();
		}

		for(int i=0;i<szFingerList.length;i++){
			szFingerList[i]=new BYTE_256();
		}

	}
}

