package com.ruoyi.dahua.api.domain;

import java.io.Serializable;

/**
 * 大华设备录像下载请求
 */
public class DahuaRecordDownloadRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long id;

    /**
     * 通道ID
     */
    private int channelId;

    /**
     * 开始时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String startTime;

    /**
     * 结束时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;

    /**
     * 录像文件类型（0-主码流，1-子码流1，2-子码流2，参考SDK文档）
     */
    private Integer recordFileType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getRecordFileType() {
        return recordFileType;
    }

    public void setRecordFileType(Integer recordFileType) {
        this.recordFileType = recordFileType;
    }
}
