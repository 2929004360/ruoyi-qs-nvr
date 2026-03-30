package com.ruoyi.haikang.service;

import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;

/**
 * @FileName IHaikangService
 * @Description
 * @Author fengcheng
 * @date 2026-03-27
 **/
public interface IHaiKangService {

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口，默认为设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户ID，失败返回-1
     */
    public int loginDevice(String ip, short port, String user, String psw);

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    public void logoutDevice(String ip);

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    public Integer getUserId(String ip);

    /**
     * 获取设备的基本参数
     *
     * @param ip 设备ip
     * @return
     */
    HaikangDeviceInfo getDeviceInfo(String ip);
}
