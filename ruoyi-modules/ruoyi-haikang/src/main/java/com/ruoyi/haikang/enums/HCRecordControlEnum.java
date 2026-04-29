package com.ruoyi.haikang.enums;

import com.ruoyi.haikang.net.HCNetSDK;

public enum HCRecordControlEnum {
    NET_DVR_PLAYSTART(HCNetSDK.NET_DVR_PLAYSTART, "开始播放", "net_dvr_playstart"),
    NET_DVR_PLAYSTOP(HCNetSDK.NET_DVR_PLAYSTOP, "停止播放", "net_dvr_playstop"),
    NET_DVR_PLAYPAUSE(HCNetSDK.NET_DVR_PLAYPAUSE, "暂停播放", "net_dvr_playpause"),
    NET_DVR_PLAYRESTART(HCNetSDK.NET_DVR_PLAYRESTART, "恢复播放", "net_dvr_playrestart"),
    NET_DVR_PLAYFAST(HCNetSDK.NET_DVR_PLAYFAST, "快放", "net_dvr_playfast"),
    NET_DVR_PLAYSLOW(HCNetSDK.NET_DVR_PLAYSLOW, "慢放", "net_dvr_playslow"),
    NET_DVR_PLAYNORMAL(HCNetSDK.NET_DVR_PLAYNORMAL, "正常速度", "net_dvr_playnormal"),
    NET_DVR_PLAYFRAME(HCNetSDK.NET_DVR_PLAYFRAME, "单帧放", "net_dvr_playframe"),
    NET_DVR_PLAYSTARTAUDIO(HCNetSDK.NET_DVR_PLAYSTARTAUDIO, "打开声音", "net_dvr_playstartaudio"),
    NET_DVR_PLAYSTOPAUDIO(HCNetSDK.NET_DVR_PLAYSTOPAUDIO, "关闭声音", "net_dvr_playstopaudio"),
    NET_DVR_PLAYAUDIOVOLUME(HCNetSDK.NET_DVR_PLAYAUDIOVOLUME, "调节音量", "net_dvr_playaudiovolume");


    private Integer code;
    private String msg;
    private String value;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }

    HCRecordControlEnum(Integer code, String msg, String value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public static HCRecordControlEnum fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (HCRecordControlEnum item : HCRecordControlEnum.values()) {
            if (item.value.equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}
