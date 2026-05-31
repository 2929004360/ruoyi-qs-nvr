package com.ruoyi.qs.controller;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.ruoyi.qs.service.IQsDeviceSnapshotService;
import com.ruoyi.zlm.api.RemoteZlmService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 设备抓图Controller
 *
 * @author ruoyi
 * @date 2026-05-17
 */
@Slf4j
@RestController
@RequestMapping("/snapshot")
public class QsDeviceSnapshotController extends BaseController {
    @Autowired
    private IQsDeviceSnapshotService qsDeviceSnapshotService;

    @Autowired
    private RemoteZlmService remoteZlmService;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Value("${file.path}")
    private String filePath;

    /**
     * 查询设备抓图列表
     */
    @RequiresPermissions("qs:snapshot:list")
    @GetMapping("/list")
    public TableDataInfo list(QsDeviceSnapshot qsDeviceSnapshot) {
        startPage();
        List<QsDeviceSnapshot> list = qsDeviceSnapshotService.selectQsDeviceSnapshotList(qsDeviceSnapshot);
        return getDataTable(list);
    }

    /**
     * 导出设备抓图列表
     */
    @RequiresPermissions("qs:snapshot:export")
    @Log(title = "设备抓图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QsDeviceSnapshot qsDeviceSnapshot) {
        List<QsDeviceSnapshot> list = qsDeviceSnapshotService.selectQsDeviceSnapshotList(qsDeviceSnapshot);
        ExcelUtil<QsDeviceSnapshot> util = new ExcelUtil<QsDeviceSnapshot>(QsDeviceSnapshot.class);
        util.exportExcel(response, list, "设备抓图数据");
    }

    /**
     * 获取设备抓图详细信息
     */
    @RequiresPermissions("qs:snapshot:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qsDeviceSnapshotService.selectQsDeviceSnapshotById(id));
    }

    /**
     * 新增设备抓图
     */
    @RequiresPermissions("qs:snapshot:add")
    @Log(title = "设备抓图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QsDeviceSnapshot qsDeviceSnapshot) {
        return toAjax(qsDeviceSnapshotService.insertQsDeviceSnapshot(qsDeviceSnapshot));
    }

    /**
     * 修改设备抓图
     */
    @RequiresPermissions("qs:snapshot:edit")
    @Log(title = "设备抓图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsDeviceSnapshot qsDeviceSnapshot) {
        return toAjax(qsDeviceSnapshotService.updateQsDeviceSnapshot(qsDeviceSnapshot));
    }

    /**
     * 删除设备抓图
     */
    @RequiresPermissions("qs:snapshot:remove")
    @Log(title = "设备抓图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qsDeviceSnapshotService.deleteQsDeviceSnapshotByIds(ids));
    }

    /**
     * 从流中抓图并保存
     *
     * @param deviceId 设备ID
     * @param app 应用名
     * @param stream 流名
     * @param snapshotType 抓图类型
     * @return 抓图结果
     */
    @RequiresPermissions("qs:snapshot:add")
    @Log(title = "设备抓图", businessType = BusinessType.INSERT)
    @PostMapping("/captureFromStream")
    public AjaxResult captureFromStream(
            @RequestParam Long deviceId,
            @RequestParam String app,
            @RequestParam String stream,
            @RequestParam(defaultValue = "manual") String snapshotType
    ) {
        try {
            log.info("开始从流中抓图，deviceId: {}, app: {}, stream: {}", deviceId, app, stream);

            // 获取设备信息
            R<QsDevice> deviceR = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(deviceR) || deviceR.getData() == null) {
                log.error("获取设备信息失败，deviceId: {}", deviceId);
                return error("获取设备信息失败");
            }
            QsDevice device = deviceR.getData();

            // 调用ZLM服务抓图
            R<String> snapR = remoteZlmService.snap(app, stream, SecurityConstants.INNER);
            if (R.isError(snapR) || snapR.getData() == null) {
                log.error("抓图失败，deviceId: {}", deviceId);
                return error("抓图失败");
            }
            String fileUrl = snapR.getData();
            log.info("抓图成功，fileUrl: {}", fileUrl);

            // 从URL中提取文件名
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            String localFilePath = filePath + "/snap/" + fileName;
            File savedFile = new File(localFilePath);
            long fileSize = savedFile.exists() ? savedFile.length() : 0;

            // 构造抓图记录
            QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
            snapshot.setDeviceId(device.getId());
            snapshot.setDeviceCode(device.getDeviceCode());
            snapshot.setDeviceName(device.getDeviceName());
            snapshot.setFileUrl(fileUrl);
            snapshot.setFilePath(localFilePath);
            snapshot.setFileSize(fileSize);
            snapshot.setFileName(fileName);
            snapshot.setFileType("jpg");
            snapshot.setSnapshotType(snapshotType);
            snapshot.setSdkType("stream");
            snapshot.setChannel(device.getChannel() != null ? device.getChannel() : 0L);
            snapshot.setCaptureTime(new Date());

            // 保存到数据库
            int result = qsDeviceSnapshotService.insertQsDeviceSnapshot(snapshot);
            if (result > 0) {
                log.info("抓图记录保存成功，snapshotId: {}", snapshot.getId());
                return success(snapshot);
            } else {
                log.error("保存抓图记录失败");
                return error("保存抓图记录失败");
            }

        } catch (Exception e) {
            log.error("从流中抓图异常", e);
            return error("抓图异常：" + e.getMessage());
        }
    }
}
