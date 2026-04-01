package com.ruoyi.zlm.service.impl;

import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.ZlmMediaServer;
import com.ruoyi.zlm.mapper.MediaServerMapper;
import com.ruoyi.zlm.mediaServer.MediaServerDeleteEvent;
import com.ruoyi.zlm.service.IMediaNodeServerService;
import com.ruoyi.zlm.service.IMediaServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 媒体服务器Service接口实现类
 *
 * @FileName MediaServerServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
@Slf4j
@Service
public class MediaServerServiceImpl implements IMediaServerService {

    @Autowired
    private MediaServerMapper mediaServerMapper;

    @Autowired
    private UserSetting userSetting;

    @Autowired
    private Map<String, IMediaNodeServerService> nodeServerServiceMap;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    @Override
    public ZlmMediaServer getDefaultMediaServer() {
        return mediaServerMapper.queryDefault(userSetting.getServerId());
    }

    /**
     * 添加媒体服务器
     *
     * @param zlmMediaServer
     */
    @Override
    public int add(ZlmMediaServer zlmMediaServer) {
        zlmMediaServer.setCreateTime(DateUtils.getNowDate());
        zlmMediaServer.setUpdateTime(DateUtils.getNowDate());
        if (zlmMediaServer.getHookAliveInterval() == null || zlmMediaServer.getHookAliveInterval() == 0F) {
            zlmMediaServer.setHookAliveInterval(10F);
        }
        if (zlmMediaServer.getType() == null) {
            log.info("[添加媒体节点] 失败, mediaServer的类型：为空");
            throw new SecurityException("[添加媒体节点] 失败, mediaServer的类型：为空");
        }
        if (mediaServerMapper.queryOne(zlmMediaServer.getId(), userSetting.getServerId()) != null) {
            log.info("[添加媒体节点] 失败, 媒体服务ID已存在，请修改媒体服务器配置, {}", zlmMediaServer.getId());
            throw new SecurityException("保存失败，媒体服务ID [ " + zlmMediaServer.getId() + " ] 已存在，请修改媒体服务器配置");
        }
        IMediaNodeServerService mediaNodeServerService = nodeServerServiceMap.get(zlmMediaServer.getType());
        if (mediaNodeServerService == null) {
            log.info("[添加媒体节点] 失败, mediaServer的类型： {}，未找到对应的实现类", zlmMediaServer.getType());
            throw new SecurityException("[添加媒体节点] 失败, mediaServer的类型： " + zlmMediaServer.getType() + "，未找到对应的实现类");
        }

        return mediaServerMapper.add(zlmMediaServer);
    }

    /**
     * 修改媒体服务器
     *
     * @param zlmMediaServer
     */
    @Override
    public int update(ZlmMediaServer zlmMediaServer) {
        return mediaServerMapper.update(zlmMediaServer);
    }

    /**
     * 删除媒体服务器
     *
     * @param zlmMediaServer
     */
    @Override
    public void delete(ZlmMediaServer zlmMediaServer) {
        mediaServerMapper.delOne(zlmMediaServer.getId(), userSetting.getServerId());

        // 发送节点移除通知
        MediaServerDeleteEvent event = new MediaServerDeleteEvent(this);
        event.setMediaServer(zlmMediaServer);
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * 从数据库中获取所有媒体服务器
     *
     * @return
     */
    @Override
    public List<ZlmMediaServer> getAllFromDatabase() {
        return mediaServerMapper.queryAll(userSetting.getServerId());
    }

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    @Override
    public ZlmMediaServer getOneFromDatabase(String id) {
        return mediaServerMapper.queryOne(id, userSetting.getServerId());
    }

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    @Override
    public ZlmMediaServer getOne(String id) {
        return mediaServerMapper.getOne(id);
    }
}
