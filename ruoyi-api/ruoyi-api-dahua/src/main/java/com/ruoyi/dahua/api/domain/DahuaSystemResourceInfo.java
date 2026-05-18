package com.ruoyi.dahua.api.domain;

import java.io.Serializable;

/**
 * 大华设备系统资源状态信息
 */
public class DahuaSystemResourceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * CPU使用率(%)
     */
    private Double cpuUsage;

    /**
     * 总内存(字节)
     */
    private Long totalMemory;

    /**
     * 已用内存(字节)
     */
    private Long usedMemory;

    /**
     * 剩余内存(字节)
     */
    private Long freeMemory;

    /**
     * 内存使用率(%)
     */
    private Double memoryUsage;

    /**
     * 总内存(MB)
     */
    private Double totalMemoryMB;

    /**
     * 已用内存(MB)
     */
    private Double usedMemoryMB;

    /**
     * 剩余内存(MB)
     */
    private Double freeMemoryMB;

    /**
     * IP通道接入速度(kbps)
     */
    private Integer ipChannelIn;

    /**
     * 网络接收剩余能力(kbps)
     */
    private Integer netRemain;

    /**
     * 网络接收总能力(kbps)
     */
    private Integer netCapability;

    /**
     * 远程预览能力(kbps)
     */
    private Integer remotePreview;

    /**
     * 远程回放及下载能力(kbps)
     */
    private Integer remotePlayDownload;

    /**
     * 远程发送剩余能力(kbps)
     */
    private Integer remoteSendRemain;

    /**
     * 远程发送总能力(kbps)
     */
    private Integer remoteSendCapability;

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    // Getters and Setters
    public Double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(Double cpuUsage) { this.cpuUsage = cpuUsage; }

    public Long getTotalMemory() { return totalMemory; }
    public void setTotalMemory(Long totalMemory) { this.totalMemory = totalMemory; }

    public Long getUsedMemory() { return usedMemory; }
    public void setUsedMemory(Long usedMemory) { this.usedMemory = usedMemory; }

    public Long getFreeMemory() { return freeMemory; }
    public void setFreeMemory(Long freeMemory) { this.freeMemory = freeMemory; }

    public Double getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(Double memoryUsage) { this.memoryUsage = memoryUsage; }

    public Double getTotalMemoryMB() { return totalMemoryMB; }
    public void setTotalMemoryMB(Double totalMemoryMB) { this.totalMemoryMB = totalMemoryMB; }

    public Double getUsedMemoryMB() { return usedMemoryMB; }
    public void setUsedMemoryMB(Double usedMemoryMB) { this.usedMemoryMB = usedMemoryMB; }

    public Double getFreeMemoryMB() { return freeMemoryMB; }
    public void setFreeMemoryMB(Double freeMemoryMB) { this.freeMemoryMB = freeMemoryMB; }

    public Integer getIpChannelIn() { return ipChannelIn; }
    public void setIpChannelIn(Integer ipChannelIn) { this.ipChannelIn = ipChannelIn; }

    public Integer getNetRemain() { return netRemain; }
    public void setNetRemain(Integer netRemain) { this.netRemain = netRemain; }

    public Integer getNetCapability() { return netCapability; }
    public void setNetCapability(Integer netCapability) { this.netCapability = netCapability; }

    public Integer getRemotePreview() { return remotePreview; }
    public void setRemotePreview(Integer remotePreview) { this.remotePreview = remotePreview; }

    public Integer getRemotePlayDownload() { return remotePlayDownload; }
    public void setRemotePlayDownload(Integer remotePlayDownload) { this.remotePlayDownload = remotePlayDownload; }

    public Integer getRemoteSendRemain() { return remoteSendRemain; }
    public void setRemoteSendRemain(Integer remoteSendRemain) { this.remoteSendRemain = remoteSendRemain; }

    public Integer getRemoteSendCapability() { return remoteSendCapability; }
    public void setRemoteSendCapability(Integer remoteSendCapability) { this.remoteSendCapability = remoteSendCapability; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
