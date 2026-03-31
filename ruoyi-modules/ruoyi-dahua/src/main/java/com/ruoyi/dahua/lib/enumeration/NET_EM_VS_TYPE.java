package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 业务类型
 * @date 2022/10/28 10:46:07
 */
public enum NET_EM_VS_TYPE {
	/**
	 * 活跃度分析实时数据,对应 NET_VIDEOSTAT_SUMMARY_STEREO
	 */
    NET_EM_VS_TYPE_ACTIVITY_ANALYSE(0, "活跃度分析实时数据,对应 NET_VIDEOSTAT_SUMMARY_STEREO"),
	/**
	 * 人群分布图报表实时数据,对应 NET_VIDEOSTAT_CROWD_DISTRI_MAP
	 */
    NET_EM_VS_TYPE_CROWD_DISTRI_MAP(1, "人群分布图报表实时数据,对应 NET_VIDEOSTAT_CROWD_DISTRI_MAP"),
	/**
	 * 智能停车实时数据,对应 NET_VIDEOSTAT_SUMMARY_INTELLIPARKING
	 */
    NET_EM_VS_TYPE_INTELLIG_PARKING(3, "智能停车实时数据,对应 NET_VIDEOSTAT_SUMMARY_INTELLIPARKING"),
	/**
	 * 抬头检测周期上报数据, 对应 NET_VIDEOSTAT_SUMMARY_HEAD_LIFT_DETECTION
	 */
    NET_EM_VS_TYPE_HEAD_LIFT_DETECTION(4, "抬头检测周期上报数据, 对应 NET_VIDEOSTAT_SUMMARY_HEAD_LIFT_DETECTION"),
	/**
	 * 贮柜储量检测实时上报数据, 对应 NET_VIDEOSTAT_SUMMARY_TANK_CAPACITY_DETECTION
	 */
    NET_EM_VS_TYPE_TANK_CAPACITY_DETECTION(5, "贮柜储量检测实时上报数据, 对应 NET_VIDEOSTAT_SUMMARY_TANK_CAPACITY_DETECTION"),
    /**
     * /// 到课率检测事件, 对应 NET_VIDEOSTAT_ATTENDANCE_RATE_DETECTION
    */
    NET_EM_VS_TYPE_ATTENDANCE_RATE_DETECTION(6, "/// 到课率检测事件, 对应 NET_VIDEOSTAT_ATTENDANCE_RATE_DETECTION"),
    /**
     * /// 就座率检测事件, 对应 NET_VIDEOSTAT_SEATING_RATE_DETECTION
    */
    NET_EM_VS_TYPE_SEATING_RATE_DETECTION(7, "/// 就座率检测事件, 对应 NET_VIDEOSTAT_SEATING_RATE_DETECTION");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_VS_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_VS_TYPE enumType : NET_EM_VS_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_VS_TYPE enumType : NET_EM_VS_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

