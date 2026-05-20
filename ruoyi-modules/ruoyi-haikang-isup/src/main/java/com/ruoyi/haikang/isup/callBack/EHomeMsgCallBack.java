package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.service.haikang.alarm.HCISUPAlarm;
import com.ruoyi.haikang.isup.utils.XmlParserUtils;
import com.ruoyi.haikang.isup.xml.EventNotificationAlert;
import com.ruoyi.qs.api.RemoteQsDeviceAlarmService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class EHomeMsgCallBack implements HCISUPAlarm.EHomeMsgCallBack {

    private final HaikangIsupConfig haikangIsupConfig;
    
    @Autowired
    private RedisTemplate redisTemplate;

    public EHomeMsgCallBack(HaikangIsupConfig haikangIsupConfig) {
        this.haikangIsupConfig = haikangIsupConfig;
    }

    @Override
    public boolean invoke(int iHandle, HCISUPAlarm.NET_EHOME_ALARM_MSG pAlarmMsg, Pointer pUser) {
        Boolean alarmStorage = haikangIsupConfig.getAlarmServer().getAlarmStorage();
        if (alarmStorage == null || !alarmStorage) {
            log.debug("收到报警信息，报警存储功能未开启，跳过处理");
            return true;
        }

        // 先解析XML判断是否是关键报警
        EventNotificationAlert event = null;
        if (pAlarmMsg.dwXmlBufLen != 0) {
            HCISUPAlarm.BYTE_ARRAY strXMLData = new HCISUPAlarm.BYTE_ARRAY(pAlarmMsg.dwXmlBufLen);
            strXMLData.write();
            Pointer pPlateInfo = strXMLData.getPointer();
            pPlateInfo.write(0, pAlarmMsg.pXmlBuf.getByteArray(0, strXMLData.size()), 0, strXMLData.size());
            strXMLData.read();
            String strXML = new String(strXMLData.byValue).trim();
            event = XmlParserUtils.parseXmlToObject(strXML, EventNotificationAlert.class);
        }

        // 过滤垃圾报警，只保留关键的安全报警
        boolean isCriticalAlarm = isCriticalAlarm(pAlarmMsg.dwAlarmType, event);
        if (!isCriticalAlarm) {
            log.debug("非关键报警，跳过处理, 报警类型:{}", pAlarmMsg.dwAlarmType);
            return true;
        }

        log.info("收到报警信息，报警类型：{}, dwAlarmInfoLen: {}, dwXmlBufLen: {}, dwHttpUrlLen: {}", 
                pAlarmMsg.dwAlarmType, pAlarmMsg.dwAlarmInfoLen, pAlarmMsg.dwXmlBufLen, pAlarmMsg.dwHttpUrlLen);

        if (event != null) {
            log.info("从 pXmlBuf 解析报警事件，开始保存到数据库");
            saveAlarmToDatabase(event.getDeviceID(), event.getIpAddress(), event, null, null, null);
        }

        processAlarmData(pAlarmMsg);
        return true;
    }

    private void processAlarmData(HCISUPAlarm.NET_EHOME_ALARM_MSG pAlarmMsg) {
        int dwAlarmType = pAlarmMsg.dwAlarmType;
        if (pAlarmMsg.pHttpUrl != Pointer.NULL) {
            dwAlarmType = HCISUPAlarm.EHOME_ISAPI_ALARM;
        }

        log.info("处理报警类型：{}", dwAlarmType);
        
        switch (dwAlarmType) {
            case HCISUPAlarm.EHOME_ALARM:
                processEhomeAlarm(pAlarmMsg);
                break;
            case HCISUPAlarm.EHOME_ISAPI_ALARM:
                processEhomeIsapiAlarm(pAlarmMsg);
                break;
            case HCISUPAlarm.EHOME_ALARM_HEATMAP_REPORT:
                log.info("热图报告报警");
                processBasicAlarm(pAlarmMsg, "heatmap");
                break;
            case HCISUPAlarm.EHOME_ALARM_FACESNAP_REPORT:
                log.info("人脸抓拍报警");
                processBasicAlarm(pAlarmMsg, "facesnap");
                break;
            case HCISUPAlarm.EHOME_ALARM_GPS:
                log.info("GPS报警");
                processBasicAlarm(pAlarmMsg, "gps");
                break;
            case HCISUPAlarm.EHOME_ALARM_CID_REPORT:
                log.info("CID报告报警");
                processBasicAlarm(pAlarmMsg, "cid");
                break;
            case HCISUPAlarm.EHOME_ALARM_NOTICE_PICURL:
                log.info("图片URL通知报警");
                processAlarmWithPicUrl(pAlarmMsg);
                break;
            case HCISUPAlarm.EHOME_ALARM_NOTIFY_FAIL:
                log.info("通知失败报警");
                processBasicAlarm(pAlarmMsg, "notify_fail");
                break;
            case HCISUPAlarm.EHOME_ALARM_ACS:
                log.info("门禁报警");
                processBasicAlarm(pAlarmMsg, "acs");
                break;
            case HCISUPAlarm.EHOME_ALARM_WIRELESS_INFO:
                log.info("无线信息报警");
                processBasicAlarm(pAlarmMsg, "wireless");
                break;
            case HCISUPAlarm.EHOME_ALARM_MPDCDATA:
                log.info("MPDC数据报警");
                processBasicAlarm(pAlarmMsg, "mpdc");
                break;
            case HCISUPAlarm.EHOME_ALARM_QRCODE:
                log.info("二维码报警");
                processBasicAlarm(pAlarmMsg, "qrcode");
                break;
            case HCISUPAlarm.EHOME_ALARM_FACETEMP:
                log.info("人脸测温报警");
                processBasicAlarm(pAlarmMsg, "facetemp");
                break;
            default:
                log.warn("未知报警类型：{}，尝试通用处理", dwAlarmType);
                processBasicAlarm(pAlarmMsg, "unknown");
                break;
        }
    }
    
    private void processAlarmWithPicUrl(HCISUPAlarm.NET_EHOME_ALARM_MSG pAlarmMsg) {
        try {
            String imageUrl = "";
            if (pAlarmMsg.pHttpUrl != Pointer.NULL && pAlarmMsg.dwHttpUrlLen > 0) {
                HCISUPAlarm.BYTE_ARRAY urlData = new HCISUPAlarm.BYTE_ARRAY(pAlarmMsg.dwHttpUrlLen);
                urlData.write();
                Pointer pUrl = urlData.getPointer();
                pUrl.write(0, pAlarmMsg.pHttpUrl.getByteArray(0, urlData.size()), 0, urlData.size());
                urlData.read();
                imageUrl = new String(urlData.byValue).trim();
                log.info("获取到图片URL：{}", imageUrl);
            }
            
            String deviceId = new String(pAlarmMsg.sSerialNumber).trim();
            
            saveAlarmToDatabase(deviceId, null, null, 0, pAlarmMsg.dwAlarmType, null, imageUrl);
        } catch (Exception e) {
            log.error("处理图片URL报警失败", e);
        }
    }
    
    private void processBasicAlarm(HCISUPAlarm.NET_EHOME_ALARM_MSG pAlarmMsg, String type) {
        try {
            String deviceId = new String(pAlarmMsg.sSerialNumber).trim();
            
            if (pAlarmMsg.pXmlBuf != Pointer.NULL && pAlarmMsg.dwXmlBufLen > 0) {
                HCISUPAlarm.BYTE_ARRAY xmlData = new HCISUPAlarm.BYTE_ARRAY(pAlarmMsg.dwXmlBufLen);
                xmlData.write();
                Pointer pXml = xmlData.getPointer();
                pXml.write(0, pAlarmMsg.pXmlBuf.getByteArray(0, xmlData.size()), 0, xmlData.size());
                xmlData.read();
                String strXML = new String(xmlData.byValue).trim();
                log.info("{}报警的XML数据：{}", type, strXML);
                
                EventNotificationAlert event = XmlParserUtils.parseXmlToObject(strXML, EventNotificationAlert.class);
                if (event != null) {
                    saveAlarmToDatabase(event.getDeviceID(), event.getIpAddress(), event, null, pAlarmMsg.dwAlarmType, null);
                    return;
                }
            }
            
            if (pAlarmMsg.pAlarmInfo != Pointer.NULL) {
                HCISUPAlarm.NET_EHOME_ALARM_INFO ehomeAlarmInfo = new HCISUPAlarm.NET_EHOME_ALARM_INFO();
                ehomeAlarmInfo.write();
                Pointer pEhomeAlarmInfo = ehomeAlarmInfo.getPointer();
                pEhomeAlarmInfo.write(0, pAlarmMsg.pAlarmInfo.getByteArray(0, ehomeAlarmInfo.size()), 0, ehomeAlarmInfo.size());
                ehomeAlarmInfo.read();
                
                if (deviceId.isEmpty()) {
                    deviceId = Native.toString(ehomeAlarmInfo.szDeviceID);
                }
                int channel = ehomeAlarmInfo.dwVideoChannel;
                
                if (pAlarmMsg.pXmlBuf != Pointer.NULL && pAlarmMsg.dwXmlBufLen > 0) {
                    HCISUPAlarm.BYTE_ARRAY xmlData = new HCISUPAlarm.BYTE_ARRAY(pAlarmMsg.dwXmlBufLen);
                    xmlData.write();
                    Pointer pXml = xmlData.getPointer();
                    pXml.write(0, pAlarmMsg.pXmlBuf.getByteArray(0, xmlData.size()), 0, xmlData.size());
                    xmlData.read();
                    String strXML = new String(xmlData.byValue).trim();
                    log.info("{}报警的XML数据：{}", type, strXML);
                    
                    EventNotificationAlert event = XmlParserUtils.parseXmlToObject(strXML, EventNotificationAlert.class);
                    saveAlarmToDatabase(deviceId, null, event, channel, pAlarmMsg.dwAlarmType, ehomeAlarmInfo);
                } else {
                    saveAlarmToDatabase(deviceId, null, null, channel, pAlarmMsg.dwAlarmType, ehomeAlarmInfo);
                }
            } else {
                saveAlarmToDatabase(deviceId, null, null, 0, pAlarmMsg.dwAlarmType, null);
            }
        } catch (Exception e) {
            log.error("处理{}报警失败", type, e);
        }
    }

    private void processEhomeAlarm(HCISUPAlarm.NET_EHOME_ALARM_MSG pAlarmMsg) {
        try {
            HCISUPAlarm.NET_EHOME_ALARM_INFO ehomeAlarmInfo = new HCISUPAlarm.NET_EHOME_ALARM_INFO();
            ehomeAlarmInfo.write();
            Pointer pEhomeAlarmInfo = ehomeAlarmInfo.getPointer();
            pEhomeAlarmInfo.write(0, pAlarmMsg.pAlarmInfo.getByteArray(0, ehomeAlarmInfo.size()), 0, ehomeAlarmInfo.size());
            ehomeAlarmInfo.read();

            String deviceId = Native.toString(ehomeAlarmInfo.szDeviceID);
            int alarmType = ehomeAlarmInfo.dwAlarmType;
            int channel = ehomeAlarmInfo.dwVideoChannel;

            log.info("基础报警 - 设备ID:{}, 报警类型:{}, 通道:{}", deviceId, alarmType, channel);

            saveAlarmToDatabase(deviceId, null, null, channel, alarmType, ehomeAlarmInfo);
        } catch (Exception e) {
            log.error("处理基础报警失败", e);
        }
    }

    private void processEhomeIsapiAlarm(HCISUPAlarm.NET_EHOME_ALARM_MSG pAlarmMsg) {
        if (pAlarmMsg.pHttpUrl != Pointer.NULL && pAlarmMsg.dwHttpUrlLen > 0) {
            log.info("ISAPI报警，URL不为空，暂不处理分离数据");
            processAlarmWithPicUrl(pAlarmMsg);
            return;
        }

        if (pAlarmMsg.pAlarmInfo == Pointer.NULL) {
            return;
        }

        try {
            HCISUPAlarm.NET_EHOME_ALARM_ISAPI_INFO strIsapiInfo = new HCISUPAlarm.NET_EHOME_ALARM_ISAPI_INFO();
            strIsapiInfo.write();
            Pointer pIsapiInfo = strIsapiInfo.getPointer();
            pIsapiInfo.write(0, pAlarmMsg.pAlarmInfo.getByteArray(0, strIsapiInfo.size()), 0, strIsapiInfo.size());
            strIsapiInfo.read();

            if (strIsapiInfo.pAlarmData != Pointer.NULL && strIsapiInfo.dwAlarmDataLen > 0 && strIsapiInfo.byDataType != 0) {
                HCISUPAlarm.BYTE_ARRAY m_strIsapiData = new HCISUPAlarm.BYTE_ARRAY(strIsapiInfo.dwAlarmDataLen);
                m_strIsapiData.write();
                Pointer pPlateInfo = m_strIsapiData.getPointer();
                pPlateInfo.write(0, strIsapiInfo.pAlarmData.getByteArray(0, m_strIsapiData.size()), 0, m_strIsapiData.size());
                m_strIsapiData.read();

                String xmlContent = new String(m_strIsapiData.byValue).trim();
                log.info("ISAPI报警XML:{}", xmlContent);

                EventNotificationAlert event = XmlParserUtils.parseXmlToObject(xmlContent, EventNotificationAlert.class);
                if (event != null) {
                    log.info("解析到报警事件 - 设备ID:{}, 设备IP:{}, 事件类型:{}, 通道:{}", 
                        event.getDeviceID(), event.getIpAddress(), event.getEventType(), event.getChannelID());
                    saveAlarmToDatabase(event.getDeviceID(), event.getIpAddress(), event, null, 0, null);
                }
            }
        } catch (Exception e) {
            log.error("处理ISAPI报警失败", e);
        }
    }

    private void saveAlarmToDatabase(String deviceId, String deviceIp, EventNotificationAlert event, 
                                     Integer channel, Integer alarmType, HCISUPAlarm.NET_EHOME_ALARM_INFO ehomeAlarmInfo) {
        saveAlarmToDatabase(deviceId, deviceIp, event, channel, alarmType, ehomeAlarmInfo, null);
    }

    private void saveAlarmToDatabase(String deviceId, String deviceIp, EventNotificationAlert event, 
                                     Integer channel, Integer alarmType, HCISUPAlarm.NET_EHOME_ALARM_INFO ehomeAlarmInfo, String imageUrl) {
        try {
            RemoteQsDeviceAlarmService remoteQsDeviceAlarmService = SpringUtils.getBean(RemoteQsDeviceAlarmService.class);
            RemoteQsDeviceService remoteQsDeviceService = SpringUtils.getBean(RemoteQsDeviceService.class);

            if (remoteQsDeviceAlarmService == null || remoteQsDeviceService == null) {
                log.warn("报警服务未初始化，跳过报警处理");
                return;
            }

            QsDevice qsDevice = null;
            
            // 优先用 deviceCode（设备ID）匹配
            if (deviceId != null && !deviceId.isEmpty()) {
                R<QsDevice> result = remoteQsDeviceService.getDeviceByDeviceCode("haikang_isup_" + deviceId, SecurityConstants.INNER);
                
                if (R.isSuccess(result) && result.getData() != null) {
                    qsDevice = result.getData();
                }
            }

            if (qsDevice == null) {
                log.warn("未找到设备信息 - 设备ID:{}, 设备IP:{}", deviceId, deviceIp);
                return;
            }

            QsDeviceAlarm alarm = new QsDeviceAlarm();
            alarm.setDeviceId(qsDevice.getId());
            alarm.setDeviceCode(qsDevice.getDeviceCode());
            alarm.setDeviceName(qsDevice.getDeviceName());
            alarm.setSdkType("hik_isup");

            if (channel != null) {
                alarm.setChannel(channel);
            } else if (event != null && event.getChannelID() != null) {
                try {
                    alarm.setChannel(Integer.parseInt(event.getChannelID()));
                } catch (NumberFormatException e) {
                    alarm.setChannel(0);
                }
            }

            if (event != null) {
                alarm.setAlarmType(convertAlarmType(event.getEventType()));
                alarm.setAlarmContent(convertAlarmDescription(event.getEventType(), event.getEventDescription()));
            } else {
                alarm.setAlarmType(convertBasicAlarmType(alarmType));
                alarm.setAlarmContent("基础报警:" + convertBasicAlarmDescription(alarmType));
            }

            alarm.setAlarmLevel("medium");
            alarm.setAlarmTime(new Date());
            alarm.setHandled(0);
            
            // 设置报警图片
            if (imageUrl != null && !imageUrl.isEmpty()) {
                alarm.setImageUrl(imageUrl);
            }

            log.info("保存报警 - 设备ID:{}, 设备名称:{}, 报警类型:{}, 通道:{}, imageUrl:{}",
                alarm.getDeviceId(), alarm.getDeviceName(), alarm.getAlarmType(), alarm.getChannel(), imageUrl);

            R<Long> result = remoteQsDeviceAlarmService.add(alarm, SecurityConstants.INNER);
            if (result.getCode() != 200) {
                log.error("保存报警失败:{}", result.getMsg());
            } else {
                Long alarmId = result.getData();
                log.info("保存报警成功，alarmId: {}", alarmId);
                
                // 把报警ID存入Redis，同时用多种key，增加匹配成功率
                // 1. 用原始设备ID
                if (deviceId != null && !deviceId.isEmpty()) {
                    String redisKey1 = "isup_alarm_pic_list:" + deviceId;
                    redisTemplate.opsForList().leftPush(redisKey1, alarmId);
                    redisTemplate.opsForList().trim(redisKey1, 0, 19); // 增加容量
                    redisTemplate.expire(redisKey1, 30, TimeUnit.MINUTES); // 延长过期时间
                    log.info("已将报警ID存入Redis(设备ID), key: {}, alarmId: {}", redisKey1, alarmId);
                }
                
                // 2. 用设备Code（带haikang_isup_前缀的）
                if (qsDevice.getDeviceCode() != null && !qsDevice.getDeviceCode().isEmpty()) {
                    String redisKey2 = "isup_alarm_pic_list:" + qsDevice.getDeviceCode();
                    redisTemplate.opsForList().leftPush(redisKey2, alarmId);
                    redisTemplate.opsForList().trim(redisKey2, 0, 19);
                    redisTemplate.expire(redisKey2, 30, TimeUnit.MINUTES);
                    log.info("已将报警ID存入Redis(设备Code), key: {}, alarmId: {}", redisKey2, alarmId);
                }
                
                // 3. 也保存一份去掉前缀的设备Code，增加匹配成功率
                if (qsDevice.getDeviceCode() != null && qsDevice.getDeviceCode().startsWith("haikang_isup_")) {
                    String simpleCode = qsDevice.getDeviceCode().replace("haikang_isup_", "");
                    String redisKey3 = "isup_alarm_pic_list:" + simpleCode;
                    redisTemplate.opsForList().leftPush(redisKey3, alarmId);
                    redisTemplate.opsForList().trim(redisKey3, 0, 19);
                    redisTemplate.expire(redisKey3, 30, TimeUnit.MINUTES);
                    log.info("已将报警ID存入Redis(简化设备Code), key: {}, alarmId: {}", redisKey3, alarmId);
                }
                
                // 4. 保存全局key，作为最后的备选
                String globalKey = "isup_alarm_pic_list:global";
                redisTemplate.opsForList().leftPush(globalKey, alarmId);
                redisTemplate.opsForList().trim(globalKey, 0, 49); // 全局key容量更大
                redisTemplate.expire(globalKey, 30, TimeUnit.MINUTES);
                log.info("已将报警ID存入Redis(全局), key: {}, alarmId: {}", globalKey, alarmId);
            }
        } catch (Exception e) {
            log.error("保存报警到数据库失败", e);
        }
    }

    private String convertAlarmType(String eventType) {
        if (eventType == null) {
            return "other";
        }
        switch (eventType.toLowerCase()) {
            case "vmd":
            case "motion_detection":
                return "motion";
            case "videolost":
            case "video_loss":
                return "video_loss";
            case "shelteralarm":
            case "video_cover":
                return "cover";
            case "linedetection":
            case "cross_line_detection":
                return "cross_line";
            case "fielddetection":
            case "area_intrusion":
                return "enter_area";
            case "enter_area":
                return "enter_area";
            case "leave_area":
                return "leave_area";
            case "object_removal":
            case "removedetection":
                return "object_remove";
            case "object_leave":
            case "parkingdetection":
                return "object_leave";
            case "facedetection":
            case "face_detection":
                return "face_detection";
            default:
                return "other";
        }
    }

    private String convertAlarmDescription(String eventType, String eventDescription) {
        if (eventType == null) {
            return eventDescription != null ? eventDescription : "未知报警";
        }
        switch (eventType.toLowerCase()) {
            case "vmd":
            case "motion_detection":
                return "检测到移动目标";
            case "videolost":
            case "video_loss":
                return "视频信号丢失";
            case "shelteralarm":
            case "video_cover":
                return "检测到视频遮挡";
            case "linedetection":
            case "cross_line_detection":
                return "检测到越界行为";
            case "fielddetection":
            case "area_intrusion":
                return "检测到区域入侵";
            case "enter_area":
                return "检测到目标进入区域";
            case "leave_area":
                return "检测到目标离开区域";
            case "object_removal":
            case "removedetection":
                return "检测到物品被移除";
            case "object_leave":
            case "parkingdetection":
                return "检测到物品遗留";
            case "facedetection":
            case "face_detection":
                return "检测到人脸";
            default:
                return eventDescription != null ? eventDescription : "其他报警";
        }
    }

    private String convertBasicAlarmType(int alarmType) {
        // 基础报警类型直接返回数字的字符串形式
        return String.valueOf(alarmType);
    }

    private String convertBasicAlarmDescription(int alarmType) {
        switch (alarmType) {
            case 0: // EHOME_ALARM_UNKNOWN
                return "未知报警";
            case 1: // EHOME_ALARM
                return "基础报警";
            case 2: // EHOME_ALARM_HEATMAP_REPORT
                return "热图报告";
            case 3: // EHOME_ALARM_FACESNAP_REPORT
                return "人脸抓拍";
            case 4: // EHOME_ALARM_GPS
                return "GPS信息";
            case 5: // EHOME_ALARM_CID_REPORT
                return "CID报告";
            case 6: // EHOME_ALARM_NOTICE_PICURL
                return "图片URL通知";
            case 7: // EHOME_ALARM_NOTIFY_FAIL
                return "通知失败";
            case 9: // EHOME_ALARM_SELFDEFINE
                return "自定义报警";
            case 10: // EHOME_ALARM_DEVICE_NETSWITCH_REPORT
                return "网络切换报告";
            case 11: // EHOME_ALARM_ACS
                return "门禁报警";
            case 12: // EHOME_ALARM_WIRELESS_INFO
                return "无线信息";
            case 13: // EHOME_ISAPI_ALARM
                return "ISAPI报警";
            case 14: // EHOME_INFO_RELEASE_PRIVATE
                return "私有信息发布";
            case 15: // EHOME_ALARM_MPDCDATA
                return "客流数据";
            case 20: // EHOME_ALARM_QRCODE
                return "二维码";
            case 21: // EHOME_ALARM_FACETEMP
                return "人脸测温";
            case 700: // ALARM_TYPE_DEV_CHANGED_STATUS
                return "设备状态变化";
            case 701: // ALARM_TYPE_CHAN_CHANGED_STATUS
                return "通道状态变化";
            case 702: // ALARM_TYPE_HD_CHANGED_STATUS
                return "硬盘状态变化";
            case 703: // ALARM_TYPE_DEV_TIMING_STATUS
                return "设备定时状态";
            case 704: // ALARM_TYPE_CHAN_TIMING_STATUS
                return "通道定时状态";
            case 705: // ALARM_TYPE_HD_TIMING_STATUS
                return "硬盘定时状态";
            case 706: // ALARM_TYPE_RECORD_ABNORMAL
                return "录像异常";
            default:
                return "其他报警(" + alarmType + ")";
        }
    }

    // 判断是否是关键的安全报警
    private boolean isCriticalAlarm(int alarmType, EventNotificationAlert event) {
        // 先检查event中的报警类型
        if (event != null && event.getEventType() != null) {
            String eventType = event.getEventType().toLowerCase();
            switch (eventType) {
                case "vmd":
                case "motion_detection":
                case "shelteralarm":
                case "video_cover":
                case "linedetection":
                case "cross_line_detection":
                case "fielddetection":
                case "area_intrusion":
                case "enter_area":
                case "leave_area":
                case "object_removal":
                case "removedetection":
                case "object_leave":
                case "parkingdetection":
                case "facedetection":
                case "face_detection":
                    return true;
            }
        }

        // 检查基础报警类型
        switch (alarmType) {
            case 1: // EHOME_ALARM - 基础报警
            case 3: // EHOME_ALARM_FACESNAP_REPORT - 人脸抓拍
            case 13: // EHOME_ISAPI_ALARM - ISAPI报警
            case 6: // EHOME_ALARM_NOTICE_PICURL - 图片URL通知（可能包含安全相关图片）
                return true;
            default:
                return false;
        }
    }
}
