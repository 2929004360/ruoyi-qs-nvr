package com.ruoyi.haikang.isup.manager;

import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;

import java.util.HashMap;
import java.util.Map;

public class StreamManager {
    public static Map<Integer, RtpServerParam> luserIdAndRtpServerParamMap = new HashMap<>();
    public static Map<Integer, Integer> userIDandSessionMap = new HashMap<>();
    public static Map<Integer, Integer> previewHandSAndSessionIDandMap = new HashMap<>();
    public static Map<Integer, Integer> sessionIDAndPreviewHandleMap = new HashMap<>();
    public static Map<Integer, PreviewStreamHandler> sessionIDAndPreviewStreamHandlerMap = new HashMap<>();
}
