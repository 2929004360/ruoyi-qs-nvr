package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 报警产品本地用户信息
 * @date 2023/03/16 09:04:50
 */
public class NET_ALARM_USER extends NetSDKLib.SdkStructure {
	/**
	 * 用户编号
	 */
    public byte[]           szID = new byte[32];
	/**
	 * 用户状态 {@link com.ruoyi.dahua.lib.enumeration.EM_GETUSERINFOBYCONDITION_USER_STATUS}
	 */
    public int              emUserStatus;
	/**
	 * 用户名
	 */
    public byte[]           szName = new byte[64];
	/**
	 * 密码
	 */
    public byte[]           szPassword = new byte[64];
	/**
	 * 用户的权限列表个数
	 */
    public int              nAuthorityListNum;
	/**
	 * 用户的权限列表，数组值参考{@link com.ruoyi.dahua.lib.enumeration.EM_ALARM_USERAUTHORITY}
	 */
    public int[]            emAuthorityList = new int[32];
	/**
	 * 用户所在组 {@link com.ruoyi.dahua.lib.enumeration.EM_ALARM_USER_GROUP}
	 */
    public int              emGroup;
	/**
	 * 是否为保留用户，保留用户不可删除
	 */
    public int              bReserved;
	/**
	 * 用户类型 {@link com.ruoyi.dahua.lib.enumeration.EM_GETUSERINFOBYCONDITION_USER_TYPE}
	 */
    public int              emUserType;
	/**
	 * 相同短信时间间隔，间隔时间内若有连续相同的报告触发，则不上传。（当用户类型为Key时有效）单位：秒
	 */
    public int              nInterval;
	/**
	 * 允许访问的时间 开始时间
	 */
    public NET_TIME         stuAccessAllowTimeStart = new NET_TIME();
	/**
	 * 允许访问的时间 结束时间
	 */
    public NET_TIME         stuAccessAllowTimeEnd = new NET_TIME();
	/**
	 * 用户备注信息
	 */
    public byte[]           szMemo = new byte[32];
	/**
	 * 关联的子系统对应子系统号的个数
	 */
    public int              nSubSystemNum;
	/**
	 * 关联的子系统，对应子系统号，用户只能操作其关联的子系统
	 */
    public int[]            nSubSystems = new int[64];
	/**
	 * 关联防区的个数
	 */
    public int              nZoneNum;
	/**
	 * 关联防区，当emUserType=EM_GETUSERINFOBYCONDITION_USER_TYPE_KEY时用来关联Key防区。不同的Key防区可以关联不同的Key用户。
	 */
    public int[]            nZones = new int[256];
	/**
	 * 一键布防配置
	 */
    public NET_ALARM_USER_ONECLICKARMING stuOneClickArming = new NET_ALARM_USER_ONECLICKARMING();
	/**
	 * 用户信息扩展数据，需要用户申请内存,参考结构体{@link NET_ALARM_USER_EXTERN }
	 */
    public Pointer          pstuAlarmUserExtern;
	/**
	 * 保留字节
	 */
    public byte[]           byReserved = new byte[256 - NetSDKLib.POINTERSIZE];
}

