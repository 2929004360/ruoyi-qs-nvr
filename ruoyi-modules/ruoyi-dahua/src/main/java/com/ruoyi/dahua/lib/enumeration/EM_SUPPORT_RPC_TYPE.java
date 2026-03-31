package com.ruoyi.dahua.lib.enumeration;

/**
 * 支持json传参的接口
*/
public enum EM_SUPPORT_RPC_TYPE
{
    /**
     * CLIENT_FindNextFileEx + DH_FILE_QUERY_FACE, 入参对应结构体NET_FINDNEXTFILEEX_DH_FILE_QUERY_FACE_IN_PARAMS，出参对应结构体NET_FINDNEXTFILEEX_DH_FILE_QUERY_FACE_OUT_PARAMS
    */
    EM_FINDNEXTFILEEX_DH_FILE_QUERY_FACE(0, "CLIENT_FindNextFileEx + DH_FILE_QUERY_FACE, 入参对应结构体NET_FINDNEXTFILEEX_DH_FILE_QUERY_FACE_IN_PARAMS，出参对应结构体NET_FINDNEXTFILEEX_DH_FILE_QUERY_FACE_OUT_PARAMS");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SUPPORT_RPC_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SUPPORT_RPC_TYPE enumType : EM_SUPPORT_RPC_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SUPPORT_RPC_TYPE enumType : EM_SUPPORT_RPC_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

