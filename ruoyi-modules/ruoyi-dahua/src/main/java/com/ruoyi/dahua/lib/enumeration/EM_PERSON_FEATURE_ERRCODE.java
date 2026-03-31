package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 47081
 * @version 1.0
 * @description 建模失败原因
 * @date 2021/4/22
 */
public enum EM_PERSON_FEATURE_ERRCODE {
  /** 未知 */
    EM_PERSON_FEATURE_ERRCODE_UNKNOWN(0, "未知"),
  /** 图片格式问题 */
    EM_PERSON_FEATURE_ERRCODE_PIC_FORMAT(1, "图片格式问题"),
  /** 无人脸或不清晰 */
    EM_PERSON_FEATURE_ERRCODE_NO_FACE(2, "无人脸或不清晰"),
  /** 多个人脸 */
    EM_PERSON_FEATURE_ERRCODE_MULTI_FACE(3, "多个人脸"),
  /** 图片解码失败 */
    EM_PERSON_FEATURE_ERRCODE_PIC_DECODE_FAIL(4, "图片解码失败"),
  /** 不推荐入库 */
    EM_PERSON_FEATURE_ERRCODE_NOT_RECOMMEND(5, "不推荐入库"),
  /** 数据库操作失败 */
    EM_PERSON_FEATURE_ERRCODE_FACEDB_FAIL(6, "数据库操作失败"),
  /** 获取图片失败 */
    EM_PERSON_FEATURE_ERRCODE_GET_PICTURE(7, "获取图片失败"),
  /** 系统异常 */
    EM_PERSON_FEATURE_ERRCODE_SYSTEM_ERROR(8, "系统异常");

  private int errorCode;
  private String reason;

  EM_PERSON_FEATURE_ERRCODE(int errorCode, String reason) {
    this.errorCode = errorCode;
    this.reason = reason;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public static EM_PERSON_FEATURE_ERRCODE getError(int errorCode) {
    for (EM_PERSON_FEATURE_ERRCODE personError : EM_PERSON_FEATURE_ERRCODE.values()) {
      if (personError.errorCode == errorCode) {
        return personError;
      }
    }
    return EM_PERSON_FEATURE_ERRCODE_UNKNOWN;
  }

  public static String getErrorMessage(int errorCode) {
    return getError(errorCode).getReason();
  }
}

