package com.ruoyi.zlm.mapper;

import com.ruoyi.zlm.api.domain.ZlmMediaServer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 媒体服务器Mapper接口
 *
 * @FileName MediaServerMapper
 * @Description
 * @Author fengcheng
 * @date 2026-03-31
 **/
@Mapper
@Repository
public interface MediaServerMapper {

    /**
     * 获取默认的媒体服务器
     *
     * @return
     */
    ZlmMediaServer queryDefault(String serverId);

    /**
     * 根据id和serverId查询
     *
     * @param id
     * @param serverId
     * @return
     */
    ZlmMediaServer queryOne(@Param("id") String id,@Param("serverId") String serverId);

    /**
     * 添加媒体服务器
     *
     * @param zlmMediaServer
     * @return
     */
    int add(ZlmMediaServer zlmMediaServer);

    /**
     * 修改媒体服务器
     *
     * @param zlmMediaServer
     * @return
     */
    int update(ZlmMediaServer zlmMediaServer);

    /**
     * 删除媒体服务器
     *
     * @param id
     * @param serverId
     */
    void delOne(@Param("id") String id,@Param("serverId") String serverId);

    /**
     * 查询所有媒体服务器
     *
     * @param serverId
     * @return
     */
    List<ZlmMediaServer> queryAll(String serverId);

    /**
     * 从数据库中获取指定id的媒体服务器
     *
     * @param id
     * @return
     */
    ZlmMediaServer getOne(String id);

    /**
     * 获取所有在线的媒体服务器
     *
     * @return
     */
    List<ZlmMediaServer> getAllOnlineMediaServe();
}
