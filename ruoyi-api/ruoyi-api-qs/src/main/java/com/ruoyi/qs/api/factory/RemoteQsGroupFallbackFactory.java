package com.ruoyi.qs.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.qs.api.RemoteQsGroupService;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.api.domain.QsGroupTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分组管理服务降级处理
 *
 * @FileName RemoteQsGroupFallbackFactory
 * @Description
 * @Author fengcheng
 * @date 2026-05-15
 **/
@Component
public class RemoteQsGroupFallbackFactory implements FallbackFactory<RemoteQsGroupService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteQsGroupFallbackFactory.class);

    @Override
    public RemoteQsGroupService create(Throwable throwable) {
        log.error("分组管理服务调用失败:{}", throwable.getMessage());

        return new RemoteQsGroupService() {
            @Override
            public R<List<QsGroupTree>> queryForTree(String query, Integer parentId, Boolean hasDevice, String source) {
                return R.fail("查询分组树失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsGroupTree>> queryAllGroups(String query, String source) {
                return R.fail("查询所有分组失败:" + throwable.getMessage());
            }

            @Override
            public R<List<QsGroup>> queryList(String query, String source) {
                return R.fail("查询分组列表失败:" + throwable.getMessage());
            }
        };
    }
}
