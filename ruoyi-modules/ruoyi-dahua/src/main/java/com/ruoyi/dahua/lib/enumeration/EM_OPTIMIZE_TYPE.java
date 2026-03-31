package com.ruoyi.dahua.lib.enumeration;

/**
 * SDK设置内部优化方案
*/
public enum EM_OPTIMIZE_TYPE
{
    /**
     * 默认不优化
    */
    EM_OPT_TYPE_DEFAULT(0, "默认不优化"),
    /**
     * 和选项 EM_OPT_TYPE_MOBILE_OPTION冲突，两者只能使用一个，不支持异步登陆优化, 手机使用优化,开启后，默认过滤设备属性和硬盘信息.
    */
    EM_OPT_TYPE_MOBILE_V1(1, "和选项 EM_OPT_TYPE_MOBILE_OPTION冲突，两者只能使用一个，不支持异步登陆优化, 手机使用优化,开启后，默认过滤设备属性和硬盘信息."),
    /**
     * P2P网络参数设置，pParam 为 NET_PARAM*
    */
    EM_OPT_TYPE_P2P_NETPARAM_V1(2, "P2P网络参数设置，pParam 为 NET_PARAM*"),
    /**
     * 和选项 EM_OPT_TYPE_MOBILE_V1冲突，两者只能使用一个,支持异步登陆优化, 手机优化选项, 参数int, 按位从低到高, 传入的值参考枚举类型 EM_OPTTYPE_MOBILE_TYPE
    */
    EM_OPT_TYPE_MOBILE_OPTION(3, "和选项 EM_OPT_TYPE_MOBILE_V1冲突，两者只能使用一个,支持异步登陆优化, 手机优化选项, 参数int, 按位从低到高, 传入的值参考枚举类型 EM_OPTTYPE_MOBILE_TYPE"),
    /**
     * 登录时获取秘钥信息，减少拉流耗时，pParam 为int*， 1表示开启优化
    */
    EM_OPT_TYPE_LOGIN_GET_ENCRYPT(4, "登录时获取秘钥信息，减少拉流耗时，pParam 为int*， 1表示开启优化");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_OPTIMIZE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_OPTIMIZE_TYPE enumType : EM_OPTIMIZE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_OPTIMIZE_TYPE enumType : EM_OPTIMIZE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

