package com.ruoyi.dahua.manager;

import com.ruoyi.dahua.callback.FRealDatarTPCallback;
import com.ruoyi.dahua.lib.NetSDKLib;

import java.util.HashMap;
import java.util.Map;

public class StreamManager {
    public static Map<String, NetSDKLib.LLong> streamKeyAndRealHandleMap = new HashMap<>();
    public static Map<String, FRealDatarTPCallback> streamKeyAndFRealDatarTPCallbackMap = new HashMap<>();
}
