package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 匹配信息扩展字段
 * @date 2022/11/15 20:11:54
 */
public class NET_OPENDOOR_MATCHINFO_EX extends NetSDKLib.SdkStructure {
    /**
     * 扩展用户名
     */
    public byte[]           szUserNameEx = new byte[128];
    /**
     * 语音类型 (医疗使用) {@link com.ruoyi.dahua.lib.enumeration.EM_CUSTOM_MEDICAL_VOICE_TYPE}
     */
    public int              emVoiceType;
    /**
     * 抗原检测信息
     */
    public NET_ANTIGEN_INFO stuAntigenInfo = new NET_ANTIGEN_INFO();
    /**
     * tts语音输出
     */
    public byte[]           szVoiceTTSMessage = new byte[128];
    /**
     * 鉴权方式，1:设备端鉴权, 在emCompareResult为0后端鉴权成功的情况下,设备还需要二次验证, 2:后端鉴权,根据emCompareResult进行开门与否的判定条件, 255:默认鉴权,兼容历史设备
     */
    public int              nCheckLocal;
    /**
     * 设备开门信息透传唯一标识，后端比对完成后必须携带
    */
    public byte[]           szTransmissionUuid = new byte[64];
    /**
     * 预留字节
     */
    public byte[]           bReserved = new byte[1552];

    public NET_OPENDOOR_MATCHINFO_EX() {
    }
}

