package com.ruoyi.gb28181.transmit.event.request.impl.message.control;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.LiveStreamType;
import com.ruoyi.dahua.api.RemoteDaHuaService;
import com.ruoyi.gb28181.api.RemoteGb28181Service;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.utils.XmlUtil;
import com.ruoyi.gb28181.transmit.event.request.impl.message.MessageHandlerAbstract;
import com.ruoyi.gb28181.transmit.event.request.impl.message.MessageRequestProcessor;
import com.ruoyi.haikang.api.RemoteHaiKangService;
import com.ruoyi.haikang.isup.api.RemoteHaiKangIsupService;
import com.ruoyi.jt1078.api.RemoteJt1078Service;
import com.ruoyi.onvif.api.RemoteOnvifService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import gov.nist.javax.sip.message.SIPRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sip.RequestEvent;
import javax.sip.message.Response;

/**
 * 命令类型： 控制命令
 */
@Slf4j
@Component
public class ControlMessageHandler extends MessageHandlerAbstract implements InitializingBean {

    private final String messageType = "Control";

    @Autowired
    private MessageRequestProcessor messageRequestProcessor;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteOnvifService remoteOnvifService;

    @Autowired
    private RemoteHaiKangService remoteHaiKangService;

    @Autowired
    private RemoteDaHuaService remoteDaHuaService;

    @Autowired
    private RemoteHaiKangIsupService remoteHaiKangIsupService;

    @Autowired
    private RemoteJt1078Service remoteJt1078Service;

