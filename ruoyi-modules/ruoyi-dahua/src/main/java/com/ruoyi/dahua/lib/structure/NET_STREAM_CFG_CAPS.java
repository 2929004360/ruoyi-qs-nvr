package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_H264_PROFILE_RANK;
import com.ruoyi.dahua.lib.enumeration.STREAM_RATE_TYPE;
import com.ruoyi.dahua.lib.enumeration.TALK_CODING_TYPE;

import static com.ruoyi.dahua.lib.constant.SDKStructureFieldLenth.*;

/**
 * 码流配置对应能力
 *
 * @author 47040
 * @since Created at 2021/5/25 21:10
 */
public class NET_STREAM_CFG_CAPS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 支持的音频编码类型 枚举值数组 {@link TALK_CODING_TYPE}
     * 有效长度由 {@link #nAudioCompressionTypeNum} 决定
     */
    public int[]            nAudioCompressionTypes = new int[CFG_MAX_AUDIO_ENCODE_TYPE];
    /**
     * 音频压缩格式个数
     * 代表 {@link #nAudioCompressionTypes} 的有效长度
     */
    public int              nAudioCompressionTypeNum;
    /**
     * 视频编码模式掩码,详见 "编码模式"
     */
    public int              dwEncodeModeMask;
    /**
     * 支持的视频分辨率
     * {@link #abIndivResolution} 为 0 时生效
     * 有效长度由 {@link #nResolutionTypeNum} 决定
     */
    public NetSDKLib.NET_RESOLUTION_INFO[] stuResolutionTypes = new NetSDKLib.NET_RESOLUTION_INFO[CFG_MAX_CAPTURE_SIZE_NUM];
    /**
     * 不同分辨率下帧率最大值
     * {@link #nFPSMax} 不为 0 时使用
     * 数组下标与 {@link #stuResolutionTypes} 匹配
     * 有效长度由 {@link #nResolutionTypeNum} 决定
     */
    public int[]            nResolutionFPSMax = new int[CFG_MAX_CAPTURE_SIZE_NUM];
    /**
     * 视频分辨率个数 决定了以下数组的有效长度
     * {@link #stuResolutionTypes}
     * {@link #nResolutionFPSMax}
     */
    public int              nResolutionTypeNum;
    /**
     * 最大视频码流(kbps)
     * 这里虽然是只限定了码流的上下界 但并不意味着配置的时候可以在界内随便填
     * 必须从 {@link STREAM_RATE_TYPE} 选择区间内的码流值
     */
    public int              nMaxBitRateOptions;
    /**
     * 最小视频码流(kbps)
     * 这里虽然是只限定了码流的上下界 但并不意味着配置的时候可以在界内随便填
     * 必须从 {@link STREAM_RATE_TYPE} 选择区间内的码流值
     */
    public int              nMinBitRateOptions;
    /**
     * 支持的 H.264 Profile等级 枚举值数组 {@link EM_H264_PROFILE_RANK}
     * 有效长度由 {@link #nH264ProfileRankNum} 决定
     */
    public byte[]           bH264ProfileRank = new byte[EM_H264_PROFILE_RANK.NET_PROFILE_HIGH.getValue()];
    /**
     * 支持的 H.264 Profile 等级个数
     * 决定了 {@link #bH264ProfileRank} 的有效长度
     */
    public int              nH264ProfileRankNum;
    /**
     * 当分辨率为 cif 时最大 p 帧(Kbps)
     */
    public int              nCifPFrameMaxSize;
    /**
     * 当分辨率为 cif 时最小 p 帧(Kbps)
     */
    public int              nCifPFrameMinSize;
    /**
     * 视频帧率最大值
     * 如果为 0 则以 {@link #nResolutionFPSMax} 为准
     */
    public int              nFPSMax;
    /**
     * 支持的视频分辨率
     * {@link #abIndivResolution} 为 1 时生效
     * 本身是一个二维数组 出于 JNA 封装需要第二维单独封装成新结构体 {@link NET_RESOLUTION_INFO_ARRAY}
     * 每个第二维数组的有效长度由 {@link #nIndivResolutionNums} 决定 其数组下标和本字段的下标精确匹配
     */
    public NET_RESOLUTION_INFO_ARRAY[] stuIndivResolutionTypes = new NET_RESOLUTION_INFO_ARRAY[CFG_MAX_COMPRESSION_TYPES_NUM];
    /**
     * 标志位 用以区别能力集是否包含所有编码格式
     * 0 表示 {@link #stuResolutionTypes} {@link #nResolutionTypeNum} 有效
     * 1 表示 {@link #stuIndivResolutionTypes} {@link #nIndivResolutionNums} 有效
     */
    public int              abIndivResolution;
    /**
     * 不同编码模式分别支持的视频分辨率个数
     * 代表 {@link #stuIndivResolutionTypes} 各个第二维数组的有效长度 其数组下标和本字段的下标精确匹配
     */
    public int[]            nIndivResolutionNums = new int[CFG_MAX_COMPRESSION_TYPES_NUM];
    /**
     * 码流分辨率限制个数
    */
    public int              nEncodingResolutionLimitNum;
    /**
     * 主辅码流分辨率组合限制个数
    */
    public int              nEncodingResolutionLimitExNum;
    /**
     * 码流分辨率限制，限制界面和功能模块只能使用指定范围之内的分辨率，前提是设备支持该分辨率。是一个二维数组,第一级对应不同的码流,第二级对应支持的分辨率集合。二级数组为空表示不做限制，使用所有设备能提供的分辨率。,参见结构体定义 {@link NET_ENCODING_RESOLUTION_LIMIT}
    */
    public NET_ENCODING_RESOLUTION_LIMIT[] stuEncodingResolutionLimit = new NET_ENCODING_RESOLUTION_LIMIT[10];
    /**
     * 主辅码流分辨率组合限制，限制界面和功能模块只能使用指定范围之内的分辨率，前提是设备支持该分辨率。是一个二维数组,第一级是主辅码流分辨率组合的个数,第二级是主辅码流分辨率组合。二级数组为空表示没有限制，使用所有设备能提供的分辨率。,参见结构体定义 {@link NET_ENCODING_RESOLUTION_LIMITEX}
    */
    public NET_ENCODING_RESOLUTION_LIMITEX[] stuEncodingResolutionLimitEx = new NET_ENCODING_RESOLUTION_LIMITEX[10];

    public NET_STREAM_CFG_CAPS() {
        dwSize = this.size();
        for (int i = 0; i < stuResolutionTypes.length; i++) {
            stuResolutionTypes[i] = new NetSDKLib.NET_RESOLUTION_INFO();
        }
        for (int i = 0; i < stuIndivResolutionTypes.length; i++) {
            stuIndivResolutionTypes[i] = new NET_RESOLUTION_INFO_ARRAY();
        }
    }
}

