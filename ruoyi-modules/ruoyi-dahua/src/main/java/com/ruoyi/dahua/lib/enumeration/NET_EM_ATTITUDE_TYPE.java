package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  姿态类型 
* @date 2023/02/06 15:24:00
*/
public enum NET_EM_ATTITUDE_TYPE {
/**
未知
*/
    NET_EM_ATTITUDE_TYPE_UNKNOWN(0, "未知"),
/**
单手递接
*/
    NET_EM_ATTITUDE_TYPE_ONE_HAND(1, "单手递接"),
/**
叉腰
*/
    NET_EM_ATTITUDE_TYPE_AKIMBO(2, "叉腰"),
/**
双手抱头
*/
    NET_EM_ATTITUDE_TYPE_HAND_SON_HEAD(3, "双手抱头"),
/**
双手抱胸
*/
    NET_EM_ATTITUDE_TYPE_HAND_SON_CHEST(4, "双手抱胸"),
/**
撞墙
*/
    NET_EM_ATTITUDE_TYPE_HIT_WALL(5, "撞墙"),
/**
弯腰
*/
    NET_EM_ATTITUDE_TYPE_BEND(6, "弯腰"),
/**
坐状态
*/
    NET_EM_ATTITUDE_TYPE_SITTING(7, "坐状态"),
/**
站状态
*/
    NET_EM_ATTITUDE_TYPE_STANDING(8, "站状态"),
/**
坐下
*/
    NET_EM_ATTITUDE_TYPE_SIT_DOWN(9, "坐下"),
/**
起立
*/
    NET_EM_ATTITUDE_TYPE_STAND_UP(10, "起立"),
/**
趴桌子
*/
    NET_EM_ATTITUDE_TYPE_ON_TABLE(11, "趴桌子"),
/**
回头
*/
    NET_EM_ATTITUDE_TYPE_LOOK_BACK(12, "回头"),
/**
传递
*/
    NET_EM_ATTITUDE_TYPE_DELIVERY(13, "传递"),
/**
东张西望
*/
    NET_EM_ATTITUDE_TYPE_GAZE_AROUND(14, "东张西望"),
/**
手放桌下偷看
*/
    NET_EM_ATTITUDE_TYPE_HAND_DOWN_PEEP(15, "手放桌下偷看"),
/**
捡东西
*/
    NET_EM_ATTITUDE_TYPE_PICK_UP(16, "捡东西"),
/**
举手
*/
    NET_EM_ATTITUDE_TYPE_HAND_UP(17, "举手"),
/**
书写
*/
    NET_EM_ATTITUDE_TYPE_WRITE(18, "书写");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_ATTITUDE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_ATTITUDE_TYPE enumType : NET_EM_ATTITUDE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_ATTITUDE_TYPE enumType : NET_EM_ATTITUDE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_ATTITUDE_TYPE getEnum(int value) {
        for (NET_EM_ATTITUDE_TYPE e : NET_EM_ATTITUDE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_ATTITUDE_TYPE.NET_EM_ATTITUDE_TYPE_UNKNOWN;
    }
}

