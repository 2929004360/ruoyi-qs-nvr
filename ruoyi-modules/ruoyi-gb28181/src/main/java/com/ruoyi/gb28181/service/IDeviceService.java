package com.ruoyi.gb28181.service;

import com.ruoyi.gb28181.bean.SipTransactionInfo;
import com.ruoyi.gb28181.domain.Device;

import java.util.List;

/**
 * 设备相关业务处理
 *
 * @author lin
 */
public interface IDeviceService {

    /**
     * 查询设备信息
     *
     * @param deviceId 设备编号
     * @return 设备信息
     */
    Device getDeviceByDeviceId(String deviceId);

    /**
     * 设备上线
     *
     * @param device 设备信息
     */
    void online(Device device, SipTransactionInfo sipTransactionInfo);

    /**
     * 批量修改设备
     *
     * @param deviceList
     */
    void updateDeviceList(List<Device> deviceList);

    /**
     * 修改设备
     *
     * @param device
     */
    void updateDevice(Device device);

    /**
     * 设备下线
     * @param deviceId 设备编号
     */
    void offline(String deviceId, String reason);

    /**
     * 更新设备心跳信息
     *
     * @param device
     */
    void updateDeviceHeartInfo(Device device);
}
