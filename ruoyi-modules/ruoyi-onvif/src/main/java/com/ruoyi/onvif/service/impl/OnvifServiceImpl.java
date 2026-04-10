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

        // 【准备阶段】获取 Redis 中现有的所有设备 IP，作为“待删除候选集”
        // 假设 Redis Key 是 "onvif:devices"
        Set<Object> existingIps = redisTemplate.opsForHash().keys(ONVIF_DEVICES);
        // 使用线程安全的集合，防止并发修改异常
        Set<Object> staleIps = new CopyOnWriteArraySet<>(existingIps);

        DiscoveryManager manager = new DiscoveryManager();
        // 设置发现超时时间
        manager.setDiscoveryTimeout(10000);
        DiscoveryListener listener = new DiscoveryListener() {
            @Override
            public void onDiscoveryStarted() {

            }

            @Override
            public void onDevicesFound(List<Device> devices) {
                if (devices == null || devices.isEmpty()) {
                    return;
                }

                ArrayList<WSDiscoveryDevice> devicesList = new ArrayList<>();

                for (Device device : devices) {
                    try {
                        URL url = new URL(device.getHostName());
                        String ip = url.getHost();

                        staleIps.remove(ip);

                        WSDiscoveryDevice dto = new WSDiscoveryDevice();
                        dto.setHostName(device.getHostName());
                        dto.setIp(ip);
                        devicesList.add(dto);
                    } catch (Exception e) {

                    }
                }

                if (devicesList.size() > 0) {
                    for (WSDiscoveryDevice device : devicesList) {
                        // 存储对象
                        redisTemplate.opsForHash().put(ONVIF_DEVICES, device.getIp(), device);
                    }

                    if (!staleIps.isEmpty()) {
                        log.info("🧹 扫描结束，发现 {} 个设备离线，正在清理...", staleIps.size());
                        for (Object offlineIp : staleIps) {
                            redisTemplate.opsForHash().delete(ONVIF_DEVICES, offlineIp);
                            log.info("❌ 已删除离线设备: {}", offlineIp);
                        }
                    }
                }
            }
        };

        // 【执行扫描】（这里是阻塞的，会等待 10秒 或直到扫描结束）
        manager.discover(listener);
    }

    /**
     * 验证登录onvif设备
     *
     * @param onvifDevice
     */
    @Override
    public com.ruoyi.onvif.api.domain.OnvifDevice verifyOnvifDeviceLogin(WSOnvifDevice onvifDevice) {
        com.ruoyi.onvif.api.domain.OnvifDevice returnOnvifDevice = new com.ruoyi.onvif.api.domain.OnvifDevice();

        // WS-Usemame token
        if (AuthTypeEnum.WS_USERNAME_TOKEN.getCode().equals(onvifDevice.getAuth())) {
            FetchMainAndSubStreamUris onvifDeviceInfo = getOnvifDeviceInfo(onvifDevice);
            returnOnvifDevice.setIp(onvifDevice.getIp());
            returnOnvifDevice.setFirm(onvifDeviceInfo.getFirm());
            returnOnvifDevice.setFirmwareVersion(onvifDeviceInfo.getFirmwareVersion());
            returnOnvifDevice.setModel(onvifDeviceInfo.getModel());
            returnOnvifDevice.setStreamUris(onvifDeviceInfo.getStreamUris());
            returnOnvifDevice.setUserName(onvifDevice.getUsername());
            returnOnvifDevice.setPassword(onvifDevice.getPassword());
            returnOnvifDevice.setHostName(onvifDevice.getPassword());
            return returnOnvifDevice;
        }


        List<String> streamUris = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(2);
        // Digest
        if (AuthTypeEnum.DIGEST.getCode().equals(onvifDevice.getAuth())) {
            OnvifManager onvifManager = new OnvifManager();
            OnvifDevice device2 = new OnvifDevice(onvifDevice.getIp(), onvifDevice.getUsername(), onvifDevice.getPassword());

            onvifManager.getMediaProfiles(device2, (device, mediaProfiles) -> {
                if (mediaProfiles.isEmpty()) {
                    latch.countDown();
                    return;
                }
                int[] remaining = {mediaProfiles.size()};

                for (OnvifMediaProfile profile : mediaProfiles) {
                    onvifManager.getMediaStreamURI(device2, profile, (device1, prof, uri) -> {
                        streamUris.add(uri);
                        synchronized (remaining) {
                            remaining[0]--;
                            if (remaining[0] <= 0) {
                                latch.countDown();
                            }
                        }
                    });
                }
            });
            onvifManager.getDeviceInformation(device2, (device, info) -> {
                try {
                    returnOnvifDevice.setIp(onvifDevice.getIp());
                    returnOnvifDevice.setFirm(info.getManufacturer());
                    returnOnvifDevice.setModel(info.getModel());
                    returnOnvifDevice.setFirmwareVersion(info.getFirmwareVersion());
                    returnOnvifDevice.setUserName(onvifDevice.getUsername());
                    returnOnvifDevice.setPassword(onvifDevice.getPassword());
                    returnOnvifDevice.setHostName(onvifDevice.getPassword());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            boolean completed = latch.await(10, TimeUnit.SECONDS);
            if (!completed) {
                throw new RuntimeException("ONVIF 获取信息超时");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("等待中断: " + e.getMessage());
        }
        returnOnvifDevice.setStreamUris(streamUris);
        return returnOnvifDevice;
    }

    /**
     * 获取onvif设备列表
     */
    @Override
    public ArrayList<WSDiscoveryDevice> getOnvifDeviceList() {
        Map<Object, Object> rawMap = redisTemplate.opsForHash().entries(ONVIF_DEVICES);
        ArrayList<WSDiscoveryDevice> deviceList = new ArrayList<>();
        if (rawMap.size() == 0) {
            return deviceList;
        }

        for (Object value : rawMap.values()) {
            // 先将对象转为 JSON 字符串，再转为实体类
            // 或者直接 JSON.toJavaObject((Map) value, WSDiscoveryDevice.class)
            String jsonString = JSON.toJSONString(value);
            WSDiscoveryDevice device = JSON.parseObject(jsonString, WSDiscoveryDevice.class);
            deviceList.add(device);
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
        // 先获取基本信息
        FetchMainAndSubStreamUris mercury = getMercury(onvifDevice);
        // 再获取视频流token
        List<String> profileTokens = getProfileToken(onvifDevice);
        if (!profileTokens.isEmpty()) {
            // 根据token获取播放地址
            for (String token : profileTokens) {
                String urlByToken = getProfilesUrlByToken(onvifDevice, token);
                String replace = urlByToken.replace("rtsp://", "rtsp://" + onvifDevice.getUsername() + ":" + onvifDevice.getPassword() + "@");
                mercury.addStreamUri(replace);
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
                e.printStackTrace();
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取视频流地址失败");
    }

    // 获取视频流地址 -- 解析
    private static String parseSoapResponseProfilesUrlByToken(String responseBody) throws Exception {
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
                e.printStackTrace();
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取基本信息失败");
    }

    // 生成token
    private static String calculatePasswordDigest(byte[] nonceBytes, String created, String password) {
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
        // 使用 DOM 解析器解析 XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));

        // 获取设备信息节点
        NodeList manufacturerNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "Manufacturer");
        NodeList modelNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "Model");
        NodeList firmwareVersionNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "FirmwareVersion");
        NodeList serialNumberNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "SerialNumber");
        NodeList hardwareIdNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "HardwareId");

        // 提取信息
        String manufacturer = manufacturerNodes.item(0).getTextContent();
        String model = modelNodes.item(0).getTextContent();
        String firmwareVersion = firmwareVersionNodes.item(0).getTextContent();
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
                e.printStackTrace();
            }
        } else if (response.getStatus() == 500) {
            throw new RuntimeException("该命名空间设备不支持");
        } else if (response.getStatus() == 401) {
            throw new RuntimeException("鉴权失败");
        }
        throw new RuntimeException("获取流token失败");

    }

    // 获取流信息
    private static String GetProfiles(String username, String nonce, String created, String passwordDigest) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" + "  <s:Header>\n" + "    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + "      <wsse:UsernameToken>\n" + "        <wsse:Username>" + username + "</wsse:Username>\n" + "        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + passwordDigest + "</wsse:Password>\n" + "        <wsse:Nonce>" + nonce + "</wsse:Nonce>\n" + "        <wsu:Created>" + created + "</wsu:Created>\n" + "      </wsse:UsernameToken>\n" + "    </wsse:Security>\n" + "  </s:Header>\n" + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" + "    <GetProfiles xmlns=\"http://www.onvif.org/ver20/media/wsdl\" />\n" + "  </s:Body>\n" + "</s:Envelope>";
    }

    // 获取流信息token -- 解析
    private static List<String> parseSoapResponseProfileToken(String responseBody) throws Exception {
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
}
