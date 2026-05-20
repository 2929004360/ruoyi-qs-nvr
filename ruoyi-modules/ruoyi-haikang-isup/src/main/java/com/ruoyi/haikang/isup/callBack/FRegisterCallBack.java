package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.alarm.AlarmService;
import com.ruoyi.haikang.isup.service.haikang.alarm.HCISUPAlarm;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.service.haikang.stream.StreamService;
import com.ruoyi.zlm.api.RemoteZlmService;
import com.sun.jna.Pointer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @FileName FRegisterCallBack
 * @Description
 * @Author fengcheng
 * @date 2025-12-24
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class FRegisterCallBack implements HCISUPCMS.DEVICE_REGISTER_CB {

    private final HaikangIsupConfig haikangIsupConfig;
    private final RemoteZlmService remoteZlmService;

    public static final ConcurrentHashMap<Integer, String> deviceIdMap = new ConcurrentHashMap<>(16);

    public static final ConcurrentHashMap<String, Integer> lUserIDMap = new ConcurrentHashMap<>(16);

    public static final CopyOnWriteArrayList<Device> deviceList = new CopyOnWriteArrayList<>();

    public boolean invoke(int lUserID, int dwDataType, Pointer pOutBuffer, int dwOutLen, Pointer pInBuffer, int dwInLen, Pointer pUser) {
        log.info("设备注册状态回调: {}, lUserID: {}", dwDataType, lUserID);
        HCISUPCMS.NET_EHOME_DEV_REG_INFO_V12 strDevRegInfo = new HCISUPCMS.NET_EHOME_DEV_REG_INFO_V12();
        Pointer pDevRegInfo = strDevRegInfo.getPointer();

        switch (dwDataType) {
            //设备上线回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_ON: {
                strDevRegInfo.write();
                pDevRegInfo.write(0, pOutBuffer.getByteArray(0, strDevRegInfo.size()), 0, strDevRegInfo.size());
                strDevRegInfo.read();

                HCISUPCMS.NET_EHOME_SERVER_INFO_V50 strEhomeServerInfo = new HCISUPCMS.NET_EHOME_SERVER_INFO_V50();
                strEhomeServerInfo.read();
                //strEhomeServerInfo.dwSize = strEhomeServerInfo.size();
                byte[] byCmsIP = new byte[0];
                //设置报警服务器地址、端口、类型
                byCmsIP = haikangIsupConfig.getAlarmServer().getIp().getBytes();
                System.arraycopy(byCmsIP, 0, strEhomeServerInfo.struUDPAlarmSever.szIP, 0, byCmsIP.length);
                System.arraycopy(byCmsIP, 0, strEhomeServerInfo.struTCPAlarmSever.szIP, 0, byCmsIP.length);

                //报警服务器类型：0- 只支持UDP协议上报，1- 支持UDP、TCP两种协议上报 2-MQTT
                strEhomeServerInfo.dwAlarmServerType = haikangIsupConfig.getAlarmServer().getType();
                strEhomeServerInfo.struTCPAlarmSever.wPort = (short) haikangIsupConfig.getAlarmServer().getTcpPort();
                strEhomeServerInfo.struUDPAlarmSever.wPort = (short) haikangIsupConfig.getAlarmServer().getUdpPort();

                byte[] byClouldAccessKey = "test".getBytes();
                System.arraycopy(byClouldAccessKey, 0, strEhomeServerInfo.byClouldAccessKey, 0, byClouldAccessKey.length);
                byte[] byClouldSecretKey = "12345".getBytes();
                System.arraycopy(byClouldSecretKey, 0, strEhomeServerInfo.byClouldSecretKey, 0, byClouldSecretKey.length);
                strEhomeServerInfo.dwClouldPoolId = 1;

                //设置图片存储服务器地址、端口、类型
                byte[] bySSIP = new byte[0];
                bySSIP = haikangIsupConfig.getPicServer().getIp().getBytes();
                System.arraycopy(bySSIP, 0, strEhomeServerInfo.struPictureSever.szIP, 0, bySSIP.length);
                strEhomeServerInfo.struPictureSever.wPort = (short) haikangIsupConfig.getPicServer().getPort();
                strEhomeServerInfo.dwPicServerType = haikangIsupConfig.getPicServer().getType();    //存储服务器（SS）类型：0-Tomcat，1-VRB，2-云存储，3-KMS，4-ISUP5.0。
                strEhomeServerInfo.write();
                dwInLen = strEhomeServerInfo.size();
                pInBuffer.write(0, strEhomeServerInfo.getPointer().getByteArray(0, dwInLen), 0, dwInLen);

                String deviceId = new String(strDevRegInfo.struRegInfo.byDeviceID).trim();
                String ip = new String(strDevRegInfo.struRegInfo.struDevAdd.szIP).trim();
                log.info("设备上线, DeviceID: {}, LoginID: {}", deviceId, lUserID);


                Device device = new Device();
                device.setDeviceId("haikang_isup_"+deviceId);
                device.setIp(ip);
                device.setLUserID(lUserID);

                // 判断是否已存在（根据 deviceId 和 ip）
                boolean exists = deviceList.stream()
                        .anyMatch(d -> d.getDeviceId().equals(deviceId) && d.getIp().equals(ip));

                if (!exists) {
                    deviceList.add(device);
                }


                deviceIdMap.put(lUserID, ip);
                lUserIDMap.put(ip, lUserID);
                return true;
            }

            //Ehome5.0设备认证回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_AUTH: {
                // Ehome5.0设备认证回调
                strDevRegInfo.write();
                pDevRegInfo.write(0, pOutBuffer.getByteArray(0, strDevRegInfo.size()), 0, strDevRegInfo.size());
                strDevRegInfo.read();
                String szEHomeKey = haikangIsupConfig.getIsupKey(); //ISUP5.0登录校验值
                byte[] bs = szEHomeKey.getBytes();
                pInBuffer.write(0, bs, 0, szEHomeKey.length());
                log.info("Ehome5.0设备认证回调 Device auth, DeviceID is: {}", new String(strDevRegInfo.struRegInfo.byDeviceID).trim());
                break;
            }

            //Ehome5.0设备Sessionkey回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SESSIONKEY: {
                log.info("Ehome5.0设备Sessionkey回调");

                // Ehome5.0设备Sessionkey回调
                strDevRegInfo.write();
                pDevRegInfo.write(0, pOutBuffer.getByteArray(0, strDevRegInfo.size()), 0, strDevRegInfo.size());
                strDevRegInfo.read();
                HCISUPCMS.NET_EHOME_DEV_SESSIONKEY struSessionKey = new HCISUPCMS.NET_EHOME_DEV_SESSIONKEY();
                System.arraycopy(strDevRegInfo.struRegInfo.byDeviceID, 0, struSessionKey.sDeviceID, 0, strDevRegInfo.struRegInfo.byDeviceID.length);
                System.arraycopy(strDevRegInfo.struRegInfo.bySessionKey, 0, struSessionKey.sSessionKey, 0, strDevRegInfo.struRegInfo.bySessionKey.length);
                struSessionKey.write();
                Pointer pSessionKey = struSessionKey.getPointer();
                CmsService.hCEhomeCMS.NET_ECMS_SetDeviceSessionKey(pSessionKey);
                
                // 对于支持ISUP5.0的设备，报警模块也需要先认证，因此必须设置设备SessionKey
                if (AlarmService.hcEHomeAlarm != null) {
                    boolean setResult = AlarmService.hcEHomeAlarm.NET_EALARM_SetDeviceSessionKey(pSessionKey);
                    log.info("Ehome5.0设备Sessionkey设置到报警模块, DeviceID: {}, Result: {}", 
                            new String(strDevRegInfo.struRegInfo.byDeviceID).trim(), setResult);
                } else {
                    log.warn("报警服务SDK未初始化，无法设置SessionKey");
                }
                
                log.info("Ehome5.0设备Sessionkey回调 Device session key, DeviceID is: {}", new String(strDevRegInfo.struRegInfo.byDeviceID).trim());
                break;
            }

            //Ehome5.0设备重定向请求回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_REQ: {
                String dasInfo = "{\n" +
                        "    \"Type\":\"DAS\",\n" +
                        "    \"DasInfo\": {\n" +
                        "        \"Address\":\"" + haikangIsupConfig.getDasServer().getIp() + "\",\n" +
                        "        \"Domain\":\"\",\n" +
                        "        \"ServerID\":\"\",\n" +
                        "        \"Port\":" + haikangIsupConfig.getDasServer().getPort() + ",\n" +
                        "        \"UdpPort\":" + haikangIsupConfig.getDasServer().getPort() + "\n" +
                        "    }\n" +
                        "}";
                byte[] bs1 = dasInfo.getBytes();
                pInBuffer.write(0, bs1, 0, dasInfo.length());
                log.info("Ehome5.0设备DAS请求回调 Device DAS request: {}", dasInfo);
                break;
            }

            //设备下线回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_OFF: {
                log.info("设备下线回调 Device off, lUserID is: {}", lUserID);
                String ip = deviceIdMap.remove(lUserID);
                if (ip != null) {
                    lUserIDMap.remove(ip);
                }
                deviceList.removeIf(d -> Objects.equals(d.getIp(), ip));
                
                // 清理该设备相关的流资源
                cleanupDeviceResources(lUserID);
                break;
            }
            //设备地址发生变化
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_ADDRESS_CHANGED: {
                log.info("设备地址发生变化");
                break;
            }
            //设备重注册回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_REREGISTER: {
                log.info("设备重注册回调");
                break;
            }
            //设备注册心跳
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_PINGREO: {
                // 心跳日志太频繁，使用debug级别
                log.debug("设备注册心跳");
                break;
            }
            //校验密码失败
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_EHOMEKEY_ERROR: {
                log.error("校验密码失败");
                break;
            }
            //设备进入休眠状态（注：休眠状态下，设备无法做预览、回放、语音对讲、配置等CMS中的信令作响应；设备可通过NET_ECMS_WakeUp接口进行唤醒）
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SLEEP: {
                log.info("设备进入休眠状态");
                break;
            }
            //EHome5.0设备sessionkey请求回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SESSIONKEY_REQ: {
                log.info("EHome5.0设备sessionkey请求回调");
                break;
            }
            //Sessionkey交互异常
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SESSIONKEY_ERROR: {
                log.error("Sessionkey交互异常");
                break;
            }
            default:
                log.warn("FRegisterCallBack unknown type: {}", dwDataType);
                break;
        }
        return true;
    }
    
    /**
     * 清理设备相关的流资源
     */
    private void cleanupDeviceResources(int lUserID) {
        try {
            log.info("开始清理设备资源，lUserID: {}", lUserID);
            
            // 查找该设备相关的 streamKey，逐个清理完整资源
            // 先复制一份 key 列表，避免在遍历中修改集合
            java.util.List<String> streamKeysToClean = new java.util.ArrayList<>();
            for (Map.Entry<String, Integer> entry : StreamManager.streamKeyAndLuserIdMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue() == lUserID) {
                    streamKeysToClean.add(entry.getKey());
                }
            }
            
            // 逐个完整清理每个 streamKey 的资源
            for (String streamKey : streamKeysToClean) {
                try {
                    log.info("清理设备流资源，streamKey: {}", streamKey);
                    cleanupStreamResource(streamKey);
                } catch (Exception e) {
                    log.error("清理流资源失败，streamKey: {}", streamKey, e);
                }
            }
            
            // 最后清理剩余的 Map 引用，确保没有遗漏
            StreamManager.userIDandSessionMap.remove(lUserID);
            StreamManager.streamKeyAndLuserIdMap.entrySet().removeIf(entry -> entry.getValue() != null && entry.getValue() == lUserID);
            
            log.info("设备资源清理完成，lUserID: {}", lUserID);
        } catch (Exception e) {
            log.error("清理设备资源失败，lUserID: {}", lUserID, e);
        }
    }

    /**
     * 完整清理单个 streamKey 的所有资源
     */
    private void cleanupStreamResource(String streamKey) {
        try {
            Integer sessionID = StreamManager.streamKeyAndSessionIDMap.remove(streamKey);
            Integer luserId = StreamManager.streamKeyAndLuserIdMap.remove(streamKey);
            Integer previewHandle = null;
            PreviewStreamHandler previewStreamHandler = null;
            RtpServerParam rtpServerParam = StreamManager.streamKeyAndRtpServerParamMap.remove(streamKey);

            if (sessionID != null) {
                previewHandle = StreamManager.sessionIDAndPreviewHandleMap.remove(sessionID);
                previewStreamHandler = StreamManager.sessionIDAndPreviewStreamHandlerMap.remove(sessionID);
                StreamManager.luserIdAndRtpServerParamMap.remove(sessionID);
            }

            // 1. 停止预览
            try {
                if (previewHandle != null) {
                    StreamService.hCEhomeStream.NET_ESTREAM_StopPreview(previewHandle);
                }
            } catch (Exception e) {
                log.error("停止预览失败，streamKey: {}", streamKey, e);
            }

            // 2. 停止获取实时流
            try {
                if (luserId != null && sessionID != null) {
                    CmsService.hCEhomeCMS.NET_ECMS_StopGetRealStream(luserId, sessionID);
                }
            } catch (Exception e) {
                log.error("停止获取实时流失败，streamKey: {}", streamKey, e);
            }

            // 3. 关闭回调
            try {
                if (previewStreamHandler != null && previewHandle != null) {
                    previewStreamHandler.close(previewHandle);
                }
            } catch (Exception e) {
                log.error("关闭回调失败，streamKey: {}", streamKey, e);
            }

            // 4. 清理其他 Map 引用
            if (luserId != null) {
                StreamManager.userIDandSessionMap.remove(luserId);
            }
            if (previewHandle != null) {
                StreamManager.previewHandSAndSessionIDandMap.remove(previewHandle);
            }

            // 5. 清理 zlm 资源
            if (rtpServerParam != null) {
                try {
                    log.info("清理 zlm 资源，streamKey: {}, ssrc: {}", streamKey, rtpServerParam.getSsrc());
                    remoteZlmService.releaseSsrc(rtpServerParam.getMediaServerId(), rtpServerParam.getSsrc(), SecurityConstants.INNER);
                    remoteZlmService.closeRTPServer(rtpServerParam.getMediaServerId(), rtpServerParam, SecurityConstants.INNER);
                } catch (Exception e) {
                    log.error("清理 zlm 资源失败，streamKey: {}", streamKey, e);
                }
            }
        } catch (Exception e) {
            log.error("清理流资源时发生异常，streamKey: {}", streamKey, e);
        }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Device implements Serializable {

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * ip
     */
    private String ip;

    /**
     * 设备登录id
     */
    private Integer lUserID;
}
