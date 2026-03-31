package com.ruoyi.dahua.lib.enumeration;

/**
 * 码流支持类型
 *
 * @author 47040
 * @since Created at 2021/5/26 19:42
 */
public enum STREAM_RATE_TYPE {
    RATE_10(10),
    RATE_20(20),
    RATE_32(32),
    RATE_48(48),
    RATE_64(64),
    RATE_80(80),
    RATE_96(96),
    RATE_128(128),
    RATE_160(160),
    RATE_192(192),
    RATE_224(224),
    RATE_256(256),
    RATE_320(320),
    RATE_384(384),
    RATE_448(448),
    RATE_512(512),
    RATE_640(640),
    RATE_768(768),
    RATE_896(896),
    RATE_1024(1024),
    RATE_1280(1280),
    RATE_1536(1536),
    RATE_1792(1792),
    RATE_2048(2048),
    RATE_3072(3072),
    RATE_4096(4096),
    RATE_6144(6144),
    RATE_8192(8192),
    RATE_10240(10240),
    RATE_12288(12288),
    RATE_14336(14336),
    RATE_16384(16384),
    RATE_18422(18432),
    RATE_20480(20480),
    RATE_22528(22528);

    private final int rate;

    STREAM_RATE_TYPE(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public static STREAM_RATE_TYPE getEnum(int value) {
        for (STREAM_RATE_TYPE e : STREAM_RATE_TYPE.values()) {
            if (e.getRate() == value)
                return e;
        }
        return STREAM_RATE_TYPE.RATE_512;
    }
}

