package com.ruoyi.dahua.manager;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.dahua.callback.FPlayBackDataCallBack;
import com.ruoyi.dahua.callback.FRealDatarTPCallback;
import com.ruoyi.dahua.lib.NetSDKLib;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {
    public static Map<String, NetSDKLib.LLong> streamKeyAndRealHandleMap = new ConcurrentHashMap<>();
    public static Map<String, FRealDatarTPCallback> streamKeyAndFRealDatarTPCallbackMap = new ConcurrentHashMap<>();
    public static Map<String, RtpServerParam> streamKeyAndRtpServerParamMap = new ConcurrentHashMap<>();
    
    // 回放相关
    public static Map<String, NetSDKLib.LLong> playbackKeyAndPlaybackHandleMap = new ConcurrentHashMap<>();
    public static Map<String, FPlayBackDataCallBack> playbackKeyAndFPlayBackDataCallBackMap = new ConcurrentHashMap<>();
    public static Map<String, RtpServerParam> playbackKeyAndRtpServerParamMap = new ConcurrentHashMap<>();
    public static Map<String, java.util.concurrent.CountDownLatch> playbackKeyAndLatchMap = new ConcurrentHashMap<>();
}
