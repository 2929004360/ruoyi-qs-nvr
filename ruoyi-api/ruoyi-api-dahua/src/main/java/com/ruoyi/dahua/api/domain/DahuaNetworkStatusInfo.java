package com.ruoyi.dahua.api.domain;

import java.io.Serializable;

/**
 * 大华设备网络状态信息
 */
public class DahuaNetworkStatusInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备IP地址
     */
    private String ipAddress;

    /**
     * 子网掩码
     */
    private String subnetMask;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 主DNS
     */
    private String dns1;

    /**
     * 备DNS
     */
    private String dns2;

    /**
     * 网卡状态: 0-断开, 1-连接
     */
    private Integer linkStatus;

    /**
     * 网卡状态描述
     */
    private String linkStatusDesc;

    /**
     * 网络是否可用
     */
    private Boolean networkAvailable;

    /**
     * 是否有无线功能
     */
    private Boolean hasWireless;

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    // Getters and Setters
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getSubnetMask() { return subnetMask; }
    public void setSubnetMask(String subnetMask) { this.subnetMask = subnetMask; }

    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }

    public String getDns1() { return dns1; }
    public void setDns1(String dns1) { this.dns1 = dns1; }

    public String getDns2() { return dns2; }
    public void setDns2(String dns2) { this.dns2 = dns2; }

    public Integer getLinkStatus() { return linkStatus; }
    public void setLinkStatus(Integer linkStatus) { this.linkStatus = linkStatus; }

    public String getLinkStatusDesc() { return linkStatusDesc; }
    public void setLinkStatusDesc(String linkStatusDesc) { this.linkStatusDesc = linkStatusDesc; }

    public Boolean getNetworkAvailable() { return networkAvailable; }
    public void setNetworkAvailable(Boolean networkAvailable) { this.networkAvailable = networkAvailable; }

    public Boolean getHasWireless() { return hasWireless; }
    public void setHasWireless(Boolean hasWireless) { this.hasWireless = hasWireless; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
