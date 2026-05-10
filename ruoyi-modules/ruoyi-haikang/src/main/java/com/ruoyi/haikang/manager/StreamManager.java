package com.ruoyi.haikang.manager;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.callback.FPlayBackDataCallBack;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {
    public static Map<String, Long> streamKeyAndRealHandleMap = new ConcurrentHashMap<>();
    public static Map<String, FRealDataForRtpOverTcpCallback> streamKeyAndFRealDataForRtpOverTcpCallbackMap = new ConcurrentHashMap<>();
    public static Map<String, RtpServerParam> streamKeyAndRtpServerParamMap = new ConcurrentHashMap<>();
    
    // 回放相关
    public static Map<String, java.util.concurrent.CountDownLatch> playbackKeyAndLatchMap = new ConcurrentHashMap<>();
    public static Map<String, Long> playbackKeyAndPlaybackHandleMap = new ConcurrentHashMap<>();
    public static Map<String, FPlayBackDataCallBack> playbackKeyAndFPlayBackDataCallBackMap = new ConcurrentHashMap<>();
    public static Map<String, RtpServerParam> playbackKeyAndRtpServerParamMap = new ConcurrentHashMap<>();
}
