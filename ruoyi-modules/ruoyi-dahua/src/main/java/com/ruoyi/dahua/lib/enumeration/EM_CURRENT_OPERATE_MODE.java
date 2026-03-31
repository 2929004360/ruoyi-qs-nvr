package com.ruoyi.dahua.lib.enumeration;
/**
 * @author 251823
 * @description 当前运行模式
 * @date 2021/08/24
 */
public enum EM_CURRENT_OPERATE_MODE {
	// 未知
    EM_CURRENT_OPERATE_UNKNOWN(0, "未知"),
	// 多时段控制
    EM_CURRENT_OPERATE_MULPERIOD(1, "多时段控制"),
	// 手动控制
    EM_CURRENT_OPERATE_MANUAL_CONTROL(2, "手动控制"),
	// 关灯控制
    EM_CURRENT_OPERATE_TURNOFFLIGHT_CONTROL(3, "关灯控制"),
	// 全红控制
    EM_CURRENT_OPERATE_ALLRED_CONTROL(4, "全红控制"),
	// 自适应滤波控制
    EM_CURRENT_OPERATE_ADAPTFILTER_CONTROL(5, "自适应滤波控制"),
	// 感应控制
    EM_CURRENT_OPERATE_INDUCTION_CONTROL(6, "感应控制"),
	// 黄闪控制
    EM_CURRENT_OPERATE_YELLOWFLASH_CONTROL(7, "黄闪控制"),
	// 单点自适应模式
    EM_CURRENT_OPERATE_SINGAL_ADAPTINVE(8, "单点自适应模式"),
	// 平台临时方案模式
    EM_CURRENT_OPERATE_TMPPLATFORM_PLAN(9, "平台临时方案模式"),
	// 临时手控模式
    EM_CURRENT_OPERATE_TEMP_HAND(10, "临时手控模式"),
	// 实时控制模式
    EM_CURRENT_OPERATE_REALTIME_CONTROL(11, "实时控制模式"),
	// 灭灯模式
    EM_CURRENT_OPERATE_LIGHT_OFF(12, "灭灯模式"),
	// 红闪模式
    EM_CURRENT_OPERATE_RED_FLASH(13, "红闪模式"),
	// 绿闪模式
    EM_CURRENT_OPERATE_GREEN_FLASH(14, "绿闪模式"),
	// 行人优先控制模式
    EM_CURRENT_OPERATE_PEDESTRAIN_PRIORITY(15, "行人优先控制模式"),
	// 拥堵溢出控制模式
    EM_CURRENT_OPERATE_CONFGOVERFLOW_CONTROL(16, "拥堵溢出控制模式"),
	// 夜间请求模式
    EM_CURRENT_OPERATE_NIGHT_REQUEST(17, "夜间请求模式");

		private int value;
		private String note;

		private EM_CURRENT_OPERATE_MODE(int givenValue, String note) {
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
			for (EM_CURRENT_OPERATE_MODE enumType : EM_CURRENT_OPERATE_MODE.values()) {
				if (givenValue == enumType.getValue()) {
					return enumType.getNote();
				}
			}
			return null;
		}

		public static int getValueByNote(String givenNote) {
			for (EM_CURRENT_OPERATE_MODE enumType : EM_CURRENT_OPERATE_MODE.values()) {
				if (givenNote.equals(enumType.getNote())) {
					return enumType.getValue();
				}
			}
			return -1;
		}
}

