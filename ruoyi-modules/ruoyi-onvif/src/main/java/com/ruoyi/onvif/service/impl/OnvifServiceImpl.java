package com.ruoyi.onvif.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.onvif.DiscoveryManager;
import com.ruoyi.onvif.OnvifManager;
import com.ruoyi.onvif.api.domain.WSOnvifDevice;
import com.ruoyi.onvif.domain.FetchMainAndSubStreamUris;
import com.ruoyi.onvif.domain.WSDiscoveryDevice;
import com.ruoyi.onvif.enums.AuthTypeEnum;
import com.ruoyi.onvif.listeners.DiscoveryListener;
import com.ruoyi.onvif.models.Device;
import com.ruoyi.onvif.models.OnvifDevice;
import com.ruoyi.onvif.models.OnvifMediaProfile;
import com.ruoyi.onvif.service.IOnvifService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
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
            manager.setDiscoveryTimeout(10000);
            DiscoveryListener listener = new DiscoveryListener() {
                @Override
                public void onDiscoveryStarted() {
                    log.debug("ONVIF 设备发现开始...");
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
            
            // 解析时间字符串
            java.time.LocalDateTime localDateTime = java.time.LocalDateTime.parse(dateTime, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            int year = localDateTime.getYear();
            int month = localDateTime.getMonthValue();
            int day = localDateTime.getDayOfMonth();
            int hour = localDateTime.getHour();
            int minute = localDateTime.getMinute();
            int second = localDateTime.getSecond();
            
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
}
