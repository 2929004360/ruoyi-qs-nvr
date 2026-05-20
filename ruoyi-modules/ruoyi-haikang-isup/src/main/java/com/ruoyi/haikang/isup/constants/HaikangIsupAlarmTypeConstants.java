package com.ruoyi.haikang.isup.constants;

public final class HaikangIsupAlarmTypeConstants {

    private HaikangIsupAlarmTypeConstants() {}

    public static final int ALARM_TYPE_DISK_FULL = 0;
    public static final int ALARM_TYPE_DISK_WRERROR = 1;
    public static final int ALARM_TYPE_VIDEO_LOST = 5;
    public static final int ALARM_TYPE_EXTERNAL = 6;
    public static final int ALARM_TYPE_VIDEO_COVERED = 7;
    public static final int ALARM_TYPE_MOTION = 8;
    public static final int ALARM_TYPE_STANDARD_NOTMATCH = 9;
    public static final int ALARM_TYPE_SPEEDLIMIT_EXCEED = 10;
    public static final int ALARM_TYPE_PIR = 11;
    public static final int ALARM_TYPE_WIRELESS = 12;
    public static final int ALARM_TYPE_CALL_HELP = 13;
    public static final int ALARM_TYPE_DISARM = 14;
    public static final int ALARM_TYPE_STREAM_PRIVATE = 15;
    public static final int ALARM_TYPE_PIC_UPLOAD_FAIL = 16;
    public static final int ALARM_TYPE_LOCAL_REC_EXCEPTION = 17;
    public static final int ALARM_TYPE_UPGRADE_FAIL = 18;
    public static final int ALARM_TYPE_ILLEGAL_ACCESS = 19;

    public static final int ALARM_TYPE_SOUNDLIMIT_EXCEED = 80;
    public static final int ALARM_TYPE_TRIFFIC_VIOLATION = 90;
    public static final int ALARM_TYPE_ALARM_CONTROL = 96;
    public static final int ALARM_TYPE_FACE_DETECTION = 97;
    public static final int ALARM_TYPE_DEFOUSE_DETECTION = 98;
    public static final int ALARM_TYPE_AUDIO_EXCEPTION = 99;

    public static final int ALARM_TYPE_SCENE_CHANGE = 100;
    public static final int ALARM_TYPE_TRAVERSE_PLANE = 101;
    public static final int ALARM_TYPE_ENTER_AREA = 102;
    public static final int ALARM_TYPE_LEAVE_AREA = 103;
    public static final int ALARM_TYPE_INTRUSION = 104;
    public static final int ALARM_TYPE_LOITER = 105;
    public static final int ALARM_TYPE_LEFT_TAKE = 106;
    public static final int ALARM_TYPE_CAR_STOP = 107;
    public static final int ALARM_TYPE_MOVE_FAST = 108;
    public static final int ALARM_TYPE_HIGH_DENSITY = 109;
    public static final int ALARM_TYPE_PDC_BY_TIME = 110;
    public static final int ALARM_TYPE_PDC_BY_FRAME = 111;
    public static final int ALARM_TYPE_LEFT = 112;
    public static final int ALARM_TYPE_TAKE = 113;
    public static final int ALARM_TYPE_ROLLOVER = 114;
    public static final int ALARM_TYPE_COLLISION = 115;

    public static final int ALARM_TYPE_FLOW_OVERRUN = 256;
    public static final int ALARM_TYPE_WARN_FLOW_OVERRUN = 257;

    public static final int ALARM_TYPE_DEV_CHANGED_STATUS = 700;
    public static final int ALARM_TYPE_CHAN_CHANGED_STATUS = 701;
    public static final int ALARM_TYPE_HD_CHANGED_STATUS = 702;
    public static final int ALARM_TYPE_DEV_TIMING_STATUS = 703;
    public static final int ALARM_TYPE_CHAN_TIMING_STATUS = 704;
    public static final int ALARM_TYPE_HD_TIMING_STATUS = 705;
    public static final int ALARM_TYPE_RECORD_ABNORMAL = 706;

