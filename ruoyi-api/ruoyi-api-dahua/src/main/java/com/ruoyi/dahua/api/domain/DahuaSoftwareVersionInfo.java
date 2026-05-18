package com.ruoyi.dahua.api.domain;

import java.io.Serializable;

/**
 * 大华设备软件版本信息
 */
public class DahuaSoftwareVersionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主版本号
     */
    private String majorVersion;

    /**
     * 次版本号
     */
    private String minorVersion;

    /**
     * 修订版本号
     */
    private String revisionVersion;

    /**
     * 完整版本号
     */
    private String fullVersion;

    /**
     * 构建日期
     */
    private String buildDate;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 硬件版本
     */
    private String hardwareVersion;

    /**
     * 软件版本
     */
    private String softwareVersion;

    /**
     * Web版本
     */
    private String webVersion;

    /**
     * 从片版本
     */
    private String peripheralVersion;

    /**
     * 地理信息定位芯片版本
     */
    private String geographyVersion;

    /**
     * 协议版本号
     */
    private Integer protocolVersion;

    /**
     * 软件构建日期
     */
    private Integer softwareBuildDate;

    /**
     * 从片构建日期
     */
    private Integer peripheralBuildDate;

    /**
     * 地理信息构建日期
     */
    private Integer geographyBuildDate;

    /**
     * 硬件日期
     */
    private Integer hardwareDate;

    /**
     * Web构建日期
     */
    private Integer webBuildDate;

    /**
     * 是否获取成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    // Getters and Setters
    public String getMajorVersion() { return majorVersion; }
    public void setMajorVersion(String majorVersion) { this.majorVersion = majorVersion; }

    public String getMinorVersion() { return minorVersion; }
    public void setMinorVersion(String minorVersion) { this.minorVersion = minorVersion; }

    public String getRevisionVersion() { return revisionVersion; }
    public void setRevisionVersion(String revisionVersion) { this.revisionVersion = revisionVersion; }

    public String getFullVersion() { return fullVersion; }
    public void setFullVersion(String fullVersion) { this.fullVersion = fullVersion; }

    public String getBuildDate() { return buildDate; }
    public void setBuildDate(String buildDate) { this.buildDate = buildDate; }

    public String getDeviceModel() { return deviceModel; }
    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getHardwareVersion() { return hardwareVersion; }
    public void setHardwareVersion(String hardwareVersion) { this.hardwareVersion = hardwareVersion; }

    public String getSoftwareVersion() { return softwareVersion; }
    public void setSoftwareVersion(String softwareVersion) { this.softwareVersion = softwareVersion; }

    public String getWebVersion() { return webVersion; }
    public void setWebVersion(String webVersion) { this.webVersion = webVersion; }

    public String getPeripheralVersion() { return peripheralVersion; }
    public void setPeripheralVersion(String peripheralVersion) { this.peripheralVersion = peripheralVersion; }

    public String getGeographyVersion() { return geographyVersion; }
    public void setGeographyVersion(String geographyVersion) { this.geographyVersion = geographyVersion; }

    public Integer getProtocolVersion() { return protocolVersion; }
    public void setProtocolVersion(Integer protocolVersion) { this.protocolVersion = protocolVersion; }

    public Integer getSoftwareBuildDate() { return softwareBuildDate; }
    public void setSoftwareBuildDate(Integer softwareBuildDate) { this.softwareBuildDate = softwareBuildDate; }

    public Integer getPeripheralBuildDate() { return peripheralBuildDate; }
    public void setPeripheralBuildDate(Integer peripheralBuildDate) { this.peripheralBuildDate = peripheralBuildDate; }

    public Integer getGeographyBuildDate() { return geographyBuildDate; }
    public void setGeographyBuildDate(Integer geographyBuildDate) { this.geographyBuildDate = geographyBuildDate; }

    public Integer getHardwareDate() { return hardwareDate; }
    public void setHardwareDate(Integer hardwareDate) { this.hardwareDate = hardwareDate; }

    public Integer getWebBuildDate() { return webBuildDate; }
    public void setWebBuildDate(Integer webBuildDate) { this.webBuildDate = webBuildDate; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
