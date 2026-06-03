package com.ruoyi.onvif.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.onvif.DiscoveryManager;
import com.ruoyi.onvif.OnvifManager;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.domain.FetchMainAndSubStreamUris;
import com.ruoyi.onvif.domain.WSDiscoveryDevice;
import com.ruoyi.onvif.enums.AuthTypeEnum;
import com.ruoyi.onvif.listeners.DiscoveryListener;
import com.ruoyi.onvif.listeners.MediaInfoListener;
import com.ruoyi.onvif.listeners.NetworkInfoListener;
import com.ruoyi.onvif.models.*;
import com.ruoyi.onvif.listeners.StorageInfoListener;
import com.ruoyi.onvif.service.IOnvifService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.RemoteQsDeviceSnapshotService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @FileName OnvifServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-04-09
 **/
@Slf4j
@Service
public class OnvifServiceImpl implements IOnvifService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteQsDeviceSnapshotService remoteQsDeviceSnapshotService;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.prefix}")
    private String filePrefix;

    private static final String ONVIF_DEVICES = "ONVIF:DEVICES";


    /**
     * 定时任务获取内网onvif设备
     */
    @Async("taskExecutor")
    @Override
    public void task() {
        log.info("🚀 开始执行 ONVIF 设备发现任务...");

        try {
            // 【准备阶段】获取 Redis 中现有的所有设备 IP，作为“待删除候选集”
            Set<Object> existingIps = redisTemplate.opsForHash().keys(ONVIF_DEVICES);
            // 使用线程安全的集合，防止并发修改异常
            Set<Object> staleIps = new CopyOnWriteArraySet<>();
            if (existingIps != null) {
                staleIps.addAll(existingIps);
            }

            DiscoveryManager manager = new DiscoveryManager();
            // 设置发现超时时间
            manager.setDiscoveryTimeout(30000);
            DiscoveryListener listener = new DiscoveryListener() {
                @Override
                public void onDiscoveryStarted() {
                    log.info("ONVIF 设备发现开始...");
                }

                @Override
                public void onDevicesFound(List<Device> devices) {
                    if (devices == null || devices.isEmpty()) {
                        log.info("未发现任何 ONVIF 设备");
                        return;
                    }

                    ArrayList<WSDiscoveryDevice> devicesList = new ArrayList<>();
                    int deviceCount = 0;

                    for (Device device : devices) {
                        try {
                            if (device == null || device.getHostName() == null) {
                                continue;
                            }
                            URL url = new URL(device.getHostName());
                            String ip = url.getHost();

                            staleIps.remove(ip);

                            WSDiscoveryDevice dto = new WSDiscoveryDevice();
                            dto.setHostName(device.getHostName());
                            dto.setIp(ip);
                            devicesList.add(dto);
                            deviceCount++;
                        } catch (Exception e) {
                            log.warn("解析设备信息失败: {}", e.getMessage());
                        }
                    }

                    log.info("发现 {} 个 ONVIF 设备", deviceCount);

                    if (!devicesList.isEmpty()) {
                        for (WSDiscoveryDevice device : devicesList) {
                            // 存储对象
                            redisTemplate.opsForHash().put(ONVIF_DEVICES, device.getIp(), device);
                        }
                    }
                }
            };

            // 【执行扫描】
            manager.discover(listener);

            // 【清理离线设备 - 不管有没有发现新设备，都要清理】
            if (!staleIps.isEmpty()) {
                log.info("🧹 扫描结束，发现 {} 个设备离线，正在清理...", staleIps.size());
                for (Object offlineIp : staleIps) {
                    redisTemplate.opsForHash().delete(ONVIF_DEVICES, offlineIp);
                    log.info("❌ 已删除离线设备: {}", offlineIp);
                }
            }
        } catch (Exception e) {
            log.error("执行 ONVIF 设备发现任务失败", e);
        }
    }

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice
     */
    @Override
    public com.ruoyi.onvif.api.domain.OnvifDevice verifyOnvifDeviceLogin(WSOnvifDevice onvifDevice) {
        if (onvifDevice == null) {
            throw new IllegalArgumentException("设备信息不能为空");
        }
        
        com.ruoyi.onvif.api.domain.OnvifDevice returnOnvifDevice = new com.ruoyi.onvif.api.domain.OnvifDevice();

        // WS-Username token
        if (AuthTypeEnum.WS_USERNAME_TOKEN.getCode().equals(onvifDevice.getAuth())) {
            FetchMainAndSubStreamUris onvifDeviceInfo = getOnvifDeviceInfo(onvifDevice);
            returnOnvifDevice.setIp(onvifDevice.getIp());
            returnOnvifDevice.setFirm(onvifDeviceInfo.getFirm());
            returnOnvifDevice.setFirmwareVersion(onvifDeviceInfo.getFirmwareVersion());
            returnOnvifDevice.setModel(onvifDeviceInfo.getModel());
            returnOnvifDevice.setStreamUris(onvifDeviceInfo.getStreamUris());
            returnOnvifDevice.setUserName(onvifDevice.getUsername());
            returnOnvifDevice.setPassword(onvifDevice.getPassword());
            returnOnvifDevice.setHostName(onvifDevice.getHostName());
            return returnOnvifDevice;
        }


        List<String> streamUris = new ArrayList<>();
        // 使用 AtomicReference 来跟踪是否成功获取了信息
        AtomicBoolean success = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(2);
        // Digest
        if (AuthTypeEnum.DIGEST.getCode().equals(onvifDevice.getAuth())) {
            OnvifManager onvifManager = new OnvifManager();
            OnvifDevice device2 = new OnvifDevice(onvifDevice.getIp(), onvifDevice.getUsername(), onvifDevice.getPassword());

            onvifManager.getMediaProfiles(device2, (device, mediaProfiles) -> {
                try {
                    if (mediaProfiles == null || mediaProfiles.isEmpty()) {
                        return;
                    }
                    int[] remaining = {mediaProfiles.size()};

                    for (OnvifMediaProfile profile : mediaProfiles) {
                        onvifManager.getMediaStreamURI(device2, profile, (device1, prof, uri) -> {
                            try {
                                if (uri != null) {
                                    streamUris.add(uri);
                                }
                            } finally {
                                synchronized (remaining) {
                                    remaining[0]--;
                                    if (remaining[0] <= 0) {
                                        success.set(true);
                                        latch.countDown();
                                    }
                                }
                            }
                        });
                    }
                } finally {
                    // 如果没有 profile，确保 countDown 被调用
                    if (mediaProfiles == null || mediaProfiles.isEmpty()) {
                        latch.countDown();
                    }
                }
            });
            onvifManager.getDeviceInformation(device2, (device, info) -> {
                try {
                    returnOnvifDevice.setIp(onvifDevice.getIp());
                    if (info != null) {
                        returnOnvifDevice.setFirm(info.getManufacturer());
                        returnOnvifDevice.setModel(info.getModel());
                        returnOnvifDevice.setFirmwareVersion(info.getFirmwareVersion());
                        success.set(true);
                    }
                    returnOnvifDevice.setUserName(onvifDevice.getUsername());
                    returnOnvifDevice.setPassword(onvifDevice.getPassword());
                    returnOnvifDevice.setHostName(onvifDevice.getHostName());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            boolean completed = latch.await(10, TimeUnit.SECONDS);
            if (!completed) {
                log.warn("ONVIF 获取信息超时");
            }
            
            // 如果没有成功获取到信息，抛出异常
            if (!success.get() && streamUris.isEmpty()) {
                throw new RuntimeException("未能成功连接到 ONVIF 设备或认证失败");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("等待中断", e);
            throw new RuntimeException("等待设备响应时被中断", e);
        }
        returnOnvifDevice.setStreamUris(streamUris);
        return returnOnvifDevice;
    }

    /**
     * 获取onvif设备列表
     */
    @Override
    public ArrayList<WSDiscoveryDevice> getOnvifDeviceList() {
        ArrayList<WSDiscoveryDevice> deviceList = new ArrayList<>();
        try {
            Map<Object, Object> rawMap = redisTemplate.opsForHash().entries(ONVIF_DEVICES);
            if (rawMap == null || rawMap.isEmpty()) {
                return deviceList;
            }

            for (Object value : rawMap.values()) {
                if (value == null) {
                    continue;
                }
                // 先将对象转为 JSON 字符串，再转为实体类
                String jsonString = JSON.toJSONString(value);
                WSDiscoveryDevice device = JSON.parseObject(jsonString, WSDiscoveryDevice.class);
                if (device != null) {
                    deviceList.add(device);
                }
            }
        } catch (Exception e) {
            log.error("获取 ONVIF 设备列表失败", e);
        }

        return deviceList;
    }

    /**
     * 获取设备信息
     *
     * @param bo
     * @return
     */
    public static FetchMainAndSubStreamUris getOnvifDeviceInfo(WSOnvifDevice onvifDevice) {
        if (onvifDevice == null) {
            throw new IllegalArgumentException("设备信息不能为空");
        }
        // 先获取基本信息
        FetchMainAndSubStreamUris mercury = getMercury(onvifDevice);
        // 再获取视频流token
        List<String> profileTokens = getProfileToken(onvifDevice);
        if (profileTokens != null && !profileTokens.isEmpty()) {
            // 根据token获取播放地址
            for (String token : profileTokens) {
                if (token == null || token.isEmpty()) {
                    continue;
                }
                try {
                    String urlByToken = getProfilesUrlByToken(onvifDevice, token);
                    if (urlByToken != null && urlByToken.startsWith("rtsp://")) {
                        String replace = urlByToken.replace("rtsp://", "rtsp://" + onvifDevice.getUsername() + ":" + onvifDevice.getPassword() + "@");
                        mercury.addStreamUri(replace);
                    }
                } catch (Exception e) {
                    // 单个获取失败不影响其他
                }
            }
        }
        return mercury;
    }

    /**
     * 获取视频流地址
     *
     * @return
     */
    public static String getProfilesUrlByToken(WSOnvifDevice onvifDevice, String profileToken) {
        if (onvifDevice == null || profileToken == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        
        byte[] nonceBytes = RandomUtil.randomBytes(16);
        String nonce = Base64.encode(nonceBytes);
        String created = Instant.now().toString();
        String passwordDigest = calculatePasswordDigest(nonceBytes, created, onvifDevice.getPassword());
        String soapRequest = GetProfilesUrl(onvifDevice.getUsername(), nonce, created, passwordDigest, profileToken);
        String url = "http://" + onvifDevice.getIp() + "/onvif/media_service";
        HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
        if (response.getStatus() == 200) {
            try {
                return parseSoapResponseProfilesUrlByToken(response.body());
            } catch (Exception e) {
                throw new RuntimeException("解析视频流地址失败: " + e.getMessage(), e);
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取视频流地址失败，状态码: " + response.getStatus());
    }

    // 获取视频流地址 -- 解析
    private static String parseSoapResponseProfilesUrlByToken(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isEmpty()) {
            throw new IllegalArgumentException("响应内容不能为空");
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        NodeList uriNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver20/media/wsdl", "Uri");
        if (uriNodes.getLength() > 0) {
            return uriNodes.item(0).getTextContent();
        } else {
            throw new RuntimeException("Uri not found in the SOAP response");
        }
    }

    /**
     * 获取基本信息
     *
     * @param bo
     * @return
     */
    private static FetchMainAndSubStreamUris getMercury(WSOnvifDevice onvifDevice) {
        if (onvifDevice == null) {
            throw new IllegalArgumentException("设备信息不能为空");
        }
        
        byte[] nonceBytes = RandomUtil.randomBytes(16);
        String nonce = Base64.encode(nonceBytes);
        String created = Instant.now().toString();
        String passwordDigest = calculatePasswordDigest(nonceBytes, created, onvifDevice.getPassword());
        String soapRequest = GetDeviceInformation(onvifDevice.getUsername(), nonce, created, passwordDigest);
        String url = "http://" + onvifDevice.getIp() + "/onvif/media_service";
        HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
        if (response.getStatus() == 200) {
            try {
                return parseSoapResponse(response.body());
            } catch (Exception e) {
                throw new RuntimeException("解析设备信息失败: " + e.getMessage(), e);
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取基本信息失败，状态码: " + response.getStatus());
    }

    // 生成token
    private static String calculatePasswordDigest(byte[] nonceBytes, String created, String password) {
        if (nonceBytes == null || created == null || password == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        
        byte[] createdBytes = created.getBytes(CharsetUtil.CHARSET_UTF_8);
        byte[] passwordBytes = password.getBytes(CharsetUtil.CHARSET_UTF_8);
        byte[] combinedBytes = new byte[nonceBytes.length + createdBytes.length + passwordBytes.length];
        System.arraycopy(nonceBytes, 0, combinedBytes, 0, nonceBytes.length);
        System.arraycopy(createdBytes, 0, combinedBytes, nonceBytes.length, createdBytes.length);
        System.arraycopy(passwordBytes, 0, combinedBytes, nonceBytes.length + createdBytes.length, passwordBytes.length);
        byte[] sha1Bytes = DigestUtil.sha1(combinedBytes);
        return Base64.encode(sha1Bytes);
    }

    // 获取设备信息
    private static String GetDeviceInformation(String username, String nonce, String created, String passwordDigest) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tds=\"http://www.onvif.org/ver10/device/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" + "  <s:Header xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <soap:Body>\n" + "    <tds:GetDeviceInformation />\n" + "  </soap:Body>\n" + "</soap:Envelope>";
    }

    //获取基本信息 -- 解析
    private static FetchMainAndSubStreamUris parseSoapResponse(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isEmpty()) {
            throw new IllegalArgumentException("响应内容不能为空");
        }
        
        // 使用 DOM 解析器解析 XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));

        // 获取设备信息节点
        NodeList manufacturerNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "Manufacturer");
        NodeList modelNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "Model");
        NodeList firmwareVersionNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "FirmwareVersion");

        // 提取信息
        String manufacturer = manufacturerNodes.getLength() > 0 ? manufacturerNodes.item(0).getTextContent() : "";
        String model = modelNodes.getLength() > 0 ? modelNodes.item(0).getTextContent() : "";
        String firmwareVersion = firmwareVersionNodes.getLength() > 0 ? firmwareVersionNodes.item(0).getTextContent() : "";
        
        FetchMainAndSubStreamUris vo = new FetchMainAndSubStreamUris();
        vo.setFirmwareVersion(firmwareVersion);
        vo.setModel(model);
        vo.setFirm(manufacturer);
        return vo;
    }

    /**
     * 获取流信息token
     *
     * @param bo
     * @return
     */
    public static List<String> getProfileToken(WSOnvifDevice onvifDevice) {
        if (onvifDevice == null) {
            throw new IllegalArgumentException("设备信息不能为空");
        }
        
        byte[] nonceBytes = RandomUtil.randomBytes(16);
        String nonce = Base64.encode(nonceBytes);
        String created = Instant.now().toString();
        String passwordDigest = calculatePasswordDigest(nonceBytes, created, onvifDevice.getPassword());
        String soapRequest = GetProfiles(onvifDevice.getUsername(), nonce, created, passwordDigest);
        String url = "http://" + onvifDevice.getIp() + "/onvif/media_service";
        HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
        if (response.getStatus() == 200) {
            try {
                return parseSoapResponseProfileToken(response.body());
            } catch (Exception e) {
                throw new RuntimeException("解析流信息失败: " + e.getMessage(), e);
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取流token失败，状态码: " + response.getStatus());

    }

    // 获取流信息
    private static String GetProfiles(String username, String nonce, String created, String passwordDigest) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <GetProfiles xmlns=\"http://www.onvif.org/ver20/media/wsdl\" />\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    // 获取流信息token -- 解析
    private static List<String> parseSoapResponseProfileToken(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> profileNames = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        NodeList profilesNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver20/media/wsdl", "Profiles");
        for (int i = 0; i < profilesNodes.getLength(); i++) {
            Element profileElement = (Element) profilesNodes.item(i);
            String token = profileElement.getAttribute("token");
            if (token != null && !token.isEmpty()) {
                profileNames.add(token);
            }
        }
        return profileNames;
    }

    // 获取流地址
    private static String GetProfilesUrl(String username, String nonce, String created, String passwordDigest, String profileToken) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <GetStreamUri xmlns=\"http://www.onvif.org/ver20/media/wsdl\">\n" + "      <Protocol>RtspUnicast</Protocol>\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "    </GetStreamUri>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 开始云台控制
     */
    @Override
    public void startPtzControl(String deviceIp, String username, String password, String direction, Integer speed) {
        log.info("🚀 开始执行 ONVIF 云台控制... 设备IP: {}, 方向: {}, 速度: {}", deviceIp, direction, speed);
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            // 根据方向计算pan/tilt/zoom值
            float pan = 0;
            float tilt = 0;
            float zoom = 0;
            float speedValue = speed != null ? speed / 100.0f : 0.5f;
            
            switch (direction) {
                case "left":
                    pan = -speedValue;
                    break;
                case "right":
                    pan = speedValue;
                    break;
                case "up":
                    tilt = speedValue;
                    break;
                case "down":
                    tilt = -speedValue;
                    break;
                case "left_up":
                    pan = -speedValue;
                    tilt = speedValue;
                    break;
                case "left_down":
                    pan = -speedValue;
                    tilt = -speedValue;
                    break;
                case "right_up":
                    pan = speedValue;
                    tilt = speedValue;
                    break;
                case "right_down":
                    pan = speedValue;
                    tilt = -speedValue;
                    break;
                case "zoomin":
                    zoom = speedValue;
                    break;
                case "zoomout":
                    zoom = -speedValue;
                    break;
                case "near":
                case "far":
                case "in":
                case "out":
                    // 对于聚焦和光圈控制，我们使用专门的SOAP请求
                    sendFocusIrisControl(deviceIp, username, password, profileToken, direction, speedValue);
                    return;
                default:
                    throw new RuntimeException("不支持的方向: " + direction);
            }
            
            // 发送连续移动请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = ContinuousMoveSoapRequest(username, nonce, created, passwordDigest, profileToken, pan, tilt, zoom);
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ ONVIF 云台控制发送成功");
            } else {
                log.error("❌ ONVIF 云台控制发送失败，状态码: {}", response.getStatus());
                throw new RuntimeException("ONVIF 云台控制发送失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 云台控制失败", e);
            throw new RuntimeException("执行 ONVIF 云台控制失败: " + e.getMessage(), e);
        }
    }

    /**
     * 发送聚焦和光圈控制请求
     */
    private void sendFocusIrisControl(String deviceIp, String username, String password, String profileToken, String direction, float speedValue) {
        try {
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            float focus = 0;
            float iris = 0;
            
            switch (direction) {
                case "near":
                    focus = -speedValue;
                    break;
                case "far":
                    focus = speedValue;
                    break;
                case "in":
                    iris = speedValue;
                    break;
                case "out":
                    iris = -speedValue;
                    break;
            }
            
            String soapRequest = ContinuousMoveFocusIrisSoapRequest(username, nonce, created, passwordDigest, profileToken, focus, iris);
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ ONVIF 聚焦/光圈控制发送成功");
            } else {
                log.error("❌ ONVIF 聚焦/光圈控制发送失败，状态码: {}", response.getStatus());
                throw new RuntimeException("ONVIF 聚焦/光圈控制发送失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 聚焦/光圈控制失败", e);
            throw new RuntimeException("执行 ONVIF 聚焦/光圈控制失败: " + e.getMessage(), e);
        }
    }

    /**
     * 停止云台控制
     */
    @Override
    public void stopPtzControl(String deviceIp, String username, String password) {
        log.info("🚀 开始执行 ONVIF 云台停止... 设备IP: {}", deviceIp);
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            // 发送停止请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = StopSoapRequest(username, nonce, created, passwordDigest, profileToken);
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ ONVIF 云台停止发送成功");
            } else {
                log.error("❌ ONVIF 云台停止发送失败，状态码: {}", response.getStatus());
                throw new RuntimeException("ONVIF 云台停止发送失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 云台停止失败", e);
            throw new RuntimeException("执行 ONVIF 云台停止失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取预置点列表
     */
    @Override
    public List<Map<String, Object>> getPresets(String deviceIp, String username, String password) {
        log.info("🚀 开始获取 ONVIF 预置点列表... 设备IP: {}", deviceIp);
        List<Map<String, Object>> presets = new ArrayList<>();
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            // 发送获取预置点请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = GetPresetsSoapRequest(username, nonce, created, passwordDigest, profileToken);
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                presets = parsePresetsResponse(response.body());
                log.info("✅ 获取 ONVIF 预置点列表成功，共 {} 个", presets.size());
            } else {
                log.error("❌ 获取 ONVIF 预置点列表失败，状态码: {}", response.getStatus());
                throw new RuntimeException("获取 ONVIF 预置点列表失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 获取 ONVIF 预置点列表失败", e);
            throw new RuntimeException("获取 ONVIF 预置点列表失败: " + e.getMessage(), e);
        }
        return presets;
    }

    /**
     * 设置预置点
     */
    @Override
    public void setPreset(String deviceIp, String username, String password, Integer presetIndex, String presetName) {
        log.info("🚀 开始设置 ONVIF 预置点... 设备IP: {}, 预置点索引: {}, 预置点名称: {}", deviceIp, presetIndex, presetName);
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            
            // 尝试1: ver20 + 带PresetToken
            log.info("🔄 尝试方案1: ver20 + 带PresetToken");
            byte[] nonceBytes1 = RandomUtil.randomBytes(16);
            String nonce1 = Base64.encode(nonceBytes1);
            String created1 = Instant.now().toString();
            String passwordDigest1 = calculatePasswordDigest(nonceBytes1, created1, password);
            String soapRequest1 = SetPresetSoapRequest(username, nonce1, created1, passwordDigest1, profileToken, presetIndex, presetName);
            HttpResponse response1 = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest1).execute();
            
            if (response1.getStatus() == 200) {
                log.info("✅ 设置 ONVIF 预置点成功");
                return;
            }
            
            // 尝试2: ver10 + 带PresetToken
            log.info("🔄 尝试方案2: ver10 + 带PresetToken");
            byte[] nonceBytes2 = RandomUtil.randomBytes(16);
            String nonce2 = Base64.encode(nonceBytes2);
            String created2 = Instant.now().toString();
            String passwordDigest2 = calculatePasswordDigest(nonceBytes2, created2, password);
            String soapRequest2 = SetPresetSoapRequestV10(username, nonce2, created2, passwordDigest2, profileToken, presetIndex, presetName);
            HttpResponse response2 = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest2).execute();
            
            if (response2.getStatus() == 200) {
                log.info("✅ 设置 ONVIF 预置点成功");
                return;
            }
        
            // 尝试3: ver20 + 不带PresetToken（让设备自动分配）
            log.info("🔄 尝试方案3: ver20 + 不带PresetToken");
            byte[] nonceBytes3 = RandomUtil.randomBytes(16);
            String nonce3 = Base64.encode(nonceBytes3);
            String created3 = Instant.now().toString();
            String passwordDigest3 = calculatePasswordDigest(nonceBytes3, created3, password);
            String soapRequest3 = SetPresetSoapRequest(username, nonce3, created3, passwordDigest3, profileToken, null, presetName);
            HttpResponse response3 = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest3).execute();
            
            if (response3.getStatus() == 200) {
                log.info("✅ 设置 ONVIF 预置点成功");
                return;
            }
       
            // 所有方案都失败
            throw new RuntimeException("无法设置 ONVIF 预置点");
        } catch (Exception e) {
            log.error("❌ 设置 ONVIF 预置点失败", e);
            throw new RuntimeException("设置 ONVIF 预置点失败: " + e.getMessage(), e);
        }
    }

    /**
     * 调用预置点
     */
    @Override
    public void gotoPreset(String deviceIp, String username, String password, Integer presetIndex, Integer speed) {
        log.info("🚀 开始调用 ONVIF 预置点... 设备IP: {}, 预置点索引: {}, 速度: {}", deviceIp, presetIndex, speed);
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            // 计算速度值
            float speedValue = speed != null ? speed / 100.0f : 0.5f;
            
            // 发送调用预置点请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = GotoPresetSoapRequest(username, nonce, created, passwordDigest, profileToken, presetIndex, speedValue);
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ 调用 ONVIF 预置点成功");
            } else {
                log.error("❌ 调用 ONVIF 预置点失败，状态码: {}", response.getStatus());
                throw new RuntimeException("调用 ONVIF 预置点失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 调用 ONVIF 预置点失败", e);
            throw new RuntimeException("调用 ONVIF 预置点失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除预置点
     */
    @Override
    public void removePreset(String deviceIp, String username, String password, Integer presetIndex) {
        log.info("🚀 开始删除 ONVIF 预置点... 设备IP: {}, 预置点索引: {}", deviceIp, presetIndex);
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            // 发送删除预置点请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = RemovePresetSoapRequest(username, nonce, created, passwordDigest, profileToken, presetIndex);
            String url = "http://" + deviceIp + "/onvif/ptz_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ 删除 ONVIF 预置点成功");
            } else {
                log.error("❌ 删除 ONVIF 预置点失败，状态码: {}", response.getStatus());
                throw new RuntimeException("删除 ONVIF 预置点失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 删除 ONVIF 预置点失败", e);
            throw new RuntimeException("删除 ONVIF 预置点失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成连续移动的SOAP请求
     */
    private static String ContinuousMoveSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken, float pan, float tilt, float zoom) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <ContinuousMove xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "      <Velocity>\n" + "        <PanTilt xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + pan + "\" y=\"" + tilt + "\"/>\n" + "        <Zoom xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + zoom + "\"/>\n" + "      </Velocity>\n" + "    </ContinuousMove>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 生成停止的SOAP请求
     */
    private static String StopSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <Stop xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "      <PanTilt>true</PanTilt>\n" + "      <Zoom>true</Zoom>\n" + "    </Stop>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 生成聚焦和光圈控制的SOAP请求
     * 注意：ONVIF标准PTZ规范主要支持PanTilt和Zoom，Focus和Iris通常通过设备IO服务或厂商扩展实现
     * 这里我们尝试使用PTZ的ContinuousMove来模拟，部分设备可能支持Focus和Iris通过Zoom参数或扩展字段
     */
    private static String ContinuousMoveFocusIrisSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken, float focus, float iris) {
        // 由于ONVIF标准PTZ规范没有明确的Focus和Iris控制字段，我们尝试两种方式：
        // 1. 将Focus映射到Zoom（有些设备这样实现）
        // 2. 对于Iris，通常通过设备IO服务或厂商扩展实现，这里我们先尝试通过Zoom参数
        float zoomValue = focus != 0 ? focus : iris;
        
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <ContinuousMove xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "      <Velocity>\n" + "        <PanTilt xmlns=\"http://www.onvif.org/ver10/schema\" x=\"0\" y=\"0\"/>\n" + "        <Zoom xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + zoomValue + "\"/>\n" + "      </Velocity>\n" + "    </ContinuousMove>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 生成获取预置点的SOAP请求
     */
    private static String GetPresetsSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <GetPresets xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "    </GetPresets>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 生成设置预置点的SOAP请求
     */
    private static String SetPresetSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken, Integer presetIndex, String presetName) {
        String presetToken = presetIndex != null ? String.valueOf(presetIndex) : "";
        String presetNameTag = presetName != null && !presetName.isEmpty() ? "<PresetName>" + presetName + "</PresetName>" : "";
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <SetPreset xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + (presetToken.isEmpty() ? "" : "<PresetToken>" + presetToken + "</PresetToken>") + presetNameTag + "\n" + "    </SetPreset>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }
    
    /**
     * 生成设置预置点的SOAP请求（ver10版本）
     */
    private static String SetPresetSoapRequestV10(String username, String nonce, String created, String passwordDigest, String profileToken, Integer presetIndex, String presetName) {
        String presetToken = presetIndex != null ? String.valueOf(presetIndex) : "";
        String presetNameTag = presetName != null && !presetName.isEmpty() ? "<tt:PresetName>" + presetName + "</tt:PresetName>" : "";
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <SetPreset xmlns=\"http://www.onvif.org/ver10/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + (presetToken.isEmpty() ? "" : "<PresetToken>" + presetToken + "</PresetToken>") + presetNameTag + "\n" + "    </SetPreset>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 生成调用预置点的SOAP请求
     */
    private static String GotoPresetSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken, Integer presetIndex, float speed) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <GotoPreset xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "      <PresetToken>" + presetIndex + "</PresetToken>\n" + "      <Speed>\n" + "        <PanTilt xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + speed + "\" y=\"" + speed + "\"/>\n" + "        <Zoom xmlns=\"http://www.onvif.org/ver10/schema\" x=\"" + speed + "\"/>\n" + "      </Speed>\n" + "    </GotoPreset>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 生成删除预置点的SOAP请求
     */
    private static String RemovePresetSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken, Integer presetIndex) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <RemovePreset xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "      <PresetToken>" + presetIndex + "</PresetToken>\n" + "    </RemovePreset>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    /**
     * 解析预置点响应
     */
    private static List<Map<String, Object>> parsePresetsResponse(String responseBody) throws Exception {
        List<Map<String, Object>> presets = new ArrayList<>();
        if (responseBody == null || responseBody.isEmpty()) {
            return presets;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        
        // 查找所有Preset节点
        NodeList presetNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver20/ptz/wsdl", "Preset");
        for (int i = 0; i < presetNodes.getLength(); i++) {
            Element presetElement = (Element) presetNodes.item(i);
            String token = presetElement.getAttribute("token");
            
            // 获取名称
            NodeList nameNodes = presetElement.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Name");
            String name = nameNodes.getLength() > 0 ? nameNodes.item(0).getTextContent() : "";
            
            Map<String, Object> preset = new java.util.HashMap<>();
            preset.put("token", token);
            preset.put("name", name);
            presets.add(preset);
        }
        
        return presets;
    }

    /**
     * 灯光控制
     */
    @Override
    public void controlLight(String deviceIp, String username, String password, boolean on) {
        log.info("🚀 开始执行 ONVIF 灯光控制... 设备IP: {}, 操作: {}", deviceIp, on ? "开灯" : "关灯");
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);

            // 发送Auxiliary命令 - 尝试多种格式
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            // 准备不同格式的辅助命令
            String[] commandFormats = {
                on ? "http://www.onvif.org/ver20/ptz/wsdl#LightOn" : "http://www.onvif.org/ver20/ptz/wsdl#LightOff",
                on ? "http://www.onvif.org/ver10/ptz/wsdl#LightOn" : "http://www.onvif.org/ver10/ptz/wsdl#LightOff",
                on ? "LightOn" : "LightOff"
            };
            String url = "http://" + deviceIp + "/onvif/ptz_service";

            // 尝试不同的命令格式和命名空间组合
            boolean success = false;
            for (String command : commandFormats) {
                for (boolean useVer10 : new boolean[]{false, true}) {
                    log.info("尝试命令: {} (使用{}命名空间)", command, useVer10 ? "ver10" : "ver20");
                    success = sendAuxiliaryCommand(username, nonce, created, passwordDigest, profileToken, command, url, useVer10);
                    if (success) {
                        log.info("✅ 找到成功的组合: 命令={}, 命名空间={}", command, useVer10 ? "ver10" : "ver20");
                        break;
                    }
                }
                if (success) {
                    break;
                }
            }

            if (!success) {
                throw new RuntimeException("ONVIF 灯光控制发送失败，所有组合都失败了");
            }

            log.info("✅ ONVIF 灯光控制发送成功");
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 灯光控制失败", e);
            throw new RuntimeException("执行 ONVIF 灯光控制失败: " + e.getMessage(), e);
        }
    }

    /**
     * 发送Auxiliary命令的辅助方法
     */
    private boolean sendAuxiliaryCommand(String username, String nonce, String created, String passwordDigest, String profileToken, String auxiliaryCommand, String url, boolean useVer10) {
        try {
            String soapRequest = SendAuxiliaryCommandSoapRequest(username, nonce, created, passwordDigest, profileToken, auxiliaryCommand, useVer10);
            
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();

            return response.getStatus() == 200;
        } catch (Exception e) {
            log.error("发送Auxiliary命令失败 ({}): {}", useVer10 ? "ver10" : "ver20", e.getMessage());
            return false;
        }
    }

    /**
     * 雨刷控制
     */
    @Override
    public void controlWiper(String deviceIp, String username, String password, boolean on) {
        log.info("🚀 开始执行 ONVIF 雨刷控制... 设备IP: {}, 操作: {}", deviceIp, on ? "开雨刷" : "关雨刷");
        try {
            // 获取profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);

            // 发送Auxiliary命令 - 尝试多种格式
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            // 准备不同格式的辅助命令
            String[] commandFormats = {
                on ? "http://www.onvif.org/ver20/ptz/wsdl#WiperOn" : "http://www.onvif.org/ver20/ptz/wsdl#WiperOff",
                on ? "http://www.onvif.org/ver10/ptz/wsdl#WiperOn" : "http://www.onvif.org/ver10/ptz/wsdl#WiperOff",
                on ? "WiperOn" : "WiperOff"
            };
            String url = "http://" + deviceIp + "/onvif/ptz_service";

            // 尝试不同的命令格式和命名空间组合
            boolean success = false;
            for (String command : commandFormats) {
                for (boolean useVer10 : new boolean[]{false, true}) {
                    log.info("尝试命令: {} (使用{}命名空间)", command, useVer10 ? "ver10" : "ver20");
                    success = sendAuxiliaryCommand(username, nonce, created, passwordDigest, profileToken, command, url, useVer10);
                    if (success) {
                        log.info("✅ 找到成功的组合: 命令={}, 命名空间={}", command, useVer10 ? "ver10" : "ver20");
                        break;
                    }
                }
                if (success) {
                    break;
                }
            }

            if (!success) {
                throw new RuntimeException("ONVIF 雨刷控制发送失败，所有组合都失败了");
            }

            log.info("✅ ONVIF 雨刷控制发送成功");
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 雨刷控制失败", e);
            throw new RuntimeException("执行 ONVIF 雨刷控制失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成SendAuxiliaryCommand的SOAP请求
     */
    private static String SendAuxiliaryCommandSoapRequest(String username, String nonce, String created, String passwordDigest, String profileToken, String auxiliaryCommand, boolean useVer10) {
        String namespace = useVer10 ? "http://www.onvif.org/ver10/ptz/wsdl" : "http://www.onvif.org/ver20/ptz/wsdl";
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
               "  <s:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </s:Header>\n" +
               "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
               "    <SendAuxiliaryCommand xmlns=\"" + namespace + "\">\n" +
               "      <ProfileToken>" + profileToken + "</ProfileToken>\n" +
               "      <AuxiliaryCommand>" + auxiliaryCommand + "</AuxiliaryCommand>\n" +
               "    </SendAuxiliaryCommand>\n" +
               "  </s:Body>\n" +
               "</s:Envelope>";
    }

    /**
     * 设备重启
     */
    @Override
    public void restartDevice(String deviceIp, String username, String password) {
        log.info("🚀 开始执行 ONVIF 设备重启... 设备IP: {}", deviceIp);
        try {
            // 发送SystemReboot请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = SystemRebootSoapRequest(username, nonce, created, passwordDigest);
            String url = "http://" + deviceIp + "/onvif/device_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ ONVIF 设备重启命令发送成功");
            } else {
                log.error("❌ ONVIF 设备重启命令发送失败，状态码: {}", response.getStatus());
                throw new RuntimeException("ONVIF 设备重启命令发送失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 设备重启失败", e);
            throw new RuntimeException("执行 ONVIF 设备重启失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成SystemReboot的SOAP请求
     */
    private static String SystemRebootSoapRequest(String username, String nonce, String created, String passwordDigest) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
               "  <s:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </s:Header>\n" +
               "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
               "    <SystemReboot xmlns=\"http://www.onvif.org/ver10/device/wsdl\" />\n" +
               "  </s:Body>\n" +
               "</s:Envelope>";
    }

    /**
     * 恢复出厂设置
     */
    @Override
    public void factoryReset(String deviceIp, String username, String password, String factoryDefault) {
        log.info("🚀 开始执行 ONVIF 设备恢复出厂设置... 设备IP: {}, 模式: {}", deviceIp, factoryDefault);
        try {
            // 发送FactoryReset请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = FactoryResetSoapRequest(username, nonce, created, passwordDigest, factoryDefault);
            String url = "http://" + deviceIp + "/onvif/device_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ ONVIF 设备恢复出厂设置命令发送成功");
            } else {
                log.error("❌ ONVIF 设备恢复出厂设置命令发送失败，状态码: {}", response.getStatus());
                throw new RuntimeException("ONVIF 设备恢复出厂设置命令发送失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 设备恢复出厂设置失败", e);
            throw new RuntimeException("执行 ONVIF 设备恢复出厂设置失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成FactoryReset的SOAP请求
     */
    private static String FactoryResetSoapRequest(String username, String nonce, String created, String passwordDigest, String factoryDefault) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
               "  <s:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </s:Header>\n" +
               "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
               "    <FactoryReset xmlns=\"http://www.onvif.org/ver10/device/wsdl\">\n" +
               "      <FactoryDefault>" + factoryDefault + "</FactoryDefault>\n" +
               "    </FactoryReset>\n" +
               "  </s:Body>\n" +
               "</s:Envelope>";
    }

    /**
     * 获取设备时间
     */
    @Override
    public Map<String, Object> getDeviceTime(String deviceIp, String username, String password) {
        log.info("🚀 开始获取 ONVIF 设备时间... 设备IP: {}", deviceIp);
        try {
            // 发送GetSystemDateAndTime请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            String soapRequest = GetSystemDateAndTimeSoapRequest(username, nonce, created, passwordDigest);
            String url = "http://" + deviceIp + "/onvif/device_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                Map<String, Object> timeInfo = parseSystemDateAndTimeResponse(response.body());
                log.info("✅ 获取 ONVIF 设备时间成功: {}", timeInfo);
                return timeInfo;
            } else {
                log.error("❌ 获取 ONVIF 设备时间失败，状态码: {}", response.getStatus());
                throw new RuntimeException("获取 ONVIF 设备时间失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 获取 ONVIF 设备时间失败", e);
            throw new RuntimeException("获取 ONVIF 设备时间失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成GetSystemDateAndTime的SOAP请求
     */
    private static String GetSystemDateAndTimeSoapRequest(String username, String nonce, String created, String passwordDigest) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
               "  <s:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </s:Header>\n" +
               "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
               "    <GetSystemDateAndTime xmlns=\"http://www.onvif.org/ver10/device/wsdl\" />\n" +
               "  </s:Body>\n" +
               "</s:Envelope>";
    }

    /**
     * 解析系统时间响应
     */
    private static Map<String, Object> parseSystemDateAndTimeResponse(String responseBody) throws Exception {
        Map<String, Object> result = new java.util.HashMap<>();
        if (responseBody == null || responseBody.isEmpty()) {
            return result;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        
        // 查找DateTimeType节点
        NodeList dateTimeTypeNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "DateTimeType");
        if (dateTimeTypeNodes.getLength() > 0) {
            result.put("dateTimeType", dateTimeTypeNodes.item(0).getTextContent());
        }
        
        // 查找TimeZone节点
        NodeList timeZoneNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "TZ");
        if (timeZoneNodes.getLength() > 0) {
            result.put("timeZone", timeZoneNodes.item(0).getTextContent());
        }
        
        // 查找UTCDateTime节点
        NodeList utcDateTimeNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "UTCDateTime");
        if (utcDateTimeNodes.getLength() > 0) {
            Element utcDateTime = (Element) utcDateTimeNodes.item(0);
            NodeList dateNodes = utcDateTime.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Date");
            NodeList timeNodes = utcDateTime.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Time");
            
            if (dateNodes.getLength() > 0) {
                Element date = (Element) dateNodes.item(0);
                result.put("year", getElementTextByTag(date, "Year", "http://www.onvif.org/ver10/schema"));
                result.put("month", getElementTextByTag(date, "Month", "http://www.onvif.org/ver10/schema"));
                result.put("day", getElementTextByTag(date, "Day", "http://www.onvif.org/ver10/schema"));
            }
            
            if (timeNodes.getLength() > 0) {
                Element time = (Element) timeNodes.item(0);
                result.put("hour", getElementTextByTag(time, "Hour", "http://www.onvif.org/ver10/schema"));
                result.put("minute", getElementTextByTag(time, "Minute", "http://www.onvif.org/ver10/schema"));
                result.put("second", getElementTextByTag(time, "Second", "http://www.onvif.org/ver10/schema"));
            }
        }
        
        return result;
    }

    /**
     * 获取元素文本内容的辅助方法
     */
    private static String getElementTextByTag(Element parent, String tagName, String namespace) {
        NodeList nodes = parent.getElementsByTagNameNS(namespace, tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }

    /**
     * 设备校时
     */
    @Override
    public void syncDeviceTime(String deviceIp, String username, String password, String dateTime) {
        log.info("🚀 开始执行 ONVIF 设备校时... 设备IP: {}, 时间: {}", deviceIp, dateTime);
        try {
            // 发送SetSystemDateAndTime请求
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            // 解析时间并转换为 UTC 时间
            java.time.LocalDateTime utcDateTime;
            if (dateTime == null || dateTime.isEmpty()) {
                // 没有传入时间，使用服务器当前 UTC 时间
                utcDateTime = java.time.LocalDateTime.now(java.time.ZoneId.of("UTC"));
            } else if (dateTime.contains("T")) {
                // ISO格式时间（可能是 UTC 时间），直接解析使用
                utcDateTime = java.time.LocalDateTime.parse(dateTime, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                // yyyy-MM-dd HH:mm:ss 格式时间（前端传的本地时间），转换为 UTC
                java.time.LocalDateTime localDateTime = java.time.LocalDateTime.parse(dateTime, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                java.time.ZonedDateTime zonedDateTime = localDateTime.atZone(java.time.ZoneId.systemDefault());
                utcDateTime = zonedDateTime.withZoneSameInstant(java.time.ZoneId.of("UTC")).toLocalDateTime();
            }
            
            int year = utcDateTime.getYear();
            int month = utcDateTime.getMonthValue();
            int day = utcDateTime.getDayOfMonth();
            int hour = utcDateTime.getHour();
            int minute = utcDateTime.getMinute();
            int second = utcDateTime.getSecond();
            
            String soapRequest = SetSystemDateAndTimeSoapRequest(username, nonce, created, passwordDigest, year, month, day, hour, minute, second);
            String url = "http://" + deviceIp + "/onvif/device_service";
            HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
            
            if (response.getStatus() == 200) {
                log.info("✅ ONVIF 设备校时命令发送成功");
            } else {
                log.error("❌ ONVIF 设备校时命令发送失败，状态码: {}", response.getStatus());
                throw new RuntimeException("ONVIF 设备校时命令发送失败，状态码: " + response.getStatus());
            }
        } catch (Exception e) {
            log.error("❌ 执行 ONVIF 设备校时失败", e);
            throw new RuntimeException("执行 ONVIF 设备校时失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成SetSystemDateAndTime的SOAP请求
     */
    private static String SetSystemDateAndTimeSoapRequest(String username, String nonce, String created, String passwordDigest, 
                                                          int year, int month, int day, int hour, int minute, int second) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
               "  <s:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </s:Header>\n" +
               "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
               "    <SetSystemDateAndTime xmlns=\"http://www.onvif.org/ver10/device/wsdl\">\n" +
               "      <DateTimeType>Manual</DateTimeType>\n" +
               "      <DaylightSavings>true</DaylightSavings>\n" +
               "      <TimeZone>\n" +
               "        <TZ>UTC</TZ>\n" +
               "      </TimeZone>\n" +
               "      <UTCDateTime>\n" +
               "        <Date>\n" +
               "          <Year>" + year + "</Year>\n" +
               "          <Month>" + month + "</Month>\n" +
               "          <Day>" + day + "</Day>\n" +
               "        </Date>\n" +
               "        <Time>\n" +
               "          <Hour>" + hour + "</Hour>\n" +
               "          <Minute>" + minute + "</Minute>\n" +
               "          <Second>" + second + "</Second>\n" +
               "        </Time>\n" +
               "      </UTCDateTime>\n" +
               "    </SetSystemDateAndTime>\n" +
               "  </s:Body>\n" +
               "</s:Envelope>";
    }

    /**
     * ONVIF设备查询录像
     */
    @Override
    public ArrayList<HashMap<String, Object>> queryRecord(String deviceIp, String username, String password, String startTime, String endTime) {
        log.info("🚀 开始查询 ONVIF 设备录像... 设备IP: {}, 时间范围: {} - {}", deviceIp, startTime, endTime);
        ArrayList<HashMap<String, Object>> recordList = new ArrayList<>();
        
        try {
            // 解码 URL 编码的时间参数
            try {
                startTime = java.net.URLDecoder.decode(startTime, "UTF-8");
                endTime = java.net.URLDecoder.decode(endTime, "UTF-8");
                log.debug("URL解码后的时间, startTime:{}, endTime:{}", startTime, endTime);
            } catch (Exception e) {
                log.warn("URL解码失败，使用原始时间, error:{}", e.getMessage());
            }

            String searchUrl = "http://" + deviceIp + "/onvif/search_service";
            String recordingUrl = "http://" + deviceIp + "/onvif/recording_service";
            
            // 第一步：调用FindRecordings获取SearchToken
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            String findRecordingsRequest = FindRecordingsSoapRequest(username, nonce, created, passwordDigest, startTime, endTime);
            
            log.debug("发送FindRecordings请求到: {}", searchUrl);
            log.debug("SOAP请求: {}", findRecordingsRequest);
            
            HttpResponse findResponse = HttpRequest.post(searchUrl)
                    .header("Content-Type", "application/soap+xml; charset=utf-8")
                    .body(findRecordingsRequest)
                    .execute();
            
            if (findResponse.getStatus() == 200) {
                String findResponseBody = findResponse.body();
                log.debug("FindRecordings响应: {}", findResponseBody);
                
                // 解析SearchToken
                String searchToken = parseSearchToken(findResponseBody);
                if (searchToken != null && !searchToken.isEmpty()) {
                    log.info("获取到SearchToken: {}", searchToken);
                    
                    // 第二步：使用SearchToken调用GetRecordingSearchResults获取实际结果
                    // 先生成新的认证信息
                    byte[] nonceBytes2 = RandomUtil.randomBytes(16);
                    String nonce2 = Base64.encode(nonceBytes2);
                    String created2 = Instant.now().toString();
                    String passwordDigest2 = calculatePasswordDigest(nonceBytes2, created2, password);
                    
                    String getResultsRequest = GetRecordingSearchResultsSoapRequest(username, nonce2, created2, passwordDigest2, searchToken);
                    
                    log.debug("发送GetRecordingSearchResults请求到: {}", searchUrl);
                    log.debug("SOAP请求: {}", getResultsRequest);
                    
                    HttpResponse resultsResponse = HttpRequest.post(searchUrl)
                            .header("Content-Type", "application/soap+xml; charset=utf-8")
                            .body(getResultsRequest)
                            .execute();
                    
                    if (resultsResponse.getStatus() == 200) {
                        String resultsResponseBody = resultsResponse.body();
                        log.debug("GetRecordingSearchResults响应: {}", resultsResponseBody);
                        recordList = parseRecordingSearchResults(resultsResponseBody);
                        log.info("✅ 查询 ONVIF 录像成功，共找到 {} 条记录", recordList.size());
                    } else {
                        log.warn("GetRecordingSearchResults失败，尝试使用GetRecordings方法");
                        // 如果失败，尝试使用GetRecordings方法
                        recordList = tryGetRecordings(deviceIp, username, password, recordingUrl);
                    }
                } else {
                    log.warn("未获取到SearchToken，尝试使用GetRecordings方法");
                    recordList = tryGetRecordings(deviceIp, username, password, recordingUrl);
                }
            } else {
                log.warn("FindRecordings失败，尝试使用GetRecordings方法");
                // 如果FindRecordings失败，尝试使用GetRecordings方法
                recordList = tryGetRecordings(deviceIp, username, password, recordingUrl);
            }
        } catch (Exception e) {
            log.error("❌ 查询 ONVIF 录像异常", e);
            throw new RuntimeException("查询录像异常: " + e.getMessage(), e);
        }
        
        // 根据本地时间范围过滤记录
        if (startTime != null && endTime != null) {
            recordList = filterByLocalTimeRange(recordList, startTime, endTime);
        }
        
        // 为每个录像记录添加回放地址
        for (HashMap<String, Object> record : recordList) {
            try {
                String recordingToken = (String) record.get("recordingToken");
                String videoTrackToken = (String) record.get("trackToken");
                
                if (recordingToken != null && videoTrackToken != null) {
                    String replayUri = getReplayUri(deviceIp, username, password, recordingToken, videoTrackToken);
                    record.put("replayUri", replayUri);
                    log.debug("为录像 {} 添加回放地址: {}", recordingToken, replayUri);
                }
            } catch (Exception e) {
                log.warn("获取录像回放地址失败: {}", e.getMessage());
            }
        }
        
        return recordList;
    }
    
    /**
     * 根据本地时间范围过滤记录
     */
    private ArrayList<HashMap<String, Object>> filterByLocalTimeRange(
            ArrayList<HashMap<String, Object>> recordList, String localStartTime, String localEndTime) {
        
        ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();
        
        try {
            // 解析本地时间范围
            java.text.SimpleDateFormat localFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormat.setTimeZone(java.util.TimeZone.getDefault());
            
            java.util.Date rangeStart = localFormat.parse(localStartTime);
            java.util.Date rangeEnd = localFormat.parse(localEndTime);
            
            // 解析 UTC 时间格式
            java.text.SimpleDateFormat utcFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            utcFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            
            for (HashMap<String, Object> record : recordList) {
                String recordStartUtc = (String) record.get("start");
                String recordEndUtc = (String) record.get("end");
                
                boolean inRange = false;
                try {
                    // 将 UTC 时间转换为本地时间
                    if (recordStartUtc != null && !recordStartUtc.isEmpty()) {
                        java.util.Date startUtc = utcFormat.parse(recordStartUtc);
                        
                        // 检查是否有重叠
                        if (recordEndUtc != null && !recordEndUtc.isEmpty()) {
                            java.util.Date endUtc = utcFormat.parse(recordEndUtc);
                            inRange = !(endUtc.before(rangeStart) || startUtc.after(rangeEnd));
                        } else {
                            inRange = !startUtc.before(rangeStart) && !startUtc.after(rangeEnd);
                        }
                        
                        // 更新记录中的时间为本地时间格式
                        record.put("start", localFormat.format(startUtc));
                        if (recordEndUtc != null && !recordEndUtc.isEmpty()) {
                            java.util.Date endUtc = utcFormat.parse(recordEndUtc);
                            record.put("end", localFormat.format(endUtc));
                        }
                    }
                } catch (Exception e) {
                    log.warn("解析时间失败: start={}, end={}", recordStartUtc, recordEndUtc, e);
                    inRange = true; // 解析失败时保留记录
                }
                
                if (inRange) {
                    filteredList.add(record);
                } else {
                    log.debug("过滤掉不在时间范围内的记录: start={}, end={}", recordStartUtc, recordEndUtc);
                }
            }
        } catch (Exception e) {
            log.error("时间过滤异常", e);
        }
        
        log.info("时间过滤后剩余 {} 条记录", filteredList.size());
        return filteredList;
    }
    
    /**
     * 从录像记录中查找视频轨道的 token
     */
    private String findVideoTrackToken(HashMap<String, Object> record) {
        Object tracksObj = record.get("tracks");
        if (tracksObj instanceof ArrayList) {
            ArrayList<?> tracks = (ArrayList<?>) tracksObj;
            for (Object trackObj : tracks) {
                if (trackObj instanceof HashMap) {
                    HashMap<?, ?> track = (HashMap<?, ?>) trackObj;
                    String trackType = (String) track.get("trackType");
                    if ("Video".equals(trackType)) {
                        return (String) track.get("trackToken");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 尝试使用GetRecordings方法获取录像
     */
    private ArrayList<HashMap<String, Object>> tryGetRecordings(String deviceIp, String username, String password, String url) {
        ArrayList<HashMap<String, Object>> recordList = new ArrayList<>();
        try {
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            String getRecordingsRequest = GetRecordingsSoapRequest(username, nonce, created, passwordDigest);
            
            log.debug("发送GetRecordings请求到: {}", url);
            log.debug("SOAP请求: {}", getRecordingsRequest);
            
            HttpResponse response = HttpRequest.post(url)
                    .header("Content-Type", "application/soap+xml; charset=utf-8")
                    .body(getRecordingsRequest)
                    .execute();
            
            if (response.getStatus() == 200) {
                String responseBody = response.body();
                log.debug("GetRecordings响应: {}", responseBody);
                recordList = parseGetRecordingsResponse(responseBody);
                log.info("✅ 使用GetRecordings查询成功，共找到 {} 条记录", recordList.size());
            } else {
                log.error("GetRecordings失败，状态码: {}", response.getStatus());
            }
        } catch (Exception e) {
            log.warn("GetRecordings方法失败: {}", e.getMessage());
        }
        return recordList;
    }

    /**
     * 生成FindRecordings的SOAP请求
     */
    private static String FindRecordingsSoapRequest(String username, String nonce, String created, String passwordDigest, 
                                                    String startTime, String endTime) {
        // 构建时间过滤条件
        StringBuilder scopeBuilder = new StringBuilder();
        
        if (startTime != null && !startTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
            try {
                // 解析时间字符串，转换为ISO格式
                java.time.LocalDateTime startDateTime = java.time.LocalDateTime.parse(startTime, 
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                java.time.LocalDateTime endDateTime = java.time.LocalDateTime.parse(endTime, 
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                
                String startIso = startDateTime.atZone(java.time.ZoneId.systemDefault())
                        .withZoneSameInstant(java.time.ZoneOffset.UTC)
                        .format(java.time.format.DateTimeFormatter.ISO_INSTANT);
                String endIso = endDateTime.atZone(java.time.ZoneId.systemDefault())
                        .withZoneSameInstant(java.time.ZoneOffset.UTC)
                        .format(java.time.format.DateTimeFormatter.ISO_INSTANT);
                
                scopeBuilder.append("<tse:RecordingInformationFilter>");
                scopeBuilder.append("<tt:RecordingSummary>");
                scopeBuilder.append("<tt:From>").append(startIso).append("</tt:From>");
                scopeBuilder.append("<tt:Until>").append(endIso).append("</tt:Until>");
                scopeBuilder.append("</tt:RecordingSummary>");
                scopeBuilder.append("</tse:RecordingInformationFilter>");
            } catch (Exception e) {
                log.warn("时间格式转换失败，使用默认查询条件: {}", e.getMessage());
            }
        }
        
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tse=\"http://www.onvif.org/ver10/search/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
               "  <soap:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </soap:Header>\n" +
               "  <soap:Body>\n" +
               "    <tse:FindRecordings>\n" +
               (scopeBuilder.length() > 0 ? "      <tse:Scope>\n" + scopeBuilder.toString() + "\n      </tse:Scope>\n" : "") +
               "      <tse:MaxMatches>100</tse:MaxMatches>\n" +
               "      <tse:KeepAliveTime>PT60S</tse:KeepAliveTime>\n" +
               "    </tse:FindRecordings>\n" +
               "  </soap:Body>\n" +
               "</soap:Envelope>";
    }

    /**
     * 解析SearchToken
     */
    private static String parseSearchToken(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isEmpty()) {
            return null;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        
        NodeList searchTokenNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/search/wsdl", "SearchToken");
        if (searchTokenNodes.getLength() > 0) {
            return searchTokenNodes.item(0).getTextContent();
        }
        return null;
    }

    /**
     * 生成GetRecordingSearchResults的SOAP请求
     */
    private static String GetRecordingSearchResultsSoapRequest(String username, String nonce, String created, String passwordDigest, String searchToken) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tse=\"http://www.onvif.org/ver10/search/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
               "  <soap:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </soap:Header>\n" +
               "  <soap:Body>\n" +
               "    <tse:GetRecordingSearchResults>\n" +
               "      <tse:SearchToken>" + searchToken + "</tse:SearchToken>\n" +
               "    </tse:GetRecordingSearchResults>\n" +
               "  </soap:Body>\n" +
               "</soap:Envelope>";
    }

    /**
     * 解析GetRecordingSearchResults响应
     */
    private static ArrayList<HashMap<String, Object>> parseRecordingSearchResults(String responseBody) throws Exception {
        return parseRecordingResults(responseBody);
    }

    /**
     * 生成GetRecordings的SOAP请求
     */
    private static String GetRecordingsSoapRequest(String username, String nonce, String created, String passwordDigest) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:trt=\"http://www.onvif.org/ver10/recording/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
               "  <soap:Header>\n" +
               "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
               "      <wsse:UsernameToken>\n" +
               "        <wsse:Username>" + username + "</wsse:Username>\n" +
               "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
               "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
               "        <wsu:Created>" + created + "</wsu:Created>\n" +
               "      </wsse:UsernameToken>\n" +
               "    </wsse:Security>\n" +
               "  </soap:Header>\n" +
               "  <soap:Body>\n" +
               "    <trt:GetRecordings />\n" +
               "  </soap:Body>\n" +
               "</soap:Envelope>";
    }

    /**
     * 解析GetRecordings响应
     */
    private static ArrayList<HashMap<String, Object>> parseGetRecordingsResponse(String responseBody) throws Exception {
        return parseRecordingResults(responseBody);
    }

    /**
     * 通用的录像结果解析方法
     */
    private static ArrayList<HashMap<String, Object>> parseRecordingResults(String responseBody) throws Exception {
        ArrayList<HashMap<String, Object>> recordList = new ArrayList<>();
        
        if (responseBody == null || responseBody.isEmpty()) {
            return recordList;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        
        String ttNamespace = "http://www.onvif.org/ver10/schema";
        
        // 尝试查找RecordingInformation节点（GetRecordingSearchResults返回的格式）
        NodeList recordingInfoNodes = document.getElementsByTagNameNS(ttNamespace, "RecordingInformation");
        for (int i = 0; i < recordingInfoNodes.getLength(); i++) {
            Element recordingInfo = (Element) recordingInfoNodes.item(i);
            HashMap<String, Object> record = new HashMap<>();
            
            // 获取录制令牌（同时兼容海康字段格式）
            NodeList recordingTokenNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "RecordingToken");
            String recordingToken = null;
            if (recordingTokenNodes.getLength() > 0) {
                recordingToken = recordingTokenNodes.item(0).getTextContent();
                record.put("recordingToken", recordingToken);
                record.put("fileName", recordingToken); // 兼容海康字段，用token作为文件名
            }
            
            // 获取录制来源
            NodeList sourceNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "Source");
            String channelId = "0";
            if (sourceNodes.getLength() > 0) {
                Element source = (Element) sourceNodes.item(0);
                HashMap<String, String> sourceInfo = new HashMap<>();
                
                NodeList sourceIdNodes = source.getElementsByTagNameNS(ttNamespace, "SourceId");
                if (sourceIdNodes.getLength() > 0) {
                    sourceInfo.put("sourceId", sourceIdNodes.item(0).getTextContent());
                    channelId = sourceIdNodes.item(0).getTextContent();
                }
                NodeList nameNodes = source.getElementsByTagNameNS(ttNamespace, "Name");
                if (nameNodes.getLength() > 0) {
                    sourceInfo.put("name", nameNodes.item(0).getTextContent());
                }
                NodeList locationNodes = source.getElementsByTagNameNS(ttNamespace, "Location");
                if (locationNodes.getLength() > 0) {
                    sourceInfo.put("location", locationNodes.item(0).getTextContent());
                }
                NodeList descriptionNodes = source.getElementsByTagNameNS(ttNamespace, "Description");
                if (descriptionNodes.getLength() > 0) {
                    sourceInfo.put("description", descriptionNodes.item(0).getTextContent());
                }
                NodeList addressNodes = source.getElementsByTagNameNS(ttNamespace, "Address");
                if (addressNodes.getLength() > 0) {
                    sourceInfo.put("address", addressNodes.item(0).getTextContent());
                }
                
                record.put("source", sourceInfo);
            }
            record.put("channel", channelId); // 兼容海康字段
            
            // 获取录制时间范围
            NodeList earliestNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "EarliestRecording");
            if (earliestNodes.getLength() > 0) {
                record.put("start", earliestNodes.item(0).getTextContent());
            }
            NodeList latestNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "LatestRecording");
            if (latestNodes.getLength() > 0) {
                record.put("end", latestNodes.item(0).getTextContent());
            }
            
            // 获取内容
            NodeList contentNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "Content");
            if (contentNodes.getLength() > 0) {
                record.put("content", contentNodes.item(0).getTextContent());
                record.put("type", contentNodes.item(0).getTextContent()); // 兼容海康字段，用content作为类型
            } else {
                record.put("type", "ONVIF录像"); // 默认类型
            }
            
            // 获取录制状态
            NodeList statusNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "RecordingStatus");
            if (statusNodes.getLength() > 0) {
                record.put("status", statusNodes.item(0).getTextContent());
            }
            
            // 获取跟踪信息
            NodeList trackNodes = recordingInfo.getElementsByTagNameNS(ttNamespace, "Track");
            ArrayList<HashMap<String, String>> tracks = new ArrayList<>();
            String videoTrackToken = null;
            for (int j = 0; j < trackNodes.getLength(); j++) {
                Element track = (Element) trackNodes.item(j);
                HashMap<String, String> trackInfo = new HashMap<>();
                
                NodeList trackTokenNodes = track.getElementsByTagNameNS(ttNamespace, "TrackToken");
                if (trackTokenNodes.getLength() > 0) {
                    trackInfo.put("trackToken", trackTokenNodes.item(0).getTextContent());
                }
                NodeList trackTypeNodes = track.getElementsByTagNameNS(ttNamespace, "TrackType");
                if (trackTypeNodes.getLength() > 0) {
                    trackInfo.put("trackType", trackTypeNodes.item(0).getTextContent());
                    if ("Video".equals(trackTypeNodes.item(0).getTextContent())) {
                        videoTrackToken = trackTokenNodes.getLength() > 0 ? trackTokenNodes.item(0).getTextContent() : null;
                    }
                }
                NodeList trackDescNodes = track.getElementsByTagNameNS(ttNamespace, "Description");
                if (trackDescNodes.getLength() > 0) {
                    trackInfo.put("description", trackDescNodes.item(0).getTextContent());
                }
                NodeList dataFromNodes = track.getElementsByTagNameNS(ttNamespace, "DataFrom");
                if (dataFromNodes.getLength() > 0) {
                    trackInfo.put("dataFrom", dataFromNodes.item(0).getTextContent());
                }
                NodeList dataToNodes = track.getElementsByTagNameNS(ttNamespace, "DataTo");
                if (dataToNodes.getLength() > 0) {
                    trackInfo.put("dataTo", dataToNodes.item(0).getTextContent());
                }
                
                tracks.add(trackInfo);
            }
            if (!tracks.isEmpty()) {
                record.put("tracks", tracks);
            }
            if (videoTrackToken != null) {
                record.put("trackToken", videoTrackToken); // 保存视频轨道token
            }
            
            record.put("fileSize", 0L); // 兼容海康字段，默认为0
            
            // 过滤掉时间为1970-01-01的无效录像
            String startTime = (String) record.get("start");
            String endTime = (String) record.get("end");
            boolean isValid = true;
            if ((startTime != null && startTime.startsWith("1970-01-01")) && 
                (endTime != null && endTime.startsWith("1970-01-01"))) {
                isValid = false;
                log.debug("过滤掉无效录像（时间为1970-01-01）: {}", record.get("recordingToken"));
            }
            
            if (isValid) {
                recordList.add(record);
                log.debug("解析到录像记录: {}", record);
            }
        }
        
        // 如果没有找到RecordingInformation，尝试查找Recording节点作为备用
        if (recordList.isEmpty()) {
            String[] namespaces = {
                "http://www.onvif.org/ver10/schema",
                "http://www.onvif.org/ver10/recording/wsdl",
                "http://www.onvif.org/ver10/search/wsdl"
            };
            
            for (String ns : namespaces) {
                NodeList recordingNodes = document.getElementsByTagNameNS(ns, "Recording");
                for (int i = 0; i < recordingNodes.getLength(); i++) {
                    Element recording = (Element) recordingNodes.item(i);
                    HashMap<String, Object> record = new HashMap<>();
                    
                    // 获取录制令牌（同时兼容海康字段格式）
                    String recordingToken = recording.getAttribute("token");
                    record.put("recordingToken", recordingToken);
                    record.put("fileName", recordingToken); // 兼容海康字段，用token作为文件名
                    
                    // 获取录制来源
                    NodeList sourceNodes = recording.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Source");
                    String channelId = "0";
                    if (sourceNodes.getLength() > 0) {
                        Element source = (Element) sourceNodes.item(0);
                        NodeList sourceTokenNodes = source.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Token");
                        if (sourceTokenNodes.getLength() > 0) {
                            channelId = sourceTokenNodes.item(0).getTextContent();
                            record.put("sourceToken", sourceTokenNodes.item(0).getTextContent());
                        }
                    }
                    record.put("channel", channelId); // 兼容海康字段
                    
                    // 获取录制时间范围
                    NodeList recordingSummaryNodes = recording.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "RecordingSummary");
                    if (recordingSummaryNodes.getLength() > 0) {
                        Element summary = (Element) recordingSummaryNodes.item(0);
                        NodeList fromNodes = summary.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "From");
                        NodeList untilNodes = summary.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Until");
                        
                        if (fromNodes.getLength() > 0) {
                            record.put("start", fromNodes.item(0).getTextContent());
                        }
                        if (untilNodes.getLength() > 0) {
                            record.put("end", untilNodes.item(0).getTextContent());
                        }
                    }
                    
                    // 获取内容
                    NodeList contentNodes = recording.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Content");
                    if (contentNodes.getLength() > 0) {
                        record.put("content", contentNodes.item(0).getTextContent());
                        record.put("type", contentNodes.item(0).getTextContent()); // 兼容海康字段，用content作为类型
                    } else {
                        record.put("type", "ONVIF录像"); // 默认类型
                    }
                    
                    // 获取跟踪信息
                    NodeList trackNodes = recording.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Track");
                    ArrayList<HashMap<String, String>> tracks = new ArrayList<>();
                    String videoTrackToken = null;
                    for (int j = 0; j < trackNodes.getLength(); j++) {
                        Element track = (Element) trackNodes.item(j);
                        HashMap<String, String> trackInfo = new HashMap<>();
                        trackInfo.put("token", track.getAttribute("token"));
                        
                        NodeList typeNodes = track.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Type");
                        if (typeNodes.getLength() > 0) {
                            trackInfo.put("type", typeNodes.item(0).getTextContent());
                            if ("Video".equals(typeNodes.item(0).getTextContent())) {
                                videoTrackToken = track.getAttribute("token");
                            }
                        }
                        
                        tracks.add(trackInfo);
                    }
                    if (!tracks.isEmpty()) {
                        record.put("tracks", tracks);
                    }
                    if (videoTrackToken != null) {
                        record.put("trackToken", videoTrackToken); // 保存视频轨道token
                    }
                    
                    record.put("fileSize", 0L); // 兼容海康字段，默认为0
                    
                    // 过滤掉时间为1970-01-01的无效录像
                    String startTime = (String) record.get("start");
                    String endTime = (String) record.get("end");
                    boolean isValid = true;
                    if ((startTime != null && startTime.startsWith("1970-01-01")) && 
                        (endTime != null && endTime.startsWith("1970-01-01"))) {
                        isValid = false;
                        log.debug("过滤掉无效录像（时间为1970-01-01）: {}", record.get("recordingToken"));
                    }
                    
                    if (isValid) {
                        recordList.add(record);
                        log.debug("解析到录像记录: {}", record);
                    }
                }
            }
        }
        
        return recordList;
    }

    /**
     * 获取回放地址
     */
    @Override
    public String getReplayUri(String deviceIp, String username, String password, String recordingToken, String trackToken) {
        try {
            byte[] nonceBytes = RandomUtil.randomBytes(16);
            String nonce = Base64.encode(nonceBytes);
            String created = Instant.now().toString();
            String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
            
            String soapRequest = getReplayUriSoapRequest(username, nonce, created, passwordDigest, recordingToken, trackToken);
            String url = "http://" + deviceIp + "/onvif/replay_service";
            
            HttpResponse response = HttpRequest.post(url)
                    .header("Content-Type", "application/soap+xml; charset=utf-8")
                    .body(soapRequest)
                    .execute();
            
            if (response.getStatus() == 200) {
                String responseBody = response.body();
                String replayUri = parseReplayUriResponse(responseBody);
                if (replayUri != null && !replayUri.isEmpty()) {
                    return addAuthToUri(replayUri, username, password);
                }
            }
            throw new RuntimeException("获取回放地址失败");
        } catch (Exception e) {
            throw new RuntimeException("获取回放地址异常: " + e.getMessage(), e);
        }
    }

    /**
     * 在URI中添加认证信息
     */
    private String addAuthToUri(String uri, String username, String password) {
        if (uri == null || uri.isEmpty() || uri.contains("@")) {
            return uri;
        }
        int protocolIndex = uri.indexOf("://");
        if (protocolIndex == -1) {
            return uri;
        }
        String protocol = uri.substring(0, protocolIndex);
        String remaining = uri.substring(protocolIndex + 3);
        int pathIndex = remaining.indexOf("/");
        String hostPort = (pathIndex != -1) ? remaining.substring(0, pathIndex) : remaining;
        String pathQuery = (pathIndex != -1) ? remaining.substring(pathIndex) : "";
        return protocol + "://" + username + ":" + password + "@" + hostPort + pathQuery;
    }

    /**
     * 生成GetReplayUri的SOAP请求
     */
    private static String getReplayUriSoapRequest(String username, String nonce, String created, String passwordDigest, 
                                                    String recordingToken, String trackToken) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:trp=\"http://www.onvif.org/ver10/replay/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
                "  <soap:Header>\n" +
                "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                "      <wsse:UsernameToken>\n" +
                "        <wsse:Username>" + username + "</wsse:Username>\n" +
                "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" +
                "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" +
                "        <wsu:Created>" + created + "</wsu:Created>\n" +
                "      </wsse:UsernameToken>\n" +
                "    </wsse:Security>\n" +
                "  </soap:Header>\n" +
                "  <soap:Body>\n" +
                "    <trp:GetReplayUri>\n" +
                "      <trp:StreamSetup>\n" +
                "        <tt:Stream>RTP-Unicast</tt:Stream>\n" +
                "        <tt:Transport>\n" +
                "          <tt:Protocol>RTSP</tt:Protocol>\n" +
                "        </tt:Transport>\n" +
                "      </trp:StreamSetup>\n" +
                "      <trp:RecordingToken>" + recordingToken + "</trp:RecordingToken>\n" +
                (trackToken != null && !trackToken.isEmpty() ? "      <trp:TrackToken>" + trackToken + "</trp:TrackToken>\n" : "") +
                "    </trp:GetReplayUri>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
    }

    /**
     * 解析GetReplayUri响应
     */
    private static String parseReplayUriResponse(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isEmpty()) {
            return null;
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        
        // 尝试查找Uri节点，使用不同的命名空间
        String[] namespaces = {
            "http://www.onvif.org/ver10/replay/wsdl",
            "http://www.onvif.org/ver10/schema"
        };
        
        for (String ns : namespaces) {
            NodeList uriNodes = document.getElementsByTagNameNS(ns, "Uri");
            if (uriNodes.getLength() > 0) {
                return uriNodes.item(0).getTextContent();
            }
        }
        
        // 尝试不使用命名空间查找
        NodeList uriNodes = document.getElementsByTagName("Uri");
        if (uriNodes.getLength() > 0) {
            return uriNodes.item(0).getTextContent();
        }
        
        log.warn("未在响应中找到Uri节点");
        return null;
    }

    /**
     * 根据设备id重启设备
     *
     * @param id 设备id
     */
    @Override
    public void restartDeviceById(Long id) {
        log.info("开始重启ONVIF设备, deviceId:{}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        // 使用设备的IP、用户名和密码调用重启方法
        restartDevice(device.getIpAddress(), device.getUserName(), device.getPassword());
        log.info("重启ONVIF设备成功, deviceId:{}, IP:{}", id, device.getIpAddress());
    }

    /**
     * 根据设备id校时
     *
     * @param id 设备id
     * @param dateTime 要设置的时间
     */
    @Override
    public void syncDeviceTimeById(Long id, String dateTime) {
        log.info("开始校时ONVIF设备, deviceId: {}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId: {}, code: {}, msg: {}", id, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId: {}, IP: {}", id, device.getIpAddress());

        // 直接传递时间给 syncDeviceTime 方法，由它来处理各种情况
        syncDeviceTime(device.getIpAddress(), device.getUserName(), device.getPassword(), dateTime);
        log.info("校时ONVIF设备成功, deviceId: {}, IP: {}", id, device.getIpAddress());
    }

    /**
     * 根据设备id获取设备时间
     *
     * @param id 设备id
     * @return 设备时间信息
     */
    @Override
    public Map<String, Object> getDeviceTimeById(Long id) {
        log.info("开始获取ONVIF设备时间, deviceId: {}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId: {}, code: {}, msg: {}", id, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId: {}, IP: {}", id, device.getIpAddress());

        // 使用设备的IP、用户名和密码调用获取时间方法
        Map<String, Object> result = getDeviceTime(device.getIpAddress(), device.getUserName(), device.getPassword());
        log.info("获取ONVIF设备时间成功, deviceId: {}, IP: {}, time: {}", id, device.getIpAddress(), result);
        return result;
    }

    /**
     * 获取设备信息
     *
     * @param deviceIp 设备IP
     * @param username 用户名
     * @param password 密码
     * @return 设备信息
     */
    @Override
    public Map<String, Object> getDeviceInfo(String deviceIp, String username, String password) {
        log.info("🚀 开始获取 ONVIF 设备信息... 设备IP: {}", deviceIp);
        try {
            Map<String, Object> result = new HashMap<>();

            // 获取基本设备信息
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(deviceIp);
            wsOnvifDevice.setUsername(username);
            wsOnvifDevice.setPassword(password);
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());

            try {
                FetchMainAndSubStreamUris deviceInfo = getOnvifDeviceInfo(wsOnvifDevice);
                result.put("manufacturer", deviceInfo.getFirm());
                result.put("model", deviceInfo.getModel());
                result.put("firmwareVersion", deviceInfo.getFirmwareVersion());
                result.put("streamUris", deviceInfo.getStreamUris());
            } catch (Exception e) {
                log.warn("获取设备基本信息失败，尝试其他方式: {}", e.getMessage());
            }

            // 获取 Profile Token 列表
            try {
                List<String> profileTokens = getProfileToken(wsOnvifDevice);
                result.put("profileCount", profileTokens != null ? profileTokens.size() : 0);
                result.put("profiles", profileTokens);
            } catch (Exception e) {
                log.warn("获取 Profile 信息失败: {}", e.getMessage());
            }

            result.put("success", true);
            log.info("✅ 获取 ONVIF 设备信息成功: {}", result);
            return result;
        } catch (Exception e) {
            log.error("❌ 获取 ONVIF 设备信息失败", e);
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", e.getMessage());
            return errorResult;
        }
    }

    /**
     * 根据设备id获取设备信息
     *
     * @param id 设备id
     * @return 设备信息
     */
    @Override
    public Map<String, Object> getDeviceInfoById(Long id) {
        log.info("开始获取ONVIF设备信息, deviceId: {}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId: {}, code: {}, msg: {}", id, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId: {}, IP: {}", id, device.getIpAddress());

        // 使用设备的IP、用户名和密码调用获取设备信息方法
        Map<String, Object> result = getDeviceInfo(device.getIpAddress(), device.getUserName(), device.getPassword());
        log.info("获取ONVIF设备信息成功, deviceId: {}, IP: {}", id, device.getIpAddress());
        return result;
    }

    // 获取快照地址
    private static String GetSnapshotUri(String username, String nonce, String created, String passwordDigest, String profileToken) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <GetSnapshotUri xmlns=\"http://www.onvif.org/ver20/media/wsdl\">\n" + "      <ProfileToken>" + profileToken + "</ProfileToken>\n" + "    </GetSnapshotUri>\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    // 获取快照地址 -- 解析
    private static String parseSoapResponseSnapshotUri(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isEmpty()) {
            throw new IllegalArgumentException("响应内容不能为空");
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        NodeList uriNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver20/media/wsdl", "Uri");
        if (uriNodes.getLength() > 0) {
            return uriNodes.item(0).getTextContent();
        } else {
            throw new RuntimeException("Uri not found in the SOAP response");
        }
    }

    /**
     * 获取快照地址
     */
    public static String getSnapshotUri(WSOnvifDevice onvifDevice, String profileToken) {
        if (onvifDevice == null || profileToken == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        
        byte[] nonceBytes = RandomUtil.randomBytes(16);
        String nonce = Base64.encode(nonceBytes);
        String created = Instant.now().toString();
        String passwordDigest = calculatePasswordDigest(nonceBytes, created, onvifDevice.getPassword());
        String soapRequest = GetSnapshotUri(onvifDevice.getUsername(), nonce, created, passwordDigest, profileToken);
        String url = "http://" + onvifDevice.getIp() + "/onvif/media_service";
        HttpResponse response = HttpRequest.post(url).header("Content-Type", "application/soap+xml; charset=utf-8").body(soapRequest).execute();
        if (response.getStatus() == 200) {
            try {
                return parseSoapResponseSnapshotUri(response.body());
            } catch (Exception e) {
                throw new RuntimeException("解析快照地址失败: " + e.getMessage(), e);
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取快照地址失败，状态码: " + response.getStatus());
    }

    @Override
    public Long captureAndSave(String deviceIp, String username, String password, Integer channelId, String snapshotType) {
        log.info("🚀 开始ONVIF设备抓图... 设备IP: {}, 通道: {}, 类型: {}", deviceIp, channelId, snapshotType);
        throw new RuntimeException("请使用 captureAndSaveById 方法");
    }

    @Override
    public Long captureAndSaveById(Long id, Integer channelId, String snapshotType) {
        log.info("开始ONVIF设备抓图, deviceId: {}, channelId: {}, snapshotType: {}", id, channelId, snapshotType);
        
        // 获取设备信息
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId: {}, code: {}, msg: {}", id, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId: {}, IP: {}", id, device.getIpAddress());
        
        try {
            // 获取 profile token
            WSOnvifDevice wsOnvifDevice = new WSOnvifDevice();
            wsOnvifDevice.setIp(device.getIpAddress());
            wsOnvifDevice.setUsername(device.getUserName());
            wsOnvifDevice.setPassword(device.getPassword());
            wsOnvifDevice.setAuth(AuthTypeEnum.WS_USERNAME_TOKEN.getCode());
            List<String> profileTokens = getProfileToken(wsOnvifDevice);
            if (profileTokens == null || profileTokens.isEmpty()) {
                throw new RuntimeException("未获取到设备的Profile Token");
            }
            String profileToken = profileTokens.get(0);
            
            // 获取快照 URI
            String snapshotUri = getSnapshotUri(wsOnvifDevice, profileToken);
            log.info("获取快照地址成功: {}", snapshotUri);
            
            // 如果快照 URI 不是以 http 开头，尝试补全
            if (!snapshotUri.startsWith("http")) {
                if (snapshotUri.startsWith("/")) {
                    snapshotUri = "http://" + device.getIpAddress() + snapshotUri;
                } else {
                    snapshotUri = "http://" + device.getIpAddress() + "/" + snapshotUri;
                }
            }
            
            // 下载快照图片
            byte[] imageData = null;
            try {
                log.info("开始使用 Digest 认证下载快照图片: {}", snapshotUri);
                
                // 使用 Digest 认证下载快照
                if (device.getUserName() != null && device.getPassword() != null) {
                    // 使用 OkHttp + Digest 认证，和 OnvifExecutor 保持一致
                    com.burgstaller.okhttp.digest.Credentials digestCredentials = new com.burgstaller.okhttp.digest.Credentials(device.getUserName(), device.getPassword());
                    com.burgstaller.okhttp.digest.DigestAuthenticator authenticator = new com.burgstaller.okhttp.digest.DigestAuthenticator(digestCredentials);
                    java.util.Map<String, com.burgstaller.okhttp.digest.CachingAuthenticator> authCache = new java.util.concurrent.ConcurrentHashMap<>();
                    
                    okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder()
                            .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                            .authenticator(new com.burgstaller.okhttp.CachingAuthenticatorDecorator(authenticator, authCache))
                            .addInterceptor(new com.burgstaller.okhttp.AuthenticationCacheInterceptor(authCache))
                            .build();
                    
                    okhttp3.Request okRequest = new okhttp3.Request.Builder()
                            .url(snapshotUri)
                            .get()
                            .addHeader("Accept", "image/jpeg, image/png, image/*")
                            .build();
                    
                    try (okhttp3.Response okResponse = okHttpClient.newCall(okRequest).execute()) {
                        if (okResponse.isSuccessful() && okResponse.body() != null) {
                            imageData = okResponse.body().bytes();
                            log.info("快照图片下载成功! 图片大小: {} bytes", imageData.length);
                        } else {
                            log.warn("快照图片下载失败，状态码: {}", okResponse.code());
                        }
                    }
                }
                
                // 如果 Digest 认证失败或没有用户名密码，尝试不带认证
                if (imageData == null) {
                    log.warn("Digest 认证失败，尝试不带认证下载");
                    HttpResponse imgResponse = HttpRequest.get(snapshotUri)
                            .header("Accept", "image/jpeg, image/png, image/*")
                            .timeout(30000)
                            .execute();
                    if (imgResponse.getStatus() == 200 || imgResponse.getStatus() == 206) {
                        imageData = imgResponse.bodyBytes();
                        log.info("不带认证下载快照图片成功! 图片大小: {} bytes", imageData.length);
                    }
                }
                
                if (imageData == null) {
                    throw new RuntimeException("无法下载快照图片，所有认证方式都失败");
                }
            } catch (Exception e) {
                log.error("下载快照图片异常", e);
                throw new RuntimeException("下载快照图片失败: " + e.getMessage(), e);
            }
            
            if (imageData == null || imageData.length == 0) {
                throw new RuntimeException("快照图片数据为空");
            }
            
            // 创建目录
            File dir = new File(filePath + "/onvif_snapshot/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 生成文件名并保存到文件
            String fileName = "onvif_" + device.getId() + "_" + System.currentTimeMillis() + ".jpg";
            String localFilePath = filePath + "/onvif_snapshot/" + fileName;
            String fileUrl = fileDomain + filePrefix + "/onvif_snapshot/" + fileName;
            
            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                fos.write(imageData);
            }
            
            File savedFile = new File(localFilePath);
            log.info("图片保存成功, deviceId:{}, channelId:{}, filePath:{}, fileUrl:{}, fileSize:{}",
                    device.getId(), channelId, localFilePath, fileUrl, savedFile.length());
            
            // 构造抓图记录
            QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
            snapshot.setDeviceId(device.getId());
            snapshot.setDeviceCode(device.getDeviceCode());
            snapshot.setDeviceName(device.getDeviceName());
            snapshot.setFileUrl(fileUrl);
            snapshot.setFilePath(localFilePath);
            snapshot.setFileSize(savedFile.length());
            snapshot.setFileName(fileName);
            snapshot.setFileType("jpg");
            snapshot.setSnapshotType(snapshotType);
            snapshot.setSdkType("onvif");
            snapshot.setChannel(Long.valueOf(channelId));
            snapshot.setCaptureTime(new Date());
            
            // 保存到数据库
            R<Long> result = remoteQsDeviceSnapshotService.add(snapshot, SecurityConstants.INNER);
            
            if (R.isSuccess(result)) {
                log.info("✅ ONVIF设备抓图记录保存成功, snapshotId:{}", result.getData());
                return result.getData();
            } else {
                String errorMsg = "保存抓图记录失败: " + result.getMsg();
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } catch (Exception e) {
            log.error("❌ ONVIF设备抓图失败", e);
            throw new RuntimeException("ONVIF设备抓图失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getStorageConfigurations(String deviceIp, String username, String password) {
        try {
            log.info("获取存储配置: deviceIp={}", deviceIp);
            StorageInfoResult result = getStorageInfo(deviceIp, username, password, "configurations");
            return result.toMap();
        } catch (Exception e) {
            log.error("获取存储配置失败", e);
            throw new RuntimeException("获取存储配置失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getStorageCapabilities(String deviceIp, String username, String password) {
        try {
            log.info("获取存储能力: deviceIp={}", deviceIp);
            StorageInfoResult result = getStorageInfo(deviceIp, username, password, "capabilities");
            return result.toMap();
        } catch (Exception e) {
            log.error("获取存储能力失败", e);
            throw new RuntimeException("获取存储能力失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getStorageState(String deviceIp, String username, String password) {
        try {
            log.info("获取存储状态: deviceIp={}", deviceIp);
            StorageInfoResult result = getStorageInfo(deviceIp, username, password, "state");
            return result.toMap();
        } catch (Exception e) {
            log.error("获取存储状态失败", e);
            throw new RuntimeException("获取存储状态失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getStorageConfigurationsById(Long id) {
        try {
            log.info("根据设备id获取存储配置: id={}", id);
            R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (!R.isSuccess(deviceR)) {
                throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
            }
            QsDevice device = deviceR.getData();
            StorageInfoResult result = getStorageInfo(device.getIpAddress(), device.getUserName(), device.getPassword(), "configurations");
            return result.toMap();
        } catch (Exception e) {
            log.error("根据设备id获取存储配置失败", e);
            throw new RuntimeException("根据设备id获取存储配置失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getStorageCapabilitiesById(Long id) {
        try {
            log.info("根据设备id获取存储能力: id={}", id);
            R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (!R.isSuccess(deviceR)) {
                throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
            }
            QsDevice device = deviceR.getData();
            StorageInfoResult result = getStorageInfo(device.getIpAddress(), device.getUserName(), device.getPassword(), "capabilities");
            return result.toMap();
        } catch (Exception e) {
            log.error("根据设备id获取存储能力失败", e);
            throw new RuntimeException("根据设备id获取存储能力失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getStorageStateById(Long id) {
        try {
            log.info("根据设备id获取存储状态: id={}", id);
            R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (!R.isSuccess(deviceR)) {
                throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
            }
            QsDevice device = deviceR.getData();
            StorageInfoResult result = getStorageInfo(device.getIpAddress(), device.getUserName(), device.getPassword(), "state");
            return result.toMap();
        } catch (Exception e) {
            log.error("根据设备id获取存储状态失败", e);
            throw new RuntimeException("根据设备id获取存储状态失败: " + e.getMessage(), e);
        }
    }

    private StorageInfoResult getStorageInfo(String deviceIp, String username, String password, String type) {
        log.info("开始获取存储信息: deviceIp={}, type={}", deviceIp, type);
        OnvifDevice onvifDevice = new OnvifDevice(deviceIp, username, password);
        OnvifManager onvifManager = new OnvifManager();
        
        try {
            StorageInfoResult result = new StorageInfoResult();
            result.setSuccess(false);
            result.setType(type);
            
            // 获取存储信息
            final boolean[] storageReady = {false};
            final StorageInfo[] storageInfo = {null};
            final RuntimeException[] storageError = {null};
            
            StorageInfoListener listener = new StorageInfoListener() {
                @Override
                public void onStorageInfoReceived(OnvifDevice device, StorageInfo info) {
                    log.info("收到存储信息: info={}", info);
                    synchronized (storageReady) {
                        storageInfo[0] = info;
                        storageReady[0] = true;
                        storageReady.notifyAll();
                    }
                }

                @Override
                public void onError(OnvifDevice device, int errorCode, String errorMessage) {
                    log.error("获取存储信息出错: errorCode={}, errorMessage={}", errorCode, errorMessage);
                    synchronized (storageReady) {
                        storageError[0] = new RuntimeException("错误码: " + errorCode + ", 信息: " + errorMessage);
                        storageReady[0] = true;
                        storageReady.notifyAll();
                    }
                }
            };
            
            switch (type) {
                case "configurations":
                    log.info("调用getStorageConfigurations");
                    onvifManager.getStorageConfigurations(onvifDevice, listener);
                    break;
                case "capabilities":
                    log.info("调用getStorageCapabilities");
                    onvifManager.getStorageCapabilities(onvifDevice, listener);
                    break;
                case "state":
                    log.info("调用getStorageState");
                    onvifManager.getStorageState(onvifDevice, listener);
                    break;
            }
            
            log.info("等待存储信息响应...");
            synchronized (storageReady) {
                try {
                    storageReady.wait(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            if (storageError[0] != null) {
                log.error("存储信息返回错误", storageError[0]);
                throw storageError[0];
            }
            
            if (storageInfo[0] != null) {
                log.info("成功获取存储信息: {}", storageInfo[0]);
                result.setSuccess(true);
                result.setStorageInfo(storageInfo[0]);
            } else {
                log.warn("没有获取到存储信息，storageInfo为null");
            }
            
            return result;
        } finally {
            onvifManager.destroy();
        }
    }

    private static class StorageInfoResult {
        private boolean success;
        private StorageInfo storageInfo;
        private String type;
        private boolean hasError;
        private String errorMessage;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public StorageInfo getStorageInfo() {
            return storageInfo;
        }

        public void setStorageInfo(StorageInfo storageInfo) {
            this.storageInfo = storageInfo;
            // 同时设置错误信息
            if (storageInfo != null) {
                this.hasError = storageInfo.isHasError();
                this.errorMessage = storageInfo.getErrorMessage();
            }
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }

        public boolean isHasError() {
            return hasError;
        }

        public void setHasError(boolean hasError) {
            this.hasError = hasError;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("success", success);
            map.put("hasError", hasError);
            map.put("errorMessage", errorMessage);
            
            if (storageInfo != null) {
                if (type == null) {
                    // 如果没有指定类型，返回所有配置
                    // 存储配置
                    List<Map<String, Object>> configsList = new ArrayList<>();
                    for (StorageInfo.StorageConfiguration config : storageInfo.getConfigurations()) {
                        Map<String, Object> configMap = new HashMap<>();
                        configMap.put("token", config.getToken());
                        configMap.put("type", config.getType());
                        configMap.put("name", config.getName());
                        configMap.put("storageUri", config.getStorageUri());
                        configMap.put("enabled", config.getEnabled());
                        configsList.add(configMap);
                    }
                    map.put("configurations", configsList);
                    
                    // 存储能力
                    List<Map<String, Object>> capsList = new ArrayList<>();
                    for (StorageInfo.StorageCapability cap : storageInfo.getCapabilities()) {
                        Map<String, Object> capMap = new HashMap<>();
                        capMap.put("token", cap.getToken());
                        capMap.put("type", cap.getType());
                        capMap.put("recording", cap.getRecording());
                        capMap.put("search", cap.getSearch());
                        capMap.put("replay", cap.getReplay());
                        capMap.put("export", cap.getExport());
                        capsList.add(capMap);
                    }
                    map.put("capabilities", capsList);
                    
                    // 存储状态
                    List<Map<String, Object>> stateList = new ArrayList<>();
                    for (StorageInfo.StorageState state : storageInfo.getStates()) {
                        Map<String, Object> stateMap = new HashMap<>();
                        stateMap.put("token", state.getToken());
                        stateMap.put("state", state.getState());
                        stateMap.put("totalCapacity", state.getTotalCapacity());
                        stateMap.put("freeCapacity", state.getFreeCapacity());
                        stateMap.put("usedCapacity", state.getUsedCapacity());
                        stateMap.put("lastUpdated", state.getLastUpdated());
                        stateList.add(stateMap);
                    }
                    map.put("states", stateList);
                } else {
                    // 根据类型只返回对应的数据
                    switch (type) {
                        case "configurations":
                            // 存储配置
                            List<Map<String, Object>> configsList = new ArrayList<>();
                            for (StorageInfo.StorageConfiguration config : storageInfo.getConfigurations()) {
                                Map<String, Object> configMap = new HashMap<>();
                                configMap.put("token", config.getToken());
                                configMap.put("type", config.getType());
                                configMap.put("name", config.getName());
                                configMap.put("storageUri", config.getStorageUri());
                                configMap.put("enabled", config.getEnabled());
                                configsList.add(configMap);
                            }
                            map.put("configurations", configsList);
                            break;
                        case "capabilities":
                            // 存储能力
                            List<Map<String, Object>> capsList = new ArrayList<>();
                            for (StorageInfo.StorageCapability cap : storageInfo.getCapabilities()) {
                                Map<String, Object> capMap = new HashMap<>();
                                capMap.put("token", cap.getToken());
                                capMap.put("type", cap.getType());
                                capMap.put("recording", cap.getRecording());
                                capMap.put("search", cap.getSearch());
                                capMap.put("replay", cap.getReplay());
                                capMap.put("export", cap.getExport());
                                capsList.add(capMap);
                            }
                            map.put("capabilities", capsList);
                            break;
                        case "state":
                            // 存储状态
                            List<Map<String, Object>> stateList = new ArrayList<>();
                            for (StorageInfo.StorageState state : storageInfo.getStates()) {
                                Map<String, Object> stateMap = new HashMap<>();
                                stateMap.put("token", state.getToken());
                                stateMap.put("state", state.getState());
                                stateMap.put("totalCapacity", state.getTotalCapacity());
                                stateMap.put("freeCapacity", state.getFreeCapacity());
                                stateMap.put("usedCapacity", state.getUsedCapacity());
                                stateMap.put("lastUpdated", state.getLastUpdated());
                                stateList.add(stateMap);
                            }
                            map.put("states", stateList);
                            break;
                    }
                }
            }
            
            return map;
        }
    }

    private static class NetworkInfoResult {
        private boolean success;
        private NetworkInfo networkInfo;
        private String type;
        private boolean hasError;
        private String errorMessage;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public NetworkInfo getNetworkInfo() {
            return networkInfo;
        }

        public void setNetworkInfo(NetworkInfo networkInfo) {
            this.networkInfo = networkInfo;
            // 同时设置错误信息
            if (networkInfo != null) {
                this.hasError = networkInfo.isHasError();
                this.errorMessage = networkInfo.getErrorMessage();
            }
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }

        public boolean isHasError() {
            return hasError;
        }

        public void setHasError(boolean hasError) {
            this.hasError = hasError;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("success", success);
            map.put("hasError", hasError);
            map.put("errorMessage", errorMessage);
            
            if (networkInfo != null) {
                if (type == null) {
                    // 如果没有指定类型，返回所有配置
                    // 网络接口配置
                    List<Map<String, Object>> interfacesList = new ArrayList<>();
                    for (NetworkInfo.NetworkInterface iface : networkInfo.getNetworkInterfaces()) {
                        Map<String, Object> ifaceMap = new HashMap<>();
                        ifaceMap.put("token", iface.getToken());
                        ifaceMap.put("name", iface.getName());
                        ifaceMap.put("enabled", iface.isEnabled());
                        ifaceMap.put("hwAddress", iface.getHwAddress());
                        ifaceMap.put("ipv4Address", iface.getIpv4Address());
                        ifaceMap.put("ipv4SubnetMask", iface.getIpv4SubnetMask());
                        ifaceMap.put("ipv4Gateway", iface.getIpv4Gateway());
                        ifaceMap.put("ipv6Address", iface.getIpv6Address());
                        ifaceMap.put("ipv6PrefixLength", iface.getIpv6PrefixLength());
                        ifaceMap.put("ipv6Gateway", iface.getIpv6Gateway());
                        ifaceMap.put("dnsServers", iface.getDnsServers());
                        ifaceMap.put("dhcpEnabled", iface.isDhcpEnabled());
                        interfacesList.add(ifaceMap);
                    }
                    map.put("networkInterfaces", interfacesList);
                    
                    // 网络协议配置
                    List<Map<String, Object>> protocolsList = new ArrayList<>();
                    for (NetworkInfo.NetworkProtocol protocol : networkInfo.getNetworkProtocols()) {
                        Map<String, Object> protocolMap = new HashMap<>();
                        protocolMap.put("name", protocol.getName());
                        protocolMap.put("enabled", protocol.isEnabled());
                        protocolMap.put("port", protocol.getPort());
                        protocolMap.put("tlsEnabled", protocol.isTlsEnabled());
                        protocolsList.add(protocolMap);
                    }
                    map.put("networkProtocols", protocolsList);
                    
                    // 网络状态
                    NetworkInfo.NetworkStatus status = networkInfo.getNetworkStatus();
                    if (status != null) {
                        Map<String, Object> statusMap = new HashMap<>();
                        statusMap.put("connected", status.isConnected());
                        statusMap.put("connectionType", status.getConnectionType());
                        statusMap.put("packetLoss", status.getPacketLoss());
                        statusMap.put("latency", status.getLatency());
                        statusMap.put("bytesSent", status.getBytesSent());
                        statusMap.put("bytesReceived", status.getBytesReceived());
                        map.put("networkStatus", statusMap);
                    }
                } else {
                    // 根据类型只返回对应的数据
                    switch (type) {
                        case "interfaces":
                            // 网络接口配置
                            List<Map<String, Object>> interfacesList = new ArrayList<>();
                            for (NetworkInfo.NetworkInterface iface : networkInfo.getNetworkInterfaces()) {
                                Map<String, Object> ifaceMap = new HashMap<>();
                                ifaceMap.put("token", iface.getToken());
                                ifaceMap.put("name", iface.getName());
                                ifaceMap.put("enabled", iface.isEnabled());
                                ifaceMap.put("hwAddress", iface.getHwAddress());
                                ifaceMap.put("ipv4Address", iface.getIpv4Address());
                                ifaceMap.put("ipv4SubnetMask", iface.getIpv4SubnetMask());
                                ifaceMap.put("ipv4Gateway", iface.getIpv4Gateway());
                                ifaceMap.put("ipv6Address", iface.getIpv6Address());
                                ifaceMap.put("ipv6PrefixLength", iface.getIpv6PrefixLength());
                                ifaceMap.put("ipv6Gateway", iface.getIpv6Gateway());
                                ifaceMap.put("dnsServers", iface.getDnsServers());
                                ifaceMap.put("dhcpEnabled", iface.isDhcpEnabled());
                                interfacesList.add(ifaceMap);
                            }
                            map.put("networkInterfaces", interfacesList);
                            break;
                        case "protocols":
                            // 网络协议配置
                            List<Map<String, Object>> protocolsList = new ArrayList<>();
                            for (NetworkInfo.NetworkProtocol protocol : networkInfo.getNetworkProtocols()) {
                                Map<String, Object> protocolMap = new HashMap<>();
                                protocolMap.put("name", protocol.getName());
                                protocolMap.put("enabled", protocol.isEnabled());
                                protocolMap.put("port", protocol.getPort());
                                protocolMap.put("tlsEnabled", protocol.isTlsEnabled());
                                protocolsList.add(protocolMap);
                            }
                            map.put("networkProtocols", protocolsList);
                            break;
                    }
                }
            }
            
            return map;
        }
    }

    private static class MediaInfoResult {
        private boolean success;
        private MediaInfo mediaInfo;
        private String type;
        private boolean hasError;
        private String errorMessage;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public MediaInfo getMediaInfo() {
            return mediaInfo;
        }

        public void setMediaInfo(MediaInfo mediaInfo) {
            this.mediaInfo = mediaInfo;
            // 同时设置错误信息
            if (mediaInfo != null) {
                this.hasError = mediaInfo.isHasError();
                this.errorMessage = mediaInfo.getErrorMessage();
            }
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }

        public boolean isHasError() {
            return hasError;
        }

        public void setHasError(boolean hasError) {
            this.hasError = hasError;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("success", success);
            map.put("hasError", hasError);
            map.put("errorMessage", errorMessage);
            
            if (mediaInfo != null) {
                if (type == null) {
                    // 如果没有指定类型，返回所有配置
                    // 视频源配置
                    List<Map<String, Object>> videoSourcesList = new ArrayList<>();
                    for (MediaInfo.VideoSourceConfig config : mediaInfo.getVideoSourceConfigs()) {
                        Map<String, Object> configMap = new HashMap<>();
                        configMap.put("token", config.getToken());
                        configMap.put("name", config.getName());
                        configMap.put("sourceToken", config.getSourceToken());
                        configMap.put("width", config.getWidth());
                        configMap.put("height", config.getHeight());
                        configMap.put("framerate", config.getFramerate());
                        configMap.put("type", config.getType());
                        videoSourcesList.add(configMap);
                    }
                    map.put("videoSourceConfigs", videoSourcesList);
                    
                    // 视频编码器配置
                    List<Map<String, Object>> videoEncodersList = new ArrayList<>();
                    for (MediaInfo.VideoEncoderConfig config : mediaInfo.getVideoEncoderConfigs()) {
                        Map<String, Object> configMap = new HashMap<>();
                        configMap.put("token", config.getToken());
                        configMap.put("name", config.getName());
                        configMap.put("sourceToken", config.getSourceToken());
                        configMap.put("encoding", config.getEncoding());
                        configMap.put("width", config.getWidth());
                        configMap.put("height", config.getHeight());
                        configMap.put("framerate", config.getFramerate());
                        configMap.put("bitrate", config.getBitrate());
                        configMap.put("quality", config.getQuality());
                        configMap.put("govLength", config.getGovLength());
                        configMap.put("profile", config.getProfile());
                        videoEncodersList.add(configMap);
                    }
                    map.put("videoEncoderConfigs", videoEncodersList);
                    
                    // 音频源配置
                    List<Map<String, Object>> audioSourcesList = new ArrayList<>();
                    for (MediaInfo.AudioSourceConfig config : mediaInfo.getAudioSourceConfigs()) {
                        Map<String, Object> configMap = new HashMap<>();
                        configMap.put("token", config.getToken());
                        configMap.put("name", config.getName());
                        configMap.put("sourceToken", config.getSourceToken());
                        configMap.put("type", config.getType());
                        configMap.put("channels", config.getChannels());
                        configMap.put("sampleRate", config.getSampleRate());
                        configMap.put("bitDepth", config.getBitDepth());
                        audioSourcesList.add(configMap);
                    }
                    map.put("audioSourceConfigs", audioSourcesList);
                    
                    // 音频编码器配置
                    List<Map<String, Object>> audioEncodersList = new ArrayList<>();
                    for (MediaInfo.AudioEncoderConfig config : mediaInfo.getAudioEncoderConfigs()) {
                        Map<String, Object> configMap = new HashMap<>();
                        configMap.put("token", config.getToken());
                        configMap.put("name", config.getName());
                        configMap.put("sourceToken", config.getSourceToken());
                        configMap.put("encoding", config.getEncoding());
                        configMap.put("bitrate", config.getBitrate());
                        configMap.put("sampleRate", config.getSampleRate());
                        configMap.put("channels", config.getChannels());
                        configMap.put("quality", config.getQuality());
                        audioEncodersList.add(configMap);
                    }
                    map.put("audioEncoderConfigs", audioEncodersList);
                    
                    // 视频输出配置
                    List<Map<String, Object>> videoOutputsList = new ArrayList<>();
                    for (MediaInfo.VideoOutputConfig config : mediaInfo.getVideoOutputConfigs()) {
                        Map<String, Object> configMap = new HashMap<>();
                        configMap.put("token", config.getToken());
                        configMap.put("name", config.getName());
                        configMap.put("layout", config.getLayout());
                        configMap.put("resolution", config.getResolution());
                        configMap.put("refreshRate", config.getRefreshRate());
                        videoOutputsList.add(configMap);
                    }
                    map.put("videoOutputConfigs", videoOutputsList);
                } else {
                    // 根据类型只返回对应的数据
                    switch (type) {
                        case "videoSource":
                            // 视频源配置
                            List<Map<String, Object>> videoSourcesList = new ArrayList<>();
                            for (MediaInfo.VideoSourceConfig config : mediaInfo.getVideoSourceConfigs()) {
                                Map<String, Object> configMap = new HashMap<>();
                                configMap.put("token", config.getToken());
                                configMap.put("name", config.getName());
                                configMap.put("sourceToken", config.getSourceToken());
                                configMap.put("width", config.getWidth());
                                configMap.put("height", config.getHeight());
                                configMap.put("framerate", config.getFramerate());
                                configMap.put("type", config.getType());
                                videoSourcesList.add(configMap);
                            }
                            map.put("videoSourceConfigs", videoSourcesList);
                            break;
                        case "videoEncoder":
                            // 视频编码器配置
                            List<Map<String, Object>> videoEncodersList = new ArrayList<>();
                            for (MediaInfo.VideoEncoderConfig config : mediaInfo.getVideoEncoderConfigs()) {
                                Map<String, Object> configMap = new HashMap<>();
                                configMap.put("token", config.getToken());
                                configMap.put("name", config.getName());
                                configMap.put("sourceToken", config.getSourceToken());
                                configMap.put("encoding", config.getEncoding());
                                configMap.put("width", config.getWidth());
                                configMap.put("height", config.getHeight());
                                configMap.put("framerate", config.getFramerate());
                                configMap.put("bitrate", config.getBitrate());
                                configMap.put("quality", config.getQuality());
                                configMap.put("govLength", config.getGovLength());
                                configMap.put("profile", config.getProfile());
                                videoEncodersList.add(configMap);
                            }
                            map.put("videoEncoderConfigs", videoEncodersList);
                            break;
                        case "audioSource":
                            // 音频源配置
                            List<Map<String, Object>> audioSourcesList = new ArrayList<>();
                            for (MediaInfo.AudioSourceConfig config : mediaInfo.getAudioSourceConfigs()) {
                                Map<String, Object> configMap = new HashMap<>();
                                configMap.put("token", config.getToken());
                                configMap.put("name", config.getName());
                                configMap.put("sourceToken", config.getSourceToken());
                                configMap.put("type", config.getType());
                                configMap.put("channels", config.getChannels());
                                configMap.put("sampleRate", config.getSampleRate());
                                configMap.put("bitDepth", config.getBitDepth());
                                audioSourcesList.add(configMap);
                            }
                            map.put("audioSourceConfigs", audioSourcesList);
                            break;
                        case "audioEncoder":
                            // 音频编码器配置
                            List<Map<String, Object>> audioEncodersList = new ArrayList<>();
                            for (MediaInfo.AudioEncoderConfig config : mediaInfo.getAudioEncoderConfigs()) {
                                Map<String, Object> configMap = new HashMap<>();
                                configMap.put("token", config.getToken());
                                configMap.put("name", config.getName());
                                configMap.put("sourceToken", config.getSourceToken());
                                configMap.put("encoding", config.getEncoding());
                                configMap.put("bitrate", config.getBitrate());
                                configMap.put("sampleRate", config.getSampleRate());
                                configMap.put("channels", config.getChannels());
                                configMap.put("quality", config.getQuality());
                                audioEncodersList.add(configMap);
                            }
                            map.put("audioEncoderConfigs", audioEncodersList);
                            break;
                        case "videoOutput":
                            // 视频输出配置
                            List<Map<String, Object>> videoOutputsList = new ArrayList<>();
                            for (MediaInfo.VideoOutputConfig config : mediaInfo.getVideoOutputConfigs()) {
                                Map<String, Object> configMap = new HashMap<>();
                                configMap.put("token", config.getToken());
                                configMap.put("name", config.getName());
                                configMap.put("layout", config.getLayout());
                                configMap.put("resolution", config.getResolution());
                                configMap.put("refreshRate", config.getRefreshRate());
                                videoOutputsList.add(configMap);
                            }
                            map.put("videoOutputConfigs", videoOutputsList);
                            break;
                    }
                }
            }
            
            return map;
        }
    }

    private NetworkInfoResult getNetworkInfo(String deviceIp, String username, String password, String type) {
        log.info("开始获取网络信息: deviceIp={}, type={}", deviceIp, type);
        com.ruoyi.onvif.models.OnvifDevice onvifDevice = new com.ruoyi.onvif.models.OnvifDevice(deviceIp, username, password);
        OnvifManager onvifManager = new OnvifManager();
        
        try {
            NetworkInfoResult result = new NetworkInfoResult();
            result.setSuccess(false);
            result.setType(type);
            
            final boolean[] networkReady = {false};
            final NetworkInfo[] networkInfo = {null};
            final RuntimeException[] networkError = {null};
            
            NetworkInfoListener listener = new NetworkInfoListener() {
                @Override
                public void onNetworkInfoReceived(com.ruoyi.onvif.models.OnvifDevice device, NetworkInfo info) {
                    log.info("收到网络信息: info={}", info);
                    synchronized (networkReady) {
                        networkInfo[0] = info;
                        networkReady[0] = true;
                        networkReady.notifyAll();
                    }
                }

                @Override
                public void onError(com.ruoyi.onvif.models.OnvifDevice device, int errorCode, String errorMessage) {
                    log.error("获取网络信息出错: errorCode={}, errorMessage={}", errorCode, errorMessage);
                    synchronized (networkReady) {
                        networkError[0] = new RuntimeException("错误码: " + errorCode + ", 信息: " + errorMessage);
                        networkReady[0] = true;
                        networkReady.notifyAll();
                    }
                }
            };
            
            switch (type) {
                case "interfaces":
                    log.info("调用getNetworkInterfaces");
                    onvifManager.getNetworkInterfaces(onvifDevice, listener);
                    break;
                case "protocols":
                    log.info("调用getNetworkProtocols");
                    onvifManager.getNetworkProtocols(onvifDevice, listener);
                    break;
            }
            
            log.info("等待网络信息响应...");
            synchronized (networkReady) {
                try {
                    networkReady.wait(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            if (networkError[0] != null) {
                log.error("网络信息返回错误", networkError[0]);
                throw networkError[0];
            }
            
            if (networkInfo[0] != null) {
                log.info("成功获取网络信息: {}", networkInfo[0]);
                result.setSuccess(true);
                result.setNetworkInfo(networkInfo[0]);
            } else {
                log.warn("没有获取到网络信息，networkInfo为null");
            }
            
            return result;
        } finally {
            onvifManager.destroy();
        }
    }

    private MediaInfoResult getMediaInfo(String deviceIp, String username, String password, String type) {
        log.info("开始获取媒体信息: deviceIp={}, type={}", deviceIp, type);
        com.ruoyi.onvif.models.OnvifDevice onvifDevice = new com.ruoyi.onvif.models.OnvifDevice(deviceIp, username, password);
        OnvifManager onvifManager = new OnvifManager();
        
        try {
            MediaInfoResult result = new MediaInfoResult();
            result.setSuccess(false);
            result.setType(type);
            
            final boolean[] mediaReady = {false};
            final MediaInfo[] mediaInfo = {null};
            final RuntimeException[] mediaError = {null};
            
            MediaInfoListener listener = new MediaInfoListener() {
                @Override
                public void onMediaInfoReceived(com.ruoyi.onvif.models.OnvifDevice device, MediaInfo info) {
                    log.info("收到媒体信息: info={}", info);
                    synchronized (mediaReady) {
                        mediaInfo[0] = info;
                        mediaReady[0] = true;
                        mediaReady.notifyAll();
                    }
                }

                @Override
                public void onError(com.ruoyi.onvif.models.OnvifDevice device, int errorCode, String errorMessage) {
                    log.error("获取媒体信息出错: errorCode={}, errorMessage={}", errorCode, errorMessage);
                    synchronized (mediaReady) {
                        mediaError[0] = new RuntimeException("错误码: " + errorCode + ", 信息: " + errorMessage);
                        mediaReady[0] = true;
                        mediaReady.notifyAll();
                    }
                }
            };
            
            switch (type) {
                case "videoSource":
                    log.info("调用getVideoSourceConfigs");
                    onvifManager.getVideoSourceConfigs(onvifDevice, listener);
                    break;
                case "videoEncoder":
                    log.info("调用getVideoEncoderConfigs");
                    onvifManager.getVideoEncoderConfigs(onvifDevice, listener);
                    break;
                case "audioSource":
                    log.info("调用getAudioSourceConfigs");
                    onvifManager.getAudioSourceConfigs(onvifDevice, listener);
                    break;
                case "audioEncoder":
                    log.info("调用getAudioEncoderConfigs");
                    onvifManager.getAudioEncoderConfigs(onvifDevice, listener);
                    break;
                case "videoOutput":
                    log.info("调用getVideoOutputConfigs");
                    onvifManager.getVideoOutputConfigs(onvifDevice, listener);
                    break;
            }
            
            log.info("等待媒体信息响应...");
            synchronized (mediaReady) {
                try {
                    mediaReady.wait(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            if (mediaError[0] != null) {
                log.error("媒体信息返回错误", mediaError[0]);
                throw mediaError[0];
            }
            
            if (mediaInfo[0] != null) {
                log.info("成功获取媒体信息: {}", mediaInfo[0]);
                result.setSuccess(true);
                result.setMediaInfo(mediaInfo[0]);
            } else {
                log.warn("没有获取到媒体信息，mediaInfo为null");
            }
            
            return result;
        } finally {
            onvifManager.destroy();
        }
    }

    @Override
    public Map<String, Object> getNetworkInterfaces(String deviceIp, String username, String password) {
        NetworkInfoResult result = getNetworkInfo(deviceIp, username, password, "interfaces");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getNetworkProtocols(String deviceIp, String username, String password) {
        NetworkInfoResult result = getNetworkInfo(deviceIp, username, password, "protocols");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getVideoSourceConfigs(String deviceIp, String username, String password) {
        MediaInfoResult result = getMediaInfo(deviceIp, username, password, "videoSource");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getVideoEncoderConfigs(String deviceIp, String username, String password) {
        MediaInfoResult result = getMediaInfo(deviceIp, username, password, "videoEncoder");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getAudioSourceConfigs(String deviceIp, String username, String password) {
        MediaInfoResult result = getMediaInfo(deviceIp, username, password, "audioSource");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getAudioEncoderConfigs(String deviceIp, String username, String password) {
        MediaInfoResult result = getMediaInfo(deviceIp, username, password, "audioEncoder");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getVideoOutputConfigs(String deviceIp, String username, String password) {
        MediaInfoResult result = getMediaInfo(deviceIp, username, password, "videoOutput");
        return result.toMap();
    }

    @Override
    public Map<String, Object> getNetworkInterfacesById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getNetworkInterfaces(device.getIpAddress(), device.getUserName(), device.getPassword());
    }

    @Override
    public Map<String, Object> getNetworkProtocolsById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getNetworkProtocols(device.getIpAddress(), device.getUserName(), device.getPassword());
    }

    @Override
    public Map<String, Object> getVideoSourceConfigsById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getVideoSourceConfigs(device.getIpAddress(), device.getUserName(), device.getPassword());
    }

    @Override
    public Map<String, Object> getVideoEncoderConfigsById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getVideoEncoderConfigs(device.getIpAddress(), device.getUserName(), device.getPassword());
    }

    @Override
    public Map<String, Object> getAudioSourceConfigsById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getAudioSourceConfigs(device.getIpAddress(), device.getUserName(), device.getPassword());
    }

    @Override
    public Map<String, Object> getAudioEncoderConfigsById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getAudioEncoderConfigs(device.getIpAddress(), device.getUserName(), device.getPassword());
    }

    @Override
    public Map<String, Object> getVideoOutputConfigsById(Long id) {
        R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (!R.isSuccess(deviceR)) {
            throw new RuntimeException("获取设备信息失败: " + deviceR.getMsg());
        }
        QsDevice device = deviceR.getData();
        return getVideoOutputConfigs(device.getIpAddress(), device.getUserName(), device.getPassword());
    }
}
