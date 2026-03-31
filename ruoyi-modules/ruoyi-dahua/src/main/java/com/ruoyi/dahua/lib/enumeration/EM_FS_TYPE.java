package com.ruoyi.dahua.lib.enumeration;

/**
 * 文件系统类型
*/
public enum EM_FS_TYPE
{
    /**
     * 未知
    */
    EM_FS_TYPE_UNKNWON(0, "未知"),
    EM_FS_TYPE_EXT3(1, ""),
    EM_FS_TYPE_EXT4(2, ""),
    EM_FS_TYPE_XFS(3, ""),
    EM_FS_TYPE_VFAT(4, ""),
    EM_FS_TYPE_DHFS(5, ""),
    EM_FS_TYPE_CQFS(6, ""),
    EM_FS_TYPE_NTFS(7, "");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_FS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_FS_TYPE enumType : EM_FS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_FS_TYPE enumType : EM_FS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

