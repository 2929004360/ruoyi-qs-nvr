package com.ruoyi.gb28181.mapper;

import com.ruoyi.gb28181.api.domain.Gb28181PlatformChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 国标GB28181平台通道关联Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface Gb28181PlatformChannelMapper {

    /**
     * 查询平台通道关联列表
     *
     * @param gb28181PlatformChannel 平台通道关联
     * @return 平台通道关联集合
     */
    List<Gb28181PlatformChannel> selectGb28181PlatformChannelList(Gb28181PlatformChannel gb28181PlatformChannel);

    /**
     * 根据平台ID查询关联的设备ID列表
     *
     * @param platformId 平台ID
     * @return 设备ID列表
     */
    List<Long> selectDeviceIdsByPlatformId(Long platformId);

    /**
     * 新增平台通道关联
     *
     * @param gb28181PlatformChannel 平台通道关联
     * @return 结果
     */
    int insertGb28181PlatformChannel(Gb28181PlatformChannel gb28181PlatformChannel);

    /**
     * 批量新增平台通道关联
     *
     * @param list 平台通道关联列表
     * @return 结果
     */
    int batchInsertGb28181PlatformChannel(@Param("list") List<Gb28181PlatformChannel> list);

    /**
     * 根据ID查询平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 平台通道关联
     */
    Gb28181PlatformChannel selectGb28181PlatformChannelById(Long id);

    /**
     * 删除平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 结果
     */
    int deleteGb28181PlatformChannelById(Long id);

    /**
     * 根据平台ID删除关联
     *
     * @param platformId 平台ID
     * @return 结果
     */
    int deleteGb28181PlatformChannelByPlatformId(Long platformId);

    /**
     * 批量删除平台通道关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteGb28181PlatformChannelByIds(Long[] ids);

    /**
     * 根据平台ID和设备ID列表删除关联
     *
     * @param platformId 平台ID
     * @param deviceIds  设备ID列表
     * @return 结果
     */
    int deleteGb28181PlatformChannelByDeviceIds(@Param("platformId") Long platformId, @Param("deviceIds") List<Long> deviceIds);
}
