package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2021/8/4 9:46
 */
public class NET_ENCODE_AUDIO_INFO extends NetSDKLib.SdkStructure {
    public   int            dwSize;
    /**  参考{@link com.ruoyi.dahua.lib.enumeration.NET_EM_FORMAT_TYPE}*/
    public   int            emFormatType;                         // 码流类型,设置和获取时都需要设置值
    public   int            nDepth;                               // 音频采样深度
    public   int            nFrequency;                           // 音频采样频率
    public   int            nMode;                                // 编码码率,不同编码类型定义不同：
                                                       /* "AMR"编码码率
                                                            0: 4.75kbps,
                                                            1: 5.15 kbps,
                                                            2: 5.9 kbps,
                                                            3: 6.7 kbps,
                                                            4: 7.4 kbps,
                                                            5: 7.95 kbps,
                                                            6: 10.2 kbps,
                                                            7: 12.2 kbps,
                                                        G726编码码率
                                                            0: 40kbps(默认)
                                                            1: 16kbps
                                                            2: 24kbps
                                                            3: 32kbps
                                                        其他: 暂无定义*/
    public   int            nFrameType;                           // 音频打包模式, 0-DHAV, 1-PS
    public int              nPacketPeriod;                        // 音频打包周期, ms

    public NET_ENCODE_AUDIO_INFO() {
        this.dwSize = this.size();
    }
}

