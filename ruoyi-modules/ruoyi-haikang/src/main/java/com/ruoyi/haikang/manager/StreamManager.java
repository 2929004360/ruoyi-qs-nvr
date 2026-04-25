package com.ruoyi.haikang.manager;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {
    public static Map<String, Long> streamKeyAndRealHandleMap = new ConcurrentHashMap<>();
    public static Map<String, FRealDataForRtpOverTcpCallback> streamKeyAndFRealDataForRtpOverTcpCallbackMap = new ConcurrentHashMap<>();
    public static Map<String, RtpServerParam> streamKeyAndRtpServerParamMap = new ConcurrentHashMap<>();
}
