package com.ruoyi.haikang.manager;

import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;

import java.util.HashMap;
import java.util.Map;

public class StreamManager {
    public static Map<String, Long> streamKeyAndRealHandleMap = new HashMap<>();
    public static Map<String, FRealDataForRtpOverTcpCallback> streamKeyAndFRealDataForRtpOverTcpCallbackMap = new HashMap<>();
}
