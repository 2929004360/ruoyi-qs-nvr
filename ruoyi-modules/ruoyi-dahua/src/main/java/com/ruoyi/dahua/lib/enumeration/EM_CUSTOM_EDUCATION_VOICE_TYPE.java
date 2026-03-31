package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 语音类型
 * @date 2023/01/03 11:10:33
 */
public enum EM_CUSTOM_EDUCATION_VOICE_TYPE {
  /**
   * 未知
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_UNKNOWN(0, "未知"),
  /**
   * 超时出校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_LEAVE_SCHOOL_TIMEOUT(1, "超时出校"),
  /**
   * 超时进校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ENTER_SCHOOL_TIMEOUT(2, "超时进校"),
  /**
   * 出校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_LEAVE_SCHOOL(3, "出校"),
  /**
   * 此卡未绑定
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_CARD_NOT_BIND(4, "此卡未绑定"),
  /**
   * 寄宿生
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_BOARDER(5, "寄宿生"),
  /**
   * 家长卡已挂失
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_PARENT_CARD_REPORT_LOST(6, "家长卡已挂失"),
  /**
   * 进校已过期
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ENTER_SCHOOL_OVERDUE(7, "进校已过期"),
  /**
   * 您有包裹待领取
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_PACKAGE_TO_PICKUP(8, "您有包裹待领取"),
  /**
   * 请假
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE(9, "请假"),
  /**
   * 请假返校超时
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_RETURN_SCHOOL_TIMEOUT_WHEN_ASKFORLEAVE(10, "请假返校超时"),
  /**
   * 请假进出校时间未到
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_INANDOUT_SCHOOL_TIMENOTUP_WHEN_ASKFORLEAVE(11, "请假进出校时间未到"),
  /**
   * 请假拒绝出校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_REFUSE_LEAVE_SCHOOL_WHEN_ASKFORLEAVE(12, "请假拒绝出校"),
  /**
   * 请假拒绝进校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_REFUSE_ENTER_SCHOOL_WHEN_ASKFORLEAVE(13, "请假拒绝进校"),
  /**
   * 请假审核中
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE_IN_REVIEW(14, "请假审核中"),
  /**
   * 请假已过期
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE_EXPIRED(15, "请假已过期"),
  /**
   * 请假已批准
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ASKFORLEAVE_APPROVED(16, "请假已批准"),
  /**
   * 请假已失效禁止重复出校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_FORBID_LEAVE_SCHOOL_WITH_LEAVE_INVALID(17, "请假已失效禁止重复出校"),
  /**
   * 时间未到
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_TIME_ISNOT_UP(18, "时间未到"),
  /**
   * 未预约
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_NOT_APPOINT(19, "未预约"),
  /**
   * 未在允许时段内禁止通行
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_NO_PASSAGE_IN_NONPERMIT_TIMESECTION(20, "未在允许时段内禁止通行"),
  /**
   * 无效卡
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_INVALID_CARD(21, "无效卡"),
  /**
   * 已预约
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ALREADY_APPOINTED(22, "已预约"),
  /**
   * 允许返校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ALLOW_BACK_SCHOOL(23, "允许返校"),
  /**
   * 再见
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_GOODBYE(24, "再见"),
  /**
   * 正常进校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ENTER_SCHOOL_NORMALLY(25, "正常进校"),
  /**
   * 重复出校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_REPEAT_LEAVE_SCHOOLL(26, "重复出校"),
  /**
   * 重复进校
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_REPEAT_ENTER_SCHOOLL(27, "重复进校"),
  /**
   * 走读生
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_DAY_STUDENT(28, "走读生"),
  /**
   * 欢迎光临
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_WELCOME(29, "欢迎光临"),
  /**
   * 教职工
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_FACULTY(30, "教职工"),
  /**
   * 老师
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_TEACHER(31, "老师"),
  /**
   * 老师好
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_HELLO_TEACHER(32, "老师好"),
  /**
   * 允许访问
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_ALLOW_ACCESS(33, "允许访问"),
  /**
   * tts文字语音输出
   */
    EM_CUSTOM_EDUCATION_VOICE_TYPE_TTS_TEXT_VOICE_OUTPUT(50, "tts文字语音输出");

  private int value;
  private String note;

  public String getNote() {
    return note;
  }

  public int getValue() {
    return value;
  }

  EM_CUSTOM_EDUCATION_VOICE_TYPE(int givenValue, String note) {
    this.value = givenValue;
    this.note = note;
  }

  public static String getNoteByValue(int givenValue) {
    for (EM_CUSTOM_EDUCATION_VOICE_TYPE enumType : EM_CUSTOM_EDUCATION_VOICE_TYPE.values()) {
      if (givenValue == enumType.getValue()) {
        return enumType.getNote();
      }
    }
    return null;
  }

  public static int getValueByNote(String givenNote) {
    for (EM_CUSTOM_EDUCATION_VOICE_TYPE enumType : EM_CUSTOM_EDUCATION_VOICE_TYPE.values()) {
      if (givenNote.equals(enumType.getNote())) {
        return enumType.getValue();
      }
    }
    return -1;
  }

  public static EM_CUSTOM_EDUCATION_VOICE_TYPE getEnum(int value) {
    for (EM_CUSTOM_EDUCATION_VOICE_TYPE e : EM_CUSTOM_EDUCATION_VOICE_TYPE.values()) {
      if (e.getValue() == value)
        return e;
    }
    return EM_CUSTOM_EDUCATION_VOICE_TYPE.EM_CUSTOM_EDUCATION_VOICE_TYPE_UNKNOWN;
  }
}

