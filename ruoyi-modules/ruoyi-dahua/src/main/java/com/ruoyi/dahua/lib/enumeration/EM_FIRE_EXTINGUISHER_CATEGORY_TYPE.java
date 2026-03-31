package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 目标物体子类型
 * @date 2021/12/27
 */
public enum EM_FIRE_EXTINGUISHER_CATEGORY_TYPE {
	// 未知
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_UNKNOWN(0, "未知"),
	// 普通类型灭火器，8kg左右
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_EXTINGUISHER_GENERAL(1, "普通类型灭火器，8kg左右"),
	// 35kg推车式灭火器
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_EXTINGUISHER_HANDPUSH(2, "35kg推车式灭火器"),
	// 普通标识牌
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_SIGNBOARD_GENERAL(3, "普通标识牌"),
	// 静电标示牌
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_SIGNBOARD_STATIC_ELETRICITY(4, "静电标示牌"),
	// 加油口盖标示牌
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_SIGNBOARD_OILCAP(5, "加油口盖标示牌"),
	// 路锥
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_ROADCONE(6, "路锥"),
	// 盖子
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_COVERPLATE(7, "盖子"),
	// 加油口盖标示牌打开
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_OILCAP_OPEN(8, "加油口盖标示牌打开"),
	// 加油口盖标示牌关闭
    EM_FIRE_EXTINGUISHER_CATEGORY_TYPE_OILCAP_CLOSE(9, "加油口盖标示牌关闭");

	private int value;
	private String note;

	private EM_FIRE_EXTINGUISHER_CATEGORY_TYPE(int givenValue, String note) {
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
		for (EM_FIRE_EXTINGUISHER_CATEGORY_TYPE enumType : EM_FIRE_EXTINGUISHER_CATEGORY_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_FIRE_EXTINGUISHER_CATEGORY_TYPE enumType : EM_FIRE_EXTINGUISHER_CATEGORY_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

