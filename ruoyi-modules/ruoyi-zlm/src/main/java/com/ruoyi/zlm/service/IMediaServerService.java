package com.ruoyi.zlm.service;

import com.ruoyi.zlm.domain.ZlmMediaServer;

import java.util.List;

/**
 * 媒体服务器Service接口
 *
 * @FileName IMediaServerService
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
public interface IMediaServerService {

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    ZlmMediaServer getDefaultMediaServer();

    /**
     * 添加媒体服务器
     *
     * @param zlmMediaServer
     */
    int add(ZlmMediaServer zlmMediaServer);

    /**
     * 修改媒体服务器
     *
     * @param zlmMediaServer
     */
    int update(ZlmMediaServer zlmMediaServer);

    /**
     * 删除媒体服务器
     *
     * @param zlmMediaServer
     */
    void delete(ZlmMediaServer zlmMediaServer);

    /**
     * 从数据库中获取所有媒体服务器
     *
     * @return
     */
    List<ZlmMediaServer> getAllFromDatabase();

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOneFromDatabase(String id);

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOne(String id);
}
