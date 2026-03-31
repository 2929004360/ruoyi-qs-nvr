package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 录像或抓图文件标志
 * @date 2022/11/03 09:21:59
 */
public enum EM_RECORD_SNAP_FLAG_TYPE {
	/**
	 * 未知
	 */
    FLAG_TYPE_UNKNOW(-1, "未知"),
	/**
	 * 定时文件
	 */
    FLAG_TYPE_TIMING(0, "定时文件"),
	/**
	 * 手动文件
	 */
    FLAG_TYPE_MANUAL(1, "手动文件"),
	/**
	 * 重要文件
	 */
    FLAG_TYPE_MARKED(2, "重要文件"),
	/**
	 * 事件文件
	 */
    FLAG_TYPE_EVENT(3, "事件文件"),
	/**
	 * 合成图片
	 */
    FLAG_TYPE_MOSAIC(4, "合成图片"),
	/**
	 * 抠图图片
	 */
    FLAG_TYPE_CUTOUT(5, "抠图图片"),
	/**
	 * 留言文件
	 */
    FLAG_TYPE_LEAVE_WORD(6, "留言文件"),
	/**
	 * 对讲本地方文件
	 */
    FLAG_TYPE_TALKBACK_LOCAL_SIDE(7, "对讲本地方文件"),
	/**
	 * 对讲远程方文件
	 */
    FLAG_TYPE_TALKBACK_REMOTE_SIDE(8, "对讲远程方文件"),
	/**
	 * 浓缩视频
	 */
    FLAG_TYPE_SYNOPSIS_VIDEO(9, "浓缩视频"),
	/**
	 * 原始视频
	 */
    FLAG_TYPE_ORIGINAL_VIDEO(10, "原始视频"),
	/**
	 * 已经预处理的原始视频
	 */
    FLAG_TYPE_PRE_ORIGINAL_VIDEO(11, "已经预处理的原始视频"),
	/**
	 * 禁止名单图片
	 */
    FLAG_TYPE_BLACK_PLATE(12, "禁止名单图片"),
	/**
	 * 原始图片
	 */
    FLAG_TYPE_ORIGINAL_PIC(13, "原始图片"),
	/**
	 * 卡号录像
	 */
    FLAG_TYPE_CARD(14, "卡号录像"),
	/**
	
	*/
    FLAG_TYPE_MAX(128, "");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_RECORD_SNAP_FLAG_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_RECORD_SNAP_FLAG_TYPE enumType : EM_RECORD_SNAP_FLAG_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_RECORD_SNAP_FLAG_TYPE enumType : EM_RECORD_SNAP_FLAG_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

