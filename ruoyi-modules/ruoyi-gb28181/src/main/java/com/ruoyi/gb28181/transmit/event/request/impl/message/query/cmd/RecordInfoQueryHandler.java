package com.ruoyi.gb28181.transmit.event.request.impl.message.query.cmd;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.bean.RecordItem;
import com.ruoyi.gb28181.api.bean.ErrorCallback;
import com.ruoyi.gb28181.api.domain.Device;
import com.ruoyi.gb28181.api.domain.DeviceChannel;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.api.utils.XmlUtil;
import com.ruoyi.gb28181.service.IDeviceService;
import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.transmit.event.request.SIPRequestProcessorParent;
import com.ruoyi.gb28181.transmit.event.request.impl.message.IMessageHandler;
import com.ruoyi.gb28181.transmit.event.request.impl.message.query.QueryMessageHandler;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.zlm.api.RemoteZlmCloudRecordService;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.Response;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 处理上级平台的RecordInfo查询
 */
@Slf4j
@Component
public class RecordInfoQueryHandler extends SIPRequestProcessorParent implements InitializingBean, IMessageHandler {

    private final static String cmdType = "RecordInfo";

    @Autowired
    private QueryMessageHandler queryMessageHandler;

    @Autowired
    private IPlatformSIPCommander platformSIPCommander;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteZlmCloudRecordService remoteZlmCloudRecordService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private RemoteHaiKangService remoteHaiKangService;

    @Autowired
    private RemoteHaiKangIsupService remoteHaiKangIsupService;

    @Autowired
    private RemoteDaHuaService remoteDaHuaService;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    @Override
    public void afterPropertiesSet() throws Exception {
        queryMessageHandler.addHandler(cmdType, this);
    }

    @Override
    public void handForDevice(RequestEvent evt, Device device, Element rootElement) {
        // 这个方法不处理设备，跳过
    }

    @Override
    public void handForPlatform(RequestEvent evt, Gb28181Platform platform, Element rootElement) {
        log.info("[平台级联] 收到上级平台 RecordInfo 查询, platform: {}", platform.getName());
        try {
            // 先回复200 OK
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            
            // 获取查询参数
            String snStr = XmlUtil.getText(rootElement, "SN");
            int sn = Integer.parseInt(snStr);
            String deviceId = XmlUtil.getText(rootElement, "DeviceID");
            String startTimeStr = XmlUtil.getText(rootElement, "StartTime");
            String endTimeStr = XmlUtil.getText(rootElement, "EndTime");
            
            log.info("[平台级联] RecordInfo 查询参数 - SN: {}, DeviceID: {}, StartTime: {}, EndTime: {}", 
                    sn, deviceId, startTimeStr, endTimeStr);
            
            // 根据 deviceId（应该是通道的 gbCode 或 gbDeviceId）查找 QsDevice
            QsDevice qsDevice = null;
            try {
                qsDevice = remoteQsDeviceService.getDeviceByGbCode(deviceId, SecurityConstants.INNER).getData();
            } catch (Exception e) {
                log.error("[平台级联] 根据 gbCode 查找设备失败: {}", e.getMessage());
            }
            
            if (qsDevice == null) {
                log.warn("[平台级联] 未找到设备，返回空录像列表");
                RecordInfo recordInfo = new RecordInfo();
                recordInfo.setChannelId(deviceId);
                recordInfo.setSumNum(0);
                recordInfo.setRecordList(new ArrayList<>());
                platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
                return;
            }
            
            log.info("[平台级联] 找到设备 - ID: {}, Name: {}, Type: {}", 
                    qsDevice.getId(), qsDevice.getDeviceName(), qsDevice.getType());
            
            // 判断设备类型
            String type = qsDevice.getType();
            if (type == null) {
                type = "";
            }
            
            // 1=RTSP,2=RTMP,3=FLV,4=HLS,6=视频文件,13=PUSH 这些查询云端录像
            // 5=ONVIF,7=海康SDK,8=海康ISUP,9=大华SDK,12=国标28181,14=部标1078 这些查询设备录像
            boolean isCloudRecord = "1".equals(type) || "2".equals(type) || "3".equals(type) || 
                    "4".equals(type) || "6".equals(type) || "13".equals(type);
            
            if (isCloudRecord) {
                log.info("[平台级联] 设备类型为云端录像类型，查询云端录像");
                queryCloudRecord(platform, qsDevice, sn, startTimeStr, endTimeStr);
            } else {
                log.info("[平台级联] 设备类型为设备录像类型，查询设备录像");
                queryDeviceRecord(platform, qsDevice, sn, deviceId, startTimeStr, endTimeStr);
            }
            
        } catch (SipException | InvalidArgumentException | ParseException | NumberFormatException e) {
            log.error("[平台级联] 处理RecordInfo查询失败", e);
        }
    }

