package com.ruoyi.onvif.models;

import java.util.ArrayList;
import java.util.List;

/**
 * ONVIF 网络配置信息
 */
public class NetworkInfo {

    private List<NetworkInterface> networkInterfaces = new ArrayList<>();
    private List<NetworkProtocol> networkProtocols = new ArrayList<>();
    private NetworkStatus networkStatus;
    private boolean hasError = false;
    private String errorMessage = "";

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

    public List<NetworkInterface> getNetworkInterfaces() {
        return networkInterfaces;
    }

    public void setNetworkInterfaces(List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    public List<NetworkProtocol> getNetworkProtocols() {
        return networkProtocols;
    }

    public void setNetworkProtocols(List<NetworkProtocol> networkProtocols) {
        this.networkProtocols = networkProtocols;
    }

    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    /**
     * 网络接口配置
     */
    public static class NetworkInterface {
        private String token;
        private String name;
        private boolean enabled;
        private String hwAddress;
        private String ipv4Address;
        private String ipv4SubnetMask;
        private String ipv4Gateway;
        private String ipv6Address;
        private String ipv6PrefixLength;
        private String ipv6Gateway;
        private List<String> dnsServers = new ArrayList<>();
        private boolean dhcpEnabled;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHwAddress() {
            return hwAddress;
        }

        public void setHwAddress(String hwAddress) {
            this.hwAddress = hwAddress;
        }

        public String getIpv4Address() {
            return ipv4Address;
        }

        public void setIpv4Address(String ipv4Address) {
            this.ipv4Address = ipv4Address;
        }

        public String getIpv4SubnetMask() {
            return ipv4SubnetMask;
        }

        public void setIpv4SubnetMask(String ipv4SubnetMask) {
            this.ipv4SubnetMask = ipv4SubnetMask;
        }

        public String getIpv4Gateway() {
            return ipv4Gateway;
        }

        public void setIpv4Gateway(String ipv4Gateway) {
            this.ipv4Gateway = ipv4Gateway;
        }

        public String getIpv6Address() {
            return ipv6Address;
        }

        public void setIpv6Address(String ipv6Address) {
            this.ipv6Address = ipv6Address;
        }

        public String getIpv6PrefixLength() {
            return ipv6PrefixLength;
        }

        public void setIpv6PrefixLength(String ipv6PrefixLength) {
            this.ipv6PrefixLength = ipv6PrefixLength;
        }

        public String getIpv6Gateway() {
            return ipv6Gateway;
        }

        public void setIpv6Gateway(String ipv6Gateway) {
            this.ipv6Gateway = ipv6Gateway;
        }

        public List<String> getDnsServers() {
            return dnsServers;
        }

        public void setDnsServers(List<String> dnsServers) {
            this.dnsServers = dnsServers;
        }

        public boolean isDhcpEnabled() {
            return dhcpEnabled;
        }

        public void setDhcpEnabled(boolean dhcpEnabled) {
            this.dhcpEnabled = dhcpEnabled;
        }

        @Override
        public String toString() {
            return "NetworkInterface{" +
                    "token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    ", enabled=" + enabled +
                    ", hwAddress='" + hwAddress + '\'' +
                    ", ipv4Address='" + ipv4Address + '\'' +
                    ", ipv4SubnetMask='" + ipv4SubnetMask + '\'' +
                    ", ipv4Gateway='" + ipv4Gateway + '\'' +
                    ", ipv6Address='" + ipv6Address + '\'' +
                    ", ipv6PrefixLength='" + ipv6PrefixLength + '\'' +
                    ", ipv6Gateway='" + ipv6Gateway + '\'' +
                    ", dnsServers=" + dnsServers +
                    ", dhcpEnabled=" + dhcpEnabled +
                    '}';
        }
    }

    /**
     * 网络协议配置
     */
    public static class NetworkProtocol {
        private String name;
        private boolean enabled;
        private int port;
        private boolean tlsEnabled;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isTlsEnabled() {
            return tlsEnabled;
        }

        public void setTlsEnabled(boolean tlsEnabled) {
            this.tlsEnabled = tlsEnabled;
        }

        @Override
        public String toString() {
            return "NetworkProtocol{" +
                    "name='" + name + '\'' +
                    ", enabled=" + enabled +
                    ", port=" + port +
                    ", tlsEnabled=" + tlsEnabled +
                    '}';
        }
    }

    /**
     * 网络连接状态
     */
    public static class NetworkStatus {
        private boolean connected;
        private String connectionType;
        private int packetLoss;
        private int latency;
        private long bytesSent;
        private long bytesReceived;

        public boolean isConnected() {
            return connected;
        }

        public void setConnected(boolean connected) {
            this.connected = connected;
        }

        public String getConnectionType() {
            return connectionType;
        }

        public void setConnectionType(String connectionType) {
            this.connectionType = connectionType;
        }

        public int getPacketLoss() {
            return packetLoss;
        }

        public void setPacketLoss(int packetLoss) {
            this.packetLoss = packetLoss;
        }

        public int getLatency() {
            return latency;
        }

        public void setLatency(int latency) {
            this.latency = latency;
        }

        public long getBytesSent() {
            return bytesSent;
        }

        public void setBytesSent(long bytesSent) {
            this.bytesSent = bytesSent;
        }

        public long getBytesReceived() {
            return bytesReceived;
        }

        public void setBytesReceived(long bytesReceived) {
            this.bytesReceived = bytesReceived;
        }

        @Override
        public String toString() {
            return "NetworkStatus{" +
                    "connected=" + connected +
                    ", connectionType='" + connectionType + '\'' +
                    ", packetLoss=" + packetLoss +
                    ", latency=" + latency +
                    ", bytesSent=" + bytesSent +
                    ", bytesReceived=" + bytesReceived +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NetworkInfo{" +
                "networkInterfaces=" + networkInterfaces +
                ", networkProtocols=" + networkProtocols +
                ", networkStatus=" + networkStatus +
                '}';
    }
}

