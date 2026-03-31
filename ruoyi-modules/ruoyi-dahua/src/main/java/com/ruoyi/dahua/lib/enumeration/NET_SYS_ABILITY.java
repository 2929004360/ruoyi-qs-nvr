package com.ruoyi.dahua.lib.enumeration;

/**
 * /,  查询类型
*/
public enum NET_SYS_ABILITY
{
    /**
     * 查询动态多连接能力,对应入参类型为BOOL
    */
    ABILITY_DYNAMIC_CONNECT(1, "查询动态多连接能力,对应入参类型为BOOL"),
    /**
     * 水印配置能力，对应结构体为DH_WATERMAKE_EN
    */
    ABILITY_WATERMARK_CFG(17, "水印配置能力，对应结构体为DH_WATERMAKE_EN"),
    /**
     * wireless配置能力,对应结构体为DH_WIRELESS_EN
    */
    ABILITY_WIRELESS_CFG(18, "wireless配置能力,对应结构体为DH_WIRELESS_EN"),
    /**
     * 设备的能力列表，对应结构体为DH_DEV_ENABLE_INFO
    */
    ABILITY_DEVALL_INFO(26, "设备的能力列表，对应结构体为DH_DEV_ENABLE_INFO"),
    /**
     * 卡号查询能力，对应结构体为DH_CARD_QUERY_EN
    */
    ABILITY_CARD_QUERY(256, "卡号查询能力，对应结构体为DH_CARD_QUERY_EN"),
    /**
     * 多画面预览能力,对应结构体为DH_MULTIPLAY_EN
    */
    ABILITY_MULTIPLAY(257, "多画面预览能力,对应结构体为DH_MULTIPLAY_EN"),
    /**
     * 快速查询配置能力，对应结构体为DH_QUICK_QUERY_CFG_EN
    */
    ABILITY_QUICK_QUERY_CFG(258, "快速查询配置能力，对应结构体为DH_QUICK_QUERY_CFG_EN"),
    /**
     * 无线报警能力,对应结构体为DH_WIRELESS_ALARM_INFO
    */
    ABILITY_INFRARED(289, "无线报警能力,对应结构体为DH_WIRELESS_ALARM_INFO"),
    /**
     * 报警输出触发方式能力，对应入参类型为int
    */
    ABILITY_TRIGGER_MODE(305, "报警输出触发方式能力，对应入参类型为int"),
    /**
     * 网络硬盘分区能力，对应结构体为DH_DISK_SUBAREA_EN
    */
    ABILITY_DISK_SUBAREA(321, "网络硬盘分区能力，对应结构体为DH_DISK_SUBAREA_EN"),
    /**
     * 查询DSP能力，对应结构体为DH_DSP_CFG
    */
    ABILITY_DSP_CFG(337, "查询DSP能力，对应结构体为DH_DSP_CFG"),
    /**
     * 查询SIP,RTSP能力，对应结构体为DH_STREAM_MEDIA_EN
    */
    ABILITY_STREAM_MEDIA(353, "查询SIP,RTSP能力，对应结构体为DH_STREAM_MEDIA_EN"),
    /**
     * 查询智能跟踪能力，对应结构体为DH_INTELLI_TRACKER_EN
    */
    ABILITY_INTELLI_TRACKER(369, "查询智能跟踪能力，对应结构体为DH_INTELLI_TRACKER_EN"),
    /**
     * 查询全速回放不抽帧能力，对应结构体为NET_DOWNLOAD_BY_ALLSPEED_EN
    */
    ABILITY_DOWNLOAD_BY_ALLSPEED(385, "查询全速回放不抽帧能力，对应结构体为NET_DOWNLOAD_BY_ALLSPEED_EN");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_SYS_ABILITY(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_SYS_ABILITY enumType : NET_SYS_ABILITY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_SYS_ABILITY enumType : NET_SYS_ABILITY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

