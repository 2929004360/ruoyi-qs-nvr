package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 查询返回的记录信息
 * @date 2022/10/09 14:08:24
 */
public class NET_CLUSTER_DETAIL_FIND_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 设备端数据库ID
	 */
    public long             nRcNo;
	/**
	 * 人员记录的唯一Key, 取32位uuid, 实际有效长度32, 由于最后一位取'\0', 数组长度扩展为40
	 */
    public byte[]           szTrackID = new byte[40];
	/**
	 * 聚档结果ID, 对应平台的sessionID, -1及小于0的代表无效记录
	 */
    public long             nProfileID;
	/**
	 * 抓拍通道号
	 */
    public long             nChannel;
	/**
	 * 年龄, 参照人体按照年龄段上报, 0.unknow 未知, 1.child 儿童, 2.young 青年, 3.adult 成年, 4.old 老年
	 */
    public int              nAge;
	/**
	 * 性别 0.Unknow 未知, 1.male 男, 2.female 女, 3.other 其他
	 */
    public int              nGender;
	/**
	 * 是否为员工类,算法上报, -1 非该类, 0 未知, 1 是员工类
	 */
    public int              nStaffType;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuEventTime = new NET_TIME_EX();
	/**
	 * 人员的经过属性, 0 进, 1 出, 2 过
	 */
    public int              nHumanState;
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[64];

	public NET_CLUSTER_DETAIL_FIND_INFO() {
	}
}

