package com.ruoyi.onvif.parsers;

import com.ruoyi.onvif.models.NetworkInfo;
import com.ruoyi.onvif.responses.OnvifResponse;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * 网络信息解析器
 */
public class NetworkInfoParser extends OnvifParser<NetworkInfo> {
    public static final String TAG = NetworkInfoParser.class.getSimpleName();

    @Override
    public NetworkInfo parse(OnvifResponse response) {
        NetworkInfo networkInfo = new NetworkInfo();

        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();

            NetworkInfo.NetworkInterface currentInterface = null;
            NetworkInfo.NetworkProtocol currentProtocol = null;

            // 首先检测响应类型
            String responseType = null;
            if (response.getXml().contains("GetNetworkInterfacesResponse")) {
                responseType = "interfaces";
            } else if (response.getXml().contains("GetNetworkProtocolsResponse")) {
                responseType = "protocols";
            }

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String fullTagName = getXpp().getName();
                // 去掉命名空间前缀，只保留标签名
                String tagName = (fullTagName != null && fullTagName.contains(":")) ? fullTagName.substring(fullTagName.indexOf(":") + 1) : fullTagName;

                // 检测 SOAP Fault
                if (eventType == XmlPullParser.START_TAG && "Fault".equals(tagName)) {
                    networkInfo.setHasError(true);
                    String faultText = parseFault(getXpp());
                    networkInfo.setErrorMessage(faultText);
                } else if (eventType == XmlPullParser.START_TAG) {
                    // 处理 NetworkInterfaces 标签（复数形式）
                    if ("NetworkInterfaces".equals(tagName)) {
                        currentInterface = new NetworkInfo.NetworkInterface();
                        // 获取token属性
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentInterface.setToken(getXpp().getAttributeValue(i));
                            }
                        }
                    }
                    // 处理 NetworkInterface 标签（单数形式）
                    else if ("NetworkInterface".equals(tagName)) {
                        currentInterface = new NetworkInfo.NetworkInterface();
                        // 获取token属性
                        for (int i = 0; i < getXpp().getAttributeCount(); i++) {
                            String attrName = getXpp().getAttributeName(i);
                            if ("token".equals(attrName) || attrName.endsWith(":token")) {
                                currentInterface.setToken(getXpp().getAttributeValue(i));
                            }
                        }
                    }
                    // 处理网络接口的子标签
                    else if (currentInterface != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentInterface.setName(getXpp().getText());
                                break;
                            case "Enabled":
                                getXpp().next();
                                if (getXpp().getText() != null) currentInterface.setEnabled(Boolean.parseBoolean(getXpp().getText()));
                                break;
                            case "HwAddress":
                                getXpp().next();
                                if (getXpp().getText() != null) currentInterface.setHwAddress(getXpp().getText());
                                break;
                            case "Address":
                                getXpp().next();
                                String address = getXpp().getText();
                                if (address != null && !address.trim().isEmpty()) {
                                    // 判断是IPv4还是IPv6
                                    if (address.contains(":")) {
                                        currentInterface.setIpv6Address(address);
                                    } else {
                                        currentInterface.setIpv4Address(address);
                                    }
                                }
                                break;
                            case "PrefixLength":
                                getXpp().next();
                                if (getXpp().getText() != null) {
                                    // 这里需要判断是IPv4还是IPv6的前缀长度
                                    // 暂时同时设置两个
                                    String prefix = getXpp().getText();
                                    currentInterface.setIpv6PrefixLength(prefix);
                                    // IPv4前缀长度转成子网掩码
                                    try {
                                        int prefixLen = Integer.parseInt(prefix);
                                        if (prefixLen >= 0 && prefixLen <= 32) {
                                            // 简单的子网掩码计算
                                            String mask = "";
                                            int fullOctets = prefixLen / 8;
                                            int remainder = prefixLen % 8;
                                            for (int i = 0; i < 4; i++) {
                                                if (i < fullOctets) {
                                                    mask += "255";
                                                } else if (i == fullOctets && remainder > 0) {
                                                    int maskPart = (0xFF << (8 - remainder)) & 0xFF;
                                                    mask += maskPart;
                                                } else {
                                                    mask += "0";
                                                }
                                                if (i < 3) mask += ".";
                                            }
                                            currentInterface.setIpv4SubnetMask(mask);
                                        }
                                    } catch (NumberFormatException ignored) {}
                                }
                                break;
                            case "DHCP":
                                getXpp().next();
                                String dhcpValue = getXpp().getText();
                                if (dhcpValue != null) {
                                    if ("true".equalsIgnoreCase(dhcpValue) || "On".equalsIgnoreCase(dhcpValue)) {
                                        currentInterface.setDhcpEnabled(true);
                                    } else if ("false".equalsIgnoreCase(dhcpValue) || "Off".equalsIgnoreCase(dhcpValue)) {
                                        currentInterface.setDhcpEnabled(false);
                                    }
                                }
                                break;
                            case "DNSManual":
                                getXpp().next();
                                String dns = getXpp().getText();
                                if (dns != null && !dns.isEmpty()) {
                                    currentInterface.getDnsServers().add(dns);
                                }
                                break;
                        }
                    }
                    // 处理网络协议
                    else if ("NetworkProtocols".equals(tagName)) {
                        currentProtocol = new NetworkInfo.NetworkProtocol();
                    } else if ("NetworkProtocol".equals(tagName)) {
                        currentProtocol = new NetworkInfo.NetworkProtocol();
                    } else if (currentProtocol != null) {
                        switch (tagName) {
                            case "Name":
                                getXpp().next();
                                if (getXpp().getText() != null) currentProtocol.setName(getXpp().getText());
                                break;
                            case "Enabled":
                                getXpp().next();
                                if (getXpp().getText() != null) currentProtocol.setEnabled(Boolean.parseBoolean(getXpp().getText()));
                                break;
                            case "Port":
                                getXpp().next();
                                try {
                                    if (getXpp().getText() != null) currentProtocol.setPort(Integer.parseInt(getXpp().getText()));
                                } catch (NumberFormatException ignored) {}
                                break;
                            case "TLS":
                                getXpp().next();
                                if (getXpp().getText() != null) currentProtocol.setTlsEnabled(Boolean.parseBoolean(getXpp().getText()));
                                break;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    // 结束标签处理
                    if (("NetworkInterfaces".equals(tagName) || "NetworkInterface".equals(tagName)) && currentInterface != null) {
                        networkInfo.getNetworkInterfaces().add(currentInterface);
                        currentInterface = null;
                    } else if (("NetworkProtocols".equals(tagName) || "NetworkProtocol".equals(tagName)) && currentProtocol != null) {
                        networkInfo.getNetworkProtocols().add(currentProtocol);
                        currentProtocol = null;
                    }
                }

                eventType = getXpp().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            networkInfo.setHasError(true);
            networkInfo.setErrorMessage("解析响应时出错: " + e.getMessage());
        }

        return networkInfo;
    }
    
    /**
     * 解析 SOAP Fault
     */
    private String parseFault(XmlPullParser xpp) throws XmlPullParserException, IOException {
        StringBuilder faultText = new StringBuilder();
        int depth = 1;
        while (depth > 0 && eventType != XmlPullParser.END_DOCUMENT) {
            String fullTagName = xpp.getName();
            String tagName = (fullTagName != null && fullTagName.contains(":")) ? fullTagName.substring(fullTagName.indexOf(":") + 1) : fullTagName;

            if (eventType == XmlPullParser.START_TAG) {
                if ("Text".equals(tagName) || "Reason".equals(tagName)) {
                    xpp.next();
                    String text = xpp.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        faultText.append(text.trim()).append(" ");
                    }
                } else if ("Value".equals(tagName)) {
                    xpp.next();
                    String text = xpp.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        if (text.contains("NotImplemented") || text.contains("ActionNotSupported")) {
                            return "设备不支持此功能";
                        }
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG && "Fault".equals(tagName)) {
                depth--;
            }
            eventType = xpp.next();
        }
        String result = faultText.toString().trim();
        if (result.isEmpty()) {
            return "设备返回错误";
        }
        return result;
    }
}

