package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  共享类型枚举 
* @date 2022/09/22 17:24:07
*/
public enum EM_CFG_NAS_DIRECTORY_PROTOCAL {
/**
未知枚举
*/
    EM_CFG_NAS_DIRECTORY_PROTOCAL_UNKNOWN(0, "未知枚举"),
/**
NFS
*/
    EM_CFG_NAS_DIRECTORY_PROTOCAL_NFS(1, "NFS"),
/**
FTP
*/
    EM_CFG_NAS_DIRECTORY_PROTOCAL_FTP(2, "FTP"),
/**
SAMBA
*/
    EM_CFG_NAS_DIRECTORY_PROTOCAL_SAMBA(3, "SAMBA"),
/**
ISCSI
*/
    EM_CFG_NAS_DIRECTORY_PROTOCAL_ISCSI(4, "ISCSI"),
/**
表示裸的LV, 没有做共享文件夹
*/
    EM_CFG_NAS_DIRECTORY_PROTOCAL_NONE(5, "表示裸的LV, 没有做共享文件夹");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CFG_NAS_DIRECTORY_PROTOCAL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CFG_NAS_DIRECTORY_PROTOCAL enumType : EM_CFG_NAS_DIRECTORY_PROTOCAL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CFG_NAS_DIRECTORY_PROTOCAL enumType : EM_CFG_NAS_DIRECTORY_PROTOCAL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_CFG_NAS_DIRECTORY_PROTOCAL getEnum(int value) {
        for (EM_CFG_NAS_DIRECTORY_PROTOCAL e : EM_CFG_NAS_DIRECTORY_PROTOCAL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CFG_NAS_DIRECTORY_PROTOCAL.EM_CFG_NAS_DIRECTORY_PROTOCAL_UNKNOWN;
    }
}

