package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 逻辑屏显示内容
 * @date 2022/05/10 10:39:28
 */
public enum NET_EM_SCREEN_SHOW_CONTENTS {
	/**
	 * 未知
	 */
    EM_TRAFFIC_LATTICE_SCREEN_UNKNOWN(0, "未知"),
	/**
	 * 车牌
	 */
    EM_TRAFFIC_LATTICE_SCREEN_PLATENUMBE(1, "车牌"),
	/**
	 * 系统时间
	 */
    EM_TRAFFIC_LATTICE_SCREEN_SYSTIME(2, "系统时间"),
	/**
	 * 停车时长
	 */
    EM_TRAFFIC_LATTICE_SCREEN_PARKTIME(3, "停车时长"),
	/**
	 * 车主姓名
	 */
    EM_TRAFFIC_LATTICE_SCREEN_MASTEROFCAR(4, "车主姓名"),
	/**
	 * 用户类型
	 */
    EM_TRAFFIC_LATTICE_SCREEN_USERTYPE(5, "用户类型"),
	/**
	 * 到期天数
	 */
    EM_TRAFFIC_LATTICE_SCREEN_REMAINDAY(6, "到期天数"),
	/**
	 * 停车费
	 */
    EM_TRAFFIC_LATTICE_SCREEN_PARKCHARGE(7, "停车费"),
	/**
	 * 余位数
	 */
    EM_TRAFFIC_LATTICE_SCREEN_REMAINSPACE(8, "余位数"),
	/**
	 * 系统日期
	 */
    EM_TRAFFIC_LATTICE_SCREEN_SYSDATE(9, "系统日期"),
	/**
	 * 车辆通过指示灯(红圆绿圆组合指示灯)
	 */
    EM_TRAFFIC_LATTICE_SCREEN_PASSICONCIRCLE(10, "车辆通过指示灯(红圆绿圆组合指示灯)"),
	/**
	 * 车辆通过指示灯(红叉绿箭组合指示灯)
	 */
    EM_TRAFFIC_LATTICE_SCREEN_PASSICONARROW(11, "车辆通过指示灯(红叉绿箭组合指示灯)"),
	/**
	 * 入场时间
	 */
    EM_TRAFFIC_LATTICE_SCREEN_INTIME(12, "入场时间"),
	/**
	 * 出场时间
	 */
    EM_TRAFFIC_LATTICE_SCREEN_OUTTIME(13, "出场时间"),
	/**
	 * 备注信息
	 */
    EM_TRAFFIC_LATTICE_SCREEN_REMARKS(14, "备注信息"),
	/**
	 * 资源文件(视频或图片)
	 */
    EM_TRAFFIC_LATTICE_SCREEN_RESOURCE(15, "资源文件(视频或图片)"),
	/**
	 * 自定义信息
	 */
    EM_TRAFFIC_LATTICE_SCREEN_CUSTOM(16, "自定义信息");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_SCREEN_SHOW_CONTENTS(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_SCREEN_SHOW_CONTENTS enumType : NET_EM_SCREEN_SHOW_CONTENTS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_SCREEN_SHOW_CONTENTS enumType : NET_EM_SCREEN_SHOW_CONTENTS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

