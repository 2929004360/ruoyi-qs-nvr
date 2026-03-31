package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 主动跟踪主操作命令, 接口 CLIENT_OperateMasterSlaveGroup
 * @date 2022/09/14 13:50:01
 */
public enum EM_MSGROUP_OPERATE_TYPE {
	/**
	 * 打开主从跟踪组, 对应 NET_IN_MSGROUP_OPEN_INFO 和 NET_OUT_MSGROUP_OPEN_INFO
	 */
    EM_MSGROUP_OPERATE_OPEN(0, "打开主从跟踪组, 对应 NET_IN_MSGROUP_OPEN_INFO 和 NET_OUT_MSGROUP_OPEN_INFO"),
	/**
	 * 联动主从跟踪组, 对应 NET_IN_MSGROUP_LOCATE_INFO 和 NET_OUT_MSGROUP_LOCATE_INTO
	 */
    EM_MSGROUP_OPERATE_LOCATE(1, "联动主从跟踪组, 对应 NET_IN_MSGROUP_LOCATE_INFO 和 NET_OUT_MSGROUP_LOCATE_INTO"),
	/**
	 * 手动选定目标跟踪, 对应 NET_IN_MSGROUP_TRACK_INFO 和 NET_OUT_MSGROUP_TRACK_INFO
	 */
    EM_MSGROUP_OPERATE_TRACK(2, "手动选定目标跟踪, 对应 NET_IN_MSGROUP_TRACK_INFO 和 NET_OUT_MSGROUP_TRACK_INFO"),
	/**
	 * 关闭主从式跟踪器实例, 对应 NET_IN_MSGROUP_CLOSE_INFO 和 NET_OUT_MSGROUP_CLOSE_INFO
	 */
    EM_MSGROUP_OPERATE_CLOSE(3, "关闭主从式跟踪器实例, 对应 NET_IN_MSGROUP_CLOSE_INFO 和 NET_OUT_MSGROUP_CLOSE_INFO"),
	/**
	 * 从主设备选择矩形框联动从设备, 对应 NET_IN_MSGROUP_RECTLOCATE_INFO 和NET_OUT_MSGROUP_RECTLOCATE_INFO
	 */
    EM_MSGROUP_OPERATE_RECTLOCATE(4, "从主设备选择矩形框联动从设备, 对应 NET_IN_MSGROUP_RECTLOCATE_INFO 和 NET_OUT_MSGROUP_RECTLOCATE_INFO"),
	/**
	 * 将从机固定位置下的视频坐标转换为主机的视频坐标, 对应 对应 NET_IN_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO 和
	 * NET_OUT_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO
	 */
    EM_MSGROUP_OPERATE_SLAVE_POSITION_TO_MASTER(5, "将从机固定位置下的视频坐标转换为主机的视频坐标, 对应 对应 NET_IN_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO 和 NET_OUT_MSGROUP_SLAVE_POSITION_TO_MASTER_INFO");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_MSGROUP_OPERATE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_MSGROUP_OPERATE_TYPE enumType : EM_MSGROUP_OPERATE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_MSGROUP_OPERATE_TYPE enumType : EM_MSGROUP_OPERATE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

