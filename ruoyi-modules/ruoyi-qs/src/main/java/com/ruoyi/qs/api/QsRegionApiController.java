package com.ruoyi.qs.api;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.qs.api.domain.QsRegion;
import com.ruoyi.qs.api.domain.QsRegionTree;
import com.ruoyi.qs.service.IQsRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域管理API Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/region")
public class QsRegionApiController {

    @Autowired
    private IQsRegionService qsRegionService;

    /**
     * 查询区域树（内部接口）
     */
    @InnerAuth
    @GetMapping("/tree/list")
    public R<List<QsRegionTree>> queryForTree(
            @RequestParam(required = false) Integer parent,
            @RequestParam(required = false) Boolean hasDevice) {
        return R.ok(qsRegionService.queryForTree(parent, hasDevice));
    }

    /**
     * 查询所有区域（内部接口）
     */
    @InnerAuth
    @GetMapping("/all/list")
    public R<List<QsRegionTree>> queryAllRegions(@RequestParam(required = false) String query) {
        if (ObjectUtils.isEmpty(query)) {
            query = null;
        }
        return R.ok(qsRegionService.queryAllRegions(query));
    }

    /**
     * 查询区域列表（内部接口）
     */
    @InnerAuth
    @GetMapping("/tree/query")
    public R<List<QsRegion>> queryList(@RequestParam(required = false) String query) {
        return R.ok(qsRegionService.queryList(null, null, query));
    }
}
