package com.ruoyi.qs.enums;

/**
 * 直播流接入类型枚举
 * 
 * 对应数据库字段值：
 * 1=RTSP, 2=RTMP, 3=FLV, 4=HLS, 5=ONVIF, 6=视频文件
 * 7=海康SDK, 8=海康ISUP, 9=大华SDK, 10=宇视SDK, 11=天地伟业SDK
 * 12=国标28181, 13=PUSH, 14=部标1078
 */
public enum LiveStreamType {

    RTSP("1", "RTSP协议", "Real Time Streaming Protocol"),
    RTMP("2", "RTMP协议", "Real Time Messaging Protocol"),
    FLV("3", "HTTP-FLV协议", "Flash Video"),
    HLS("4", "HLS协议", "HTTP Live Streaming"),
    ONVIF("5", "ONVIF协议", "Open Network Video Interface Forum"),
    VIDEO_FILE("6", "视频文件", "Video File"),
    HIK_SDK("7", "海康SDK", "Hikvision SDK"),
    HIK_ISUP("8", "海康ISUP", "Hikvision ISUP/Ehome"),
    DAHUA_SDK("9", "大华SDK", "Dahua SDK"),
    UNIVIEW_SDK("10", "宇视SDK", "Uniview SDK"),
    TIANDI_SDK("11", "天地伟业SDK", "Tiandi SDK"),
    GB28181("12", "国标28181", "GB/T 28181"),
    PUSH("13", "推流模式", "Push Stream"),
    JT1078("14", "部标1078", "JT/T 1078");

    private final String code;
    private final String desc;
    private final String fullName;

    LiveStreamType(String code, String desc, String fullName) {
        this.code = code;
        this.desc = desc;
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * 根据 code 获取枚举
     * @param code 类型代码
     * @return 对应的枚举值，如果不存在则返回 null
     */
    public static LiveStreamType getByCode(String code) {
        for (LiveStreamType type : LiveStreamType.values()) {
            if (type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }
}