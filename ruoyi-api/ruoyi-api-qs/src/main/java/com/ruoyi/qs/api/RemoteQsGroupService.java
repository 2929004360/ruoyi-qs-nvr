package com.ruoyi.qs.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.api.domain.QsGroupTree;
import com.ruoyi.qs.api.factory.RemoteQsGroupFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分组管理 服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteQsGroupService", value = ServiceNameConstants.QS_SERVICE, fallbackFactory = RemoteQsGroupFallbackFactory.class)
public interface RemoteQsGroupService {

    /**
     * 查询分组树
     *
     * @param query     查询条件
     * @param parentId  父节点
     * @param hasDevice 是否包含设备
     * @param source    请求来源
     * @return
     */
    @GetMapping("/api/group/tree/list")
    R<List<QsGroupTree>> queryForTree(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer parentId,
            @RequestParam(required = false) Boolean hasDevice,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 查询所有分组
     *
     * @param query  查询条件
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/group/all/list")
    R<List<QsGroupTree>> queryAllGroups(@RequestParam(required = false) String query, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 查询分组列表
     *
     * @param query  查询条件
     * @param source 请求来源
     * @return
     */
    @GetMapping("/api/group/tree/query")
    R<List<QsGroup>> queryList(@RequestParam(required = false) String query, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
