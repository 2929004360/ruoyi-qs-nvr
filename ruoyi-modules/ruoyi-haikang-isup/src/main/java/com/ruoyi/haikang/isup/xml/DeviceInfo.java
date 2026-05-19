package com.ruoyi.haikang.isup.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "DeviceInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceInfo {

    /**
     * 设备版本号
     * 使用XmlAttribute注解表示该字段作为XML属性
     */
    @XmlAttribute(name = "version")
    private String version;

    /**
     * 设备名称
     * 使用XmlElement注解表示该字段作为XML元素
     */
    @XmlElement(name = "deviceName")
    private String deviceName;

    /**
     * 设备ID
     * 唯一标识设备的字符串
     */
    @XmlElement(name = "deviceID")
    private String deviceID;

    /**
     * 设备描述信息
     */
    @XmlElement(name = "deviceDescription")
    private String deviceDescription;

    /**
     * 设备位置信息
     */
    @XmlElement(name = "deviceLocation")
    private String deviceLocation;

    /**
     * 系统联系人信息
     */
    @XmlElement(name = "systemContact")
    private String systemContact;

    /**
     * 设备型号
     */
    @XmlElement(name = "model")
    private String model;

    /**
     * 设备序列号
     */
    @XmlElement(name = "serialNumber")
    private String serialNumber;

    /**
     * 设备MAC地址
     */
    @XmlElement(name = "macAddress")
    private String macAddress;

    /**
     * 固件版本号
     */
    @XmlElement(name = "firmwareVersion")
    private String firmwareVersion;

    /**
     * 固件发布日期
     */
    @XmlElement(name = "firmwareReleasedDate")
    private String firmwareReleasedDate;

    /**
     * 编码器版本号
     */
    @XmlElement(name = "encoderVersion")
    private String encoderVersion;

    /**
     * 编码器发布日期
     */
    @XmlElement(name = "encoderReleasedDate")
    private String encoderReleasedDate;

    /**
     * 引导程序版本号
     */
    @XmlElement(name = "bootVersion")
    private String bootVersion;

    /**
     * 引导程序发布日期
     */
    @XmlElement(name = "bootReleasedDate")
    private String bootReleasedDate;

    /**
     * 硬件版本号
     */
    @XmlElement(name = "hardwareVersion")
    private String hardwareVersion;

    /**
     * 设备类型
     */
    @XmlElement(name = "deviceType")
    private String deviceType;

    /**
     * 远程控制ID
     */
    @XmlElement(name = "telecontrolID")
    private String telecontrolID;

    /**
     * 是否支持蜂鸣器
     */
    @XmlElement(name = "supportBeep")
    private Boolean supportBeep;

    /**
     * 是否支持视频丢失检测
     */
    @XmlElement(name = "supportVideoLoss")
    private Boolean supportVideoLoss;

    /**
     * 固件版本详细信息
     */
    @XmlElement(name = "firmwareVersionInfo")
    private String firmwareVersionInfo;

    /**
     * 制造商信息
     */
    @XmlElement(name = "manufacturer")
    private String manufacturer;

    /**
     * 子序列号
     */
    @XmlElement(name = "subSerialNumber")
    private String subSerialNumber;

    /**
     * OEM代码
     */
    @XmlElement(name = "OEMCode")
    private Integer oemCode;
}
