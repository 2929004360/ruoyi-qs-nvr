package com.ruoyi.zlm.api.domain;

import java.io.Serializable;


/**
 * 流地址信息
 */
public class StreamURL implements Serializable,Cloneable {

    /**
     * 协议
     */
    private String protocol;

    /**
     * 主机地址
     */
    private String host;

    /**
     * 端口
     */
    private int port = -1;

    /**
     * 定位位置
     */
    private String file;

    /**
     * 拼接后的地址
     */
    private String url;

    public StreamURL() {
    }

    public StreamURL(String protocol, String host, int port, String file) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.file = file;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUrl() {
        return this.toString();
    }

    @Override
    public String toString() {
        if (protocol != null && host != null && port != -1 ) {
            return String.format("%s://%s:%s/%s", protocol, host, port, file);
        }else {
            return null;
        }
    }
    @Override
    public StreamURL clone() throws CloneNotSupportedException {
        return (StreamURL) super.clone();
    }
}
