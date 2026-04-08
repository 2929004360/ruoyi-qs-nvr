package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.haikang.isup.handler.PreviewStreamHandler;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.stream.HCISUPStream;
import com.ruoyi.haikang.isup.service.haikang.stream.StreamService;
import com.sun.jna.Pointer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实时预览数据回调（预览数据存储到文件）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FPREVIEW_NEWLINK_CB_FILE implements HCISUPStream.PREVIEW_NEWLINK_CB {

    private final Map<Integer, PreviewStreamHandler> handlerMap = new ConcurrentHashMap<>();

    @Override
    public boolean invoke(int lPreviewHandle, HCISUPStream.NET_EHOME_NEWLINK_CB_MSG pNewLinkCBMsg, Pointer pUserData) throws IOException {
        //预览数据回调参数
        log.info("预览数据回调参数 lPreviewHandle: {}", lPreviewHandle);

        int iSessionID = pNewLinkCBMsg.iSessionID;
        StreamManager.previewHandSAndSessionIDandMap.put(lPreviewHandle, iSessionID);
        StreamManager.sessionIDAndPreviewHandleMap.put(iSessionID, lPreviewHandle);
        log.info("pNewLinkCBMsg.iSessionID是和预览的时候的sessionID一致的 这个应该是全局唯一 通过sessionID可以确定是哪个摄像头 iSessionID: {}", iSessionID);

        // 为每个预览会话创建独立的回调处理器实例
        PreviewStreamHandler previewStreamHandler = handlerMap.computeIfAbsent(lPreviewHandle, handle -> {
            log.info("创建新的PreviewStreamHandler实例，句柄: {}", handle);
            PreviewStreamHandler preview = new PreviewStreamHandler();
            StreamManager.sessionIDAndPreviewStreamHandlerMap.put(iSessionID, preview);
            return preview;
        });

        HCISUPStream.NET_EHOME_PREVIEW_DATA_CB_PARAM struDataCB = new HCISUPStream.NET_EHOME_PREVIEW_DATA_CB_PARAM();
        struDataCB.fnPreviewDataCB = previewStreamHandler;

        if (!StreamService.hCEhomeStream.NET_ESTREAM_SetPreviewDataCB(lPreviewHandle, struDataCB)) {
            log.info("NET_ESTREAM_SetPreviewDataCB 失败 err: {}", StreamService.hCEhomeStream.NET_ESTREAM_GetLastError());
            return false;
        }
        return true;
    }
}