    public static String getDescription(int alarmType) {
        return switch (alarmType) {
            case ALARM_TYPE_DISK_FULL -> "硬盘已满报警";
            case ALARM_TYPE_DISK_WRERROR -> "硬盘读写出错报警";
            case ALARM_TYPE_VIDEO_LOST -> "视频（信号）丢失报警";
            case ALARM_TYPE_EXTERNAL -> "外部（信号量）报警";
            case ALARM_TYPE_VIDEO_COVERED -> "视频遮盖报警";
            case ALARM_TYPE_MOTION -> "移动侦测";
            case ALARM_TYPE_STANDARD_NOTMATCH -> "视频制式不匹配报警";
            case ALARM_TYPE_SPEEDLIMIT_EXCEED -> "超速报警";
            case ALARM_TYPE_PIR -> "PIR 报警";
            case ALARM_TYPE_WIRELESS -> "无线报警";
            case ALARM_TYPE_CALL_HELP -> "呼救报警";
            case ALARM_TYPE_DISARM -> "布撤防报警";
            case ALARM_TYPE_STREAM_PRIVATE -> "码流隐私状态改变报警";
            case ALARM_TYPE_PIC_UPLOAD_FAIL -> "设备上传图片失败报警";
            case ALARM_TYPE_LOCAL_REC_EXCEPTION -> "设备本地录像（取证）异常报警";
            case ALARM_TYPE_UPGRADE_FAIL -> "设备版本升级失败报警";
            case ALARM_TYPE_ILLEGAL_ACCESS -> "非法访问报警";
            case ALARM_TYPE_SOUNDLIMIT_EXCEED -> "声音分贝数超标报警";
            case ALARM_TYPE_TRIFFIC_VIOLATION -> "违章报警";
            case ALARM_TYPE_ALARM_CONTROL -> "布防报警";
            case ALARM_TYPE_FACE_DETECTION -> "人脸侦测报警";
            case ALARM_TYPE_DEFOUSE_DETECTION -> "虚焦侦测报警";
            case ALARM_TYPE_AUDIO_EXCEPTION -> "音频异常报警";
            case ALARM_TYPE_SCENE_CHANGE -> "场景变更侦测报警";
            case ALARM_TYPE_TRAVERSE_PLANE -> "越界侦测报警";
            case ALARM_TYPE_ENTER_AREA -> "进入区域侦测报警";
            case ALARM_TYPE_LEAVE_AREA -> "离开区域侦测报警";
            case ALARM_TYPE_INTRUSION -> "区域入侵侦测报警";
            case ALARM_TYPE_LOITER -> "徘徊侦测报警";
            case ALARM_TYPE_LEFT_TAKE -> "遗留物品拿取侦测报警";
            case ALARM_TYPE_CAR_STOP -> "停车侦测报警";
            case ALARM_TYPE_MOVE_FAST -> "快速移动侦测报警";
            case ALARM_TYPE_HIGH_DENSITY -> "人员聚集侦测报警";
            case ALARM_TYPE_PDC_BY_TIME -> "按时间段统计客流量报警";
            case ALARM_TYPE_PDC_BY_FRAME -> "单帧统计客流量报警";
            case ALARM_TYPE_LEFT -> "物品遗留侦测报警";
            case ALARM_TYPE_TAKE -> "物品拿取侦测报警";
            case ALARM_TYPE_ROLLOVER -> "侧翻报警";
            case ALARM_TYPE_COLLISION -> "碰撞报警";
            case ALARM_TYPE_FLOW_OVERRUN -> "流量超限报警";
            case ALARM_TYPE_WARN_FLOW_OVERRUN -> "人员超限提醒";
            case ALARM_TYPE_DEV_CHANGED_STATUS -> "设备状态改变报警";
            case ALARM_TYPE_CHAN_CHANGED_STATUS -> "通道状态改变报警";
            case ALARM_TYPE_HD_CHANGED_STATUS -> "硬盘状态改变报警";
            case ALARM_TYPE_DEV_TIMING_STATUS -> "定时上传设备状态报警";
            case ALARM_TYPE_CHAN_TIMING_STATUS -> "定时上传通道状态报警";
            case ALARM_TYPE_HD_TIMING_STATUS -> "定时上传硬盘状态报警";
            case ALARM_TYPE_RECORD_ABNORMAL -> "录像异常报警";
            default -> "未知报警类型";
        };
    }
}
