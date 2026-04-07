package com.ruoyi.zlm.mediaServer;

import com.ruoyi.zlm.event.MediaEvent;

/**
 * 录像文件进度通知事件
 */
public class MediaRecordProcessEvent extends MediaEvent {

    private Integer currentFileDuration;
    private Integer TotalVideoDuration;
    private String fileName;
    private long startTime;
    private long endTime;

    public MediaRecordProcessEvent(Object source) {
        super(source);
    }

    public Integer getCurrentFileDuration() {
        return currentFileDuration;
    }

    public void setCurrentFileDuration(Integer currentFileDuration) {
        this.currentFileDuration = currentFileDuration;
    }

    public Integer getTotalVideoDuration() {
        return TotalVideoDuration;
    }

    public void setTotalVideoDuration(Integer totalVideoDuration) {
        TotalVideoDuration = totalVideoDuration;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
