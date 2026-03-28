package com.ruoyi.haikang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.haikang.service.IHaiKangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName HaiKangServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-27
 **/
@Service
@Slf4j
public class HaiKangServiceImpl implements IHaiKangService {

    @Autowired
    private Client client;

    /**
     * 设备信息
     */
    public static ConcurrentHashMap<String, HCNetSDK.NET_DVR_DEVICEINFO_V40> deviceInfoMap = new ConcurrentHashMap<String, HCNetSDK.NET_DVR_DEVICEINFO_V40>();

    /**
     * 海康登录用户ID
     */
    public static ConcurrentHashMap<String, Integer> userIdMap = new ConcurrentHashMap<String, Integer>();

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口，默认为设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户ID，失败返回-1
     */
    public int loginDevice(String ip, short port, String user, String psw) {
        // 创建设备登录信息和设备信息对象
        HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

        // 设置设备IP地址
        byte[] deviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        byte[] ipBytes = ip.getBytes();
        System.arraycopy(ipBytes, 0, deviceAddress, 0, Math.min(ipBytes.length, deviceAddress.length));
        loginInfo.sDeviceAddress = deviceAddress;

        // 设置用户名和密码
        byte[] userName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        byte[] password = psw.getBytes();
        System.arraycopy(user.getBytes(), 0, userName, 0, Math.min(user.length(), userName.length));
        System.arraycopy(password, 0, loginInfo.sPassword, 0, Math.min(password.length, loginInfo.sPassword.length));
        loginInfo.sUserName = userName;

        // 设置端口和登录模式
        loginInfo.wPort = port;
        // 同步登录
        loginInfo.bUseAsynLogin = false;
        // 使用SDK私有协议
        loginInfo.byLoginMode = 0;

        // 执行登录操作
        int userId = client.hCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
        if (userId == -1) {
            throw new ServiceException("登录失败" + ip + "，错误码为: " + client.hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.info(ip + " 设备登录成功！");
        }

        userIdMap.put(ip, userId);
        deviceInfoMap.put(ip, deviceInfo);
        return userId;
    }

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    public void logoutDevice(String ip) {
        if (ObjectUtil.isNull(ip)) {
            throw new ServiceException("设备id不能为空");
        }
        Integer userId = getUserId(ip);
        if (userId != null && userId >= 0) {
            if (!client.hCNetSDK.NET_DVR_Logout(userId)) {
                log.error("注销失败，错误码为" + client.hCNetSDK.NET_DVR_GetLastError());
            }
            log.info("注销成功");
            userIdMap.remove(ip);
            deviceInfoMap.remove(ip);
        } else {
            log.error("设备未登录");
        }
    }

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    public Integer getUserId(String ip) {
        if (ObjectUtil.isNull(ip)) {
            throw new ServiceException("设备id不能为空");
        }

        return userIdMap.get(ip);
    }
}
