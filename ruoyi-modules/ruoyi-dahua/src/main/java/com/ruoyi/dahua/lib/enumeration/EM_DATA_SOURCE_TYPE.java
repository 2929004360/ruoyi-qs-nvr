package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @description  智能分析数据源类型
 * @date 2022/06/28 15:29:39
 */
public enum EM_DATA_SOURCE_TYPE {
    /**
     未知
     */
    EM_DATA_SOURCE_REMOTE_UNKNOWN(0, "未知"),
    /**
     远程实时流 , 对应 NET_REMOTE_REALTIME_STREAM_INFO
     */
    EM_DATA_SOURCE_REMOTE_REALTIME_STREAM(1, "远程实时流 , 对应 NET_REMOTE_REALTIME_STREAM_INFO"),
    /**
     主动推送图片文件, 对应 NET_PUSH_PICFILE_INFO
     */
    EM_DATA_SOURCE_PUSH_PICFILE(2, "主动推送图片文件, 对应 NET_PUSH_PICFILE_INFO"),
    /**
     远程视频文件, 对应 NET_REMOTE_VIDEO_FILE_INFO
     */
    EM_DATA_SOURCE_REMOTE_VIDEO_FILE(3, "远程视频文件, 对应 NET_REMOTE_VIDEO_FILE_INFO"),
    /**
     远程图片文件, 对应 NET_REMOTE_PICTURE_FILE_INFO
     */
    EM_DATA_SOURCE_REMOTE_PICTURE_FILE(4, "远程图片文件, 对应 NET_REMOTE_PICTURE_FILE_INFO"),
    /**
     离线视频文件（第三方导入的文件）, 对应 NET_OFFLINE_VIDEO_FILE_INFO
     */
    EM_DATA_SOURCE_OFFLINE_VIDEO_FILE(5, "离线视频文件（第三方导入的文件）, 对应 NET_OFFLINE_VIDEO_FILE_INFO"),
    /**
     主动推送图片文件，添加任务时无规则和图片信息，通过推送图片接口，每张图片中带有不同的规则信息（目前能源场景中使用）, 对应 NET_PUSH_PICFILE_BYRULE_INFO
     */
    EM_DATA_SOURCE_PUSH_PICFILE_BYRULE(6, "主动推送图片文件，添加任务时无规则和图片信息，通过推送图片接口，每张图片中带有不同的规则信息（目前能源场景中使用）, 对应 NET_PUSH_PICFILE_BYRULE_INFO"),
    /**
     本地实时流, 对应 NET_LOCAL_STREAM_INFO
     */
    EM_DATA_SOURCE_LOCAL_STREAM(7, "本地实时流, 对应 NET_LOCAL_STREAM_INFO");

    private int type;
    private String des;

    private EM_DATA_SOURCE_TYPE(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static EM_DATA_SOURCE_TYPE getDataSourceType(int type) {
        for (EM_DATA_SOURCE_TYPE source : EM_DATA_SOURCE_TYPE.values()) {
            if (type == source.getType()) {
                return source;
            }
        }
        return null;
    }
}

