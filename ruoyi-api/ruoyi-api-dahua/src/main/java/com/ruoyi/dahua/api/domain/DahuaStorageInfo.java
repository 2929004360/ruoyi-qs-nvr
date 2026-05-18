package com.ruoyi.dahua.api.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 大华设备存储/硬盘信息
 */
public class DahuaStorageInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 存储设备列表
     */
    private List<StorageDevice> storageDevices;

    public List<StorageDevice> getStorageDevices() {
        return storageDevices;
    }

    public void setStorageDevices(List<StorageDevice> storageDevices) {
        this.storageDevices = storageDevices;
    }

    /**
     * 存储设备信息
     */
    public static class StorageDevice implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        /**
         * 设备名称
         */
        private String name;
        
        /**
         * 总空间(字节)
         */
        private Long totalSpace;
        
        /**
         * 总空间(GB)
         */
        private Double totalSpaceGB;
        
        /**
         * 剩余空间(字节)
         */
        private Long freeSpace;
        
        /**
         * 剩余空间(GB)
         */
        private Double freeSpaceGB;
        
        /**
         * 已用空间(字节)
         */
        private Long usedSpace;
        
        /**
         * 已用空间(GB)
         */
        private Double usedSpaceGB;
        
        /**
         * 使用率(%)
         */
        private Double usagePercent;
        
        /**
         * 介质类型: 0-DISK, 1-CDROM, 2-FLASH
         */
        private Integer mediaType;
        
        /**
         * 介质类型描述
         */
        private String mediaTypeDesc;
        
        /**
         * 总线类型: 0-ATA, 1-SATA, 2-USB, 3-SDIO, 4-SCSI
         */
        private Integer busType;
        
        /**
         * 总线类型描述
         */
        private String busTypeDesc;
        
        /**
         * 卷类型: 0-物理卷, 1-Raid卷, 2-VG虚拟卷, 3-ISCSI, 4-独立物理卷, 5-全局热备卷, 6-NAS卷
         */
        private Integer volumeType;
        
        /**
         * 卷类型描述
         */
        private String volumeTypeDesc;
        
        /**
         * 物理硬盘状态
         */
        private Integer state;
        
        /**
         * 物理硬盘状态描述
         */
        private String stateDesc;
        
        /**
         * 物理编号
         */
        private Integer physicNo;
        
        /**
         * 逻辑编号
         */
        private Integer logicNo;
        
        /**
         * 上级存储组名称
         */
        private String parent;
        
        /**
         * 设备模块
         */
        private String module;
        
        /**
         * 序列号
         */
        private String serial;
        
        /**
         * 固件版本
         */
        private String firmware;
        
        /**
         * 分区数
         */
        private Integer partitionNum;
        
        /**
         * 设备操作状态
         */
        private Integer opState;
        
        /**
         * 设备操作状态描述
         */
        private String opStateDesc;
        
        /**
         * 分区列表
         */
        private List<StoragePartition> partitions;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Long getTotalSpace() { return totalSpace; }
        public void setTotalSpace(Long totalSpace) { this.totalSpace = totalSpace; }
        
        public Double getTotalSpaceGB() { return totalSpaceGB; }
        public void setTotalSpaceGB(Double totalSpaceGB) { this.totalSpaceGB = totalSpaceGB; }
        
        public Long getFreeSpace() { return freeSpace; }
        public void setFreeSpace(Long freeSpace) { this.freeSpace = freeSpace; }
        
        public Double getFreeSpaceGB() { return freeSpaceGB; }
        public void setFreeSpaceGB(Double freeSpaceGB) { this.freeSpaceGB = freeSpaceGB; }
        
        public Long getUsedSpace() { return usedSpace; }
        public void setUsedSpace(Long usedSpace) { this.usedSpace = usedSpace; }
        
        public Double getUsedSpaceGB() { return usedSpaceGB; }
        public void setUsedSpaceGB(Double usedSpaceGB) { this.usedSpaceGB = usedSpaceGB; }
        
        public Double getUsagePercent() { return usagePercent; }
        public void setUsagePercent(Double usagePercent) { this.usagePercent = usagePercent; }
        
        public Integer getMediaType() { return mediaType; }
        public void setMediaType(Integer mediaType) { this.mediaType = mediaType; }
        
        public String getMediaTypeDesc() { return mediaTypeDesc; }
        public void setMediaTypeDesc(String mediaTypeDesc) { this.mediaTypeDesc = mediaTypeDesc; }
        
        public Integer getBusType() { return busType; }
        public void setBusType(Integer busType) { this.busType = busType; }
        
        public String getBusTypeDesc() { return busTypeDesc; }
        public void setBusTypeDesc(String busTypeDesc) { this.busTypeDesc = busTypeDesc; }
        
        public Integer getVolumeType() { return volumeType; }
        public void setVolumeType(Integer volumeType) { this.volumeType = volumeType; }
        
        public String getVolumeTypeDesc() { return volumeTypeDesc; }
        public void setVolumeTypeDesc(String volumeTypeDesc) { this.volumeTypeDesc = volumeTypeDesc; }
        
        public Integer getState() { return state; }
        public void setState(Integer state) { this.state = state; }
        
        public String getStateDesc() { return stateDesc; }
        public void setStateDesc(String stateDesc) { this.stateDesc = stateDesc; }
        
        public Integer getPhysicNo() { return physicNo; }
        public void setPhysicNo(Integer physicNo) { this.physicNo = physicNo; }
        
        public Integer getLogicNo() { return logicNo; }
        public void setLogicNo(Integer logicNo) { this.logicNo = logicNo; }
        
        public String getParent() { return parent; }
        public void setParent(String parent) { this.parent = parent; }
        
        public String getModule() { return module; }
        public void setModule(String module) { this.module = module; }
        
        public String getSerial() { return serial; }
        public void setSerial(String serial) { this.serial = serial; }
        
        public String getFirmware() { return firmware; }
        public void setFirmware(String firmware) { this.firmware = firmware; }
        
        public Integer getPartitionNum() { return partitionNum; }
        public void setPartitionNum(Integer partitionNum) { this.partitionNum = partitionNum; }
        
        public Integer getOpState() { return opState; }
        public void setOpState(Integer opState) { this.opState = opState; }
        
        public String getOpStateDesc() { return opStateDesc; }
        public void setOpStateDesc(String opStateDesc) { this.opStateDesc = opStateDesc; }
        
        public List<StoragePartition> getPartitions() { return partitions; }
        public void setPartitions(List<StoragePartition> partitions) { this.partitions = partitions; }
    }
    
    /**
     * 存储分区信息
     */
    public static class StoragePartition implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        /**
         * 分区名称
         */
        private String name;
        
        /**
         * 分区路径
         */
        private String path;
        
        /**
         * 总空间(字节)
         */
        private Long totalSpace;
        
        /**
         * 总空间(GB)
         */
        private Double totalSpaceGB;
        
        /**
         * 剩余空间(字节)
         */
        private Long freeSpace;
        
        /**
         * 剩余空间(GB)
         */
        private Double freeSpaceGB;
        
        /**
         * 分区类型
         */
        private Integer type;
        
        /**
         * 分区状态
         */
        private Integer state;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }
        
        public Long getTotalSpace() { return totalSpace; }
        public void setTotalSpace(Long totalSpace) { this.totalSpace = totalSpace; }
        
        public Double getTotalSpaceGB() { return totalSpaceGB; }
        public void setTotalSpaceGB(Double totalSpaceGB) { this.totalSpaceGB = totalSpaceGB; }
        
        public Long getFreeSpace() { return freeSpace; }
        public void setFreeSpace(Long freeSpace) { this.freeSpace = freeSpace; }
        
        public Double getFreeSpaceGB() { return freeSpaceGB; }
        public void setFreeSpaceGB(Double freeSpaceGB) { this.freeSpaceGB = freeSpaceGB; }
        
        public Integer getType() { return type; }
        public void setType(Integer type) { this.type = type; }
        
        public Integer getState() { return state; }
        public void setState(Integer state) { this.state = state; }
    }
}
