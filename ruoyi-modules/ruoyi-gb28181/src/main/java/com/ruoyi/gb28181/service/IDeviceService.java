package com.ruoyi.gb28181.service;

import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.bean.Preset;
import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.bean.SipTransactionInfo;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;

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
     *
     * @param deviceId 设备编号
     */
    void offline(String deviceId, String reason);

    /**
     * 更新设备心跳信息
     *
     * @param device
     */
    void updateDeviceHeartInfo(Device device);

    /**
     * 根据设备id和通道获取设备通道
     *
     * @param gbDeviceId
     * @param gbChannelId
     * @return
     */
    DeviceChannel getDeviceChannelByChannelId(String gbDeviceId, String gbChannelId);

    /**
     * 获取所有国标设备
     *
     * @return 设备列表
     */
    List<Device> getAllDevices();

    /**
     * 根据设备id获取所有通道
     *
     * @param gbDeviceId 设备编号
     * @return 通道列表
     */
    List<DeviceChannel> getChannelsByDeviceId(String gbDeviceId);

    /**
     * 通用前端控制命令(参考国标文档A.3.1指令格式)
     *
     * @param device       设备
     * @param channelId    通道国标编号
     * @param cmdCode      指令码(对应国标文档指令格式中的字节4)
     * @param parameter1   数据一(对应国标文档指令格式中的字节5, 范围0-255)
     * @param parameter2   数据二(对应国标文档指令格式中的字节6, 范围0-255)
     * @param combindCode2 组合码二(对应国标文档指令格式中的字节7, 范围0-15)
     */
    void frontEndCommand(Device device, String channelId, Integer cmdCode, Integer parameter1, Integer parameter2, Integer combindCode2);

    /**
     * 查询预置位
     *
     * @param device    设备国标编号
     * @param channelId 通道国标编号
     * @param callback
     */
    void queryPreset(Device device, String channelId, ErrorCallback<List<Preset>> callback);

    /**
     * 查询录像文件列表
     *
     * @param device    设备
     * @param channel   通道
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param callback  回调
     */
    void queryRecord(Device device, DeviceChannel channel, String startTime, String endTime, ErrorCallback<RecordInfo> callback);
}