    @Autowired
    private RemoteGb28181Service remoteGb28181Service;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageRequestProcessor.addHandler(messageType, this);
    }

    @Override
    public void handForPlatform(RequestEvent evt, Gb28181Platform platform, Element rootElement) {
        try {
            String cmdType = XmlUtil.getText(rootElement, "CmdType");
            String deviceId = XmlUtil.getText(rootElement, "DeviceID");
            log.info("[平台级联云台控制] 收到控制命令, CmdType: {}, DeviceID: {}, platform: {}, ptz: {}", 
                    cmdType, deviceId, platform.getName(), platform.getPtz());

            if ("DeviceControl".equals(cmdType)) {
                Element ptzCmdElement = rootElement.element("PTZCmd");
                if (ptzCmdElement != null) {
                    String ptzCmd = ptzCmdElement.getTextTrim();
                    log.info("[平台级联云台控制] PTZCmd: {}", ptzCmd);
                    
                    // 检查平台是否启用了云台控制
                    if (platform.getPtz() == null || platform.getPtz() != 1) {
                        log.info("[平台级联云台控制] 平台未启用云台控制, 直接回复200, platform: {}", platform.getName());
                        responseAck((SIPRequest) evt.getRequest(), Response.OK);
                        return;
                    }
                    
                    QsDevice qsDevice = findDeviceByGbCode(deviceId);
                    if (qsDevice == null) {
                        log.warn("[平台级联云台控制] 未找到设备, DeviceID: {}", deviceId);
                        responseAck((SIPRequest) evt.getRequest(), Response.OK);
                        return;
                    }
                    
                    log.info("[平台级联云台控制] 找到设备, 设备名称: {}, 协议类型: {}", qsDevice.getDeviceName(), qsDevice.getType());
                    
                    PresetCommand presetCommand = parsePresetCmd(ptzCmd);
                    if (presetCommand != null) {
                        handlePresetControl(qsDevice, presetCommand);
                    } else {
                        PtzCommand ptzCommand = parsePtzCmd(ptzCmd);
                        if (ptzCommand != null) {
                            handlePtzControl(qsDevice, ptzCommand);
                        }
                    }
                }
            }
            
            responseAck((SIPRequest) evt.getRequest(), Response.OK);
            
        } catch (Exception e) {
            log.error("[平台级联云台控制] 处理控制命令失败", e);
            try {
                responseAck((SIPRequest) evt.getRequest(), Response.OK);
            } catch (Exception ex) {
                log.error("[平台级联云台控制] 回复失败", ex);
            }
        }
    }

    private QsDevice findDeviceByGbCode(String gbCode) {
        try {
            R<QsDevice> qsDeviceR = remoteQsDeviceService.getDeviceByGbCode(gbCode, SecurityConstants.INNER);
            if (qsDeviceR.getCode() == 200 && qsDeviceR.getData() != null) {
                return qsDeviceR.getData();
            }
        } catch (Exception e) {
            log.warn("[平台级联云台控制] 通过gbCode查询设备失败, gbCode: {}", gbCode, e);
        }
        return null;
    }

    private void handlePtzControl(QsDevice qsDevice, PtzCommand ptzCommand) {
        String deviceType = qsDevice.getType();
        Integer channelId = qsDevice.getChannel();
        if (channelId == null) {
            channelId = 1;
        }
        
        log.info("[平台级联云台控制] 处理云台控制, 设备类型: {}, 方向: {}, 速度: {}", 
                deviceType, ptzCommand.direction, ptzCommand.speed);

        try {
            if (LiveStreamType.JT1078.getCode().equals(deviceType)) {
                handleJt1078Ptz(qsDevice, ptzCommand, channelId);
            } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
                handleGb28181Ptz(qsDevice, ptzCommand);
            } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
                handleHikIsupPtz(qsDevice, ptzCommand, channelId);
            } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
                handleOnvifPtz(qsDevice, ptzCommand);
            } else if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
                handleHikPtz(qsDevice, ptzCommand, channelId);
            } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
                handleDahuaPtz(qsDevice, ptzCommand, channelId);
            } else {
                log.info("[平台级联云台控制] 设备类型不支持云台控制, 不处理: {}", deviceType);
            }
        } catch (Exception e) {
            log.error("[平台级联云台控制] 处理云台控制失败", e);
        }
    }

    private void handlePresetControl(QsDevice qsDevice, PresetCommand presetCommand) {
        String deviceType = qsDevice.getType();
        Integer channelId = qsDevice.getChannel();
        if (channelId == null) {
            channelId = 1;
        }
        
        log.info("[平台级联预置点控制] 处理预置点控制, 设备类型: {}, 命令: {}, 预置点: {}", 
                deviceType, presetCommand.command, presetCommand.presetIndex);

        try {
            if (LiveStreamType.JT1078.getCode().equals(deviceType)) {
                log.info("[平台级联预置点控制] JT1078设备不支持预置点, 不处理");
            } else if (LiveStreamType.GB28181.getCode().equals(deviceType)) {
                handleGb28181Preset(qsDevice, presetCommand);
            } else if (LiveStreamType.HIK_ISUP.getCode().equals(deviceType)) {
                handleHikIsupPreset(qsDevice, presetCommand, channelId);
            } else if (LiveStreamType.ONVIF.getCode().equals(deviceType)) {
                handleOnvifPreset(qsDevice, presetCommand);
            } else if (LiveStreamType.HIK_SDK.getCode().equals(deviceType)) {
                handleHikPreset(qsDevice, presetCommand, channelId);
            } else if (LiveStreamType.DAHUA_SDK.getCode().equals(deviceType)) {
                handleDahuaPreset(qsDevice, presetCommand, channelId);
            } else {
                log.info("[平台级联预置点控制] 设备类型不支持预置点, 不处理: {}", deviceType);
            }
        } catch (Exception e) {
            log.error("[平台级联预置点控制] 处理预置点控制失败", e);
        }
    }

    private void handleHikPreset(QsDevice qsDevice, PresetCommand presetCommand, Integer channelId) {
        try {
            Long deviceId = qsDevice.getId();
            int presetIndex = presetCommand.presetIndex;
            
            switch (presetCommand.command) {
                case "query":
                    remoteHaiKangService.getPresets(deviceId, channelId, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康SDK查询预置点, deviceId: {}, channelId: {}", deviceId, channelId);
                    break;
                case "add":
                    remoteHaiKangService.setPresets(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康SDK设置预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                case "call":
                    remoteHaiKangService.invokePresets(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康SDK调用预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                case "delete":
                    remoteHaiKangService.delPresets(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康SDK删除预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                default:
                    log.warn("[平台级联预置点控制] 未知预置点命令: {}", presetCommand.command);
            }
        } catch (Exception e) {
            log.error("[平台级联预置点控制] 海康SDK预置点控制失败", e);
        }
    }

    private void handleHikIsupPreset(QsDevice qsDevice, PresetCommand presetCommand, Integer channelId) {
        try {
            Long deviceId = qsDevice.getId();
            int presetIndex = presetCommand.presetIndex;
            
            switch (presetCommand.command) {
                case "query":
                    remoteHaiKangIsupService.getPresetList(deviceId, channelId, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康ISUP查询预置点, deviceId: {}, channelId: {}", deviceId, channelId);
                    break;
                case "add":
                    remoteHaiKangIsupService.setPreset(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康ISUP设置预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                case "call":
                    remoteHaiKangIsupService.gotoPreset(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康ISUP调用预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                case "delete":
                    remoteHaiKangIsupService.clearPreset(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 海康ISUP删除预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                default:
                    log.warn("[平台级联预置点控制] 未知预置点命令: {}", presetCommand.command);
            }
        } catch (Exception e) {
            log.error("[平台级联预置点控制] 海康ISUP预置点控制失败", e);
        }
    }

    private void handleOnvifPreset(QsDevice qsDevice, PresetCommand presetCommand) {
        try {
            String deviceIp = qsDevice.getIpAddress();
            String username = qsDevice.getUserName();
            String password = qsDevice.getPassword();
            int presetIndex = presetCommand.presetIndex;
            
            switch (presetCommand.command) {
                case "query":
                    remoteOnvifService.getPresets(deviceIp, username, password, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] ONVIF查询预置点, deviceIp: {}", deviceIp);
                    break;
                case "add":
                    remoteOnvifService.setPreset(deviceIp, username, password, presetIndex, null, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] ONVIF设置预置点, deviceIp: {}, presetIndex: {}", deviceIp, presetIndex);
                    break;
                case "call":
                    remoteOnvifService.gotoPreset(deviceIp, username, password, presetIndex, 50, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] ONVIF调用预置点, deviceIp: {}, presetIndex: {}", deviceIp, presetIndex);
                    break;
                case "delete":
                    remoteOnvifService.removePreset(deviceIp, username, password, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] ONVIF删除预置点, deviceIp: {}, presetIndex: {}", deviceIp, presetIndex);
                    break;
                default:
                    log.warn("[平台级联预置点控制] 未知预置点命令: {}", presetCommand.command);
            }
        } catch (Exception e) {
            log.error("[平台级联预置点控制] ONVIF预置点控制失败", e);
        }
    }

    private void handleGb28181Preset(QsDevice qsDevice, PresetCommand presetCommand) {
        try {
            String gbDeviceId = qsDevice.getGbDeviceId();
            String gbChannelId = qsDevice.getGbChannelId();
            if (gbDeviceId == null || gbChannelId == null) {
                log.warn("[平台级联预置点控制] GB28181设备国标ID为空, 不处理");
                return;
            }
            
            int presetIndex = presetCommand.presetIndex;
            
            switch (presetCommand.command) {
                case "query":
                    remoteGb28181Service.queryPreset(gbDeviceId, gbChannelId, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] GB28181查询预置点, gbDeviceId: {}, gbChannelId: {}", gbDeviceId, gbChannelId);
                    break;
                case "add":
                    remoteGb28181Service.addPreset(gbDeviceId, gbChannelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] GB28181设置预置点, gbDeviceId: {}, gbChannelId: {}, presetIndex: {}", gbDeviceId, gbChannelId, presetIndex);
                    break;
                case "call":
                    remoteGb28181Service.callPreset(gbDeviceId, gbChannelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] GB28181调用预置点, gbDeviceId: {}, gbChannelId: {}, presetIndex: {}", gbDeviceId, gbChannelId, presetIndex);
                    break;
                case "delete":
                    remoteGb28181Service.deletePreset(gbDeviceId, gbChannelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] GB28181删除预置点, gbDeviceId: {}, gbChannelId: {}, presetIndex: {}", gbDeviceId, gbChannelId, presetIndex);
                    break;
                default:
                    log.warn("[平台级联预置点控制] 未知预置点命令: {}", presetCommand.command);
            }
        } catch (Exception e) {
            log.error("[平台级联预置点控制] GB28181预置点控制失败", e);
        }
    }

    private void handleDahuaPreset(QsDevice qsDevice, PresetCommand presetCommand, Integer channelId) {
        try {
            Long deviceId = qsDevice.getId();
            int presetIndex = presetCommand.presetIndex;
            
            switch (presetCommand.command) {
                case "query":
                    remoteDaHuaService.getPresetList(deviceId, channelId, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 大华SDK查询预置点, deviceId: {}, channelId: {}", deviceId, channelId);
                    break;
                case "add":
                    remoteDaHuaService.setPreset(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 大华SDK设置预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                case "call":
                    remoteDaHuaService.invokePreset(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 大华SDK调用预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                case "delete":
                    remoteDaHuaService.delPreset(deviceId, channelId, presetIndex, SecurityConstants.INNER);
                    log.info("[平台级联预置点控制] 大华SDK删除预置点, deviceId: {}, channelId: {}, presetIndex: {}", deviceId, channelId, presetIndex);
                    break;
                default:
                    log.warn("[平台级联预置点控制] 未知预置点命令: {}", presetCommand.command);
            }
        } catch (Exception e) {
            log.error("[平台级联预置点控制] 大华SDK预置点控制失败", e);
        }
    }

    private void handleOnvifPtz(QsDevice qsDevice, PtzCommand ptzCommand) {
        try {
            String deviceIp = qsDevice.getIpAddress();
            String username = qsDevice.getUserName();
            String password = qsDevice.getPassword();
            String direction = ptzCommand.direction;
            Integer speed = ptzCommand.speed != null ? ptzCommand.speed : 50;

            if (ptzCommand.isStop) {
                log.info("[平台级联云台控制] ONVIF设备停止云台控制, IP: {}", deviceIp);
                remoteOnvifService.stopPtzControl(deviceIp, username, password, SecurityConstants.INNER);
            } else {
                log.info("[平台级联云台控制] ONVIF设备开始云台控制, IP: {}, 方向: {}, 速度: {}", 
                        deviceIp, direction, speed);
                remoteOnvifService.startPtzControl(deviceIp, username, password, direction, speed, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[平台级联云台控制] ONVIF云台控制失败", e);
        }
    }

    private void handleHikPtz(QsDevice qsDevice, PtzCommand ptzCommand, Integer channelId) {
        try {
            Long deviceId = qsDevice.getId();
            String direction = ptzCommand.direction;

            if (ptzCommand.isStop) {
                log.info("[平台级联云台控制] 海康SDK设备停止云台控制, 设备ID: {}, 通道: {}", deviceId, channelId);
                remoteHaiKangService.endPlayControl(deviceId, channelId, direction, SecurityConstants.INNER);
            } else {
                log.info("[平台级联云台控制] 海康SDK设备开始云台控制, 设备ID: {}, 通道: {}, 方向: {}", 
                        deviceId, channelId, direction);
                remoteHaiKangService.startPlayControl(deviceId, channelId, direction, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[平台级联云台控制] 海康SDK云台控制失败", e);
        }
    }

    private void handleDahuaPtz(QsDevice qsDevice, PtzCommand ptzCommand, Integer channelId) {
        try {
            Long deviceId = qsDevice.getId();
            String originalDirection = ptzCommand.direction;
            String direction = convertToDaHuaDirection(originalDirection);
            Integer speed = ptzCommand.speed != null ? ptzCommand.speed : 50;

            if (ptzCommand.isStop) {
                log.info("[平台级联云台控制] 大华SDK设备停止云台控制, 设备ID: {}, 通道: {}, 方向: {}", deviceId, channelId, direction);
                remoteDaHuaService.ptzControlUpEnd(deviceId, channelId, direction, SecurityConstants.INNER);
            } else {
                log.info("[平台级联云台控制] 大华SDK设备开始云台控制, 设备ID: {}, 通道: {}, 方向: {}, 速度: {}", 
                        deviceId, channelId, direction, speed);
                remoteDaHuaService.ptzControlUpStart(deviceId, channelId, direction, speed, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[平台级联云台控制] 大华SDK云台控制失败", e);
        }
    }

    private String convertToDaHuaDirection(String direction) {
        switch (direction) {
            case "left_up":
                return "top-left";
            case "left_down":
                return "bottom-left";
            case "right_up":
                return "upper-right";
            case "right_down":
                return "lower-right";
            default:
                return direction;
        }
    }

    private void handleJt1078Ptz(QsDevice qsDevice, PtzCommand ptzCommand, Integer channelId) {
        try {
            String mobileNo = qsDevice.getJtMobileNo();
            if (mobileNo == null || mobileNo.isEmpty()) {
                log.warn("[平台级联云台控制] JT1078设备手机号为空, 不处理");
                return;
            }
            
            int direction = convertToJt1078Direction(ptzCommand.direction);
            int speed = ptzCommand.speed != null ? ptzCommand.speed : 50;
            
            if (ptzCommand.isStop) {
                log.info("[平台级联云台控制] JT1078设备停止云台控制, 手机号: {}, 通道: {}", mobileNo, channelId);
                remoteJt1078Service.ptzRotate(mobileNo, channelId, 0, 0, SecurityConstants.INNER);
            } else if (ptzCommand.direction.equals("zoomin") || ptzCommand.direction.equals("zoomout")) {
                log.info("[平台级联云台控制] JT1078设备变倍控制, 手机号: {}, 通道: {}, 方向: {}, 速度: {}", 
                        mobileNo, channelId, ptzCommand.direction, speed);
                int zoomDirection = ptzCommand.direction.equals("zoomin") ? 1 : 2;
                remoteJt1078Service.ptzZoom(mobileNo, channelId, zoomDirection, speed, SecurityConstants.INNER);
            } else {
                log.info("[平台级联云台控制] JT1078设备开始云台控制, 手机号: {}, 通道: {}, 方向: {}, 速度: {}", 
                        mobileNo, channelId, ptzCommand.direction, speed);
                remoteJt1078Service.ptzRotate(mobileNo, channelId, direction, speed, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[平台级联云台控制] JT1078设备云台控制失败", e);
        }
    }

    private int convertToJt1078Direction(String direction) {
        switch (direction) {
            case "left":
                return 1;
            case "right":
                return 2;
            case "up":
                return 3;
            case "down":
                return 4;
            case "left_up":
                return 5;
            case "right_up":
                return 6;
            case "left_down":
                return 7;
            case "right_down":
                return 8;
            default:
                return 0;
        }
    }

    private void handleGb28181Ptz(QsDevice qsDevice, PtzCommand ptzCommand) {
        try {
            String gbDeviceId = qsDevice.getGbDeviceId();
            String gbChannelId = qsDevice.getGbChannelId();
            if (gbDeviceId == null || gbChannelId == null) {
                log.warn("[平台级联云台控制] GB28181设备国标ID为空, 不处理");
                return;
            }
            
            String command = convertToGb28181Command(ptzCommand.direction);
            Integer speed = ptzCommand.speed != null ? ptzCommand.speed : 50;
            
            log.info("[平台级联云台控制] GB28181设备云台控制, 设备ID: {}, 通道ID: {}, 命令: {}, 速度: {}", 
                    gbDeviceId, gbChannelId, command, speed);
            remoteGb28181Service.ptz(gbDeviceId, gbChannelId, command, speed, speed, speed, SecurityConstants.INNER);
        } catch (Exception e) {
            log.error("[平台级联云台控制] GB28181设备云台控制失败", e);
        }
    }

    private String convertToGb28181Command(String direction) {
        switch (direction) {
            case "left":
                return "left";
            case "right":
                return "right";
            case "up":
                return "up";
            case "down":
                return "down";
            case "left_up":
                return "upleft";
            case "right_up":
                return "upright";
            case "left_down":
                return "downleft";
            case "right_down":
                return "downright";
            case "zoomin":
                return "zoomin";
            case "zoomout":
                return "zoomout";
            case "stop":
            default:
                return "stop";
        }
    }

    private void handleHikIsupPtz(QsDevice qsDevice, PtzCommand ptzCommand, Integer channelId) {
        try {
            Long deviceId = qsDevice.getId();
            int ptzCmd = convertToHikIsupCmd(ptzCommand.direction);
            int speed = ptzCommand.speed != null ? ptzCommand.speed : 50;
            
            if (ptzCommand.isStop) {
                log.info("[平台级联云台控制] 海康ISUP设备停止云台控制, 设备ID: {}, 通道: {}", deviceId, channelId);
                remoteHaiKangIsupService.endPtz(deviceId, channelId, 0, 0, SecurityConstants.INNER);
            } else {
                log.info("[平台级联云台控制] 海康ISUP设备开始云台控制, 设备ID: {}, 通道: {}, 命令: {}, 速度: {}", 
                        deviceId, channelId, ptzCmd, speed);
                remoteHaiKangIsupService.startPtz(deviceId, channelId, ptzCmd, speed, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[平台级联云台控制] 海康ISUP设备云台控制失败", e);
        }
    }

    private int convertToHikIsupCmd(String direction) {
        switch (direction) {
            case "left":
                return 2;
            case "right":
                return 1;
            case "up":
                return 8;
            case "down":
                return 4;
            case "left_up":
                return 10;
            case "right_up":
                return 9;
            case "left_down":
                return 6;
            case "right_down":
                return 5;
            case "zoomin":
                return 16;
            case "zoomout":
                return 32;
            default:
                return 0;
        }
    }

    private PtzCommand parsePtzCmd(String ptzCmd) {
        if (ObjectUtils.isEmpty(ptzCmd) || ptzCmd.length() < 16) {
            return null;
        }

        try {
            PtzCommand command = new PtzCommand();
            
            String cmdCodeHex = ptzCmd.substring(6, 8);
            int cmdCode = Integer.parseInt(cmdCodeHex, 16);
            
            String speedHex = ptzCmd.substring(8, 10);
            int speed = Integer.parseInt(speedHex, 16);
            command.speed = speed;

            if (cmdCode == 0) {
                command.isStop = true;
                command.direction = "stop";
                return command;
            }

            boolean hasLeft = (cmdCode & 0x02) != 0;
            boolean hasRight = (cmdCode & 0x01) != 0;
            boolean hasUp = (cmdCode & 0x08) != 0;
            boolean hasDown = (cmdCode & 0x04) != 0;
            boolean hasZoomIn = (cmdCode & 0x10) != 0;
            boolean hasZoomOut = (cmdCode & 0x20) != 0;

            String direction;
            if (hasZoomIn) {
                direction = "zoomin";
            } else if (hasZoomOut) {
                direction = "zoomout";
            } else if (hasLeft && hasUp) {
                direction = "left_up";
            } else if (hasLeft && hasDown) {
                direction = "left_down";
            } else if (hasRight && hasUp) {
                direction = "right_up";
            } else if (hasRight && hasDown) {
                direction = "right_down";
            } else if (hasLeft) {
                direction = "left";
            } else if (hasRight) {
                direction = "right";
            } else if (hasUp) {
                direction = "up";
            } else if (hasDown) {
                direction = "down";
            } else {
                direction = "stop";
                command.isStop = true;
            }

            command.direction = direction;
            command.isStop = "stop".equals(direction);

            log.info("[平台级联云台控制] 解析PTZCmd, cmdCode: {}, speed: {}, direction: {}", 
                    Integer.toHexString(cmdCode), speed, command.direction);
            
            return command;
            
        } catch (Exception e) {
            log.error("[平台级联云台控制] 解析PTZCmd失败, PTZCmd: {}", ptzCmd, e);
            return null;
        }
    }

    private PresetCommand parsePresetCmd(String ptzCmd) {
        if (ObjectUtils.isEmpty(ptzCmd) || ptzCmd.length() < 16) {
            return null;
        }

        try {
            PresetCommand command = new PresetCommand();
            
            String cmdCodeHex = ptzCmd.substring(6, 8);
            int cmdCode = Integer.parseInt(cmdCodeHex, 16);
            
            String presetIndexHex = ptzCmd.substring(10, 12);
            int presetIndex = Integer.parseInt(presetIndexHex, 16);
            command.presetIndex = presetIndex;

            if (cmdCode == 0x81) {
                command.command = "add";
            } else if (cmdCode == 0x82) {
                command.command = "call";
            } else if (cmdCode == 0x83) {
                command.command = "delete";
            } else if (cmdCode == 0x84) {
                command.command = "query";
            } else {
                return null;
            }

            log.info("[平台级联预置点控制] 解析预置点命令, cmdCode: {}, command: {}, presetIndex: {}", 
                    Integer.toHexString(cmdCode), command.command, command.presetIndex);
            
            return command;
            
        } catch (Exception e) {
            log.error("[平台级联预置点控制] 解析预置点命令失败, PTZCmd: {}", ptzCmd, e);
            return null;
        }
    }

    private static class PtzCommand {
        String direction;
        Integer speed;
        boolean isStop = false;
    }

    private static class PresetCommand {
        String command;
        int presetIndex;
    }
}
