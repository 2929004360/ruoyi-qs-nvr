package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 大华设备SD卡信息
 */
public class DahuaSDCardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 磁盘信息
     */
    public static class DiskInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer diskNumber;
        private Integer partitionNumber;
        private Integer volume;
        private Integer freeSpace;
        private Integer status;
        private Integer signal;
        private Integer diskType;
        private Integer diskStatus;

        public Integer getDiskNumber() { return diskNumber; }
        public void setDiskNumber(Integer diskNumber) { this.diskNumber = diskNumber; }

        public Integer getPartitionNumber() { return partitionNumber; }
        public void setPartitionNumber(Integer partitionNumber) { this.partitionNumber = partitionNumber; }

        public Integer getVolume() { return volume; }
        public void setVolume(Integer volume) { this.volume = volume; }

        public Integer getFreeSpace() { return freeSpace; }
        public void setFreeSpace(Integer freeSpace) { this.freeSpace = freeSpace; }

        public Integer getStatus() { return status; }
        public void setStatus(Integer status) { this.status = status; }

        public Integer getSignal() { return signal; }
        public void setSignal(Integer signal) { this.signal = signal; }

        public Integer getDiskType() { return diskType; }
        public void setDiskType(Integer diskType) { this.diskType = diskType; }

        public Integer getDiskStatus() { return diskStatus; }
        public void setDiskStatus(Integer diskStatus) { this.diskStatus = diskStatus; }
    }

    /**
     * SD卡是否存在
     */
    private Boolean exists;

    /**
     * 磁盘数量
     */
    private Integer diskCount;

    /**
     * 磁盘列表
     */
    private List<DiskInfo> diskList;

    /**
     * SD卡总容量(字节)
     */
    private Long totalSpace;

    /**
     * SD卡已用容量(字节)
     */
    private Long usedSpace;

    /**
     * SD卡剩余容量(字节)
     */
    private Long freeSpace;

    /**
     * 总容量(GB)
     */
    private Double totalSpaceGB;

    /**
     * 已用容量(GB)
     */
    private Double usedSpaceGB;

    /**
     * 剩余容量(GB)
     */
    private Double freeSpaceGB;

    /**
     * 使用率(%)
     */
    private Double usagePercent;

    /**
     * SD卡状态
     */
    private Integer state;

    /**
     * 状态描述
     */
    private String stateDesc;

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    // Getters and Setters
    public Boolean getExists() { return exists; }
    public void setExists(Boolean exists) { this.exists = exists; }

    public Integer getDiskCount() { return diskCount; }
    public void setDiskCount(Integer diskCount) { this.diskCount = diskCount; }

    public List<DiskInfo> getDiskList() { return diskList; }
    public void setDiskList(List<DiskInfo> diskList) { this.diskList = diskList; }

    public Long getTotalSpace() { return totalSpace; }
    public void setTotalSpace(Long totalSpace) { this.totalSpace = totalSpace; }

    public Long getUsedSpace() { return usedSpace; }
    public void setUsedSpace(Long usedSpace) { this.usedSpace = usedSpace; }

    public Long getFreeSpace() { return freeSpace; }
    public void setFreeSpace(Long freeSpace) { this.freeSpace = freeSpace; }

    public Double getTotalSpaceGB() { return totalSpaceGB; }
    public void setTotalSpaceGB(Double totalSpaceGB) { this.totalSpaceGB = totalSpaceGB; }

    public Double getUsedSpaceGB() { return usedSpaceGB; }
    public void setUsedSpaceGB(Double usedSpaceGB) { this.usedSpaceGB = usedSpaceGB; }

    public Double getFreeSpaceGB() { return freeSpaceGB; }
    public void setFreeSpaceGB(Double freeSpaceGB) { this.freeSpaceGB = freeSpaceGB; }

    public Double getUsagePercent() { return usagePercent; }
    public void setUsagePercent(Double usagePercent) { this.usagePercent = usagePercent; }

    public Integer getState() { return state; }
    public void setState(Integer state) { this.state = state; }

    public String getStateDesc() { return stateDesc; }
    public void setStateDesc(String stateDesc) { this.stateDesc = stateDesc; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
