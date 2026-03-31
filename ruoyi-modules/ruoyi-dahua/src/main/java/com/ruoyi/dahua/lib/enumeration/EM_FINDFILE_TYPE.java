package com.ruoyi.dahua.lib.enumeration;

/**
 * 查询文件的类型
*/
public enum EM_FINDFILE_TYPE
{
    /**
     * 未知
    */
    EM_FINDFILE_UNKNOWN(0, "未知"),
    /**
     * 泛指图片
    */
    EM_FINDFILE_JPG(1, "泛指图片"),
    /**
     * 泛指视频
    */
    EM_FINDFILE_DAV(2, "泛指视频"),
    /**
     * log信息
    */
    EM_FINDFILE_LOG(3, "log信息"),
    /**
     * gps信息
    */
    EM_FINDFILE_GPS(4, "gps信息"),
    /**
     * jpk信息,图片打包文件，指文件中存有多张图片的文件，该类型和jpg类型互斥，同一个查询条件里面不能既有jpg又有jpk
    */
    EM_FINDFILE_JPK(5, "jpk信息,图片打包文件，指文件中存有多张图片的文件，该类型和jpg类型互斥，同一个查询条件里面不能既有jpg又有jpk"),
    /**
     * MP4信息,(因与二代协议不兼容删除mp4，用dav表示所有视频)无Types字段，且有DB字段，默认查询"jpg"类型
    */
    EM_FINDFILE_MP4(6, "MP4信息,(因与二代协议不兼容删除mp4，用dav表示所有视频)无Types字段，且有DB字段，默认查询\"jpg\"类型"),
    /**
     * AUDIO信息,泛指音频
    */
    EM_FINDFILE_AUDIO(7, "AUDIO信息,泛指音频");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_FINDFILE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_FINDFILE_TYPE enumType : EM_FINDFILE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_FINDFILE_TYPE enumType : EM_FINDFILE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

