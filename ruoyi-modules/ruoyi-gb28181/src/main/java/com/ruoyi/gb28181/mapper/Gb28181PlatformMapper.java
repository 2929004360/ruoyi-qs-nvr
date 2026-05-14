package com.ruoyi.gb28181.mapper;

import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 国标GB28181平台配置Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface Gb28181PlatformMapper {

    /**
     * 查询国标GB28181平台配置列表
     *
     * @param gb28181Platform 国标GB28181平台配置
     * @return 国标GB28181平台配置集合
     */
    List<Gb28181Platform> selectGb28181PlatformList(Gb28181Platform gb28181Platform);

    /**
     * 查询国标GB28181平台配置
     *
     * @param id 国标GB28181平台配置主键
     * @return 国标GB28181平台配置
     */
    Gb28181Platform selectGb28181PlatformById(Long id);

    /**
     * 根据平台国标编码查询
     *
     * @param serverGbId 平台国标编码
     * @return 国标GB28181平台配置
     */
    Gb28181Platform selectGb28181PlatformByServerGbId(String serverGbId);

    /**
     * 根据设备国标编码查询平台（用于处理上级平台Query）
     *
     * @param deviceGbId 设备国标编码（即下级平台的国标编码）
     * @return 国标GB28181平台配置
     */
    Gb28181Platform selectGb28181PlatformByDeviceGbId(String deviceGbId);

    /**
     * 新增国标GB28181平台配置
     *
     * @param gb28181Platform 国标GB28181平台配置
     * @return 结果
     */
    int insertGb28181Platform(Gb28181Platform gb28181Platform);

    /**
     * 修改国标GB28181平台配置
     *
     * @param gb28181Platform 国标GB28181平台配置
     * @return 结果
     */
    int updateGb28181Platform(Gb28181Platform gb28181Platform);

    /**
     * 删除国标GB28181平台配置
     *
     * @param id 国标GB28181平台配置主键
     * @return 结果
     */
    int deleteGb28181PlatformById(Long id);

    /**
     * 批量删除国标GB28181平台配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteGb28181PlatformByIds(Long[] ids);
}
