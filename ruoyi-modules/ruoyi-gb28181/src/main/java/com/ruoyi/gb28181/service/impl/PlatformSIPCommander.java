package com.ruoyi.gb28181.service.impl;

import com.ruoyi.gb28181.api.bean.RecordInfo;
import com.ruoyi.gb28181.api.bean.RecordItem;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.api.utils.DateUtil;
import com.ruoyi.gb28181.api.utils.SipUtils;
import com.ruoyi.gb28181.auth.DigestClientAuthenticationHelper;
import com.ruoyi.gb28181.config.SipConfig;
import com.ruoyi.gb28181.runner.SipLayer;
import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.task.platform.PlatformCascadeTaskManager;
import com.ruoyi.gb28181.transmit.SIPSender;
import com.ruoyi.gb28181.transmit.cmd.PlatformSIPRequestHeaderProvider;
import com.ruoyi.qs.api.domain.SimpleDeviceInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.SipException;
import javax.sip.SipFactory;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 平台级联SIP命令服务实现
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class PlatformSIPCommander implements IPlatformSIPCommander {

    @Autowired
    private SipConfig sipConfig;

    @Autowired
    private SipLayer sipLayer;

    @Autowired
    private SIPSender sipSender;

    @Autowired
    private PlatformSIPRequestHeaderProvider platformSIPRequestHeaderProvider;

    @Autowired
    @Lazy
    private PlatformCascadeTaskManager platformCascadeTaskManager;

    // 保存各平台的CSEQ
    private final Map<Long, Long> platformCSeqMap = new ConcurrentHashMap<>();

    // 保存各平台的CallId
    private final Map<Long, CallIdHeader> platformCallIdMap = new ConcurrentHashMap<>();

    // 保存CallId对应的请求上下文
    private final Map<String, RegisterContext> registerContextMap = new ConcurrentHashMap<>();

    // 认证帮助类
    private DigestClientAuthenticationHelper authHelper;

    @Data
    private static class RegisterContext {
        private Gb28181Platform platform;
        private String viaTag;
        private String fromTag;
        private String toTag;
        private Long cseq;
    }

    public PlatformSIPCommander() {
        try {
            this.authHelper = new DigestClientAuthenticationHelper();
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to initialize DigestClientAuthenticationHelper", e);
        }
    }

    public void handleRegisterResponse(Response response, String localIp) {
        CallIdHeader callIdHeader = (CallIdHeader) response.getHeader(CallIdHeader.NAME);
        RegisterContext context = registerContextMap.get(callIdHeader.getCallId());
        if (context == null) {
            log.warn("No register context found for callId: {}", callIdHeader.getCallId());
            return;
        }

        int statusCode = response.getStatusCode();
        if (statusCode == Response.UNAUTHORIZED) {
            log.info("[平台级联] 收到401 Unauthorized，准备发送带认证的REGISTER请求，平台: {}", context.platform.getName());
            try {
                WWWAuthenticateHeader wwwAuthenticate = (WWWAuthenticateHeader) response.getHeader(WWWAuthenticateHeader.NAME);
                if (wwwAuthenticate == null) {
                    log.warn("No WWWAuthenticate header found in 401 response");
                    return;
                }

                // 创建新的REGISTER请求，带Authorization头
                Request originalRequest = platformSIPRequestHeaderProvider.createRegisterRequest(context.platform, context.viaTag, context.fromTag, context.toTag, callIdHeader, context.cseq);
                AuthorizationHeader authHeader = authHelper.createAuthorizationHeader(
                        SipFactory.getInstance().createHeaderFactory(),
                        originalRequest,
                        wwwAuthenticate,
                        context.platform.getDeviceGbId(),
                        context.platform.getPassword()
                );

                Request authRequest = platformSIPRequestHeaderProvider.createRegisterRequest(
                        context.platform,
                        SipUtils.getNewViaTag(),
                        context.fromTag,
                        context.toTag,
                        callIdHeader,
                        context.cseq + 1,
                        authHeader
                );

                sipSender.transmitRequest(localIp, authRequest);
                platformCSeqMap.put(context.platform.getId(), context.cseq + 2);
                log.info("[平台级联] 已发送带认证的REGISTER请求，平台: {}", context.platform.getName());
            } catch (Exception e) {
                log.error("[平台级联] 处理401响应失败", e);
            }
        } else if (statusCode == Response.OK) {
            log.info("[平台级联] 注册成功！平台: {}", context.platform.getName());
            registerContextMap.remove(callIdHeader.getCallId());
            // 记录注册成功
            platformCascadeTaskManager.onRegisterSuccess(context.platform.getId());
        } else {
            log.warn("[平台级联] 收到REGISTER响应，状态码: {}, 平台: {}, 标记为离线", statusCode, context.platform.getName());
            registerContextMap.remove(callIdHeader.getCallId());
            // 注册失败，通知更新平台状态为离线，但保持任务运行以便重试
            platformCascadeTaskManager.onRegisterFailure(context.platform.getId());
        }
    }

    @Override
    public void register(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException {
        Long platformId = platform.getId();
        Long cseq = platformCSeqMap.getOrDefault(platformId, 1L);
        String viaTag = SipUtils.getNewViaTag();
        String fromTag = SipUtils.getNewFromTag();

        // 获取或创建CallId
        CallIdHeader callIdHeader = platformCallIdMap.get(platformId);
        if (callIdHeader == null) {
            callIdHeader = sipSender.getNewCallIdHeader(
                    ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp(),
                    platform.getTransport()
            );
            platformCallIdMap.put(platformId, callIdHeader);
        }

        // 保存上下文
        RegisterContext context = new RegisterContext();
        context.platform = platform;
        context.viaTag = viaTag;
        context.fromTag = fromTag;
        context.toTag = null;
        context.cseq = cseq;
        registerContextMap.put(callIdHeader.getCallId(), context);

        Request request = platformSIPRequestHeaderProvider.createRegisterRequest(
                platform,
                viaTag,
                fromTag,
                null,
                callIdHeader,
                cseq
        );

        // 发送请求
        String localIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        sipSender.transmitRequest(localIp, request);

        // 更新CSEQ
        platformCSeqMap.put(platformId, cseq + 1);

        log.info("[平台级联] 发送注册请求到平台: {}, CSEQ: {}", platform.getName(), cseq);
    }

    @Override
    public void unregister(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException {
        Long platformId = platform.getId();
        Long cseq = platformCSeqMap.getOrDefault(platformId, 1L);

        CallIdHeader callIdHeader = platformCallIdMap.get(platformId);
        if (callIdHeader == null) {
            log.warn("[平台级联] 未找到平台 {} 的CallId，无法注销", platform.getName());
            return;
        }

        // 发送Expires=0的REGISTER请求
        String originalExpires = platform.getExpires();
        platform.setExpires("0");

        Request request = platformSIPRequestHeaderProvider.createRegisterRequest(
                platform,
                SipUtils.getNewViaTag(),
                SipUtils.getNewFromTag(),
                null,
                callIdHeader,
                cseq
        );

        // 恢复原来的Expires
        platform.setExpires(originalExpires);

        String localIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        sipSender.transmitRequest(localIp, request);

        platformCSeqMap.put(platformId, cseq + 1);

        log.info("[平台级联] 发送注销请求到平台: {}", platform.getName());
    }

    @Override
    public void keepAlive(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException {
        CallIdHeader callIdHeader = sipSender.getNewCallIdHeader(
                ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp(),
                platform.getTransport()
        );

        Request request = platformSIPRequestHeaderProvider.createKeepAliveRequest(
                platform,
                SipUtils.getNewViaTag(),
                SipUtils.getNewFromTag(),
                null,
                callIdHeader
        );

        String localIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        sipSender.transmitRequest(localIp, request);

        log.debug("[平台级联] 发送心跳到平台: {}", platform.getName());
    }

    @Override
    public void sendDeviceInfo(Gb28181Platform platform) throws SipException, InvalidArgumentException, ParseException {
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        sendDeviceInfo(platform, sn);
    }

    @Override
    public void sendDeviceInfo(Gb28181Platform platform, int sn) throws SipException, InvalidArgumentException, ParseException {
        String charset = ObjectUtils.isEmpty(platform.getCharacterSet()) ? "GB2312" : platform.getCharacterSet();
        StringBuffer content = new StringBuffer();

        content.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        content.append("<Response>\r\n");
        content.append("<CmdType>DeviceInfo</CmdType>\r\n");
        content.append("<SN>" + sn + "</SN>\r\n");
        content.append("<DeviceID>" + platform.getDeviceGbId() + "</DeviceID>\r\n");
        content.append("<Result>OK</Result>\r\n");

        // 设备信息（直接放在 Response 下，不要额外的 DeviceInfo 包裹）
        if (!ObjectUtils.isEmpty(platform.getName())) {
            content.append("<DeviceName>" + platform.getName() + "</DeviceName>\r\n"); // 改成 DeviceName
        }
        content.append("<Manufacturer>" + (ObjectUtils.isEmpty(platform.getManufacturer()) ? "RUOYI-QS-NVR" : platform.getManufacturer()) + "</Manufacturer>\r\n");
        if (!ObjectUtils.isEmpty(platform.getModel())) {
            content.append("<Model>" + platform.getModel() + "</Model>\r\n");
        }
        if (!ObjectUtils.isEmpty(platform.getAddress())) {
            content.append("<Address>" + platform.getAddress() + "</Address>\r\n");
        }
        if (!ObjectUtils.isEmpty(platform.getCivilCode())) {
            content.append("<CivilCode>" + platform.getCivilCode() + "</CivilCode>\r\n");
        }
        content.append("<Parental>" + (platform.getCatalogGroup() != null ? platform.getCatalogGroup() : 0) + "</Parental>\r\n");
        if (platform.getRegisterWay() != null) {
            content.append("<RegisterWay>" + platform.getRegisterWay() + "</RegisterWay>\r\n");
        }
        if (!ObjectUtils.isEmpty(platform.getSecrecy())) {
            content.append("<Secrecy>" + platform.getSecrecy() + "</Secrecy>\r\n");
        }
        if (!ObjectUtils.isEmpty(platform.getDeviceIp())) {
            content.append("<IPAddress>" + platform.getDeviceIp() + "</IPAddress>\r\n");
        }
        if (!ObjectUtils.isEmpty(platform.getDevicePort())) {
            content.append("<Port>" + platform.getDevicePort() + "</Port>\r\n");
        }
        if (platform.getPtz() != null) {
            content.append("<PTZType>" + platform.getPtz() + "</PTZType>\r\n");
        }
        content.append("<Status>" + (Integer.valueOf(1).equals(platform.getStatus()) ? "ON" : "OFF") + "</Status>\r\n");
        content.append("<Longitude>" + 0 + "</Longitude>\r\n");
        content.append("<Latitude>" + 0 + "</Latitude>\r\n");

        content.append("</Response>\r\n");

        sendMessage(platform, content.toString());
        log.info("[平台级联] 发送设备信息到平台: {}, SN: {}", platform.getName(), sn);
    }

    @Override
    public void sendCatalog(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList) throws SipException, InvalidArgumentException, ParseException {
        int sn = (int) ((Math.random() * 9 + 1) * 100000);
        sendCatalog(platform, deviceList, sn);
    }

    @Override
    public void sendCatalog(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList, int sn) throws SipException, InvalidArgumentException, ParseException {
        String charset = ObjectUtils.isEmpty(platform.getCharacterSet()) ? "GB2312" : platform.getCharacterSet();
        StringBuffer content = new StringBuffer();

        content.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        content.append("<Response>\r\n");
        content.append("<CmdType>Catalog</CmdType>\r\n");
        content.append("<SN>" + sn + "</SN>\r\n");
        content.append("<DeviceID>" + platform.getDeviceGbId() + "</DeviceID>\r\n");

        boolean withRegion = platform.getCatalogWithRegion() != null && platform.getCatalogWithRegion() == 1;
        boolean withGroup = platform.getCatalogWithGroup() != null && platform.getCatalogWithGroup() == 1;

        if (withRegion && withGroup) {
            // 同时启用区域和分组目录推送
            log.info("[平台级联] 平台 {} 同时启用区域和分组目录推送", platform.getName());
            sendCatalogWithRegionAndGroup(platform, deviceList, content);
        } else if (withRegion) {
            // 仅启用区域目录推送
            log.info("[平台级联] 平台 {} 启用区域目录推送", platform.getName());
            sendCatalogWithRegion(platform, deviceList, content);
        } else if (withGroup) {
            // 仅启用分组目录推送
            log.info("[平台级联] 平台 {} 启用分组目录推送", platform.getName());
            sendCatalogWithGroup(platform, deviceList, content);
        } else {
            // 不启用分组/区域目录推送，只推送设备
            sendCatalogOnlyDevices(platform, deviceList, content);
        }

        content.append("</Response>\r\n");

        sendMessage(platform, content.toString());
        log.info("[平台级联] 发送目录到平台: {}, SN: {}", platform.getName(), sn);
    }

    /**
     * 只推送设备（不包含分组）
     */
    private void sendCatalogOnlyDevices(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList, StringBuffer content) {
        if (deviceList == null) {
            deviceList = List.of();
        }

        // 过滤掉 DeviceID 为空的设备
        List<SimpleDeviceInfo> validDeviceList = new java.util.ArrayList<>();
        for (SimpleDeviceInfo device : deviceList) {
            String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
            if (!ObjectUtils.isEmpty(deviceId)) {
                validDeviceList.add(device);
            } else {
                log.warn("[平台级联] 跳过 DeviceID 为空的设备: {}", device.getDeviceName());
            }
        }

        content.append("<SumNum>" + validDeviceList.size() + "</SumNum>\r\n");
        content.append("<DeviceList Num=\"" + validDeviceList.size() + "\">\r\n");

        for (SimpleDeviceInfo device : validDeviceList) {
            appendDeviceItem(content, device, platform.getDeviceGbId());
        }

        content.append("</DeviceList>\r\n");
        log.info("[平台级联] 发送目录到平台: {}, 设备数: {}", platform.getName(), validDeviceList.size());
    }

    /**
     * 推送包含分组的目录
     */
    private void sendCatalogWithGroup(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList, StringBuffer content) {
        // 获取所有分组
        List<com.ruoyi.qs.api.domain.QsGroupTree> groupList = null;
        try {
            groupList = platformCascadeTaskManager.getGroupTreeList();
        } catch (Exception e) {
            log.error("[平台级联] 获取分组树失败", e);
        }

        // 构建 id 到 deviceId 的映射表
        java.util.Map<Integer, String> idToDeviceIdMap = new java.util.HashMap<>();
        if (groupList != null) {
            for (com.ruoyi.qs.api.domain.QsGroupTree group : groupList) {
                if (group.getDeviceId() != null) {
                    idToDeviceIdMap.put(group.getId(), group.getDeviceId());
                }
            }
        }

        // 统计总数
        int groupCount = groupList != null ? groupList.size() : 0;
        int deviceCount = 0;
        if (deviceList != null) {
            for (SimpleDeviceInfo device : deviceList) {
                String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
                if (!ObjectUtils.isEmpty(deviceId)) {
                    deviceCount++;
                }
            }
        }
        int totalCount = groupCount + deviceCount;

        content.append("<SumNum>" + totalCount + "</SumNum>\r\n");
        content.append("<DeviceList Num=\"" + totalCount + "\">\r\n");

        // 添加分组项（所有分组）
        if (groupList != null) {
            log.info("[平台级联] 开始推分组，分组数量: {}, 平台DeviceGbId={}", groupList.size(), platform.getDeviceGbId());
            // 按层级排序分组，确保父分组在子分组之前推送
            List<com.ruoyi.qs.api.domain.QsGroupTree> sortedGroupList = sortGroupsByHierarchy(groupList);
            for (com.ruoyi.qs.api.domain.QsGroupTree group : sortedGroupList) {
                if (group.getDeviceId() != null) {
                    // 如果 parentDeviceId 为空，但 parentId 不为空，根据 parentId 查找对应的 deviceId
                    if (ObjectUtils.isEmpty(group.getParentDeviceId()) && group.getParentId() != null) {
                        String parentDeviceId = idToDeviceIdMap.get(group.getParentId());
                        if (!ObjectUtils.isEmpty(parentDeviceId)) {
                            log.info("[平台级联] 分组 {} 的 parentDeviceId 为空，根据 parentId={} 查找得到 parentDeviceId={}", 
                                group.getDeviceId(), group.getParentId(), parentDeviceId);
                            group.setParentDeviceId(parentDeviceId);
                        }
                    }
                    log.info("[平台级联] 推分组: DeviceId={}, Name={}, ParentDeviceId={}, BusinessGroup={}", 
                        group.getDeviceId(), group.getName(), group.getParentDeviceId(), group.getBusinessGroup());
                    // 暂时去掉业务分组过滤，先推送所有分组
                    appendGroupItem(content, group, platform.getDeviceGbId());
                }
            }
        }

        // 添加设备项
        log.info("[平台级联] 开始处理设备(分组模式)，deviceList={}, size={}", deviceList, deviceList != null ? deviceList.size() : 0);
        if (deviceList != null) {
            // 先收集所有被推送的分组 DeviceID，用于校验设备的 ParentID
            java.util.Set<String> pushedGroupIds = new java.util.HashSet<>();
            if (groupList != null) {
                for (com.ruoyi.qs.api.domain.QsGroupTree group : groupList) {
                    if (group.getDeviceId() != null) {
                        pushedGroupIds.add(group.getDeviceId());
                    }
                }
            }
            log.info("[平台级联] 已推送的分组ID列表={}", pushedGroupIds);
            
            for (SimpleDeviceInfo device : deviceList) {
                String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
                if (!ObjectUtils.isEmpty(deviceId)) {
                    String parentId = ObjectUtils.isEmpty(device.getGbParentId()) ? platform.getDeviceGbId() : device.getGbParentId();
                    // 检查 parentId 是否有效：如果 parentId 不在 pushedGroupIds 中，且不是平台 deviceId，才使用默认
                    if (parentId != null && !pushedGroupIds.contains(parentId) && !parentId.equals(platform.getDeviceGbId())) {
                        log.warn("[平台级联] 设备 {} 的 ParentID {} 无效，使用默认 ParentID {}", deviceId, parentId, platform.getDeviceGbId());
                        parentId = platform.getDeviceGbId();
                    }
                    appendDeviceItem(content, device, parentId);
                }
            }
        }

        content.append("</DeviceList>\r\n");
        log.info("[平台级联] 发送目录到平台: {}, 分组数: {}, 设备数: {}", platform.getName(), groupCount, deviceCount);
    }

    /**
     * 添加分组项
     */
    private void appendGroupItem(StringBuffer content, com.ruoyi.qs.api.domain.QsGroupTree group, String rootDeviceId) {
        String parentId = ObjectUtils.isEmpty(group.getParentDeviceId()) ? rootDeviceId : group.getParentDeviceId();
        content.append("<Item>\r\n");
        content.append("<DeviceID>" + group.getDeviceId() + "</DeviceID>\r\n");
        content.append("<Name>" + (ObjectUtils.isEmpty(group.getName()) ? "" : group.getName()) + "</Name>\r\n");
        content.append("<Manufacturer>泉视</Manufacturer>\r\n");
        if (!ObjectUtils.isEmpty(group.getCivilCode())) {
            content.append("<CivilCode>" + group.getCivilCode() + "</CivilCode>\r\n");
        }
        if (!ObjectUtils.isEmpty(group.getParentDeviceId())) {
            content.append("<ParentID>" + parentId + "</ParentID>\r\n");
        } else {
            content.append("<ParentID>" + rootDeviceId + "</ParentID>\r\n");
        }
        content.append("<Status>ON</Status>\r\n");
        content.append("<Parental>1</Parental>\r\n");
        content.append("<Longitude>0</Longitude>\r\n");
        content.append("<Latitude>0</Latitude>\r\n");
        content.append("</Item>\r\n");
    }

    /**
     * 推送包含区域的目录
     */
    private void sendCatalogWithRegion(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList, StringBuffer content) {
        // 获取所有区域
        List<com.ruoyi.qs.api.domain.QsRegionTree> regionList = null;
        try {
            regionList = platformCascadeTaskManager.getRegionTreeList();
        } catch (Exception e) {
            log.error("[平台级联] 获取区域树失败", e);
        }

        // 构建 id 到 deviceId 的映射表
        java.util.Map<Integer, String> idToDeviceIdMap = new java.util.HashMap<>();
        if (regionList != null) {
            for (com.ruoyi.qs.api.domain.QsRegionTree region : regionList) {
                if (region.getDeviceId() != null) {
                    idToDeviceIdMap.put(region.getId(), region.getDeviceId());
                }
            }
        }

        // 统计总数
        int regionCount = regionList != null ? regionList.size() : 0;
        int deviceCount = 0;
        if (deviceList != null) {
            for (SimpleDeviceInfo device : deviceList) {
                String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
                if (!ObjectUtils.isEmpty(deviceId)) {
                    deviceCount++;
                }
            }
        }
        int totalCount = regionCount + deviceCount;

        content.append("<SumNum>" + totalCount + "</SumNum>\r\n");
        content.append("<DeviceList Num=\"" + totalCount + "\">\r\n");

        // 添加区域项（所有区域）
        if (regionList != null) {
            log.info("[平台级联] 开始推区域，区域数量: {}, 平台DeviceGbId={}", regionList.size(), platform.getDeviceGbId());
            // 按层级排序区域，确保父区域在子区域之前推送
            List<com.ruoyi.qs.api.domain.QsRegionTree> sortedRegionList = sortRegionsByHierarchy(regionList);
            for (com.ruoyi.qs.api.domain.QsRegionTree region : sortedRegionList) {
                if (region.getDeviceId() != null) {
                    // 确保父区域的 deviceId 存在
                    ensureParentRegionDeviceId(region, idToDeviceIdMap);
                    appendRegionItem(content, region, platform.getDeviceGbId());
                }
            }
        }

        // 添加设备项
        if (deviceList != null) {
            // 先收集所有被推送的区域 DeviceID，用于校验设备的 ParentID
            java.util.Set<String> pushedRegionIds = new java.util.HashSet<>();
            if (regionList != null) {
                for (com.ruoyi.qs.api.domain.QsRegionTree region : regionList) {
                    if (region.getDeviceId() != null) {
                        pushedRegionIds.add(region.getDeviceId());
                    }
                }
            }
            
            for (SimpleDeviceInfo device : deviceList) {
                String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
                if (!ObjectUtils.isEmpty(deviceId)) {
                    // 设备的 gbCivilCode 是区域的 deviceId
                    String parentId = ObjectUtils.isEmpty(device.getGbCivilCode()) ? platform.getDeviceGbId() : device.getGbCivilCode();
                    
                    // 检查 parentId 是否有效：如果 parentId 不在 pushedRegionIds 中，且不是平台 deviceId，才使用默认
                    if (parentId != null && !pushedRegionIds.contains(parentId) && !parentId.equals(platform.getDeviceGbId())) {
                        log.warn("[平台级联] 设备 {} 的 ParentID {} 无效，使用默认 ParentID {}", deviceId, parentId, platform.getDeviceGbId());
                        parentId = platform.getDeviceGbId();
                    }
                    appendDeviceItem(content, device, parentId);
                }
            }
        }

        content.append("</DeviceList>\r\n");
        log.info("[平台级联] 发送目录到平台: {}, 区域数: {}, 设备数: {}", platform.getName(), regionCount, deviceCount);
    }

    /**
     * 同时推送包含区域和分组的目录
     */
    private void sendCatalogWithRegionAndGroup(Gb28181Platform platform, List<SimpleDeviceInfo> deviceList, StringBuffer content) {
        // 获取所有区域
        List<com.ruoyi.qs.api.domain.QsRegionTree> regionList = null;
        try {
            regionList = platformCascadeTaskManager.getRegionTreeList();
        } catch (Exception e) {
            log.error("[平台级联] 获取区域树失败", e);
        }

        // 获取所有分组
        List<com.ruoyi.qs.api.domain.QsGroupTree> groupList = null;
        try {
            groupList = platformCascadeTaskManager.getGroupTreeList();
        } catch (Exception e) {
            log.error("[平台级联] 获取分组树失败", e);
        }

        // 构建 id 到 deviceId 的映射表
        java.util.Map<Integer, String> regionIdToDeviceIdMap = new java.util.HashMap<>();
        java.util.Map<Integer, String> groupIdToDeviceIdMap = new java.util.HashMap<>();
        if (regionList != null) {
            for (com.ruoyi.qs.api.domain.QsRegionTree region : regionList) {
                if (region.getDeviceId() != null) {
                    regionIdToDeviceIdMap.put(region.getId(), region.getDeviceId());
                }
            }
        }
        if (groupList != null) {
            for (com.ruoyi.qs.api.domain.QsGroupTree group : groupList) {
                if (group.getDeviceId() != null) {
                    groupIdToDeviceIdMap.put(group.getId(), group.getDeviceId());
                }
            }
        }

        // 统计总数
        int regionCount = regionList != null ? regionList.size() : 0;
        int groupCount = groupList != null ? groupList.size() : 0;
        int deviceCount = 0;
        if (deviceList != null) {
            for (SimpleDeviceInfo device : deviceList) {
                String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
                if (!ObjectUtils.isEmpty(deviceId)) {
                    deviceCount++;
                }
            }
        }
        int totalCount = regionCount + groupCount + deviceCount;

        content.append("<SumNum>" + totalCount + "</SumNum>\r\n");
        content.append("<DeviceList Num=\"" + totalCount + "\">\r\n");

        // 添加区域项（所有区域）
        if (regionList != null) {
            log.info("[平台级联] 开始推区域，区域数量: {}, 平台DeviceGbId={}", regionList.size(), platform.getDeviceGbId());
            // 按层级排序区域，确保父区域在子区域之前推送
            List<com.ruoyi.qs.api.domain.QsRegionTree> sortedRegionList = sortRegionsByHierarchy(regionList);
            for (com.ruoyi.qs.api.domain.QsRegionTree region : sortedRegionList) {
                if (region.getDeviceId() != null) {
                    // 确保父区域的 deviceId 存在
                    ensureParentRegionDeviceId(region, regionIdToDeviceIdMap);
                    appendRegionItem(content, region, platform.getDeviceGbId());
                }
            }
        }

        // 添加分组项（所有分组）
        if (groupList != null) {
            log.info("[平台级联] 开始推分组，分组数量: {}, 平台DeviceGbId={}", groupList.size(), platform.getDeviceGbId());
            List<com.ruoyi.qs.api.domain.QsGroupTree> sortedGroupList = sortGroupsByHierarchy(groupList);
            for (com.ruoyi.qs.api.domain.QsGroupTree group : sortedGroupList) {
                if (group.getDeviceId() != null) {
                    // 如果 parentDeviceId 为空，但 parentId 不为空，根据 parentId 查找对应的 deviceId
                    if (ObjectUtils.isEmpty(group.getParentDeviceId()) && group.getParentId() != null) {
                        String parentDeviceId = groupIdToDeviceIdMap.get(group.getParentId());
                        if (!ObjectUtils.isEmpty(parentDeviceId)) {
                            log.info("[平台级联] 分组 {} 的 parentDeviceId 为空，根据 parentId={} 查找得到 parentDeviceId={}",
                                group.getDeviceId(), group.getParentId(), parentDeviceId);
                            group.setParentDeviceId(parentDeviceId);
                        }
                    }
                    appendGroupItem(content, group, platform.getDeviceGbId());
                }
            }
        }

        // 添加设备项
        if (deviceList != null) {
            // 先收集所有被推送的区域和分组 DeviceID，用于校验设备的 ParentID
            java.util.Set<String> pushedIds = new java.util.HashSet<>();
            if (regionList != null) {
                for (com.ruoyi.qs.api.domain.QsRegionTree region : regionList) {
                    if (region.getDeviceId() != null) {
                        pushedIds.add(region.getDeviceId());
                    }
                }
            }
            if (groupList != null) {
                for (com.ruoyi.qs.api.domain.QsGroupTree group : groupList) {
                    if (group.getDeviceId() != null) {
                        pushedIds.add(group.getDeviceId());
                    }
                }
            }
            
            for (SimpleDeviceInfo device : deviceList) {
                String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
                if (!ObjectUtils.isEmpty(deviceId)) {
                    // 同时启用区域和分组时，先检查该 parentId 是否在已推送的列表中
                    String parentId = platform.getDeviceGbId();
                    if (!ObjectUtils.isEmpty(device.getGbParentId()) && pushedIds.contains(device.getGbParentId())) {
                        parentId = device.getGbParentId();
                    } else if (!ObjectUtils.isEmpty(device.getGbCivilCode()) && pushedIds.contains(device.getGbCivilCode())) {
                        parentId = device.getGbCivilCode();
                    }
                    
                    // 检查 parentId 是否有效：如果 parentId 不在 pushedIds 中，且不是平台 deviceId，才使用默认
                    if (parentId != null && !pushedIds.contains(parentId) && !parentId.equals(platform.getDeviceGbId())) {
                        log.warn("[平台级联] 设备 {} 的 ParentID {} 无效，使用默认 ParentID {}", deviceId, parentId, platform.getDeviceGbId());
                        parentId = platform.getDeviceGbId();
                    }
                    appendDeviceItem(content, device, parentId);
                }
            }
        }

        content.append("</DeviceList>\r\n");
        log.info("[平台级联] 发送目录到平台: {}, 区域数: {}, 分组数: {}, 设备数: {}", platform.getName(), regionCount, groupCount, deviceCount);
    }

    /**
     * 按层级排序区域
     */
    private List<com.ruoyi.qs.api.domain.QsRegionTree> sortRegionsByHierarchy(List<com.ruoyi.qs.api.domain.QsRegionTree> regionList) {
        Map<Integer, List<com.ruoyi.qs.api.domain.QsRegionTree>> regionByParentId = new HashMap<>();
        List<com.ruoyi.qs.api.domain.QsRegionTree> rootRegions = new ArrayList<>();
        for (com.ruoyi.qs.api.domain.QsRegionTree region : regionList) {
            if (region.getParentId() == null) rootRegions.add(region);
            else regionByParentId.computeIfAbsent(region.getParentId(), k -> new ArrayList<>()).add(region);
        }
        List<com.ruoyi.qs.api.domain.QsRegionTree> sortedList = new ArrayList<>();
        Queue<com.ruoyi.qs.api.domain.QsRegionTree> queue = new LinkedList<>(rootRegions);
        while (!queue.isEmpty()) {
            com.ruoyi.qs.api.domain.QsRegionTree current = queue.poll();
            sortedList.add(current);
            List<com.ruoyi.qs.api.domain.QsRegionTree> children = regionByParentId.get(current.getId());
            if (children != null) queue.addAll(children);
        }
        return sortedList;
    }

    /**
     * 按层级排序分组
     */
    private List<com.ruoyi.qs.api.domain.QsGroupTree> sortGroupsByHierarchy(List<com.ruoyi.qs.api.domain.QsGroupTree> groupList) {
        Map<Integer, List<com.ruoyi.qs.api.domain.QsGroupTree>> groupByParentId = new HashMap<>();
        List<com.ruoyi.qs.api.domain.QsGroupTree> rootGroups = new ArrayList<>();
        for (com.ruoyi.qs.api.domain.QsGroupTree group : groupList) {
            if (group.getParentId() == null) rootGroups.add(group);
            else groupByParentId.computeIfAbsent(group.getParentId(), k -> new ArrayList<>()).add(group);
        }
        List<com.ruoyi.qs.api.domain.QsGroupTree> sortedList = new ArrayList<>();
        Queue<com.ruoyi.qs.api.domain.QsGroupTree> queue = new LinkedList<>(rootGroups);
        while (!queue.isEmpty()) {
            com.ruoyi.qs.api.domain.QsGroupTree current = queue.poll();
            sortedList.add(current);
            List<com.ruoyi.qs.api.domain.QsGroupTree> children = groupByParentId.get(current.getId());
            if (children != null) queue.addAll(children);
        }
        return sortedList;
    }

    /**
     * 确保父区域的 deviceId 存在
     */
    private void ensureParentRegionDeviceId(com.ruoyi.qs.api.domain.QsRegionTree region, java.util.Map<Integer, String> idToDeviceIdMap) {
        if (ObjectUtils.isEmpty(region.getParentDeviceId()) && region.getParentId() != null) {
            String parentDeviceId = idToDeviceIdMap.get(region.getParentId());
            if (!ObjectUtils.isEmpty(parentDeviceId)) {
                log.info("[平台级联] 区域 {} 的 parentDeviceId 为空，根据 parentId={} 查找得到 parentDeviceId={}", 
                    region.getDeviceId(), region.getParentId(), parentDeviceId);
                region.setParentDeviceId(parentDeviceId);
            }
        }
    }

    /**
     * 添加区域项
     */
    private void appendRegionItem(StringBuffer content, com.ruoyi.qs.api.domain.QsRegionTree region, String rootDeviceId) {
        String parentId = ObjectUtils.isEmpty(region.getParentDeviceId()) ? rootDeviceId : region.getParentDeviceId();
        content.append("<Item>\r\n");
        content.append("<DeviceID>" + region.getDeviceId() + "</DeviceID>\r\n");
        content.append("<Name>" + (ObjectUtils.isEmpty(region.getName()) ? "" : region.getName()) + "</Name>\r\n");
        content.append("<Manufacturer>泉视</Manufacturer>\r\n");
        if (!ObjectUtils.isEmpty(region.getDeviceId())) {
            content.append("<CivilCode>" + region.getDeviceId() + "</CivilCode>\r\n");
        }
        if (!ObjectUtils.isEmpty(region.getParentDeviceId())) {
            content.append("<ParentID>" + parentId + "</ParentID>\r\n");
        } else {
            content.append("<ParentID>" + rootDeviceId + "</ParentID>\r\n");
        }
        content.append("<Status>ON</Status>\r\n");
        content.append("<Parental>1</Parental>\r\n");
        content.append("<Longitude>0</Longitude>\r\n");
        content.append("<Latitude>0</Latitude>\r\n");
        content.append("</Item>\r\n");
    }

    /**
     * 添加设备项
     */
    private void appendDeviceItem(StringBuffer content, SimpleDeviceInfo device, String parentId) {
        String deviceId = ObjectUtils.isEmpty(device.getGbCode()) ? device.getGbDeviceId() : device.getGbCode();
        
        content.append("<Item>\r\n");
        content.append("<DeviceID>" + deviceId + "</DeviceID>\r\n");
        content.append("<Name>" + (ObjectUtils.isEmpty(device.getDeviceName()) ? "" : device.getDeviceName()) + "</Name>\r\n");
        content.append("<Manufacturer>" + (ObjectUtils.isEmpty(device.getManufacturer()) ? "泉视" : device.getManufacturer()) + "</Manufacturer>\r\n");
        if (!ObjectUtils.isEmpty(device.getAddress())) {
            content.append("<Address>" + device.getAddress() + "</Address>\r\n");
        }
        if (!ObjectUtils.isEmpty(device.getGbCivilCode())) {
            content.append("<CivilCode>" + device.getGbCivilCode() + "</CivilCode>\r\n");
        }
        content.append("<Status>" + ("ON".equals(device.getDeviceStatus()) ? "ON" : "OFF") + "</Status>\r\n");
        content.append("<Parental>0</Parental>\r\n");
        if (device.getChannel() != null) {
            content.append("<Channel>" + device.getChannel() + "</Channel>\r\n");
        }
        if (!ObjectUtils.isEmpty(device.getIpAddress())) {
            content.append("<IPAddress>" + device.getIpAddress() + "</IPAddress>\r\n");
        }
        if (device.getPort() != null) {
            content.append("<Port>" + device.getPort() + "</Port>\r\n");
        }
        if (!ObjectUtils.isEmpty(device.getPtzType())) {
            content.append("<PTZType>" + device.getPtzType() + "</PTZType>\r\n");
        }
        if (!ObjectUtils.isEmpty(parentId)) {
            content.append("<ParentID>" + parentId + "</ParentID>\r\n");
        }
        content.append("<Longitude>" + (ObjectUtils.isEmpty(device.getLongitude()) ? 0 : device.getLongitude()) + "</Longitude>\r\n");
        content.append("<Latitude>" + (ObjectUtils.isEmpty(device.getLatitude()) ? 0 : device.getLatitude()) + "</Latitude>\r\n");
        content.append("</Item>\r\n");
    }

    @Override
    public void sendDeviceStatus(Gb28181Platform platform, int sn) throws SipException, InvalidArgumentException, ParseException {
        String charset = ObjectUtils.isEmpty(platform.getCharacterSet()) ? "GB2312" : platform.getCharacterSet();
        StringBuffer content = new StringBuffer();

        content.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        content.append("<Response>\r\n");
        content.append("<CmdType>DeviceStatus</CmdType>\r\n");
        content.append("<SN>" + sn + "</SN>\r\n");
        content.append("<DeviceID>" + platform.getDeviceGbId() + "</DeviceID>\r\n");
        content.append("<Result>OK</Result>\r\n");

        content.append("<Online>ON</Online>\r\n");
        content.append("<Status>OK</Status>\r\n");

        content.append("</Response>\r\n");

        sendMessage(platform, content.toString());
        log.info("[平台级联] 发送设备状态到平台: {}, SN: {}", platform.getName(), sn);
    }

    @Override
    public void sendRecordInfo(Gb28181Platform platform, RecordInfo recordInfo, int sn) throws SipException, InvalidArgumentException, ParseException {
        String charset = ObjectUtils.isEmpty(platform.getCharacterSet()) ? "GB2312" : platform.getCharacterSet();
        StringBuffer content = new StringBuffer();

        content.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>\r\n");
        content.append("<Response>\r\n");
        content.append("<CmdType>RecordInfo</CmdType>\r\n");
        content.append("<SN>" + sn + "</SN>\r\n");
        content.append("<DeviceID>" + (recordInfo.getChannelId() != null ? recordInfo.getChannelId() : platform.getDeviceGbId()) + "</DeviceID>\r\n");
        
        if (recordInfo.getName() != null) {
            content.append("<Name>" + recordInfo.getName() + "</Name>\r\n");
        }
        
        int sumNum = recordInfo.getRecordList() != null ? recordInfo.getRecordList().size() : 0;
        content.append("<SumNum>" + sumNum + "</SumNum>\r\n");
        
        if (recordInfo.getRecordList() != null && !recordInfo.getRecordList().isEmpty()) {
            content.append("<RecordList>\r\n");
            for (RecordItem item : recordInfo.getRecordList()) {
                content.append("<Item>\r\n");
                if (item.getDeviceId() != null) {
                    content.append("<DeviceID>" + item.getDeviceId() + "</DeviceID>\r\n");
                }
                if (item.getName() != null) {
                    content.append("<Name>" + item.getName() + "</Name>\r\n");
                }
                if (item.getFilePath() != null) {
                    content.append("<FilePath>" + item.getFilePath() + "</FilePath>\r\n");
                }
                if (item.getFileSize() != null) {
                    content.append("<FileSize>" + item.getFileSize() + "</FileSize>\r\n");
                }
                if (item.getAddress() != null) {
                    content.append("<Address>" + item.getAddress() + "</Address>\r\n");
                }
                if (item.getStartTime() != null) {
                    content.append("<StartTime>" + DateUtil.yyyy_MM_dd_HH_mm_ssToISO8601(item.getStartTime()) + "</StartTime>\r\n");
                }
                if (item.getEndTime() != null) {
                    content.append("<EndTime>" + DateUtil.yyyy_MM_dd_HH_mm_ssToISO8601(item.getEndTime()) + "</EndTime>\r\n");
                }
                content.append("<Secrecy>" + item.getSecrecy() + "</Secrecy>\r\n");
                if (item.getType() != null) {
                    content.append("<Type>" + item.getType() + "</Type>\r\n");
                }
                if (item.getRecorderId() != null) {
                    content.append("<RecorderID>" + item.getRecorderId() + "</RecorderID>\r\n");
                }
                content.append("</Item>\r\n");
            }
            content.append("</RecordList>\r\n");
        }

        content.append("</Response>\r\n");

        sendMessage(platform, content.toString());
        log.info("[平台级联] 发送录像信息到平台: {}, SN: {}, 录像数: {}", platform.getName(), sn, sumNum);
    }

    /**
     * 发送MESSAGE消息
     */
    private void sendMessage(Gb28181Platform platform, String content) throws SipException, InvalidArgumentException, ParseException {
        CallIdHeader callIdHeader = sipSender.getNewCallIdHeader(
                ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp(),
                platform.getTransport()
        );

        Request request = platformSIPRequestHeaderProvider.createMessageRequest(
                platform,
                content,
                SipUtils.getNewViaTag(),
                SipUtils.getNewFromTag(),
                null,
                callIdHeader
        );

        String localIp = ObjectUtils.isEmpty(platform.getDeviceIp()) ? sipLayer.getLocalIp(null) : platform.getDeviceIp();
        sipSender.transmitRequest(localIp, request);
    }
}
