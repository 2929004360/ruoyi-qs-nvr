package com.ruoyi.dahua.lib.enumeration;

/**
 * 物体动作支持类型
*/
public enum EM_VAOBJECT_ROADCONE_ACTION
{
    /**
     * 未知类型
    */
    EM_VAOBJECT_ROADCONE_ACTION_UNKNOWN(0, "未知类型"),
    /**
     * 首次出现在检测区域或者跟踪时物理分离动作中尚未确认的新物体
    */
    EM_VAOBJECT_ROADCONE_ACTION_APPEAR(1, "首次出现在检测区域或者跟踪时物理分离动作中尚未确认的新物体"),
    /**
     * 正在运动
    */
    EM_VAOBJECT_ROADCONE_ACTION_MOVE(2, "正在运动"),
    /**
     * 物体停止运动
    */
    EM_VAOBJECT_ROADCONE_ACTION_STAY(3, "物体停止运动"),
    /**
     * 物体从原来的区域移除，或者被遮盖，或者跟踪失败
    */
    EM_VAOBJECT_ROADCONE_ACTION_REMOVE(4, "物体从原来的区域移除，或者被遮盖，或者跟踪失败"),
    /**
     * 运动到跟踪区域之外，或者达到了算法跟踪物体的上限而被清除，消失的物体ID将不再出现
    */
    EM_VAOBJECT_ROADCONE_ACTION_DISAPPEAR(5, "运动到跟踪区域之外，或者达到了算法跟踪物体的上限而被清除，消失的物体ID将不再出现"),
    /**
     * 从其他物体中分离出来
    */
    EM_VAOBJECT_ROADCONE_ACTION_SPLIT(6, "从其他物体中分离出来"),
    /**
     * 合并到其他物体
    */
    EM_VAOBJECT_ROADCONE_ACTION_MERGE(7, "合并到其他物体"),
    /**
     * 暂时使用的新的ID
    */
    EM_VAOBJECT_ROADCONE_ACTION_RENAME(8, "暂时使用的新的ID");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VAOBJECT_ROADCONE_ACTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VAOBJECT_ROADCONE_ACTION enumType : EM_VAOBJECT_ROADCONE_ACTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VAOBJECT_ROADCONE_ACTION enumType : EM_VAOBJECT_ROADCONE_ACTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

