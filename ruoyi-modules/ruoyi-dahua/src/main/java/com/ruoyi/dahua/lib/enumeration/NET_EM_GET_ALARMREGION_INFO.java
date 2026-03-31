package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 获取的操作类型
 * @date 2021/11/08
 */
public class NET_EM_GET_ALARMREGION_INFO {
    public static final int   NET_EM_GET_ALARMREGION_INFO_UNKNOWN = 0; // 未知
    public static final int   NET_EM_GET_ALARMREGION_INFO_ALARMCAPS = 1; // 获取防区能力，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_ALARMCAPS，
																		// pstuOutParam类型为NET_OUT_GET_ALARMCAPS
    public static final int   NET_EM_GET_ALARMREGION_INFO_ARMMODE = 2; // 获取布防状态，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_ALARMMODE，
																		// pstuOutParam类型为NET_OUT_GET_ALARMMODE
    public static final int   NET_EM_GET_ALARMREGION_INFO_BYPASSMODE = 3; // 获取旁路状态，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_BYPASSMODE，
																		// pstuOutParam类型为NET_OUT_GET_BYPASSMODE
    public static final int   NET_EM_GET_ALARMREGION_INFO_AREAZONES = 4; // 获取区域防区，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_AREAZONES，
																		// pstuOutParam类型为NET_OUT_GET_AREAZONES
    public static final int   NET_EM_GET_ALARMREGION_INFO_ALLINSLOTS = 5; // 获取所有的报警防区，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_ALLINSLOTS，
																		// pstuOutParam类型为NET_OUT_GET_ALLINSLOTS
    public static final int   NET_EM_GET_ALARMREGION_INFO_ALLOUTSLOTS = 6; // 获取所有的报警输出，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_ALLOUTSLOTS，
																			// pstuOutParam类型为NET_OUT_GET_ALLOUTSLOTS
    public static final int   NET_EM_GET_ALARMREGION_INFO_ZONECONNECTIONSSTATUS = 7; // 获取防区连接状态，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_CONNECTIONSTATUS，
																					// pstuOutParam类型为NET_OUT_GET_CONNECTIONSTATUS
    public static final int   NET_EM_GET_ALARMREGION_INFO_AREASTATUS = 8; // 获取区域状态，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_AREAS_STATUS，
																		// pstuOutParam类型为NET_OUT_GET_AREAS_STATUS
    public static final int   NET_EM_GET_ALARMREGION_INFO_OUTPUTSTATE = 9; // 获取输出状态，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_OUTPUT_STATE，
																			// pstuOutParam类型为NET_OUT_GET_OUTPUT_STATE
    public static final int   NET_EM_GET_ALARMREGION_INFO_ZONESTROUBLE = 10; // 获取防区故障信息，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_ZONES_TROUBLE，
																			// pstuOutParam类型为NET_OUT_GET_ZONES_TROUBLE
    public static final int   NET_EM_GET_ALARMREGION_INFO_CHANNELSSTATE = 11; // 获取通道状态，此时CLIENT_GetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_GET_CHANNELS_STATE，
																			// pstuOutParam类型为NET_OUT_GET_CHANNELS_STATE
}

