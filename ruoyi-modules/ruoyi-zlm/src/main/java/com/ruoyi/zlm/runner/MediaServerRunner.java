package com.ruoyi.zlm.runner;

import com.ruoyi.zlm.config.MediaConfig;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import com.ruoyi.zlm.mediaServer.MediaServerChangeEvent;
import com.ruoyi.zlm.service.IMediaServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 启动是从配置文件加载节点信息，以及发送个节点状态管理去控制节点状态
 *
 * @FileName MediaServerRunner
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
@Slf4j
@Component
@Order(value=12)
public class MediaServerRunner implements CommandLineRunner {

    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private MediaConfig mediaConfig;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(String... args) throws Exception {
        ZlmMediaServer defaultMediaServer = mediaServerService.getDefaultMediaServer();
        ZlmMediaServer mediaSerItemInConfig = mediaConfig.getMediaSerItem();
        mediaSerItemInConfig.setServerId(userSetting.getServerId());

        if (defaultMediaServer != null && mediaSerItemInConfig.getId().equals(defaultMediaServer.getId())) {
            mediaServerService.update(mediaSerItemInConfig);
        }else {
            if (defaultMediaServer != null) {
                mediaServerService.delete(defaultMediaServer);
            }
            ZlmMediaServer mediaServerItem = mediaServerService.getOneFromDatabase(mediaSerItemInConfig.getId());
            if (mediaServerItem == null) {
                mediaServerService.add(mediaSerItemInConfig);
            }else {
                mediaServerService.update(mediaSerItemInConfig);
            }
        }

        // 获取所有的zlm， 并开启主动连接
        List<ZlmMediaServer> all = mediaServerService.getAllFromDatabase();
        log.info("[媒体节点] 加载节点列表， 共{}个节点", all.size());
        MediaServerChangeEvent event = new MediaServerChangeEvent(this);
        event.setMediaServerItemList(all);
        applicationEventPublisher.publishEvent(event);
    }
}
