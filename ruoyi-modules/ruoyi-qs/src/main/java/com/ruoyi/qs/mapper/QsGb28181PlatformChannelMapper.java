package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 国标GB28181平台通道关联Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface QsGb28181PlatformChannelMapper {

    /**
     * 查询平台通道关联列表
     *
     * @param qsGb28181PlatformChannel 平台通道关联
     * @return 平台通道关联集合
     */
    List<QsGb28181PlatformChannel> selectQsGb28181PlatformChannelList(QsGb28181PlatformChannel qsGb28181PlatformChannel);

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
     * @param qsGb28181PlatformChannel 平台通道关联
     * @return 结果
     */
    int insertQsGb28181PlatformChannel(QsGb28181PlatformChannel qsGb28181PlatformChannel);

    /**
     * 批量新增平台通道关联
     *
     * @param list 平台通道关联列表
     * @return 结果
     */
    int batchInsertQsGb28181PlatformChannel(@Param("list") List<QsGb28181PlatformChannel> list);

    /**
     * 根据ID查询平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 平台通道关联
     */
    QsGb28181PlatformChannel selectQsGb28181PlatformChannelById(Long id);

    /**
     * 删除平台通道关联
     *
     * @param id 平台通道关联主键
     * @return 结果
     */
    int deleteQsGb28181PlatformChannelById(Long id);

    /**
     * 根据平台ID删除关联
     *
     * @param platformId 平台ID
     * @return 结果
     */
    int deleteQsGb28181PlatformChannelByPlatformId(Long platformId);

    /**
     * 批量删除平台通道关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteQsGb28181PlatformChannelByIds(Long[] ids);

    /**
     * 根据平台ID和设备ID列表删除关联
     *
     * @param platformId 平台ID
     * @param deviceIds 设备ID列表
     * @return 结果
     */
    int deleteQsGb28181PlatformChannelByDeviceIds(@Param("platformId") Long platformId, @Param("deviceIds") List<Long> deviceIds);

    /**
     * 根据平台ID统计关联设备数量
     *
     * @param platformId 平台ID
     * @return 设备数量
     */
    int countDeviceByPlatformId(Long platformId);
}
