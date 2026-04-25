package com.ruoyi.qs.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.domain.*;
import com.ruoyi.qs.service.IQsDeviceService;
import com.ruoyi.qs.utils.VideoSnapshotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频监控设备Controller
 *
 * @author fengcheng
 * @date 2026-03-27
 */
@Slf4j
@RestController
@RequestMapping("/device")
public class QsDeviceController extends BaseController {
    @Autowired
    private IQsDeviceService qsDeviceService;

    @Autowired
    private VideoSnapshotUtil videoSnapshotUtil;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.prefix}")
    private String filePrefix;

    /**
     * 查询视频监控设备列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QsDevice qsDevice) {
        startPage();
        List<QsDevice> list = qsDeviceService.selectQsDeviceList(qsDevice);
        return getDataTable(list);
    }

    /**
     * 根据行政区域获取视频监控设备列表
     */
    @GetMapping("/queryListByCivilCode")
    public TableDataInfo queryListByCivilCode(QsDevice qsDevice) {
        startPage();
        List<QsDevice> list = qsDeviceService.queryListByCivilCode(qsDevice);
        return getDataTable(list);
    }

    /**
     * 获取视频监控设备详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(qsDeviceService.selectQsDeviceById(id));
    }

    /**
     * 新增视频监控设备
     */
    @Log(title = "视频监控设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QsDevice qsDevice) {
        return toAjax(qsDeviceService.insertQsDevice(qsDevice));
    }

    /**
     * 修改视频监控设备
     */
    @Log(title = "视频监控设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QsDevice qsDevice) {
        return toAjax(qsDeviceService.updateQsDevice(qsDevice));
    }

    /**
     * 删除视频监控设备
     */
    @Log(title = "视频监控设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qsDeviceService.deleteQsDeviceByIds(ids));
    }

    /**
     * 状态修改
     */
    @Log(title = "视频监控设备", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody QsDevice qsDevice) {
        qsDevice.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(qsDeviceService.updateQsDeviceStatus(qsDevice));
    }

    /**
     * 获取计划记录对应的视频监控设备
     */
    @GetMapping("/listPlanRecord")
    public TableDataInfo listPlanRecordQsDevice(QsDevice qsDevice) {
        startPage();
        List<QsDevice> list = qsDeviceService.listPlanRecordQsDevice(qsDevice);
        return getDataTable(list);
    }

    /**
     * 设备关联录制计划
     *
     * @param param
     * @return
     */
    @PostMapping("/link")
    public AjaxResult link(@RequestBody RecordPlanParam param) {
        if (param.getAllLink() != null) {
            if (param.getAllLink()) {
                qsDeviceService.linkAll(param.getPlanId());
            } else {
                qsDeviceService.cleanAll(param.getPlanId());
            }
            return success();
        }

        if (param.getDeviceIds() == null) {
            throw new RuntimeException("设备ID不可都为NULL");
        }

        qsDeviceService.link(param.getDeviceIds(), param.getPlanId());

        return success();
    }


    /**
     * 设备设置行政区划
     *
     * @param param
     */
    @PostMapping("/region/add")
    public AjaxResult addChannelToRegion(@RequestBody DeviceToRegionParam param) {
        Assert.notEmpty(param.getDeviceIds(), "设备ID不可为空");
        Assert.hasLength(param.getCivilCode(), "未添加行政区划");
        qsDeviceService.addDeviceToRegion(param.getCivilCode(), param.getDeviceIds());
        return success();
    }

    /**
     * 设备删除行政区划
     *
     * @param param
     */
    @PostMapping("/region/delete")
    public AjaxResult deleteDeviceToRegion(@RequestBody DeviceToRegionParam param) {
        Assert.isTrue(!param.getDeviceIds().isEmpty() || !ObjectUtils.isEmpty(param.getCivilCode()), "参数异常");
        qsDeviceService.deleteDeviceToRegion(param.getCivilCode(), param.getDeviceIds());
        return success();
    }


    /**
     * 存在行政区划但无法挂载的设备列表
     */
    @GetMapping("/civilCode/unusual/list")
    public TableDataInfo queryListByCivilCodeForUnusual(QsDevice qsDevice) {
        startPage();
        List<QsDevice> list = qsDeviceService.queryListByCivilCodeForUnusual(qsDevice);
        return getDataTable(list);
    }

    /**
     * 清除存在行政区划但无法挂载的设备列表
     *
     * @param param
     */
    @PostMapping("/civilCode/unusual/clear")
    public AjaxResult clearDeviceCivilCode(@RequestBody DeviceToRegionParam param) {
        qsDeviceService.clearDeviceCivilCode(param.getAll(), param.getDeviceIds());
        return success();
    }

    /**
     * 获取编码列表
     *
     * @return
     */
    @GetMapping("/network/identification/list")
    public AjaxResult getNetworkIdentificationTypeList() {
        List<NetworkIdentificationType> list = qsDeviceService.getNetworkIdentificationTypeList();
        return success(list);
    }

    /**
     * 获取编码列表
     *
     * @return
     */
    @GetMapping("/type/list")
    public AjaxResult getDeviceTypeList() {
        List<DeviceType> list = qsDeviceService.getDeviceTypeList();
        return success(list);
    }

    /**
     * 获取行业编码列表
     *
     * @return
     */
    @GetMapping("/industry/list")
    public AjaxResult getIndustryCodeList() {
        List<IndustryCodeType> list = qsDeviceService.getIndustryCodeList();
        return success(list);
    }

    /**
     * 获取关联业务分组设备列表
     */
    @GetMapping("/parent/list")
    public TableDataInfo queryListByParentId(QsDevice qsDevice) {
        startPage();
        List<QsDevice> list = qsDeviceService.queryListByParentId(qsDevice);
        return getDataTable(list);
    }

    /**
     * 设备设置业务分组
     *
     * @param param
     */
    @PostMapping("/group/add")
    public AjaxResult addChannelToGroup(@RequestBody DeviceToGroupParam param) {
        Assert.notEmpty(param.getDeviceIds(), "设备ID不可为空");
        Assert.hasLength(param.getParentId(), "未添加上级分组编号");
        Assert.hasLength(param.getBusinessGroup(), "未添加业务分组");
        qsDeviceService.addChannelToGroup(param.getParentId(), param.getBusinessGroup(), param.getDeviceIds());
        return success();
    }

    /**
     * 删除业务分组设备
     *
     * @param param
     */
    @PostMapping("/group/delete")
    public AjaxResult deleteDeviceToGroup(@RequestBody DeviceToGroupParam param) {
        Assert.isTrue(!param.getDeviceIds().isEmpty() || (!ObjectUtils.isEmpty(param.getParentId()) && !ObjectUtils.isEmpty(param.getBusinessGroup())), "参数异常");
        qsDeviceService.deleteDeviceToGroup(param.getParentId(), param.getBusinessGroup(), param.getDeviceIds());
        return success();
    }

    /**
     * 存在父节点编号但无法挂载的设备列表
     */
    @GetMapping("/parent/unusual/list")
    public TableDataInfo queryListByParentForUnusual(QsDevice qsDevice) {
        startPage();
        List<QsDevice> list = qsDeviceService.queryListByParentForUnusual(qsDevice);
        return getDataTable(list);
    }

    /**
     * 清除存在分组节点但无法挂载的设备列表
     *
     * @param param
     */
    @PostMapping("/parent/unusual/clear")
    public AjaxResult clearDeviceParent(@RequestBody DeviceToGroupParam param) {
        qsDeviceService.clearDeviceParent(param.getAll(), param.getDeviceIds());
        return success();
    }

    /**
     * 获取本地mp4截图
     */
    @RequiresPermissions("qs:device:edit")
    @Log(title = "视频监控设备", businessType = BusinessType.UPDATE)
    @PutMapping("/getVideoSnapshot/{id}")
    public AjaxResult getVideoSnapshot(@PathVariable("id") Long id) {
        QsDevice device = qsDeviceService.selectQsDeviceById(id);
        
        if (device == null) {
            return error("设备不存在");
        }
        
        if (device.getLiveAddress() == null || device.getLiveAddress().isEmpty()) {
            return error("设备直播地址为空");
        }

        try {
            String videoPath = convertUrlToPath(device.getLiveAddress(), this.fileDomain, this.filePrefix, this.filePath);

            String fileName = "/video_file-" + device.getDeviceCode() + ".jpg";

            String savePath = this.filePath + "/snap" + fileName;

            // 截取第 1 秒的画面
            videoSnapshotUtil.takeSnapshot(videoPath, savePath, 1.0);

            String filePath = fileDomain + filePrefix + "/snap/" + fileName;

            QsDevice qsDevice = new QsDevice();
            qsDevice.setId(id);
            qsDevice.setSnap(filePath);
            qsDeviceService.updateQsDevice(qsDevice);
        } catch (Exception e) {
            log.error("[获取视频截图失败] 设备ID: {}, 错误: {}", id, e.getMessage());
            return error("获取视频截图失败: " + e.getMessage());
        }

        return success();
    }

    /**
     * 将网络访问路径转换为本地文件物理路径
     */
    public String convertUrlToPath(String url, String domain, String prefix, String localBasePath) {
        // 步骤 A: 去除域名部分
        // 例如：http://127.0.0.1:9300/statics/...  ->  /statics/...
        if (url.startsWith(domain)) {
            url = url.substring(domain.length());
        }

        // 步骤 B: 去除前缀部分
        // 例如：/statics/2026/...  ->  /2026/...
        if (url.startsWith(prefix)) {
            url = url.substring(prefix.length());
        }

        // 步骤 C: 拼接本地路径
        // 注意：防止路径分隔符重复或缺失
        // localBasePath: D:/ruoyi/uploadPath
        // url: /2026/03/27/...

        if (url.startsWith("/")) {
            return localBasePath + url;
        } else {
            return localBasePath + "/" + url;
        }
    }
}
