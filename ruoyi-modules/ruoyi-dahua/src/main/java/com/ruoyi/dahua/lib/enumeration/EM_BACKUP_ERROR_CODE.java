package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 备份模式设置错误码
 * @date 2021/09/28
 */
public enum EM_BACKUP_ERROR_CODE {
	// 未知
    EM_BACKUP_ERROR_CODE_UNKNOWN(-1, "未知"),
	// 备份模式设置成功
    EM_BACKUP_ERROR_CODE_SUCCESS(0, "备份模式设置成功"),
	// 周期方案表有误
    EM_BACKUP_ERROR_CODE_CYCLE_PLAN(1, "周期方案表有误"),
	// 绿信比表有误
    EM_BACKUP_ERROR_CODE_GREEN_LETTER(2, "绿信比表有误"),
	// 时基方案表有误
    EM_BACKUP_ERROR_CODE_TIME_BASE_PLAN(3, "时基方案表有误"),
	// 日计划表有误
    EM_BACKUP_ERROR_CODE_DAY_PLAN(4, "日计划表有误"),
	// 动作方案表有误
    EM_BACKUP_ERROR_CODE_ACTION_PLAN(5, "动作方案表有误"),
	// 相序表有误
    EM_BACKUP_ERROR_CODE_PHASE_SEQUENCE(6, "相序表有误"),
	// 通道表有误
    EM_BACKUP_ERROR_CODE_CHANNEL_TABLE(7, "通道表有误"),
	// 跟随相位表有误
    EM_BACKUP_ERROR_CODE_FOLLOW_PHASE_TABLE(8, "跟随相位表有误"),
	// 相位冲突
    EM_BACKUP_ERROR_CODE_PHASE_CONFLCT(9, "相位冲突"),
	// 下发方案超时
    EM_BACKUP_ERROR_CODE_DELIVERY_PLAN(10, "下发方案超时"),
	// 车道表设置有误
    EM_BACKUP_ERROR_CODE_LANE_TABLE_SETTING(11, "车道表设置有误"),
    // 用于信号灯，提示信号灯不支持短连接路口
    EM_BACKUP_ERROR_CODE_NOT_SUPPORT_SHORT_JUNCTIONS(12, "用于信号灯，提示信号灯不支持短连接路口"),
    // 通道配置有误 用于信号灯 无掉头箭头灯、直行非机动车灯、左转非机动车灯、公交信号灯--可变车道
    EM_BACKUP_ERROR_CODE_CHANNEL_CONFIGURATION(13, "通道配置有误"),
    // 可变车道方案表有误
    EM_BACKUP_ERROR_CODE_VARIABLEE_LANE_TABLE(14, "可变车道方案表有误	"),
    // 周计划方案表有误
    EM_BACKUP_ERROR_CODE_WEEKLY_PLAN(15, "周计划方案表有误");

	private int value;
	private String note;

	private EM_BACKUP_ERROR_CODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_BACKUP_ERROR_CODE enumType : EM_BACKUP_ERROR_CODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_BACKUP_ERROR_CODE enumType : EM_BACKUP_ERROR_CODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

