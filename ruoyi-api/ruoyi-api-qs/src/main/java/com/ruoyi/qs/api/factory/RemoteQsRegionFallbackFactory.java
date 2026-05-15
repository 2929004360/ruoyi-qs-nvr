package com.ruoyi.qs.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsRegionService;
import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.api.domain.QsRegionTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 区域管理服务降级处理
 *
 * @FileName RemoteQsRegionFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-05-15
 **/
@Component
public class RemoteQsRegionFallbackFactory implements FallbackFactory<RemoteQsRegionService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteQsRegionFallbackFactory.class);

    @Override
    public RemoteQsRegionService create(Throwable throwable) {
        log.error("区域管理服务调用失败:{}", throwable.getMessage());

        return new RemoteQsRegionService() {
            @Override
            public R<List<QsRegionTree>> queryForTree(Integer parent, Boolean hasDevice, String source) {
                return R.fail("查询区域树失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsRegionTree>> queryAllRegions(String query, String source) {
                return R.fail("查询所有区域失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsRegion>> queryList(String query, String source) {
                return R.fail("查询区域列表失败:" + throwable.getMessage());
            }
        };
    }
}
