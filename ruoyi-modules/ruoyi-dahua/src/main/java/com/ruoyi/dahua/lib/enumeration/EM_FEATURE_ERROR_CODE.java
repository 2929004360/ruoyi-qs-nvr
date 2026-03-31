package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 特征建模失败错误码
 * @origin autoTool
 * @date 2023/06/28 14:07:32
 */
public enum EM_FEATURE_ERROR_CODE {
	/**
     * 成功
     */
    EM_FEATURE_ERROR_SUCCESS(0, "成功"),
	/**
     * 未知
     */
    EM_FEATURE_ERROR_UNKNOWN(1, "未知"),
	/**
     * 图片格式问题
     */
    EM_FEATURE_ERROR_IMAGE_FORMAT_ERROR(2, "图片格式问题"),
	/**
     * 无目标或不够清晰
     */
    EM_FEATURE_ERROR_NOFACE_OR_NOTCLEAR(3, "无目标或不够清晰"),
	/**
     * 多个目标
     */
    EM_FEATURE_ERROR_MULT_FACES(4, "多个目标"),
	/**
     * 图片解码失败
     */
    EM_FEATURE_ERROR_IMAGH_DECODE_FAILED(5, "图片解码失败"),
	/**
     * 不推荐入库
     */
    EM_FEATURE_ERROR_NOT_SUGGEST_STORAGE(6, "不推荐入库"),
	/**
     * 数据库操作失败
     */
    EM_FEATURE_ERROR_DATABASE_OPERATE_FAILED(7, "数据库操作失败"),
	/**
     * 获取图片失败
     */
    EM_FEATURE_ERROR_GET_IMAGE_FAILED(8, "获取图片失败"),
	/**
     * 系统异常（如Licence失效、建模分析器未启动导致的失败）
     */
    EM_FEATURE_ERROR_SYSTEM_EXCEPTION(9, "系统异常（如Licence失效、建模分析器未启动导致的失败）"),
	/**
     * 目标宽度过小
     */
    EM_FEATURE_ERROR_UNDERSIZED_WIDTH(10, "目标宽度过小"),
	/**
     * 目标眼瞳间距过小
     */
    EM_FEATURE_ERROR_UNDERSIZED_INTERPUPILLARY_DISTANCE(11, "目标眼瞳间距过小"),
	/**
     * 目标脸对齐分数过低
     */
    EM_FEATURE_ERROR_FACE_ALIGNMENT_SCORE_LOW(12, "目标脸对齐分数过低"),
	/**
     * 目标俯仰角过大
     */
    EM_FEATURE_ERROR_OVERSIZED_FACE_PITCH_ANGLE(13, "目标俯仰角过大"),
	/**
     * 目标偏航角过大
     */
    EM_FEATURE_ERROR_OVERSIZED_FACE_YAW_ANGLE(14, "目标偏航角过大"),
	/**
     * 目标滚转角过大
     */
    EM_FEATURE_ERROR_OVERSIZED_FACE_ROLL_ANGLE(15, "目标滚转角过大"),
	/**
     * 目标质量分过低
     */
    EM_FEATURE_ERROR_FACE_QUALITY_LOW(16, "目标质量分过低"),
	/**
     * 目标清晰度过低
     */
    EM_FEATURE_ERROR_FACE_DEFINITION_LOW(17, "目标清晰度过低"),
	/**
     * 目标眼部遮挡
     */
    EM_FEATURE_ERROR_EYE_OCCLUSION(18, "目标眼部遮挡"),
	/**
     * 目标额头遮挡
     */
    EM_FEATURE_ERROR_FOREHEAD_OCCLUSION(19, "目标额头遮挡"),
	/**
     * 目标嘴巴遮挡
     */
    EM_FEATURE_ERROR_MOUTH_OCCLUSION(20, "目标嘴巴遮挡"),
	/**
     * 目标不完整
     */
    EM_FEATURE_ERROR_INCOMPLETE_FACE(21, "目标不完整"),
	/**
     * 目标光照过曝或者欠曝
     */
    EM_FEATURE_ERROR_ABNORMAL_FACE_EXPOSURE(22, "目标光照过曝或者欠曝"),
	/**
     * 目标饱和度过低
     */
    EM_FEATURE_ERROR_FACE_SATURATION_LOW(23, "目标饱和度过低"),
	/**
     * 目标区域在图像中占比过大
     */
    EM_FEATURE_ERROR_FACE_AREA_OVER_PROPORTION(24, "目标区域在图像中占比过大"),
	/**
     * 图像中未检出目标
     */
    EM_FEATURE_ERROR_FACE_DETECTION_FAILE(25, "图像中未检出目标");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_FEATURE_ERROR_CODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_FEATURE_ERROR_CODE enumType : EM_FEATURE_ERROR_CODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_FEATURE_ERROR_CODE enumType : EM_FEATURE_ERROR_CODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_FEATURE_ERROR_CODE getEnum(int value) {
		for (EM_FEATURE_ERROR_CODE e : EM_FEATURE_ERROR_CODE.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EM_FEATURE_ERROR_CODE.EM_FEATURE_ERROR_SUCCESS;
	}
}

