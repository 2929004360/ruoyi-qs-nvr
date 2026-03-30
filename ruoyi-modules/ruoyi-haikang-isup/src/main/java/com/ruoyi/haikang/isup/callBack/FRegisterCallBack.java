package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.service.haikang.cms.CmsService;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.sun.jna.Pointer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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

    public static final ConcurrentHashMap<Integer, String> deviceIdMap = new ConcurrentHashMap<>(16);

    public static final ConcurrentHashMap<String, Integer> lUserIDMap = new ConcurrentHashMap<>(16);

    public static final CopyOnWriteArrayList<Device> deviceList = new CopyOnWriteArrayList<>();

    public boolean invoke(int lUserID, int dwDataType, Pointer pOutBuffer, int dwOutLen, Pointer pInBuffer, int dwInLen, Pointer pUser) {
        log.info("设备注册状态回调:" + dwDataType + ", lUserID:" + lUserID);
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
                device.setDeviceId(deviceId);
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
                System.out.println("Ehome5.0设备Sessionkey回调");

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
                log.info("Ehome5.0设备Sessionkey回调 Device session key, DeviceID is: {}", new String(strDevRegInfo.struRegInfo.byDeviceID).trim());

//                AlarmService.hcEHomeAlarm.NET_EALARM_SetDeviceSessionKey(pSessionKey);
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
                        "        \"UdpPort\":\n" +
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
                deviceList.removeIf(d -> Objects.equals(d.getIp(), ip));
                break;
            }
            //设备地址发生变化
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_ADDRESS_CHANGED: {
                System.out.println("设备地址发生变化");
                break;
            }
            //设备重注册回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_REREGISTER: {
                System.out.println("设备重注册回调");
                break;
            }
            //设备注册心跳
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_PINGREO: {
                System.out.println("设备注册心跳");
                break;
            }
            //校验密码失败
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_DAS_EHOMEKEY_ERROR: {
                System.out.println("校验密码失败");
                break;
            }
            //设备进入休眠状态（注：休眠状态下，设备无法做预览、回放、语音对讲、配置等CMS中的信令作响应；设备可通过NET_ECMS_WakeUp接口进行唤醒）
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SLEEP: {
                System.out.println("设备进入休眠状态");
                break;
            }
            //EHome5.0设备sessionkey请求回调
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SESSIONKEY_REQ: {
                System.out.println("EHome5.0设备sessionkey请求回调");
                break;
            }
            //Sessionkey交互异常
            case HCISUPCMS.EHOME_REGISTER_TYPE.ENUM_DEV_SESSIONKEY_ERROR: {
                System.out.println("Sessionkey交互异常");
                break;
            }
            default:
                System.out.println("FRegisterCallBack default type:" + dwDataType);
                break;
        }
        return true;
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
