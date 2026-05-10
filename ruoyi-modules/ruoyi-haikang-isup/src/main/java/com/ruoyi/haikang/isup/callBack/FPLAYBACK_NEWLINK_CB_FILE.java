package com.ruoyi.haikang.isup.callBack;

import com.ruoyi.haikang.isup.handler.PlaybackStreamHandler;
import com.ruoyi.haikang.isup.manager.StreamManager;
import com.ruoyi.haikang.isup.service.haikang.stream.HCISUPStream;
import com.ruoyi.haikang.isup.service.haikang.stream.StreamService;
import com.sun.jna.Pointer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 回放数据回调（回放数据存储到文件）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FPLAYBACK_NEWLINK_CB_FILE implements HCISUPStream.PLAYBACK_NEWLINK_CB {

    private final Map<Integer, PlaybackStreamHandler> handlerMap = new ConcurrentHashMap<>();

    @Override
    public boolean invoke(int lPlayBackLinkHandle, HCISUPStream.NET_EHOME_PLAYBACK_NEWLINK_CB_INFO pNewLinkCBMsg, Pointer pUserData) {
        // 回放数据回调参数
        log.info("回放数据回调参数 lPlayBackLinkHandle: {}", lPlayBackLinkHandle);

        int iSessionID = pNewLinkCBMsg.lSessionID;
        log.info("pNewLinkCBMsg.lSessionID是和回放的时候的sessionID一致的 这个应该是全局唯一 通过sessionID可以确定是哪个摄像头 iSessionID: {}", iSessionID);

        // 设置双向映射
        StreamManager.playbackHandSAndSessionIDandMap.put(lPlayBackLinkHandle, iSessionID);
        StreamManager.sessionIDAndPlaybackHandleMap.put(iSessionID, lPlayBackLinkHandle);

        // 为每个回放会话创建独立的回调处理器实例
        PlaybackStreamHandler playbackStreamHandler = handlerMap.computeIfAbsent(lPlayBackLinkHandle, handle -> {
            log.info("创建新的PlaybackStreamHandler实例，句柄: {}", handle);
            PlaybackStreamHandler playback = new PlaybackStreamHandler();
            StreamManager.sessionIDAndPlaybackStreamHandlerMap.put(iSessionID, playback);
            return playback;
        });

        HCISUPStream.NET_EHOME_PLAYBACK_DATA_CB_PARAM struDataCB = new HCISUPStream.NET_EHOME_PLAYBACK_DATA_CB_PARAM();
        struDataCB.fnPlayBackDataCB = playbackStreamHandler;
        struDataCB.byStreamFormat = 1; // 1-RTP格式

        if (!StreamService.hCEhomeStream.NET_ESTREAM_SetPlayBackDataCB(lPlayBackLinkHandle, struDataCB)) {
            log.info("NET_ESTREAM_SetPlayBackDataCB 失败 err: {}", StreamService.hCEhomeStream.NET_ESTREAM_GetLastError());
            return false;
        }
        return true;
    }
}
