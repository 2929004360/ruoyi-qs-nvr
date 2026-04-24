package com.ruoyi.haikang.isup.manager;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {
    // 全部使用 ConcurrentHashMap 保证线程安全
    public static Map<Integer, RtpServerParam> luserIdAndRtpServerParamMap = new ConcurrentHashMap<>();
    public static Map<Integer, Integer> userIDandSessionMap = new ConcurrentHashMap<>();
    public static Map<Integer, Integer> previewHandSAndSessionIDandMap = new ConcurrentHashMap<>();
    public static Map<Integer, Integer> sessionIDAndPreviewHandleMap = new ConcurrentHashMap<>();
    public static Map<Integer, PreviewStreamHandler> sessionIDAndPreviewStreamHandlerMap = new ConcurrentHashMap<>();
    public static Map<String, RtpServerParam> streamKeyAndRtpServerParamMap = new ConcurrentHashMap<>();
    public static Map<String, Integer> streamKeyAndSessionIDMap = new ConcurrentHashMap<>();
    public static Map<String, Integer> streamKeyAndLuserIdMap = new ConcurrentHashMap<>();
}
