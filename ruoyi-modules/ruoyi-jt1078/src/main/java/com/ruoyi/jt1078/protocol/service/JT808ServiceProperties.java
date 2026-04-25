package com.ruoyi.jt1078.protocol.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jt808-service")
public class JT808ServiceProperties {

    public JT808ServiceProperties() {
    }

    public JT808ServiceProperties(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected String baseUrl;

}