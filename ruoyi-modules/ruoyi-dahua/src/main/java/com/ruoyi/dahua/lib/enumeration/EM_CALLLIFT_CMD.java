package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  呼叫电梯命令 
* @date 2022/10/19 14:01:07
*/
public enum EM_CALLLIFT_CMD {
/**
未知
*/
    EM_CALLLIFT_CMD_UNKNOWN(-1, "未知"),
/**
呼叫电梯到某个楼层，并开放其它任何楼层数
*/
    EM_CALLLIFT_CMD_ANY_FLOOR(0, "呼叫电梯到某个楼层，并开放其它任何楼层数"),
/**
呼叫电梯到某个楼层，用户进去后只能按目标楼层
*/
    EM_CALLLIFT_CMD_DEST_FLOOR(1, "呼叫电梯到某个楼层，用户进去后只能按目标楼层"),
/**
呼叫电梯到某个楼层后，还需要刷卡，电梯才能运行, 默认电梯上行
*/
    EM_CALLLIFT_CMD_CARD_UP(2, "呼叫电梯到某个楼层后，还需要刷卡，电梯才能运行, 默认电梯上行"),
/**
呼叫电梯到某个楼层,用户进去不用按钮，电梯会自动运行到目标楼层
*/
    EM_CALLLIFT_CMD_AUTO_FLOOR(3, "呼叫电梯到某个楼层,用户进去不用按钮，电梯会自动运行到目标楼层"),
/**
呼叫电梯到某个楼层后，还需要刷卡，电梯才能运行, 默认电梯下行
*/
    EM_CALLLIFT_CMD_CARD_DOWN(4, "呼叫电梯到某个楼层后，还需要刷卡，电梯才能运行, 默认电梯下行");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CALLLIFT_CMD(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CALLLIFT_CMD enumType : EM_CALLLIFT_CMD.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CALLLIFT_CMD enumType : EM_CALLLIFT_CMD.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_CALLLIFT_CMD getEnum(int value) {
        for (EM_CALLLIFT_CMD e : EM_CALLLIFT_CMD.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CALLLIFT_CMD.EM_CALLLIFT_CMD_UNKNOWN;
    }
}

