package com.ruoyi.onvif.enums;

import java.util.Arrays;

/**
 * 认证类型枚举
 * 用于区分 WS-UsernameToken 和 Digest 认证
 */
public enum AuthTypeEnum {

    /**
     * 1: WS-UsernameToken (通常用于 SOAP/WebService)
     */
    WS_USERNAME_TOKEN("1", "WS-UsernameToken"),

    /**
     * 2: Digest (通常用于 HTTP/REST API)
     */
    DIGEST("2", "Digest");

    // 枚举对应的代码值 (1 或 2)
    private final String code;
    // 枚举对应的描述
    private final String desc;

    // 私有构造函数
    AuthTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 获取代码值
    public String getCode() {
        return code;
    }

    // 获取描述
    public String getDesc() {
        return desc;
    }

    /**
     * 根据 code 值获取对应的枚举对象
     *
     * @param code 传入的 code (1 或 2)
     * @return 对应的枚举对象，如果找不到则返回 null
     */
    public static AuthTypeEnum getByCode(String code) {
        return Arrays.stream(AuthTypeEnum.values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElse(null);
    }
}