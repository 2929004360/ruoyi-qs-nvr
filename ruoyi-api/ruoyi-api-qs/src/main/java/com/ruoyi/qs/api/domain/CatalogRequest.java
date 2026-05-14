package com.ruoyi.qs.api.domain;

import java.util.List;

public class CatalogRequest {
    private QsGb28181Platform platform;
    private List<SimpleDeviceInfo> deviceList;

    public QsGb28181Platform getPlatform() {
        return platform;
    }

    public void setPlatform(QsGb28181Platform platform) {
        this.platform = platform;
    }

    public List<SimpleDeviceInfo> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<SimpleDeviceInfo> deviceList) {
        this.deviceList = deviceList;
    }
}
