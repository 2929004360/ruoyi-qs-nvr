package com.ruoyi.zlm.constants;

/**
 * @description: 定义常量
 * @author: fengcheng
 *
 */
public class VideoManagerConstants {

	public static final String ZLM_SERVER_STREAM_PREFIX = "ZLM_SIGNALLING_STREAM_";

	public static final String ONLINE_MEDIA_SERVERS_PREFIX = "ZLM_ONLINE_MEDIA_SERVERS:";

	public static final String MEDIA_STREAM_AUTHORITY = "ZLM_MEDIA_STREAM_AUTHORITY";

	public static final String INVITE_PREFIX = "ZLM_RTP_INVITE_INFO";

	//************************** redis 消息*********************************

	/**
	 * 流变化的通知
	 */
	public static final String ZLM_MSG_STREAM_CHANGE_PREFIX = "ZLM_MSG_STREAM_CHANGE_";

	/**
	 * 接收推流设备的GPS变化通知
	 */
	public static final String VM_MSG_GPS = "VM_MSG_GPS";

	/**
	 * 接收推流设备的GPS变化通知
	 */
	public static final String VM_MSG_PUSH_STREAM_STATUS_CHANGE = "VM_MSG_PUSH_STREAM_STATUS_CHANGE";
	/**
	 * 接收推流设备列表更新变化通知
	 */
	public static final String VM_MSG_PUSH_STREAM_LIST_CHANGE = "VM_MSG_PUSH_STREAM_LIST_CHANGE";

	/**
	 * 请求同步三方组织结构
	 */
	public static final String VM_MSG_GROUP_LIST_REQUEST = "VM_MSG_GROUP_LIST_REQUEST";

	/**
	 * 同步三方组织结构回复
	 */
	public static final String VM_MSG_GROUP_LIST_RESPONSE = "VM_MSG_GROUP_LIST_RESPONSE";

	/**
	 * 同步三方组织结构回复
	 */
	public static final String VM_MSG_GROUP_LIST_CHANGE = "VM_MSG_GROUP_LIST_CHANGE";

	/**
	 * redis 消息通知设备推流到平台
	 */
	public static final String VM_MSG_STREAM_PUSH_REQUESTED = "VM_MSG_STREAM_PUSH_REQUESTED";

	/**
	 * redis 消息通知上级平台开始观看流
	 */
	public static final String VM_MSG_STREAM_START_PLAY_NOTIFY = "VM_MSG_STREAM_START_PLAY_NOTIFY";

	/**
	 * redis 消息通知上级平台停止观看流
	 */
	public static final String VM_MSG_STREAM_STOP_PLAY_NOTIFY = "VM_MSG_STREAM_STOP_PLAY_NOTIFY";

	/**
	 * redis 消息接收关闭一个推流
	 */
	public static final String VM_MSG_STREAM_PUSH_CLOSE_REQUESTED = "VM_MSG_STREAM_PUSH_CLOSE_REQUESTED";


	/**
	 * redis 消息通知平台通知设备推流结果
	 */
	public static final String VM_MSG_STREAM_PUSH_RESPONSE = "VM_MSG_STREAM_PUSH_RESPONSE";

	/**
	 * redis 通知平台关闭推流
	 */
	public static final String VM_MSG_STREAM_PUSH_CLOSE = "VM_MSG_STREAM_PUSH_CLOSE";

	/**
	 * redis 消息请求所有的在线通道
	 */
	public static final String VM_MSG_GET_ALL_ONLINE_REQUESTED = "VM_MSG_GET_ALL_ONLINE_REQUESTED";

	/**
	 * 报警订阅的通知（收到报警向redis发出通知）
	 */
	public static final String VM_MSG_SUBSCRIBE_ALARM = "alarm";


	/**
	 * 报警通知的发送 （收到redis发出的通知，转发给其他平台）
	 */
	public static final String VM_MSG_SUBSCRIBE_ALARM_RECEIVE= "alarm_receive";

	/**
	 * 设备状态订阅的通知
	 */
	public static final String VM_MSG_SUBSCRIBE_DEVICE_STATUS = "device";

	public static final String PUSH_STREAM_LIST = "ZLM_PUSH_STREAM_LIST_";
}
