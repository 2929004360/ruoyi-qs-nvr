package com.ruoyi.qs.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.qs.api.domain.QsGroup;
import com.ruoyi.qs.api.domain.QsGroupTree;
import com.ruoyi.qs.service.IQsGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分组管理API Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/group")
public class QsGroupApiController {

    @Autowired
    private IQsGroupService qsGroupService;

    /**
     * 查询分组树（内部接口）
     */
    @InnerAuth
    @GetMapping("/tree/list")
    public R<List<QsGroupTree>> queryForTree(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer parentId,
            @RequestParam(required = false) Boolean hasDevice) {
        if (ObjectUtils.isEmpty(query)) {
            query = null;
        }
        return R.ok(qsGroupService.queryForTree(query, parentId, hasDevice));
    }

    /**
     * 查询所有分组（内部接口）
     */
    @InnerAuth
    @GetMapping("/all/list")
    public R<List<QsGroupTree>> queryAllGroups(@RequestParam(required = false) String query) {
        if (ObjectUtils.isEmpty(query)) {
            query = null;
        }
        return R.ok(qsGroupService.queryAllGroups(query));
    }

    /**
     * 查询分组列表（内部接口）
     */
    @InnerAuth
    @GetMapping("/tree/query")
    public R<List<QsGroup>> queryList(@RequestParam(required = false) String query) {
        return R.ok(qsGroupService.queryList(query));
    }
}
