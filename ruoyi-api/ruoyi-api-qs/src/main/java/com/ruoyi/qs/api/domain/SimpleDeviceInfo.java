package com.ruoyi.qs.api.domain;

public class SimpleDeviceInfo {
    private String gbCode;
    private String gbDeviceId;
    private String deviceName;
    private String manufacturer;
    private String address;
    private String deviceStatus;
    private Integer channel;
    private String ipAddress;
    private Integer port;
    private Integer ptzType;
    private String longitude;
    private String latitude;
    private String gbParentId;

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode;
    }

    public String getGbDeviceId() {
        return gbDeviceId;
    }

    public void setGbDeviceId(String gbDeviceId) {
        this.gbDeviceId = gbDeviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPtzType() {
        return ptzType;
    }

    public void setPtzType(Integer ptzType) {
        this.ptzType = ptzType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGbParentId() {
        return gbParentId;
    }

    public void setGbParentId(String gbParentId) {
        this.gbParentId = gbParentId;
    }
}
