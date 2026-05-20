package com.ruoyi.haikang.callback;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.qs.api.RemoteQsDeviceAlarmService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class AlarmDataParse {

    public static void alarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        log.info("收到报警信息，报警类型：{}, dwBufLen: {}", Integer.toHexString(lCommand), dwBufLen);

        String deviceIp = null;
        String deviceId = null;

        if (pAlarmer.byDeviceIPValid == 1) {
            deviceIp = new String(pAlarmer.sDeviceIP).trim();
        }

        if (pAlarmer.bySerialValid == 1) {
            deviceId = new String(pAlarmer.sSerialNumber).trim();
        }

        String alarmTypeDesc = getAlarmTypeDescription(lCommand);
        log.info("报警信息 - 设备ID:{}, 设备IP:{}, 报警类型:{}, 十六进制:0x{}, 十进制:{}", 
                 deviceId, deviceIp, alarmTypeDesc, Integer.toHexString(lCommand), lCommand);

        switch (lCommand) {
            case HCNetSDK.COMM_ALARM:
                handleCommonAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ALARM_V30:
                handleCommonAlarmV30(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ISAPI_ALARM:
                handleIsapiAlarm(pAlarmInfo, dwBufLen, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ALARM_RULE:
                handleVcaAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_VCA_ALARM:
                handleVcaJsonAlarm(pAlarmInfo, dwBufLen, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ITS_PLATE_RESULT:
                handlePlateResultAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ALARM_TFS:
                handleTfsAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ALARM_AID_V41:
                handleAidAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ALARM_TPS_V41:
            case HCNetSDK.COMM_ALARM_TPS_STATISTICS:
            case HCNetSDK.COMM_ALARM_TPS_REAL_TIME:
                handleTpsAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_ALARM_ACS:
                handleAcsAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_SWITCH_ALARM:
                handleSwitchAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            case HCNetSDK.COMM_SENSOR_ALARM:
                handleSensorAlarm(pAlarmInfo, deviceId, deviceIp, lCommand);
                break;
            default:
                log.info("其他报警类型，暂不处理：{}", Integer.toHexString(lCommand));
                break;
        }
    }

    private static String getAlarmTypeDescription(int commandType) {
        switch (commandType) {
            case HCNetSDK.COMM_ALARM:
                return "基础报警 (0x1100)";
            case HCNetSDK.COMM_ALARM_V30:
                return "基础报警V30 (0x4000)";
            case HCNetSDK.COMM_ISAPI_ALARM:
                return "ISAPI协议报警 (0x6009)";
            case HCNetSDK.COMM_ALARM_RULE:
                return "智能检测规则报警 (0x1102)";
            case HCNetSDK.COMM_VCA_ALARM:
                return "智能检测通用报警 (0x4993)";
            case HCNetSDK.COMM_ITS_PLATE_RESULT:
                return "交通抓拍终端图片上传 (0x3050)";
            case HCNetSDK.COMM_ALARM_TFS:
                return "交通取证报警 (0x1113)";
            case HCNetSDK.COMM_ALARM_AID_V41:
                return "交通事件检测报警 (0x1115)";
            case HCNetSDK.COMM_ALARM_TPS_V41:
                return "交通事件报警扩展 (0x1114)";
            case HCNetSDK.COMM_ALARM_TPS_STATISTICS:
                return "交通数据统计报警 (0x3082)";
            case HCNetSDK.COMM_ALARM_TPS_REAL_TIME:
                return "交通数据实时报警 (0x3081)";
            case HCNetSDK.COMM_ALARM_ACS:
                return "门禁主机报警 (0x5002)";
            case HCNetSDK.COMM_SWITCH_ALARM:
                return "开关量报警 (0x1122)";
            case HCNetSDK.COMM_SENSOR_ALARM:
                return "模拟量报警 (0x1121)";
            default:
                return "未知报警类型";
        }
    }

    private static void handleCommonAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_ALARMINFO alarmInfo = new HCNetSDK.NET_DVR_ALARMINFO();
            alarmInfo.write();
            Pointer pAlarmInfoPointer = alarmInfo.getPointer();
            pAlarmInfoPointer.write(0, pAlarmInfo.getByteArray(0, alarmInfo.size()), 0, alarmInfo.size());
            alarmInfo.read();

            // 找到第一个有效的通道号
            Integer channel = null;
            if (alarmInfo.dwChannel != null && alarmInfo.dwChannel.length > 0) {
                for (int i = 0; i < alarmInfo.dwChannel.length; i++) {
                    if (alarmInfo.dwChannel[i] != 0) {
                        channel = i + 1; // 通道号从1开始
                        break;
                    }
                }
            }

            String alarmType = convertBasicAlarmType(alarmInfo.dwAlarmType);
            String alarmContent = getAlarmDescription(alarmInfo.dwAlarmType);

            log.info("基础报警 - 报警类型:{}, 转换类型:{}, 通道:{}", alarmInfo.dwAlarmType, alarmType, channel);

            saveAlarmToDatabaseWithBasicType(deviceId, deviceIp, null, channel, alarmInfo.dwAlarmType, alarmContent, null);
        } catch (Exception e) {
            log.error("处理基础报警失败", e);
        }
    }

    private static void handleCommonAlarmV30(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_ALARMINFO_V30 alarmInfo = new HCNetSDK.NET_DVR_ALARMINFO_V30();
            alarmInfo.write();
            Pointer pAlarmInfoPointer = alarmInfo.getPointer();
            pAlarmInfoPointer.write(0, pAlarmInfo.getByteArray(0, alarmInfo.size()), 0, alarmInfo.size());
            alarmInfo.read();

            // 找到第一个有效的通道号
            Integer channel = null;
            if (alarmInfo.byChannel != null && alarmInfo.byChannel.length > 0) {
                for (int i = 0; i < alarmInfo.byChannel.length; i++) {
                    if (alarmInfo.byChannel[i] != 0) {
                        channel = i + 1; // 通道号从1开始
                        break;
                    }
                }
            }

            String alarmType = convertBasicAlarmType(alarmInfo.dwAlarmType);
            String alarmContent = getAlarmDescription(alarmInfo.dwAlarmType);

            log.info("基础报警V30 - 报警类型:{}, 转换类型:{}, 通道:{}", alarmInfo.dwAlarmType, alarmType, channel);

            saveAlarmToDatabaseWithBasicType(deviceId, deviceIp, null, channel, alarmInfo.dwAlarmType, alarmContent, null);
        } catch (Exception e) {
            log.error("处理基础报警V30失败", e);
        }
    }

    private static void handleIsapiAlarm(Pointer pAlarmInfo, int dwBufLen, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_ALARM_ISAPI_INFO isapiInfo = new HCNetSDK.NET_DVR_ALARM_ISAPI_INFO();
            isapiInfo.write();
            Pointer pIsapiInfo = isapiInfo.getPointer();
            pIsapiInfo.write(0, pAlarmInfo.getByteArray(0, isapiInfo.size()), 0, isapiInfo.size());
            isapiInfo.read();

            log.info("ISAPI报警 - 数据类型:{}, 图片数量:{}", isapiInfo.byDataType, isapiInfo.byPicturesNumber);

            String strXML = null;
            if (isapiInfo.pAlarmData != Pointer.NULL && isapiInfo.dwAlarmDataLen > 0) {
                HCNetSDK.BYTE_ARRAY m_strIsapiData = new HCNetSDK.BYTE_ARRAY(isapiInfo.dwAlarmDataLen);
                m_strIsapiData.write();
                Pointer pPlateInfo = m_strIsapiData.getPointer();
                pPlateInfo.write(0, isapiInfo.pAlarmData.getByteArray(0, m_strIsapiData.size()), 0, m_strIsapiData.size());
                m_strIsapiData.read();

                strXML = new String(m_strIsapiData.byValue).trim();
                log.info("ISAPI报警数据：{}", strXML);
            }

            // 保存报警图片
            String imageUrl = null;
            if (isapiInfo.byPicturesNumber > 0 && isapiInfo.pPicPackData != Pointer.NULL) {
                try {
                    int offset = 0;
                    for (int i = 0; i < isapiInfo.byPicturesNumber; i++) {
                        // 读取图片数据结构
                        HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA picData = new HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA();
                        picData.write();
                        Pointer pPicData = picData.getPointer();
                        
                        // 从 pPicPackData 偏移位置读取
                        byte[] structData = isapiInfo.pPicPackData.getByteArray(offset, picData.size());
                        pPicData.write(0, structData, 0, structData.length);
                        picData.read();
                        
                        if (picData.dwPicLen > 0 && picData.pPicData != Pointer.NULL) {
                            byte[] imageData = picData.pPicData.getByteArray(0, picData.dwPicLen);
                            if (imageData != null && imageData.length > 0) {
                                imageUrl = saveAlarmImage(imageData, deviceIp, "isapi_" + i);
                                String filename = new String(picData.szFilename).trim();
                                log.info("保存第{}张ISAPI报警图片成功 - 文件名:{}, 类型:{}", i, filename, picData.byPicType);
                                break; // 只保存第一张图片
                            }
                        }
                        
                        offset += picData.size();
                    }
                } catch (Exception e) {
                    log.warn("解析ISAPI报警图片失败: " + e.getMessage());
                }
            }

            saveAlarmToDatabase(deviceId, deviceIp, strXML, null, lCommand, "ISAPI报警", imageUrl);
        } catch (Exception e) {
            log.error("处理ISAPI报警失败", e);
        }
    }

    private static void handleVcaAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_VCA_RULE_ALARM vcaAlarm = new HCNetSDK.NET_VCA_RULE_ALARM();
            vcaAlarm.write();
            Pointer pVCAInfo = vcaAlarm.getPointer();
            pVCAInfo.write(0, pAlarmInfo.getByteArray(0, vcaAlarm.size()), 0, vcaAlarm.size());
            vcaAlarm.read();

            String eventType = getVcaEventType(vcaAlarm.struRuleInfo.wEventTypeEx);
            String eventDesc = getVcaEventDescription(vcaAlarm.struRuleInfo.wEventTypeEx);
            String alarmType = convertVcaAlarmType(vcaAlarm.struRuleInfo.wEventTypeEx);

            // 获取通道号
            Integer channel = null;
            if (vcaAlarm.struDevInfo != null && vcaAlarm.struDevInfo.byChannel >= 0) {
                channel = (int) vcaAlarm.struDevInfo.byChannel;
            }

            log.info("智能报警 - 事件类型:{}, 转换类型:{}, 事件描述:{}, 通道:{}, 图片长度:{}", eventType, alarmType, eventDesc, channel, vcaAlarm.dwPicDataLen);

            // 保存报警图片
            String imageUrl = null;
            if (vcaAlarm.dwPicDataLen > 0 && vcaAlarm.pImage != null) {
                byte[] imageData = vcaAlarm.pImage.getByteArray(0, vcaAlarm.dwPicDataLen);
                if (imageData != null && imageData.length > 0) {
                    imageUrl = saveAlarmImage(imageData, deviceIp, "vca");
                    log.info("保存智能报警图片成功 - 图片类型:{}", vcaAlarm.byPicType);
                }
            }

            // 这里我们需要传入事件类型来让 saveAlarmToDatabase 正确处理
            saveAlarmToDatabaseWithVcaType(deviceId, deviceIp, null, channel, lCommand, eventDesc, vcaAlarm.struRuleInfo.wEventTypeEx, imageUrl);
        } catch (Exception e) {
            log.error("处理智能报警失败", e);
        }
    }

    private static void handleVcaJsonAlarm(Pointer pAlarmInfo, int dwBufLen, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.BYTE_ARRAY alarmData = new HCNetSDK.BYTE_ARRAY(dwBufLen);
            alarmData.write();
            Pointer pAlarmData = alarmData.getPointer();
            pAlarmData.write(0, pAlarmInfo.getByteArray(0, alarmData.size()), 0, alarmData.size());
            alarmData.read();

            String strJSON = new String(alarmData.byValue).trim();
            log.info("智能JSON报警数据：{}", strJSON);

            saveAlarmToDatabase(deviceId, deviceIp, strJSON, null, lCommand, "智能JSON报警");
        } catch (Exception e) {
            log.error("处理智能JSON报警失败", e);
        }
    }

    private static void saveAlarmToDatabase(String deviceId, String deviceIp, String alarmData, Integer channel, Integer alarmType, String alarmContent) {
        saveAlarmToDatabase(deviceId, deviceIp, alarmData, channel, alarmType, alarmContent, null);
    }

    private static void saveAlarmToDatabase(String deviceId, String deviceIp, String alarmData, Integer channel, Integer alarmType, String alarmContent, String imageUrl) {
        saveAlarmToDatabaseInternal(deviceId, deviceIp, alarmData, channel, alarmType, alarmContent, imageUrl, null, null);
    }

    private static void saveAlarmToDatabaseWithVcaType(String deviceId, String deviceIp, String alarmData, Integer channel, Integer alarmType, String alarmContent, Short vcaEventType, String imageUrl) {
        saveAlarmToDatabaseInternal(deviceId, deviceIp, alarmData, channel, alarmType, alarmContent, imageUrl, vcaEventType, null);
    }

    private static void saveAlarmToDatabaseWithBasicType(String deviceId, String deviceIp, String alarmData, Integer channel, Integer basicAlarmType, String alarmContent, String imageUrl) {
        saveAlarmToDatabaseInternal(deviceId, deviceIp, alarmData, channel, null, alarmContent, imageUrl, null, basicAlarmType);
    }

    private static void saveAlarmToDatabaseInternal(String deviceId, String deviceIp, String alarmData, Integer channel, Integer alarmType, String alarmContent, String imageUrl, Short vcaEventType, Integer basicAlarmType) {
        try {
            RemoteQsDeviceAlarmService remoteQsDeviceAlarmService = SpringUtils.getBean(RemoteQsDeviceAlarmService.class);

            if (remoteQsDeviceAlarmService == null) {
                log.warn("报警服务未初始化，跳过报警处理");
                return;
            }

            QsDevice qsDevice = null;

            // 优先从本地 map 中获取设备信息
            if (deviceIp != null && !deviceIp.isEmpty()) {
                qsDevice = com.ruoyi.haikang.service.impl.HaiKangServiceImpl.getQsDeviceMap().get(deviceIp);
            }

            if (qsDevice == null) {
                log.warn("未找到设备信息 - 设备ID:{}, 设备IP:{}", deviceId, deviceIp);
                return;
            }

            QsDeviceAlarm alarm = new QsDeviceAlarm();
            alarm.setDeviceId(qsDevice.getId());
            alarm.setDeviceCode(qsDevice.getDeviceCode());
            alarm.setDeviceName(qsDevice.getDeviceName());
            alarm.setSdkType("hik");

            if (channel != null) {
                alarm.setChannel(channel);
            }

            // 根据不同的报警类型调用相应的转换方法
            String convertedAlarmType;
            if (vcaEventType != null) {
                convertedAlarmType = convertVcaAlarmType(vcaEventType);
            } else if (basicAlarmType != null) {
                convertedAlarmType = convertBasicAlarmType(basicAlarmType);
            } else {
                convertedAlarmType = convertAlarmType(alarmType);
            }

            alarm.setAlarmType(convertedAlarmType);
            alarm.setAlarmContent(alarmContent);
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
                log.info("保存报警成功，alarmId: {}", result.getData());
            }
        } catch (Exception e) {
            log.error("保存报警到数据库失败", e);
        }
    }
    
    // 保存报警图片到文件系统并返回URL
    private static String saveAlarmImage(byte[] imageData, String deviceIp, String typeSuffix) {
        try {
            com.ruoyi.haikang.service.impl.HaiKangServiceImpl haiKangService = SpringUtils.getBean(com.ruoyi.haikang.service.impl.HaiKangServiceImpl.class);
            
            // 获取配置
            java.lang.reflect.Field filePathField = com.ruoyi.haikang.service.impl.HaiKangServiceImpl.class.getDeclaredField("filePath");
            filePathField.setAccessible(true);
            String filePath = (String) filePathField.get(haiKangService);
            
            java.lang.reflect.Field fileDomainField = com.ruoyi.haikang.service.impl.HaiKangServiceImpl.class.getDeclaredField("fileDomain");
            fileDomainField.setAccessible(true);
            String fileDomain = (String) fileDomainField.get(haiKangService);
            
            java.lang.reflect.Field filePrefixField = com.ruoyi.haikang.service.impl.HaiKangServiceImpl.class.getDeclaredField("filePrefix");
            filePrefixField.setAccessible(true);
            String filePrefix = (String) filePrefixField.get(haiKangService);
            
            // 创建目录
            String saveDir = filePath + "/haikang_alarm";
            java.io.File dir = new java.io.File(saveDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 生成文件名
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = "alarm_" + deviceIp + "_" + sdf.format(new java.util.Date()) + "_" + typeSuffix + ".jpg";
            String localFilePath = saveDir + "/" + fileName;
            String fileUrl = fileDomain + filePrefix + "/haikang_alarm/" + fileName;
            
            // 保存文件
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(localFilePath)) {
                fos.write(imageData);
            }
            
            log.info("报警图片保存成功 - localPath:{}, fileUrl:{}", localFilePath, fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("保存报警图片失败", e);
            return null;
        }
    }

    private static String convertAlarmType(Integer alarmType) {
        if (alarmType == null) {
            return "other";
        }
        switch (alarmType) {
            case HCNetSDK.COMM_ITS_PLATE_RESULT:
                return "plate";
            case HCNetSDK.COMM_ALARM_TFS:
                return "tfs";
            case HCNetSDK.COMM_ALARM_AID_V41:
                return "aid";
            case HCNetSDK.COMM_ALARM_TPS_V41:
            case HCNetSDK.COMM_ALARM_TPS_STATISTICS:
            case HCNetSDK.COMM_ALARM_TPS_REAL_TIME:
                return "tps";
            case HCNetSDK.COMM_ALARM_ACS:
                return "acs";
            default:
                return "other";
        }
    }

    private static String convertBasicAlarmType(int basicAlarmType) {
        switch (basicAlarmType) {
            case 3: // 移动侦测
                return "motion";
            case 2: // 信号丢失
            case 9: // 视频丢失
                return "video_loss";
            case 6: // 遮挡报警
                return "cover";
            default:
                return String.valueOf(basicAlarmType);
        }
    }

    private static String convertVcaAlarmType(short eventTypeEx) {
        switch (eventTypeEx) {
            case 1: // 越界检测
                return "cross_line";
            case 2: // 进入区域
                return "enter_area";
            case 3: // 离开区域
                return "leave_area";
            case 4: // 周界入侵
                return "intrusion";
            case 5: // 徘徊检测
                return "loitering";
            case 8: // 快速移动
                return "fast_move";
            case 15: // 离岗检测
                return "leave_position";
            case 20: // 倒地检测
                return "fall_down";
            case 44: // 玩手机检测
                return "play_cellphone";
            case 45: // 持续检测
                return "persistent_detection";
            default:
                return String.valueOf(eventTypeEx);
        }
    }

    private static String getAlarmDescription(int alarmType) {
        switch (alarmType) {
            case 0:
                return "信号量报警";
            case 1:
                return "硬盘满";
            case 2:
                return "信号丢失";
            case 3:
                return "移动侦测";
            case 4:
                return "硬盘未格式化";
            case 5:
                return "硬盘出错";
            case 6:
                return "遮挡报警";
            case 7:
                return "制式不匹配";
            case 8:
                return "非法访问";
            case 9:
                return "视频丢失";
            case 10:
                return "视频异常";
            default:
                return "其他报警";
        }
    }

    private static String getVcaEventType(short eventTypeEx) {
        switch (eventTypeEx) {
            case 1:
                return "cross_line";
            case 2:
                return "enter_area";
            case 3:
                return "leave_area";
            case 4:
                return "intrusion";
            case 5:
                return "loitering";
            case 8:
                return "fast_move";
            case 15:
                return "leave_position";
            case 20:
                return "fall_down";
            case 44:
                return "play_cellphone";
            case 45:
                return "persistent_detection";
            default:
                return String.valueOf(eventTypeEx);
        }
    }

    private static String getVcaEventDescription(short eventTypeEx) {
        switch (eventTypeEx) {
            case 1:
                return "检测到越界行为";
            case 2:
                return "检测到目标进入区域";
            case 3:
                return "检测到目标离开区域";
            case 4:
                return "检测到周界入侵";
            case 5:
                return "检测到徘徊行为";
            case 8:
                return "检测到快速移动";
            case 15:
                return "检测到离岗行为";
            case 20:
                return "检测到倒地行为";
            case 44:
                return "检测到玩手机行为";
            case 45:
                return "持续检测";
            default:
                return "智能报警:" + eventTypeEx;
        }
    }

    private static void handlePlateResultAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_ITS_PLATE_RESULT plateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
            plateResult.write();
            Pointer pPlateResult = plateResult.getPointer();
            pPlateResult.write(0, pAlarmInfo.getByteArray(0, plateResult.size()), 0, plateResult.size());
            plateResult.read();

            String plateNumber = new String(plateResult.struPlateInfo.sLicense, "GBK").trim();
            byte vehicleType = plateResult.byVehicleType;
            String vehicleTypeDesc = getVehicleTypeDescription(vehicleType);

            // 获取通道号
            Integer channel = null;
            if (plateResult.byChanIndex >= 0) {
                channel = (int) plateResult.byChanIndex;
                if (plateResult.byChanIndexEx >= 0) {
                    channel = channel + (int) plateResult.byChanIndexEx * 256;
                }
            }

            log.info("交通抓拍结果 - 车牌号:{}, 车辆类型:{}, 通道:{}, 图片数量:{}", plateNumber, vehicleTypeDesc, channel, plateResult.dwPicNum);

            // 保存报警图片
            String imageUrl = null;
            if (plateResult.dwPicNum > 0 && plateResult.struPicInfo != null) {
                for (int i = 0; i < plateResult.dwPicNum && i < plateResult.struPicInfo.length; i++) {
                    HCNetSDK.NET_ITS_PICTURE_INFO picInfo = plateResult.struPicInfo[i];
                    if (picInfo.dwDataLen > 0 && picInfo.pBuffer != null) {
                        byte[] imageData = picInfo.pBuffer.getByteArray(0, picInfo.dwDataLen);
                        if (imageData != null && imageData.length > 0) {
                            imageUrl = saveAlarmImage(imageData, deviceIp, "plate_" + i);
                            log.info("保存第{}张交通抓拍图片成功 - 图片类型:{}", i, picInfo.byType);
                            break; // 只保存第一张图片
                        }
                    }
                }
            }

            String alarmContent = "交通抓拍 - 车牌号:" + plateNumber + ", 车辆类型:" + vehicleTypeDesc;
            saveAlarmToDatabase(deviceId, deviceIp, null, channel, lCommand, alarmContent, imageUrl);
        } catch (Exception e) {
            log.error("处理交通抓拍结果报警失败", e);
        }
    }

    private static void handleTfsAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_TFS_ALARM tfsAlarm = new HCNetSDK.NET_DVR_TFS_ALARM();
            tfsAlarm.write();
            Pointer pTfsAlarm = tfsAlarm.getPointer();
            pTfsAlarm.write(0, pAlarmInfo.getByteArray(0, tfsAlarm.size()), 0, tfsAlarm.size());
            tfsAlarm.read();

            int illegalType = tfsAlarm.dwIllegalType;
            String illegalTypeDesc = getIllegalTypeDescription(illegalType);

            // 获取通道号
            Integer channel = null;
            if (tfsAlarm.struDevInfo != null && tfsAlarm.struDevInfo.byChannel >= 0) {
                channel = (int) tfsAlarm.struDevInfo.byChannel;
            }

            log.info("道路违章取证报警 - 违章类型:{}, 通道:{}, 图片数量:{}", illegalTypeDesc, channel, tfsAlarm.dwPicNum);

            // 保存报警图片
            String imageUrl = null;
            if (tfsAlarm.dwPicNum > 0 && tfsAlarm.struPicInfo != null) {
                for (int i = 0; i < tfsAlarm.dwPicNum && i < tfsAlarm.struPicInfo.length; i++) {
                    HCNetSDK.NET_ITS_PICTURE_INFO picInfo = tfsAlarm.struPicInfo[i];
                    if (picInfo.dwDataLen > 0 && picInfo.pBuffer != null) {
                        byte[] imageData = picInfo.pBuffer.getByteArray(0, picInfo.dwDataLen);
                        if (imageData != null && imageData.length > 0) {
                            imageUrl = saveAlarmImage(imageData, deviceIp, "tfs_" + i);
                            log.info("保存第{}张道路违章取证图片成功 - 图片类型:{}", i, picInfo.byType);
                            break; // 只保存第一张图片
                        }
                    }
                }
            }

            String alarmContent = "道路违章取证 - 违章类型:" + illegalTypeDesc;
            saveAlarmToDatabase(deviceId, deviceIp, null, channel, lCommand, alarmContent, imageUrl);
        } catch (Exception e) {
            log.error("处理道路违章取证报警失败", e);
        }
    }

    private static void handleAidAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_AID_ALARM_V41 aidAlarm = new HCNetSDK.NET_DVR_AID_ALARM_V41();
            aidAlarm.write();
            Pointer pAidAlarm = aidAlarm.getPointer();
            pAidAlarm.write(0, pAlarmInfo.getByteArray(0, aidAlarm.size()), 0, aidAlarm.size());
            aidAlarm.read();

            int aidType = aidAlarm.struAIDInfo.dwAIDType;
            String aidTypeDesc = getAidTypeDescription(aidType);

            // 获取通道号
            Integer channel = null;
            if (aidAlarm.struDevInfo != null && aidAlarm.struDevInfo.byChannel >= 0) {
                channel = (int) aidAlarm.struDevInfo.byChannel;
            }

            log.info("道路事件检测报警 - 事件类型:{}, 通道:{}, 图片长度:{}", aidTypeDesc, channel, aidAlarm.dwPicDataLen);

            // 保存报警图片
            String imageUrl = null;
            if (aidAlarm.dwPicDataLen > 0 && aidAlarm.pImage != null) {
                byte[] imageData = aidAlarm.pImage.getByteArray(0, aidAlarm.dwPicDataLen);
                if (imageData != null && imageData.length > 0) {
                    imageUrl = saveAlarmImage(imageData, deviceIp, "aid");
                    log.info("保存道路事件检测图片成功");
                }
            }

            String alarmContent = "道路事件检测 - 事件类型:" + aidTypeDesc;
            saveAlarmToDatabase(deviceId, deviceIp, null, channel, lCommand, alarmContent, imageUrl);
        } catch (Exception e) {
            log.error("处理道路事件检测报警失败", e);
        }
    }

    private static void handleTpsAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            log.info("交通数据统计报警 - 报警类型:{}", Integer.toHexString(lCommand));

            String alarmTypeDesc;
            switch (lCommand) {
                case HCNetSDK.COMM_ALARM_TPS_V41:
                    alarmTypeDesc = "交通数据统计报警";
                    break;
                case HCNetSDK.COMM_ALARM_TPS_STATISTICS:
                    alarmTypeDesc = "统计过车数据报警";
                    break;
                case HCNetSDK.COMM_ALARM_TPS_REAL_TIME:
                    alarmTypeDesc = "实时过车数据报警";
                    break;
                default:
                    alarmTypeDesc = "交通数据统计相关报警";
            }

            saveAlarmToDatabase(deviceId, deviceIp, null, null, lCommand, alarmTypeDesc);
        } catch (Exception e) {
            log.error("处理交通数据统计报警失败", e);
        }
    }

    private static void handleAcsAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_ACS_ALARM_INFO acsAlarm = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
            acsAlarm.write();
            Pointer pAcsAlarm = acsAlarm.getPointer();
            pAcsAlarm.write(0, pAlarmInfo.getByteArray(0, acsAlarm.size()), 0, acsAlarm.size());
            acsAlarm.read();

            String cardNo = new String(acsAlarm.struAcsEventInfo.byCardNo).trim();
            int majorType = acsAlarm.dwMajor;
            int minorType = acsAlarm.dwMinor;

            String acsEventDesc = getAcsEventDescription(majorType, minorType);

            log.info("门禁主机报警 - 卡号:{}, 事件描述:{}", cardNo, acsEventDesc);

            String alarmContent = "门禁事件 - 卡号:" + cardNo + ", 事件:" + acsEventDesc;
            saveAlarmToDatabase(deviceId, deviceIp, null, null, lCommand, alarmContent);
        } catch (Exception e) {
            log.error("处理门禁主机报警失败", e);
        }
    }

    private static String getVehicleTypeDescription(byte vehicleType) {
        switch (vehicleType) {
            case 0:
                return "其他车辆";
            case 1:
                return "小型车";
            case 2:
                return "大型车";
            case 3:
                return "行人";
            case 4:
                return "二轮车";
            case 5:
                return "三轮车";
            case 6:
                return "机动车";
            default:
                return "未知类型";
        }
    }

    private static String getIllegalTypeDescription(int illegalType) {
        switch (illegalType) {
            case 1:
                return "闯红灯";
            case 2:
                return "逆行";
            case 3:
                return "不按导向行驶";
            case 4:
                return "违法变道";
            case 5:
                return "超速";
            case 6:
                return "压黄线";
            case 7:
                return "压白实线";
            case 8:
                return "机占非";
            case 9:
                return "占用公交专用道";
            case 10:
                return "违法掉头";
            case 11:
                return "违法停车";
            default:
                return "其他违章:" + illegalType;
        }
    }

    private static String getAidTypeDescription(int aidType) {
        switch (aidType) {
            case 1:
                return "停车检测";
            case 2:
                return "逆行检测";
            case 3:
                return "变道检测";
            case 4:
                return "行人和非机动车检测";
            case 5:
                return "抛洒物检测";
            case 6:
                return "拥堵检测";
            case 7:
                return "排队长度检测";
            default:
                return "其他交通事件:" + aidType;
        }
    }

    private static String getAcsEventDescription(int majorType, int minorType) {
        String majorDesc;
        String minorDesc;

        switch (majorType) {
            case 0x1:
                majorDesc = "报警";
                break;
            case 0x2:
                majorDesc = "异常";
                break;
            case 0x3:
                majorDesc = "操作";
                break;
            case 0x5:
                majorDesc = "事件";
                break;
            default:
                majorDesc = "其他";
        }

        minorDesc = "次类型:" + Integer.toHexString(minorType);

        return majorDesc + " - " + minorDesc;
    }

    private static void handleSwitchAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_SWITCH_ALARM switchAlarm = new HCNetSDK.NET_DVR_SWITCH_ALARM();
            switchAlarm.write();
            Pointer pSwitchAlarm = switchAlarm.getPointer();
            pSwitchAlarm.write(0, pAlarmInfo.getByteArray(0, switchAlarm.size()), 0, switchAlarm.size());
            switchAlarm.read();

            String switchName = new String(switchAlarm.byName).trim();
            short channel = switchAlarm.wSwitchChannel;
            byte alarmType = switchAlarm.byAlarmType;

            String alarmTypeDesc = getSwitchAlarmTypeDescription(alarmType);

            log.info("开关量报警 - 名称:{}, 通道:{}, 报警类型:{}", switchName, channel, alarmTypeDesc);

            String alarmContent = "开关量报警 - 名称:" + switchName + ", 通道:" + channel + ", 状态:" + alarmTypeDesc;
            saveAlarmToDatabase(deviceId, deviceIp, null, (int) channel, lCommand, alarmContent);
        } catch (Exception e) {
            log.error("处理开关量报警失败", e);
        }
    }

    private static String getSwitchAlarmTypeDescription(byte alarmType) {
        switch (alarmType) {
            case 0:
                return "正常";
            case 1:
                return "短路报警";
            case 2:
                return "断路报警";
            case 3:
                return "异常报警";
            default:
                return "未知状态:" + alarmType;
        }
    }

    private static void handleSensorAlarm(Pointer pAlarmInfo, String deviceId, String deviceIp, int lCommand) {
        try {
            HCNetSDK.NET_DVR_SENSOR_ALARM sensorAlarm = new HCNetSDK.NET_DVR_SENSOR_ALARM();
            sensorAlarm.write();
            Pointer pSensorAlarm = sensorAlarm.getPointer();
            pSensorAlarm.write(0, pAlarmInfo.getByteArray(0, sensorAlarm.size()), 0, sensorAlarm.size());
            sensorAlarm.read();

            String sensorName = new String(sensorAlarm.byName).trim();
            byte channel = sensorAlarm.bySensorChannel;
            byte sensorType = sensorAlarm.byType;
            byte alarmType = sensorAlarm.byAlarmType;
            float value = sensorAlarm.fValue;

            String alarmTypeDesc = getSensorAlarmTypeDescription(alarmType);

            log.info("模拟量报警 - 名称:{}, 通道:{}, 类型:{}, 报警类型:{}, 当前值:{}", 
                     sensorName, channel, sensorType, alarmTypeDesc, value);

            String alarmContent = "模拟量报警 - 名称:" + sensorName + ", 通道:" + channel + 
                                   ", 报警类型:" + alarmTypeDesc + ", 当前值:" + value;
            saveAlarmToDatabase(deviceId, deviceIp, null, (int) channel, lCommand, alarmContent);
        } catch (Exception e) {
            log.error("处理模拟量报警失败", e);
        }
    }

    private static String getSensorAlarmTypeDescription(byte alarmType) {
        switch (alarmType) {
            case 1:
                return "上限4";
            case 2:
                return "上限3";
            case 3:
                return "上限2";
            case 4:
                return "上限1";
            case 5:
                return "下限1";
            case 6:
                return "下限2";
            case 7:
                return "下限3";
            case 8:
                return "下限4";
            default:
                return "未知报警:" + alarmType;
        }
    }
}