    /**
     * 查询云端录像
     */
    private void queryCloudRecord(Gb28181Platform platform, QsDevice qsDevice, int sn, 
            String startTimeStr, String endTimeStr) {
        try {
            // 构造查询条件
            ZlmCloudRecord queryRecord = new ZlmCloudRecord();
            
            // 云端录像的 app ，stream 为 deviceCode
            // 根据设备类型设置对应的 app
            String app = "";
            if (LiveStreamType.RTSP.getCode().equals(qsDevice.getType())) {
                app = "rtsp";
            } else if (LiveStreamType.RTMP.getCode().equals(qsDevice.getType())) {
                app = "rtmp";
            } else if (LiveStreamType.FLV.getCode().equals(qsDevice.getType())) {
                app = "flv";
            } else if (LiveStreamType.HLS.getCode().equals(qsDevice.getType())) {
                app = "hls";
            } else if (LiveStreamType.ONVIF.getCode().equals(qsDevice.getType())) {
                app = "onvif";
            } else if (LiveStreamType.PUSH.getCode().equals(qsDevice.getType())) {
                app = "push";
            }
            queryRecord.setApp(app);
            String stream = qsDevice.getDeviceCode();
            queryRecord.setStream(stream);
            
            log.info("[平台级联] 查询云端录像 - 设备类型: {}, app: {}, stream: {}, startTime: {}, endTime: {}",
                    qsDevice.getType(), app, stream, startTimeStr, endTimeStr);
            
            // 先不限制时间范围，查询所有相关录像
            R<List<ZlmCloudRecord>> queryResult = remoteZlmCloudRecordService.selectZlmCloudRecordList(
                    queryRecord, SecurityConstants.INNER);
            
            if (queryResult.getCode() != Constants.SUCCESS) {
                log.error("[查询云端录像失败] code: {}, msg: {}", queryResult.getCode(), queryResult.getMsg());
                RecordInfo recordInfo = new RecordInfo();
                recordInfo.setChannelId(qsDevice.getGbCode() != null ? qsDevice.getGbCode() : qsDevice.getGbDeviceId());
                recordInfo.setName(qsDevice.getDeviceName());
                recordInfo.setSumNum(0);
                recordInfo.setRecordList(new ArrayList<>());
                platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
                return;
            }
            
            List<ZlmCloudRecord> cloudRecordList = queryResult.getData();
            
            log.info("[平台级联] 云端录像查询结果 - 数量: {}", 
                    cloudRecordList != null ? cloudRecordList.size() : 0);
            
            // 如果有时间范围，再在内存中过滤
            if (cloudRecordList != null && !cloudRecordList.isEmpty() && 
                (startTimeStr != null && !startTimeStr.isEmpty() || endTimeStr != null && !endTimeStr.isEmpty())) {
                
                // 先把时间字符串转换为时间戳
                Long startTimeStamp = null;
                Long endTimeStamp = null;
                
                if (startTimeStr != null && !startTimeStr.isEmpty()) {
                    String startTimeNormal = DateUtil.ISO8601Toyyyy_MM_dd_HH_mm_ss(startTimeStr);
                    startTimeStamp = DateUtil.yyyy_MM_dd_HH_mm_ssToTimestampMs(startTimeNormal);
                }
                if (endTimeStr != null && !endTimeStr.isEmpty()) {
                    String endTimeNormal = DateUtil.ISO8601Toyyyy_MM_dd_HH_mm_ss(endTimeStr);
                    endTimeStamp = DateUtil.yyyy_MM_dd_HH_mm_ssToTimestampMs(endTimeNormal);
                }
                
                log.info("[平台级联] 时间范围过滤 - startTimeStamp: {}, endTimeStamp: {}", startTimeStamp, endTimeStamp);
                
                // 过滤录像
                List<ZlmCloudRecord> filteredList = new ArrayList<>();
                for (ZlmCloudRecord record : cloudRecordList) {
                    boolean match = true;
                    // 录像的结束时间 >= 查询的开始时间
                    if (startTimeStamp != null && record.getEndTime() != null && record.getEndTime() < startTimeStamp) {
                        match = false;
                    }
                    // 录像的开始时间 <= 查询的结束时间
                    if (endTimeStamp != null && record.getStartTime() != null && record.getStartTime() > endTimeStamp) {
                        match = false;
                    }
                    if (match) {
                        filteredList.add(record);
                    }
                }
                cloudRecordList = filteredList;
                log.info("[平台级联] 时间范围过滤后 - 数量: {}", cloudRecordList.size());
            }
            
            // 构造 RecordInfo
            RecordInfo recordInfo = new RecordInfo();
            recordInfo.setChannelId(qsDevice.getGbCode() != null ? qsDevice.getGbCode() : qsDevice.getGbDeviceId());
            recordInfo.setName(qsDevice.getDeviceName());
            
            List<RecordItem> recordItemList = new ArrayList<>();
            if (cloudRecordList != null && !cloudRecordList.isEmpty()) {
                for (ZlmCloudRecord cloudRecord : cloudRecordList) {
                    RecordItem recordItem = new RecordItem();
                    recordItem.setDeviceId(qsDevice.getGbCode() != null ? qsDevice.getGbCode() : qsDevice.getGbDeviceId());
                    recordItem.setName(cloudRecord.getFileName());
                    recordItem.setFilePath(cloudRecord.getFilePath());
                    if (cloudRecord.getFileSize() != null) {
                        recordItem.setFileSize(String.valueOf(cloudRecord.getFileSize()));
                    }
                    recordItem.setSecrecy(0);
                    recordItem.setType("time");
                    
                    // 转换时间格式
                    if (cloudRecord.getStartTime() != null) {
                        LocalDateTime startTime = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(cloudRecord.getStartTime()), 
                                ZoneId.systemDefault());
                        recordItem.setStartTime(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                    if (cloudRecord.getEndTime() != null) {
                        LocalDateTime endTime = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(cloudRecord.getEndTime()), 
                                ZoneId.systemDefault());
                        recordItem.setEndTime(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                    
                    recordItemList.add(recordItem);
                }
            }
            
            recordInfo.setSumNum(recordItemList.size());
            recordInfo.setRecordList(recordItemList);
            
            // 发送录像信息
            platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
            log.info("[平台级联] 云端录像信息发送完成 - 录像数量: {}", recordItemList.size());
            
        } catch (Exception e) {
            log.error("[平台级联] 查询云端录像失败", e);
            try {
                // 发生错误时返回空录像列表
                RecordInfo recordInfo = new RecordInfo();
                recordInfo.setChannelId(qsDevice.getGbCode() != null ? qsDevice.getGbCode() : qsDevice.getGbDeviceId());
                recordInfo.setName(qsDevice.getDeviceName());
                recordInfo.setSumNum(0);
                recordInfo.setRecordList(new ArrayList<>());
                platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
            } catch (Exception e2) {
                log.error("[平台级联] 发送空录像信息失败", e2);
            }
        }
    }

    /**
     * 查询设备录像
     */
    private void queryDeviceRecord(Gb28181Platform platform, QsDevice qsDevice, int sn, String deviceId, String startTimeStr, String endTimeStr) {
        try {
            // 转换时间格式从 ISO8601 到 yyyy-MM-dd HH:mm:ss
            String startTime = null;
            String endTime = null;

            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                startTime = DateUtil.ISO8601Toyyyy_MM_dd_HH_mm_ss(startTimeStr);
            }
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                endTime = DateUtil.ISO8601Toyyyy_MM_dd_HH_mm_ss(endTimeStr);
            }

            String channelId = qsDevice.getGbCode() != null ? qsDevice.getGbCode() : qsDevice.getGbDeviceId();
            log.info("[平台级联] 查询设备录像 - 设备类型: {}, 设备ID: {}, 通道ID: {}, startTime: {}, endTime: {}",
                    qsDevice.getType(), qsDevice.getId(), channelId, startTime, endTime);

            // 从设备中获取通道ID
            Integer queryChannelId = qsDevice.getChannel() != null ? qsDevice.getChannel() : 1;
            ArrayList<HashMap<String, Object>> recordList = null;
            RecordInfo recordInfo = null;

            // 根据设备类型调用对应的接口
            if ("7".equals(qsDevice.getType())) {
                // 海康SDK
                log.info("[平台级联] 调用海康SDK设备录像查询");
                com.ruoyi.common.core.domain.R<ArrayList<HashMap<String, Object>>> result =
                        remoteHaiKangService.getRecMonth(qsDevice.getId(), queryChannelId, startTime, endTime,
                                com.ruoyi.common.core.constant.SecurityConstants.INNER);
                if (result != null && result.getCode() == com.ruoyi.common.core.constant.Constants.SUCCESS) {
                    recordList = result.getData();
                }
            } else if ("8".equals(qsDevice.getType())) {
                // 海康ISUP
                log.info("[平台级联] 调用海康ISUP设备录像查询");
                com.ruoyi.common.core.domain.R<ArrayList<HashMap<String, Object>>> result =
                        remoteHaiKangIsupService.getRecMonth(qsDevice.getId(), queryChannelId, startTime, endTime,
                                com.ruoyi.common.core.constant.SecurityConstants.INNER);
                if (result != null && result.getCode() == com.ruoyi.common.core.constant.Constants.SUCCESS) {
                    recordList = result.getData();
                }
            } else if ("9".equals(qsDevice.getType())) {
                // 大华SDK
                log.info("[平台级联] 调用大华SDK设备录像查询");
                com.ruoyi.common.core.domain.R<ArrayList<HashMap<String, Object>>> result =
                        remoteDaHuaService.queryRecord(qsDevice.getId(), queryChannelId, startTime, endTime,
                                com.ruoyi.common.core.constant.SecurityConstants.INNER);
                if (result != null && result.getCode() == com.ruoyi.common.core.constant.Constants.SUCCESS) {
                    recordList = result.getData();
                }
            } else if ("5".equals(qsDevice.getType())) {
                // ONVIF
                log.info("[平台级联] 调用ONVIF设备录像查询");
                com.ruoyi.common.core.domain.R<Object> result =
                        remoteOnvifService.queryRecord(qsDevice.getIpAddress(), qsDevice.getUserName(),
                                qsDevice.getPassword(), startTime, endTime,
                                com.ruoyi.common.core.constant.SecurityConstants.INNER);
                if (result != null && result.getCode() == com.ruoyi.common.core.constant.Constants.SUCCESS) {
                    recordList = (ArrayList<HashMap<String, Object>>) result.getData();
                }
            } else if ("12".equals(qsDevice.getType())) {
                // 国标28181
                log.info("[平台级联] 调用国标28181设备录像查询");
                // 获取国标设备信息
                String gbDeviceId = qsDevice.getGbDeviceId();
                String gbChannelId = qsDevice.getGbChannelId() != null ? qsDevice.getGbChannelId() : deviceId;
                Device gbDevice = deviceService.getDeviceByDeviceId(gbDeviceId);
                if (gbDevice == null) {
                    log.error("[平台级联] 未找到国标设备: {}", gbDeviceId);
                } else {
                    // 获取通道信息
                    DeviceChannel deviceChannel = null;
                    try {
                        log.info("[平台级联] 查找国标通道 - 设备ID: {}, 通道ID: {}", gbDeviceId, gbChannelId);
                        deviceChannel = deviceService.getDeviceChannelByChannelId(gbDeviceId, gbChannelId);
                    } catch (Exception e) {
                        log.error("[平台级联] 获取国标设备通道失败: {}", e.getMessage());
                    }
                    if (deviceChannel != null) {
                        // 使用 AtomicReference 存储结果，避免多个线程写入的问题
                        AtomicReference<RecordInfo> resultRef = new AtomicReference<>();
                        CountDownLatch latch = new CountDownLatch(1);
                        // 转换时间格式为 yyyy-MM-dd HH:mm:ss
                        String startTimeForDevice = null;
                        String endTimeForDevice = null;
                        if (startTimeStr != null && !startTimeStr.isEmpty()) {
                            startTimeForDevice = DateUtil.ISO8601Toyyyy_MM_dd_HH_mm_ss(startTimeStr);
                        }
                        if (endTimeStr != null && !endTimeStr.isEmpty()) {
                            endTimeForDevice = DateUtil.ISO8601Toyyyy_MM_dd_HH_mm_ss(endTimeStr);
                        }
                        log.info("[平台级联] 发送录像查询 - 设备ID: {}, 通道ID: {}, startTime: {}, endTime: {}", 
                            gbDeviceId, gbChannelId, startTimeForDevice, endTimeForDevice);
                        deviceService.queryRecord(gbDevice, deviceChannel, startTimeForDevice, endTimeForDevice, (code, msg, data) -> {
                            try {
                                log.info("[平台级联] 收到录像查询回调 - code: {}, msg: {}, hasData: {}", code, msg, data != null);
                                if (code == 0) { // ErrorCode.SUCCESS.getCode() is 0
                                    log.info("[平台级联] 国标28181设备录像查询成功");
                                    resultRef.set(data);
                                } else {
                                    log.error("[平台级联] 国标28181设备录像查询失败: {}", msg);
                                    resultRef.set(null);
                                }
                            } catch (Exception e) {
                                log.error("[平台级联] 处理国标28181设备录像查询结果失败: {}", e.getMessage(), e);
                                resultRef.set(null);
                            } finally {
                                latch.countDown();
                            }
                        });
                        // 等待结果返回
                        boolean completed = latch.await(30, TimeUnit.SECONDS);
                        if (completed) {
                            recordInfo = resultRef.get();
                            if (recordInfo != null) {
                                log.info("[平台级联] 国标28181设备录像查询成功，录像数量: {}", recordInfo.getSumNum());
                            } else {
                                log.warn("[平台级联] 国标28181设备录像查询完成，但没有数据");
                            }
                        } else {
                            log.error("[平台级联] 国标28181设备录像查询超时");
                        }
                    } else {
                        log.error("[平台级联] 未找到国标设备通道: {}", channelId);
                    }
                }
            } else {
                log.warn("[平台级联] 不支持的设备类型: {}", qsDevice.getType());
            }

            // 构建RecordInfo对象
            if ("12".equals(qsDevice.getType()) && recordInfo != null) {
                // 国标28181设备，直接使用查询结果
                String finalChannelId = qsDevice.getGbChannelId() != null ? qsDevice.getGbChannelId() : channelId;
                recordInfo.setChannelId(finalChannelId);
                if (recordInfo.getName() == null || recordInfo.getName().isEmpty()) {
                    recordInfo.setName(qsDevice.getDeviceName());
                }
                log.info("[平台级联] 设备录像查询完成 - 录像数量: {}", recordInfo.getSumNum());
                // 发送录像信息给上级平台
                platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
            } else {
                // 其他设备类型，构建RecordInfo对象
                recordInfo = new RecordInfo();
                recordInfo.setChannelId(channelId);
                recordInfo.setName(qsDevice.getDeviceName());

                List<RecordItem> recordItemList = new ArrayList<>();
                if (recordList != null && !recordList.isEmpty()) {
                    for (HashMap<String, Object> record : recordList) {
                        RecordItem recordItem = new RecordItem();
                        recordItem.setDeviceId(channelId);
                        recordItem.setName((String) record.get("fileName"));
                        recordItem.setFilePath((String) record.get("fileName"));

                        Object fileSizeObj = record.get("fileSize");
                        if (fileSizeObj != null) {
                            recordItem.setFileSize(String.valueOf(fileSizeObj));
                        }

                        recordItem.setSecrecy(0);
                        recordItem.setType("time");

                        // 处理时间格式，将斜杠转换为横杠
                        String recordStartTime = (String) record.get("start");
                        String recordEndTime = (String) record.get("end");
                        if (recordStartTime != null) {
                            recordStartTime = recordStartTime.replace('/', '-');
                        }
                        if (recordEndTime != null) {
                            recordEndTime = recordEndTime.replace('/', '-');
                        }
                        recordItem.setStartTime(recordStartTime);
                        recordItem.setEndTime(recordEndTime);

                        recordItemList.add(recordItem);
                    }
                }

                recordInfo.setSumNum(recordItemList.size());
                recordInfo.setRecordList(recordItemList);

                log.info("[平台级联] 设备录像查询完成 - 录像数量: {}", recordItemList.size());

                // 发送录像信息给上级平台
                platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
            }

        } catch (Exception e) {
            log.error("[平台级联] 查询设备录像失败", e);
            try {
                // 发生错误时返回空录像列表
                RecordInfo recordInfo = new RecordInfo();
                recordInfo.setChannelId(qsDevice.getGbCode() != null ? qsDevice.getGbCode() : qsDevice.getGbDeviceId());
                recordInfo.setName(qsDevice.getDeviceName());
                recordInfo.setSumNum(0);
                recordInfo.setRecordList(new ArrayList<>());
                platformSIPCommander.sendRecordInfo(platform, recordInfo, sn);
            } catch (Exception e2) {
                log.error("[平台级联] 发送空录像信息失败", e2);
            }
        }
    }
}
