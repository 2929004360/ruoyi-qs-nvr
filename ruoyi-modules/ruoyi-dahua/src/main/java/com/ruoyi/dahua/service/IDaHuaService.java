package com.ruoyi.dahua.service;

import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 大华sdk 接口
 *
 * @FileName IDaHuaService
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
public interface IDaHuaService {

    /**
     * 大华设备登录
     *
     * @param m_strIp 设备ip
     * @param m_nPort 设备端口
     * @param m_strUser 设备用户名
     * @param m_strPassword 设备密码
     * @param deviceId 设备id
     * @param onlineType 上线类型(1=主动添加, 2=主动注册)
     */
    NetSDKLib.LLong loginDevice(String m_strIp, int m_nPort, String m_strUser, String m_strPassword, String deviceId,String onlineType);

    /**
     * 查询是否登录
     *
     * @param ip 设备ip
     */
    Boolean isUserId(String ip);

    /**
     * 大华设备获取时间
     *
     * @param ip 设备ip
     * @return
     */
    String getTime(String ip);

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    DahuaDevice getDahuaDevice(String ip);

    /**
     * 大华设备登出
     *
     * @param ip 设备ip
     * @return
     */
    Boolean logoutDevice(String ip);
}
