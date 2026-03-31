package com.ruoyi.dahua.lib.enumeration;
/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 10:28
 *  报警位置
 */
public enum EM_SECURITYGATE_ALARM_POSITION {
    EM_SECURITYGATE_ALARM_POSITION_UNKNWON(-1, "未知"),// 未知
    EM_SECURITYGATE_ALARM_POSITION_LEFT_1(0, "左1"),// 左1
    EM_SECURITYGATE_ALARM_POSITION_LEFT_2(1, "左2"),// 左2
    EM_SECURITYGATE_ALARM_POSITION_LEFT_3(2, "左3"),// 左3
    EM_SECURITYGATE_ALARM_POSITION_LEFT_4(3, "左4"),// 左4
    EM_SECURITYGATE_ALARM_POSITION_LEFT_5(4, "左5"),// 左5
    EM_SECURITYGATE_ALARM_POSITION_LEFT_6(5, "左6"),// 左6
    EM_SECURITYGATE_ALARM_POSITION_MIDDLE_1(6, "中1"),// 中1
    EM_SECURITYGATE_ALARM_POSITION_MIDDLE_2(7, "中2"),// 中2
    EM_SECURITYGATE_ALARM_POSITION_MIDDLE_3(8, "中3"),// 中3
    EM_SECURITYGATE_ALARM_POSITION_MIDDLE_4(9, "中4"),// 中4
    EM_SECURITYGATE_ALARM_POSITION_MIDDLE_5(10, "中5"),// 中5
    EM_SECURITYGATE_ALARM_POSITION_MIDDLE_6(11, "中6"),// 中6
    EM_SECURITYGATE_ALARM_POSITION_RIGHT_1(12, "右1"),// 右1
    EM_SECURITYGATE_ALARM_POSITION_RIGHT_2(13, "右2"),// 右2
    EM_SECURITYGATE_ALARM_POSITION_RIGHT_3(14, "右3"),// 右3
    EM_SECURITYGATE_ALARM_POSITION_RIGHT_4(15, "右4"),// 右4
    EM_SECURITYGATE_ALARM_POSITION_RIGHT_5(16, "右5"),// 右5
    EM_SECURITYGATE_ALARM_POSITION_RIGHT_6(17, "右6");// 右6

    private final int value;
    private final String note;

    EM_SECURITYGATE_ALARM_POSITION(int givenValue, String note) {
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
        for (EM_SECURITYGATE_ALARM_POSITION enumType : EM_SECURITYGATE_ALARM_POSITION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITYGATE_ALARM_POSITION enumType : EM_SECURITYGATE_ALARM_POSITION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SECURITYGATE_ALARM_POSITION getEnum(int value) {
        for (EM_SECURITYGATE_ALARM_POSITION e : EM_SECURITYGATE_ALARM_POSITION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SECURITYGATE_ALARM_POSITION.EM_SECURITYGATE_ALARM_POSITION_UNKNWON;
    }
}

