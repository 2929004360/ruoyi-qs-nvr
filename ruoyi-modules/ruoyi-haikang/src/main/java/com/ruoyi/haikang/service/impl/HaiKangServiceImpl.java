package com.ruoyi.haikang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;
import com.ruoyi.haikang.api.domain.PresetInfo;
import com.ruoyi.haikang.enums.HCPlayControlEnum;
import com.ruoyi.haikang.manager.StreamManager;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.haikang.service.IHaiKangService;
import com.ruoyi.haikang.service.IHaikangMediaStreamService;
import com.ruoyi.haikang.utils.CommonUtil;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.haikang.enums.HCCameraAuxEnum;
import com.ruoyi.haikang.enums.HCCruiseControlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IHaikangMediaStreamService mediaStreamService;

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
        log.info("开始登录海康设备, IP:{}, port:{}, user:{}", ip, port, user);
        
        // 检查是否已登录
        Integer existingUserId = userIdMap.get(ip);
        if (existingUserId != null && existingUserId >= 0) {
            log.warn("设备已登录，返回现有用户ID, IP:{}, userId:{}", ip, existingUserId);
            return existingUserId;
        }

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
            String errorMsg = "登录失败" + ip + "，错误码为: " + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        } else {
            log.info("海康设备登录成功, IP:{}, userId:{}", ip, userId);
        }

        userIdMap.put(ip, userId);
        deviceInfoMap.put(ip, deviceInfo);
        log.info("设备信息已保存, IP:{}, userId:{}", ip, userId);
        return userId;
    }

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    public void logoutDevice(String ip) {
        log.info("开始调用登出设备方法, IP:{}", ip);
        
        if (ObjectUtil.isNull(ip)) {
            log.error("登出设备失败，设备id不能为空");
            throw new ServiceException("设备id不能为空");
        }
        
        Integer userId = getUserId(ip);
        if (userId != null && userId >= 0) {
            log.debug("设备用户ID有效, IP:{}, userId:{}", ip, userId);
            try {
                log.info("开始登出海康设备, IP:{}", ip);
                
                // 清理设备相关的流媒体资源
                cleanDeviceStreamResources(ip);
                
                if (!client.hCNetSDK.NET_DVR_Logout(userId)) {
                    log.error("注销失败, IP:{}, 错误码:{}", ip, client.hCNetSDK.NET_DVR_GetLastError());
                } else {
                    log.info("注销成功, IP:{}", ip);
                }
            } catch (Exception e) {
                log.error("海康设备登出异常, IP:{}", ip, e);
            } finally {
                userIdMap.remove(ip);
                deviceInfoMap.remove(ip);
                log.info("设备信息已从缓存中移除, IP:{}", ip);
            }
        } else {
            log.warn("设备未登录，无需登出, IP:{}", ip);
        }
    }

    /**
     * 清理设备相关的流媒体资源
     */
    private void cleanDeviceStreamResources(String ip) {
        try {
            // 复制一份 key 列表，避免在遍历中修改集合
            List<String> streamKeysToClean = new ArrayList<>();
            StreamManager.streamKeyAndRealHandleMap.forEach((streamKey, handle) -> {
                if (streamKey.contains(ip)) {
                    streamKeysToClean.add(streamKey);
                }
            });
            
            // 逐个清理资源
            for (String streamKey : streamKeysToClean) {
                log.info("清理设备流媒体资源, streamKey:{}", streamKey);
                Long realHandle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
                FRealDataForRtpOverTcpCallback callback = StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.get(streamKey);
                RtpServerParam rtpParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
                
                mediaStreamService.cleanupResources(streamKey, rtpParam, realHandle, callback);
            }
            
        } catch (Exception e) {
            log.error("清理设备流媒体资源异常, IP:{}", ip, e);
        }
    }

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    public Integer getUserId(String ip) {
        log.info("开始获取设备用户ID, IP:{}", ip);
        
        if (ObjectUtil.isNull(ip)) {
            log.error("获取用户ID失败，IP不能为空");
            throw new ServiceException("设备id不能为空");
        }

        Integer userId = userIdMap.get(ip);
        if (userId == null) {
            log.warn("设备用户ID不存在, IP:{}", ip);
        } else {
            log.info("获取设备用户ID成功, IP:{}, userId:{}", ip, userId);
        }
        return userId;
    }

    /**
     * 获取设备的基本参数
     *
     * @param ip 设备ip
     * @return
     */
    @Override
    public HaikangDeviceInfo getDeviceInfo(String ip) {
        log.info("开始获取设备基本参数, IP:{}", ip);
        
        Integer iUserID = getUserId(ip);
        if (iUserID == null || iUserID < 0) {
            log.error("获取设备信息失败，设备未登录, IP:{}", ip);
            throw new ServiceException("设备未登录, IP:" + ip);
        }
        
        log.debug("设备用户ID有效, IP:{}, userId:{}", ip, iUserID);
        
        HaikangDeviceInfo deviceInfo = new HaikangDeviceInfo();
        HCNetSDK.NET_DVR_DEVICECFG_V40 m_strDeviceCfg = new HCNetSDK.NET_DVR_DEVICECFG_V40();
        m_strDeviceCfg.dwSize = m_strDeviceCfg.size();
        m_strDeviceCfg.write();
        Pointer pStrDeviceCfg = m_strDeviceCfg.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean b_GetCfg = client.hCNetSDK.NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_DEVICECFG_V40, 0Xffffffff, pStrDeviceCfg, m_strDeviceCfg.dwSize, pInt);
        if (!b_GetCfg) {
            log.error("获取设备参数失败, IP:{}, 错误码：{}", ip, client.hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.debug("获取设备参数成功, IP:{}", ip);
        }
        m_strDeviceCfg.read();
        parseVersion(m_strDeviceCfg.dwSoftwareVersion, deviceInfo);
        parseBuildTime(m_strDeviceCfg.dwSoftwareBuildDate, deviceInfo);
        parseDSPBuildDate(m_strDeviceCfg.dwDSPSoftwareBuildDate, deviceInfo);

        deviceInfo.setDeviceName(CommonUtil.parseHikvisionString(m_strDeviceCfg.sDVRName));
        deviceInfo.setDeviceSerial(CommonUtil.parseHikvisionString(m_strDeviceCfg.sSerialNumber));
        deviceInfo.setByChanNum(m_strDeviceCfg.byChanNum);

        log.info("获取设备基本参数成功, IP:{}, deviceName:{}, serial:{}", ip, deviceInfo.getDeviceName(), deviceInfo.getDeviceSerial());
        return deviceInfo;
    }

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    @Override
    public void startPlay(RtpServerParam rtpServerParam) {
        log.info("开始调用播放接口, deviceId:{}", rtpServerParam.getId());
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", rtpServerParam.getId(), r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", rtpServerParam.getId(), device.getIpAddress());

        String streamKey = "haikang_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", rtpServerParam.getId(), device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", rtpServerParam.getId(), lUserID);

        log.info("开始播放海康设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.startPlay(lUserID, device, streamKey, rtpServerParam);
        log.info("播放接口调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    @Override
    public void stopPlay(Long id) {
        log.info("开始调用停止播放接口, deviceId:{}", id);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());
        
        String streamKey = "haikang_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.warn("海康设备未登录，无法停止播放, deviceId:{}, IP:{}", id, device.getIpAddress());
            return;
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", id, lUserID);

        log.info("停止播放海康设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.endPlay(device.getId(), device.getChannel(), streamKey);
        log.info("停止播放接口调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    //设备版本解析
    public void parseVersion(int version, HaikangDeviceInfo deviceInfo) {
        int firstVersion = (version & 0XFF << 24) >> 24;
        int secondVersion = (version & 0XFF << 16) >> 16;
        int lowVersion = version & 0XFF;

        deviceInfo.setFirstVersion(firstVersion);
        deviceInfo.setSecondVersion(secondVersion);
        deviceInfo.setLowVersion(lowVersion);
    }

    public void parseBuildTime(int buildTime, HaikangDeviceInfo deviceInfo) {
        int year = ((buildTime & 0XFF << 16) >> 16) + 2000;
        int month = (buildTime & 0XFF << 8) >> 8;
        int data = buildTime & 0xFF;

        deviceInfo.setBuildTime(year + "-" + month + "-" + data);
    }

    public void parseDSPBuildDate(int DSPBuildDate, HaikangDeviceInfo deviceInfo) {
        int year = ((DSPBuildDate & 0XFF << 16) >> 16) + 2000;
        int month = (DSPBuildDate & 0XFF << 8) >> 8;
        int data = DSPBuildDate & 0xFF;

        deviceInfo.setDSPBuildDate(year + "-" + month + "-" + data);
    }

    @Override
    public void startPlayControl(Long deviceId, int channelId, String direction) {
        log.info("开始云台控制, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
        
        if (StringUtils.isEmpty(direction)) {
            log.error("云台控制失败，方向不能为空, deviceId:{}", deviceId);
            throw new ServiceException("方向不能为空!");
        }
        HCPlayControlEnum hcPlayControlEnum = HCPlayControlEnum.fromValue(direction);
        if (hcPlayControlEnum == null) {
            log.error("云台控制失败，无效的方向参数, deviceId:{}, direction:{}", deviceId, direction);
            throw new ServiceException("无效的方向参数: " + direction);
        }
        log.debug("方向参数验证成功, deviceId:{}, direction:{}", deviceId, direction);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
        
        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);
        
        boolean playHandle = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, hcPlayControlEnum.getCode(), 0);
        IntByReference m_err = new IntByReference(0);
        if (!playHandle) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("云台控制失败, deviceId:{}, errorCode:{}, error:{}", deviceId, m_err.getValue(), err);
            throw new ServiceException("控制失败!错误原因:" + m_err.getValue() + "," + err);
        }
        
        log.info("云台控制成功, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
    }

    @Override
    public void endPlayControl(Long deviceId, int channelId, String direction) {
        log.info("结束云台控制, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
        
        if (StringUtils.isEmpty(direction)) {
            log.error("云台控制停止失败，方向不能为空, deviceId:{}", deviceId);
            throw new ServiceException("方向不能为空!");
        }
        HCPlayControlEnum hcPlayControlEnum = HCPlayControlEnum.fromValue(direction);
        if (hcPlayControlEnum == null) {
            log.error("云台控制停止失败，无效的方向参数, deviceId:{}, direction:{}", deviceId, direction);
            throw new ServiceException("无效的方向参数: " + direction);
        }
        log.debug("方向参数验证成功, deviceId:{}, direction:{}", deviceId, direction);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
        
        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);
        
        IntByReference m_err = new IntByReference(0);
        boolean playHandle = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, hcPlayControlEnum.getCode(), 1);
        if (!playHandle) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("云台控制停止失败, deviceId:{}, errorCode:{}, error:{}", deviceId, m_err.getValue(), err);
            throw new ServiceException("(停止)控制失败!错误原因:" + m_err.getValue() + "," + err);
        }
        
        log.info("云台控制停止成功, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
    }

    /**
     * 重启设备
     *
     * @param deviceId
     */
    @Override
    public void restartDevice(Long deviceId) {
        log.info("开始重启设备, deviceId:{}", deviceId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        boolean b = client.hCNetSDK.NET_DVR_RebootDVR(lUserID);
        if (!b) {
            String errorMsg = "重启设备失败，错误码：" + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("重启设备成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
    }

    /**
     * 关机
     *
     * @param deviceId
     */
    @Override
    public void shutDown(Long deviceId) {
        log.info("开始关闭设备, deviceId:{}", deviceId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        boolean b = client.hCNetSDK.NET_DVR_ShutDownDVR(lUserID);
        if (!b) {
            String errorMsg = "关机失败，错误码：" + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("关闭设备成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
    }

    /**
     * 获取设备音频编码参数，确定转发音频参数
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @Override
    public HashMap<String, Object> getCurrentAudio(Long deviceId, int channelId) {
        log.info("开始获取设备音频编码参数, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HashMap<String, Object> map = new HashMap<>(16);
        //获取设备音频编码参数，确定转发音频参数
        HCNetSDK.NET_DVR_COMPRESSION_AUDIO net_dvr_compression_audio = getCurrentAudioCompress(lUserID);

        map.put("byAudioEncType", net_dvr_compression_audio.byAudioEncType);
        map.put("byAudioSamplingRate", net_dvr_compression_audio.byAudioSamplingRate);

        log.info("获取设备音频编码参数成功, deviceId:{}, encType:{}, sampleRate:{}", deviceId, net_dvr_compression_audio.byAudioEncType, net_dvr_compression_audio.byAudioSamplingRate);
        return map;
    }

    private HCNetSDK.NET_DVR_COMPRESSION_AUDIO getCurrentAudioCompress(int lUserID) {
        log.debug("开始获取音频编码格式参数, userId:{}", lUserID);
        
        HCNetSDK.NET_DVR_COMPRESSION_AUDIO net_dvr_compression_audio = new HCNetSDK.NET_DVR_COMPRESSION_AUDIO();
        boolean b_AudioCompress = client.hCNetSDK.NET_DVR_GetCurrentAudioCompress(lUserID, net_dvr_compression_audio);
        if (!b_AudioCompress) {
            String errorMsg = "获取音频编码格式参数失败，错误码为" + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        net_dvr_compression_audio.read();
        
        log.debug("获取音频编码格式参数成功, userId:{}", lUserID);
        return net_dvr_compression_audio;
    }

    /**
     * 获取预置点列表
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @Override
    public List<PresetInfo> getPresets(Long deviceId, int channelId) {
        log.info("开始获取预置点列表, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        List<PresetInfo> presetsList = new ArrayList<>();

        HCNetSDK.NET_DVR_PRESET_NAME[] presetNames = new HCNetSDK.NET_DVR_PRESET_NAME[HCNetSDK.MAX_PRESET_V30];
        for (int i = 0; i < presetNames.length; i++) {
            presetNames[i] = new HCNetSDK.NET_DVR_PRESET_NAME();
            presetNames[i].dwSize = presetNames[i].size();
            presetNames[i].write();
        }
        Pointer lpOutBuffer = new Memory(presetNames.length * presetNames[0].size());
        int dwOutBufferSize = presetNames.length * presetNames[0].size();
        IntByReference lpBytesReturned = new IntByReference(0);

        boolean presetList = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PRESET_NAME, channelId, lpOutBuffer, dwOutBufferSize, lpBytesReturned);
        if (!presetList) {
            log.error("获取预置位数据失败, deviceId:{}, channelId:{}, 错误码：{}", deviceId, channelId, client.hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.debug("获取预置位数据成功, deviceId:{}, channelId:{}", deviceId, channelId);
            ByteBuffer byteBuffer = lpOutBuffer.getByteBuffer(0, lpBytesReturned.getValue());
            // 设置字节顺序
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            for (int i = 0; i < HCNetSDK.MAX_PRESET_V30; i++) {
                HCNetSDK.NET_DVR_PRESET_NAME presetNameStruct = new HCNetSDK.NET_DVR_PRESET_NAME();

                presetNameStruct.dwSize = byteBuffer.getInt();
                presetNameStruct.wPresetNum = byteBuffer.getShort();
                byteBuffer.get(presetNameStruct.byRes1); // 读取保留字节
                byteBuffer.get(presetNameStruct.byName); // 读取名称
                presetNameStruct.wPanPos = byteBuffer.getShort();//水平参数
                presetNameStruct.wTiltPos = byteBuffer.getShort();//垂直参数
                presetNameStruct.wZoomPos = byteBuffer.getShort();//变倍参数
                byteBuffer.get(presetNameStruct.byRes); // 读取保留字节
                if (presetNameStruct.wPresetNum != 0)
                // 输出预置位数据
                {
                    if (Integer.valueOf(presetNameStruct.wPanPos) != 0 && Integer.valueOf(presetNameStruct.wTiltPos) != 0) {
                        presetsList.add(new PresetInfo(presetNameStruct.wPresetNum, CommonUtil.parseHikvisionString(presetNameStruct.byName), presetNameStruct.wPanPos, presetNameStruct.wTiltPos, presetNameStruct.wZoomPos));
                    }
                }
            }

        }

        log.info("获取预置点列表完成, deviceId:{}, channelId:{}, count:{}", deviceId, channelId, presetsList.size());
        return presetsList;
    }

    /**
     * 设置预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @Override
    public void setPresets(Long deviceId, int channelId, int presetIndex) {
        log.info("开始设置预置点, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // PTZ 控制命令：设置预置点
        boolean success = client.hCNetSDK.NET_DVR_PTZPreset_Other(
                lUserID,
                channelId,
                HCNetSDK.SET_PRESET,
                presetIndex
        );

        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "设置预置点位置失败, 错误码: " + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("设置预置点成功, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    /**
     * 清除预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @Override
    public void delPresets(Long deviceId, int channelId, int presetIndex) {
        log.info("开始清除预置点, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // PTZ 控制命令：清楚预置点
        boolean success = client.hCNetSDK.NET_DVR_PTZPreset_Other(
                lUserID,
                channelId,
                HCNetSDK.CLE_PRESET,
                presetIndex
        );

        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "清除预置点位置失败, 错误码: " + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("清除预置点成功, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    /**
     * 调用预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @Override
    public void invokePresets(Long deviceId, int channelId, int presetIndex) {
        log.info("开始调用预置点, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // PTZ 控制命令：调用预置点
        boolean success = client.hCNetSDK.NET_DVR_PTZPreset_Other(
                lUserID,
                channelId,
                HCNetSDK.GOTO_PRESET,
                presetIndex
        );

        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "调用预置点位置失败, 错误码: " + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("调用预置点成功, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    @Override
    public void cameraAuxControl(Long deviceId, int channelId, String operation, boolean isStart) {
        log.info("开始辅助设备控制, deviceId:{}, channelId:{}, operation:{}, isStart:{}", deviceId, channelId, operation, isStart);

        if (StringUtils.isEmpty(operation)) {
            log.error("辅助设备控制失败，操作类型不能为空, deviceId:{}", deviceId);
            throw new ServiceException("操作类型不能为空");
        }

        HCCameraAuxEnum auxEnum = HCCameraAuxEnum.fromValue(operation);
        if (auxEnum == null) {
            log.error("辅助设备控制失败，无效的操作类型, deviceId:{}, operation:{}", deviceId, operation);
            throw new ServiceException("无效的操作类型: " + operation);
        }
        log.debug("操作类型验证成功, deviceId:{}, operation:{}, msg:{}", deviceId, operation, auxEnum.getMsg());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        int action = isStart ? 0 : 1;
        boolean success = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, auxEnum.getCode(), action);
        IntByReference m_err = new IntByReference(0);

        if (!success) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("辅助设备控制失败, deviceId:{}, operation:{}, errorCode:{}, error:{}", deviceId, operation, m_err.getValue(), err);
            throw new ServiceException("辅助设备控制失败！错误原因:" + m_err.getValue() + "," + err);
        }

        log.info("辅助设备控制成功, deviceId:{}, channelId:{}, operation:{}, isStart:{}", deviceId, channelId, operation, isStart);
    }

    @Override
    public void cruiseControl(Long deviceId, int channelId, String operation, Integer param) {
        log.info("开始巡航控制, deviceId:{}, channelId:{}, operation:{}, param:{}", deviceId, channelId, operation, param);

        if (StringUtils.isEmpty(operation)) {
            log.error("巡航控制失败，操作类型不能为空, deviceId:{}", deviceId);
            throw new ServiceException("操作类型不能为空");
        }

        HCCruiseControlEnum cruiseEnum = HCCruiseControlEnum.fromValue(operation);
        if (cruiseEnum == null) {
            log.error("巡航控制失败，无效的操作类型, deviceId:{}, operation:{}", deviceId, operation);
            throw new ServiceException("无效的操作类型: " + operation);
        }
        log.debug("操作类型验证成功, deviceId:{}, operation:{}, msg:{}", deviceId, operation, cruiseEnum.getMsg());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        boolean success;
        IntByReference m_err = new IntByReference(0);

        if (param != null) {
            success = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, cruiseEnum.getCode(), param);
        } else {
            success = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, cruiseEnum.getCode(), 0);
        }

        if (!success) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("巡航控制失败, deviceId:{}, operation:{}, errorCode:{}, error:{}", deviceId, operation, m_err.getValue(), err);
            throw new ServiceException("巡航控制失败！错误原因:" + m_err.getValue() + "," + err);
        }

        log.info("巡航控制成功, deviceId:{}, channelId:{}, operation:{}, param:{}", deviceId, channelId, operation, param);
    }

    @Override
    public HashMap<String, Object> getPTZcfg(Long deviceId, int channelId) {
        log.info("开始获取球机PTZ参数, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_PTZPOS ptzPos = new HCNetSDK.NET_DVR_PTZPOS();
        boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PTZPOS, channelId, ptzPos.getPointer(), ptzPos.size(), new IntByReference(0));
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "获取球机PTZ参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        ptzPos.read();

        HashMap<String, Object> result = new HashMap<>();
        result.put("wAction", ptzPos.wAction);
        result.put("wPanPos", ptzPos.wPanPos);
        result.put("wTiltPos", ptzPos.wTiltPos);
        result.put("wZoomPos", ptzPos.wZoomPos);

        log.info("获取球机PTZ参数成功, deviceId:{}, channelId:{}, pan:{}, tilt:{}, zoom:{}", deviceId, channelId, ptzPos.wPanPos, ptzPos.wTiltPos, ptzPos.wZoomPos);
        return result;
    }

    @Override
    public void setPTZcfg(Long deviceId, int channelId, short p, short t, short z) {
        log.info("开始设置球机PTZ参数, deviceId:{}, channelId:{}, p:{}, t:{}, z:{}", deviceId, channelId, p, t, z);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_PTZPOS ptzPos = new HCNetSDK.NET_DVR_PTZPOS();
        ptzPos.wAction = 1;
        ptzPos.wPanPos = p;
        ptzPos.wTiltPos = t;
        ptzPos.wZoomPos = z;
        ptzPos.write();

        boolean success = client.hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_PTZPOS, channelId, ptzPos.getPointer(), ptzPos.size());
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "设置球机PTZ参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        log.info("设置球机PTZ参数成功, deviceId:{}, channelId:{}", deviceId, channelId);
    }

    @Override
    public HashMap<String, Object> getPTZAbsoluteEx(Long deviceId, int channelId) {
        log.info("开始获取高精度PTZ绝对位置配置, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_PTZABSOLUTEEX_CFG ptzAbsoluteExCfg = new HCNetSDK.NET_DVR_PTZABSOLUTEEX_CFG();
        ptzAbsoluteExCfg.dwSize = ptzAbsoluteExCfg.size();
        ptzAbsoluteExCfg.write();

        boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PTZABSOLUTEEX, channelId, ptzAbsoluteExCfg.getPointer(), ptzAbsoluteExCfg.size(), new IntByReference(0));
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "获取高精度PTZ绝对位置配置失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        ptzAbsoluteExCfg.read();

        HashMap<String, Object> result = new HashMap<>();
        result.put("dwSize", ptzAbsoluteExCfg.dwSize);
        result.put("dwFocalLen", ptzAbsoluteExCfg.dwFocalLen);
        result.put("fHorizontalSpeed", ptzAbsoluteExCfg.fHorizontalSpeed);
        result.put("fVerticalSpeed", ptzAbsoluteExCfg.fVerticalSpeed);
        result.put("byZoomType", ptzAbsoluteExCfg.byZoomType);

        log.info("获取高精度PTZ绝对位置配置成功, deviceId:{}, channelId:{}", deviceId, channelId);
        return result;
    }

    @Override
    public String getDevTime(Long deviceId) {
        log.info("开始获取设备时间参数, deviceId:{}", deviceId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_TIME m_Time = new HCNetSDK.NET_DVR_TIME();
        boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_TIMECFG, 0, m_Time.getPointer(), m_Time.size(), new IntByReference(0));
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "获取设备时间参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        m_Time.read();

        Pointer pTime = m_Time.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean b_GetTime = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_TIMECFG, 0xffffffff, pTime, m_Time.size(), pInt);
        if (!b_GetTime) {
            throw new ServiceException("获取时间参数失败，错误码：" + client.hCNetSDK.NET_DVR_GetLastError());
        }
        m_Time.read();

        return String.format("%04d-%02d-%02d %02d:%02d:%02d", m_Time.dwYear, m_Time.dwMonth, m_Time.dwDay, m_Time.dwHour, m_Time.dwMinute, m_Time.dwSecond);
    }

    @Override
    public void setDevTime(Long deviceId, String time) {
        log.info("开始设置设备时间参数, deviceId:{}, time:{}", deviceId, time);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_TIME devTime = new HCNetSDK.NET_DVR_TIME();
        if (StringUtils.isNotEmpty(time)) {
            try {
                String[] timeParts = time.split("-| |:");
                if (timeParts.length >= 6) {
                    devTime.dwYear = Integer.parseInt(timeParts[0]);
                    devTime.dwMonth = Integer.parseInt(timeParts[1]);
                    devTime.dwDay = Integer.parseInt(timeParts[2]);
                    devTime.dwHour = Integer.parseInt(timeParts[3]);
                    devTime.dwMinute = Integer.parseInt(timeParts[4]);
                    devTime.dwSecond = Integer.parseInt(timeParts[5]);
                }
            } catch (Exception e) {
                log.error("解析时间格式失败, deviceId:{}, time:{}, error:{}", deviceId, time, e.getMessage());
                throw new ServiceException("时间格式错误，请使用格式: yyyy-MM-dd HH:mm:ss");
            }
        }
        devTime.write();

        boolean success = client.hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_TIMECFG, 0, devTime.getPointer(), devTime.size());
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "设置设备时间参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        log.info("设置设备时间参数成功, deviceId:{}", deviceId);
    }

    /**
     * 海康设备查询录像
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public ArrayList<HashMap<String, Object>> queryRecord(Long deviceId, int channelId, String startTime, String endTime) {
        log.info("开始查询海康设备录像, deviceId:{}, channelId:{}, startTime:{}, endTime:{}", deviceId, channelId, startTime, endTime);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // 解码 URL 编码的时间参数
        try {
            startTime = java.net.URLDecoder.decode(startTime, "UTF-8");
            endTime = java.net.URLDecoder.decode(endTime, "UTF-8");
            log.debug("URL解码后的时间, startTime:{}, endTime:{}", startTime, endTime);
        } catch (Exception e) {
            log.warn("URL解码失败，使用原始时间, error:{}", e.getMessage());
        }

        // 查询录像
        ArrayList<HashMap<String, Object>> recordList = tryFindFile(lUserID, channelId, startTime, endTime, deviceId);

        if (recordList.isEmpty()) {
            log.warn("未查询到录像文件, deviceId:{}, channelId:{}", deviceId, channelId);
            throw new ServiceException("未查询到录像文件");
        }

        log.info("查询海康设备录像完成, deviceId:{}, channelId:{}, 共查询到 {} 条录像记录", deviceId, channelId, recordList.size());
        return recordList;
    }

    /**
     * 尝试使用旧版 API 查询录像
     */
    private ArrayList<HashMap<String, Object>> tryFindFile(int lUserID, int channelId, String startTime, String endTime, Long deviceId) {
        ArrayList<HashMap<String, Object>> recordList = new ArrayList<>();

        HCNetSDK.NET_DVR_FILECOND_V40 fileCondition = new HCNetSDK.NET_DVR_FILECOND_V40();
        fileCondition.read(); // 先读取初始值

        // 解析时间
        try {
            String[] dateStartByFile = startTime.split(" ");
            String[] dateStart1 = dateStartByFile[0].split("-");
            String[] dateStart2 = dateStartByFile[1].split(":");

            fileCondition.struStartTime.dwYear = Integer.parseInt(dateStart1[0]);
            fileCondition.struStartTime.dwMonth = Integer.parseInt(dateStart1[1]);
            fileCondition.struStartTime.dwDay = Integer.parseInt(dateStart1[2]);
            fileCondition.struStartTime.dwHour = Integer.parseInt(dateStart2[0]);
            fileCondition.struStartTime.dwMinute = Integer.parseInt(dateStart2[1]);
            fileCondition.struStartTime.dwSecond = Integer.parseInt(dateStart2[2]);

            String[] dateEndByFile = endTime.split(" ");
            String[] dateEnd1 = dateEndByFile[0].split("-");
            String[] dateEnd2 = dateEndByFile[1].split(":");

            fileCondition.struStopTime.dwYear = Integer.parseInt(dateEnd1[0]);
            fileCondition.struStopTime.dwMonth = Integer.parseInt(dateEnd1[1]);
            fileCondition.struStopTime.dwDay = Integer.parseInt(dateEnd1[2]);
            fileCondition.struStopTime.dwHour = Integer.parseInt(dateEnd2[0]);
            fileCondition.struStopTime.dwMinute = Integer.parseInt(dateEnd2[1]);
            fileCondition.struStopTime.dwSecond = Integer.parseInt(dateEnd2[2]);
        } catch (Exception e) {
            log.error("时间参数解析失败, startTime:{}, endTime:{}, error:{}", startTime, endTime, e.getMessage(), e);
            return recordList;
        }

        // 设置其他查询条件
        fileCondition.lChannel = channelId;
        fileCondition.dwFileType = 0xff; // 全部类型
        fileCondition.dwIsLocked = 0xff; // 所有文件
        fileCondition.byStreamType = 0; // 主码流
        fileCondition.write(); // 重要：写入内存！

        log.debug("开始查询, 通道号:{}, 时间范围:{}-{}", channelId, startTime, endTime);

        // 先尝试旧版 API
        int findHandle = client.hCNetSDK.NET_DVR_FindFile_V40(lUserID, fileCondition);
        
        if (findHandle == -1) {
            int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
            log.warn("NET_DVR_FindFile_V40 失败, 通道号:{}, 错误码:{}", channelId, errorCode);
            return recordList;
        }

        try {
            HCNetSDK.NET_DVR_FINDDATA_V40 findData = new HCNetSDK.NET_DVR_FINDDATA_V40();
            int findNextResult;

            while (true) {
                findNextResult = client.hCNetSDK.NET_DVR_FindNextFile_V40(findHandle, findData);
                if (findNextResult == 1000) { // NET_DVR_FILE_SUCCESS - 找到文件
                    findData.read(); // 读取数据

                    String fileName = CommonUtil.parseHikvisionString(findData.sFileName);
                    String start = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            findData.struStartTime.dwYear, findData.struStartTime.dwMonth, findData.struStartTime.dwDay,
                            findData.struStartTime.dwHour, findData.struStartTime.dwMinute, findData.struStartTime.dwSecond);
                    String stop = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            findData.struStopTime.dwYear, findData.struStopTime.dwMonth, findData.struStopTime.dwDay,
                            findData.struStopTime.dwHour, findData.struStopTime.dwMinute, findData.struStopTime.dwSecond);

                    HashMap<String, Object> record = new HashMap<>(16);
                    record.put("channel", String.valueOf(channelId));
                    record.put("type", getRecordTypeString(findData.byFileType));
                    record.put("start", start);
                    record.put("end", stop);
                    record.put("fileName", fileName);
                    record.put("fileSize", findData.dwFileSize);
                    recordList.add(record);
                    
                    log.debug("找到录像: channel={}, fileName={}, start={}, end={}", channelId, fileName, start, stop);
                } else if (findNextResult == 1003) { // NET_DVR_NOMOREFILE - 没有更多文件，查找结束
                    log.debug("查询结束, 共找到 {} 条记录", recordList.size());
                    break;
                } else if (findNextResult == 1001) { // NET_DVR_FILE_NOFIND - 未查找到文件
                    log.warn("未查找到文件");
                    break;
                } else if (findNextResult == 1002) { // NET_DVR_ISFINDING - 正在查找请等待
                    log.debug("正在查找，请等待...");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else if (findNextResult == 1004) { // NET_DVR_FILE_EXCEPTION - 查找文件时异常
                    log.warn("查找文件时异常");
                    break;
                } else if (findNextResult == 1005) { // NET_DVR_FIND_TIMEOUT - 查找文件超时
                    log.warn("查找文件超时");
                    break;
                } else { // 其他错误
                    int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                    log.warn("查找下一个文件失败, 返回值:{}, 错误码:{}", findNextResult, errorCode);
                    break;
                }
            }
        } finally {
            client.hCNetSDK.NET_DVR_FindClose_V30(findHandle);
        }

        return recordList;
    }

    /**
     * 将海康设备的录像类型转换为可读字符串
     *
     * @param fileType 海康设备返回的文件类型
     * @return 可读的录像类型字符串
     */
    private String getRecordTypeString(byte fileType) {
        switch (fileType) {
            case 0:
                return "定时录像";
            case 1:
                return "移动侦测";
            case 2:
                return "报警触发";
            case 3:
                return "报警|移动侦测";
            case 4:
                return "报警&移动侦测";
            case 5:
                return "命令触发";
            case 6:
                return "手动录像";
            case 7:
                return "震动报警";
            case 8:
                return "环境报警";
            case 9:
                return "智能报警";
            case 10:
                return "PIR报警";
            case 11:
                return "无线报警";
            case 12:
                return "呼救报警";
            case 13:
                return "移动侦测/PIR/无线/呼救等报警";
            case 14:
                return "智能交通事件";
            case 15:
                return "越界侦测";
            case 16:
                return "区域入侵侦测";
            case 17:
                return "音频异常侦测";
            case 18:
                return "场景变更侦测";
            case 19:
                return "智能侦测";
            case 20:
                return "人脸侦测";
            case 21:
                return "信号量/POS录像";
            case 22:
                return "回传";
            case 23:
                return "回迁录像";
            case 24:
                return "遮挡";
            case 26:
                return "进入区域侦测";
            case 27:
                return "离开区域侦测";
            case 28:
                return "徘徊侦测";
            case 29:
                return "人员聚集侦测";
            case 30:
                return "快速运动侦测";
            case 31:
                return "停车侦测";
            case 32:
                return "物品遗留侦测";
            case 33:
                return "物品拿取侦测";
            case 34:
                return "火点检测";
            case 36:
                return "船只检测";
            case 37:
                return "测温预警";
            case 38:
                return "测温报警";
            case 42:
                return "温差报警";
            case 43:
                return "离线测温报警";
            case 44:
                return "防区报警";
            case 45:
                return "紧急求助";
            case 46:
                return "业务咨询";
            case 47:
                return "起身检测";
            case 48:
                return "折线攀高";
            case 49:
                return "目标区域滞留超时";
            default:
                return "未知类型(" + fileType + ")";
        }
    }
}
