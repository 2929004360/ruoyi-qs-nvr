package com.ruoyi.qs.mapper;

import com.ruoyi.qs.api.domain.QsGb28181Platform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 国标GB28181平台配置Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface QsGb28181PlatformMapper {

    /**
     * 查询国标GB28181平台配置列表
     *
     * @param qsGb28181Platform 国标GB28181平台配置
     * @return 国标GB28181平台配置集合
     */
    List<QsGb28181Platform> selectQsGb28181PlatformList(QsGb28181Platform qsGb28181Platform);

    /**
     * 查询国标GB28181平台配置
     *
     * @param id 国标GB28181平台配置主键
     * @return 国标GB28181平台配置
     */
    QsGb28181Platform selectQsGb28181PlatformById(Long id);

    /**
     * 根据平台国标编码查询
     *
     * @param serverGbId 平台国标编码
     * @return 国标GB28181平台配置
     */
    QsGb28181Platform selectQsGb28181PlatformByServerGbId(String serverGbId);

    /**
     * 新增国标GB28181平台配置
     *
     * @param qsGb28181Platform 国标GB28181平台配置
     * @return 结果
     */
    int insertQsGb28181Platform(QsGb28181Platform qsGb28181Platform);

    /**
     * 修改国标GB28181平台配置
     *
     * @param qsGb28181Platform 国标GB28181平台配置
     * @return 结果
     */
    int updateQsGb28181Platform(QsGb28181Platform qsGb28181Platform);

    /**
     * 删除国标GB28181平台配置
     *
     * @param id 国标GB28181平台配置主键
     * @return 结果
     */
    int deleteQsGb28181PlatformById(Long id);

    /**
     * 批量删除国标GB28181平台配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteQsGb28181PlatformByIds(Long[] ids);
}
