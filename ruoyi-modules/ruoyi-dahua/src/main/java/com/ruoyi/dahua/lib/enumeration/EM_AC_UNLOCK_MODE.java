package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  开锁模式 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public enum EM_AC_UNLOCK_MODE {
/**
未知
*/
    EM_AC_UNLOCK_MODE_UNKNOWN(0, "未知"),
/**
只允许密码开锁
*/
    EM_AC_UNLOCK_MODE_PWD_ONLY(1, "只允许密码开锁"),
/**
只允许刷卡开锁
*/
    EM_AC_UNLOCK_MODE_CARD(2, "只允许刷卡开锁"),
/**
密码或刷卡开锁
*/
    EM_AC_UNLOCK_MODE_PWD_OR_CARD(3, "密码或刷卡开锁"),
/**
先刷卡后密码开锁
*/
    EM_AC_UNLOCK_MODE_CARD_FIRST(4, "先刷卡后密码开锁"),
/**
先密码后刷卡开锁
*/
    EM_AC_UNLOCK_MODE_PWD_FIRST(5, "先密码后刷卡开锁"),
/**
分时段开门
*/
    EM_AC_UNLOCK_MODE_SECTION(6, "分时段开门"),
/**
仅信息开锁
*/
    EM_AC_UNLOCK_MODE_FINGERPRINTONLY(7, "仅信息开锁"),
/**
密码或刷卡或信息开锁
*/
    EM_AC_UNLOCK_MODE_PWD_OR_CARD_OR_FINGERPRINT(8, "密码或刷卡或信息开锁"),
/**
密码+刷卡+信息组合开锁
*/
    EM_AC_UNLOCK_MODE_PWD_AND_CARD_AND_FINGERPINT(9, "密码+刷卡+信息组合开锁"),
/**
密码+信息组合开锁
*/
    EM_AC_UNLOCK_MODE_PWD_AND_FINGERPRINT(10, "密码+信息组合开锁"),
/**
刷卡+信息开锁
*/
    EM_AC_UNLOCK_MODE_CARD_AND_FINGERPRINT(11, "刷卡+信息开锁"),
/**
多人开锁
*/
    EM_AC_UNLOCK_MODE_MULTI_PERSON(12, "多人开锁"),
/**
人证对比
*/
    EM_AC_UNLOCK_MODE_FACEIDCARD(13, "人证对比"),
/**
证件+ 人证比对
*/
    EM_AC_UNLOCK_MODE_FACEIDCARD_AND_IDCARD(14, "证件+ 人证比对"),
/**
人证比对或刷卡或信息
*/
    EM_AC_UNLOCK_MODE_FACEIDCARD_OR_CARD_OR_FINGER(15, "人证比对或刷卡或信息"),
/**
(证件+认证比对)或刷卡或信息
*/
    EM_AC_UNLOCK_MODE_FACEIPCARDANDIDCARD_OR_CARD_OR_FINGER(16, "(证件+认证比对)或刷卡或信息"),
/**
UserID+密码
*/
    EM_AC_UNLOCK_MODE_USERID_AND_PWD(17, "UserID+密码"),
/**
只允许目标开锁
*/
    EM_AC_UNLOCK_MODE_FACE_ONLY(18, "只允许目标开锁"),
/**
目标+密码开锁
*/
    EM_AC_UNLOCK_MODE_FACE_AND_PWD(19, "目标+密码开锁"),
/**
信息+密码开锁
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT_AND_PWD(20, "信息+密码开锁"),
/**
信息+目标开锁
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT_AND_FACE(21, "信息+目标开锁"),
/**
刷卡+目标开锁
*/
    EM_AC_UNLOCK_MODE_CARD_AND_FACE(22, "刷卡+目标开锁"),
/**
目标或密码开锁
*/
    EM_AC_UNLOCK_MODE_FACE_OR_PWD(23, "目标或密码开锁"),
/**
信息或密码开锁
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT_OR_PWD(24, "信息或密码开锁"),
/**
信息或目标开锁
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT_OR_FACE(25, "信息或目标开锁"),
/**
刷卡或目标开锁
*/
    EM_AC_UNLOCK_MODE_CARD_OR_FACE(26, "刷卡或目标开锁"),
/**
刷卡或信息开锁
*/
    EM_AC_UNLOCK_MODE_CARD_OR_FINGERPRINT(27, "刷卡或信息开锁"),
/**
信息+目标+密码开锁
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT_AND_FACE_AND_PWD(28, "信息+目标+密码开锁"),
/**
刷卡+目标+密码开锁
*/
    EM_AC_UNLOCK_MODE_CARD_AND_FACE_AND_PWD(29, "刷卡+目标+密码开锁"),
/**
刷卡+信息+密码开锁
*/
    EM_AC_UNLOCK_MODE_CARD_AND_FINGERPRINT_AND_PWD(30, "刷卡+信息+密码开锁"),
/**
卡+信息+目标组合开锁
*/
    EM_AC_UNLOCK_MODE_CARD_AND_FINGERPRINT_AND_FACE(31, "卡+信息+目标组合开锁"),
/**
信息或目标或密码
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT_OR_FACE_OR_PWD(32, "信息或目标或密码"),
/**
卡或目标或密码开锁
*/
    EM_AC_UNLOCK_MODE_CARD_OR_FACE_OR_PWD(33, "卡或目标或密码开锁"),
/**
卡或信息或目标开锁
*/
    EM_AC_UNLOCK_MODE_CARD_OR_FINGERPRINT_OR_FACE(34, "卡或信息或目标开锁"),
/**
卡+信息+目标+密码组合开锁
*/
    EM_AC_UNLOCK_MODE_CARD_AND_FINGERPRINT_AND_FACE_AND_PWD(35, "卡+信息+目标+密码组合开锁"),
/**
卡或信息或目标或密码开锁
*/
    EM_AC_UNLOCK_MODE_CARD_OR_FINGERPRINT_OR_FACE_OR_PWD(36, "卡或信息或目标或密码开锁"),
/**
(证件+人证比对)或 刷卡 或 目标
*/
    EM_AC_UNLOCK_MODE_FACEIPCARDANDIDCARD_OR_CARD_OR_FACE(37, "(证件+人证比对)或 刷卡 或 目标"),
/**
人证比对 或 刷卡(二维码) 或 目标
*/
    EM_AC_UNLOCK_MODE_FACEIDCARD_OR_CARD_OR_FACE(38, "人证比对 或 刷卡(二维码) 或 目标"),
/**
(卡+密码）或（信息+密码）默认为2
*/
    EM_AC_UNLOCK_MODE_CARDANDPWD_OR_FINGERPRINTANDPWD(39, "(卡+密码）或（信息+密码）默认为2"),
/**
人证(照片)或目标
*/
    EM_AC_UNLOCK_MODE_PHOTO_OR_FACE(40, "人证(照片)或目标"),
/**
人证(信息)
*/
    EM_AC_UNLOCK_MODE_FINGERPRINT(41, "人证(信息)"),
/**
人证(照片+信息)
*/
    EM_AC_UNLOCK_MODE_PHOTO_AND_FINGERPRINT(42, "人证(照片+信息)"),
/**
人证或刷卡或信息或目标或密码
*/
    EM_AC_UNLOCK_MODE_FACEIDCARD_OR_CARD_OR_FINGERPRINT_OR_FACE_OR_PASSWORD(43, "人证或刷卡或信息或目标或密码"),
/**
多用户类型
*/
    EM_AC_UNLOCK_MODE_MULTI_USER_TYPE(44, "多用户类型"),
/**
人证或健康码
*/
    EM_AC_UNLOCK_MODE_FACEIDCARD_OR_HEALTHCODE(45, "人证或健康码");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_AC_UNLOCK_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_AC_UNLOCK_MODE enumType : EM_AC_UNLOCK_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_AC_UNLOCK_MODE enumType : EM_AC_UNLOCK_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_AC_UNLOCK_MODE getEnum(int value) {
        for (EM_AC_UNLOCK_MODE e : EM_AC_UNLOCK_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_AC_UNLOCK_MODE.EM_AC_UNLOCK_MODE_UNKNOWN;
    }
}

