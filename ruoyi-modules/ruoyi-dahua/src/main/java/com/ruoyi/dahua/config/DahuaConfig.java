package com.ruoyi.dahua.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 大华配置
 *
 * @author fengcheng
 */
@Data
@Component
@ConfigurationProperties(prefix = "dahua")
public class DahuaConfig {

    /**
     * [必须修改] 大华服务器IP（内网或公网）
     */
    private String ip;

    /**
     * [必须修改] 大华服务器端口
     */
    private int port;

    /**
     * 是否公网部署
     */
    private boolean isPublicNetwork;
}