package com.ruoyi.qs.service;

import com.ruoyi.qs.api.domain.QsGb28181Platform;

import java.util.List;

/**
 * 国标GB28181平台配置Service接口
 *
 * @author ruoyi
 */
public interface IQsGb28181PlatformService {

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
     * 批量删除国标GB28181平台配置
     *
     * @param ids 需要删除的国标GB28181平台配置主键集合
     * @return 结果
     */
    int deleteQsGb28181PlatformByIds(Long[] ids);

    /**
     * 删除国标GB28181平台配置信息
     *
     * @param id 国标GB28181平台配置主键
     * @return 结果
     */
    int deleteQsGb28181PlatformById(Long id);
}
