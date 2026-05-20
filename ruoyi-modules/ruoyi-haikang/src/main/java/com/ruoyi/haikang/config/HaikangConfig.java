package com.ruoyi.haikang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "haikang")
public class HaikangConfig {

    /**
     * 是否监听报警
     */
    private Boolean enableAlarmListen = true;
}
