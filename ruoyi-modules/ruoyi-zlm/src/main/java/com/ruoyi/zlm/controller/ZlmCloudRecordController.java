package com.ruoyi.zlm.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.zlm.api.domain.StreamContent;
import com.ruoyi.zlm.api.domain.StreamInfo;
import com.ruoyi.zlm.api.domain.ZlmCloudRecord;
import com.ruoyi.zlm.common.InviteErrorCode;
import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.domain.CloudRecordUrl;
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IZlmCloudRecordService;
import com.ruoyi.zlm.utils.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 云端录像Controller
 *
 * @author fengcheng
 * @date 2026-04-10
 */
@Slf4j
@RestController
@RequestMapping("/cloudRecord")
public class ZlmCloudRecordController extends BaseController {
    @Autowired
    private IZlmCloudRecordService zlmCloudRecordService;

    @Autowired
    private UserSetting userSetting;

    /**
     * 查询云端录像列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZlmCloudRecord zlmCloudRecord) {
        startPage();
        List<ZlmCloudRecord> list = zlmCloudRecordService.selectZlmCloudRecordList(zlmCloudRecord);
        return getDataTable(list);
    }

    /**
     * 获取云端录像详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(zlmCloudRecordService.selectZlmCloudRecordById(id));
    }

    /**
     * 删除云端录像
     */
    @Log(title = "云端录像", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        zlmCloudRecordService.deleteZlmCloudRecordByIds(ids);
        return success();
    }

    /**
     * 播放云端录像
     *
     * @param id 云端录像id
     * @return
     */
    @GetMapping("/loadRecord/{id}")
    public DeferredResult<R<StreamContent>> loadRecord(@PathVariable Long id, HttpServletRequest request) {
        DeferredResult<R<StreamContent>> result = new DeferredResult<>();

        result.onTimeout(() -> {
            log.info("[加载录像文件超时] id={}", id);
            R<StreamContent> wvpResult = R.fail();
            wvpResult.setMsg("加载录像文件超时");
            result.setResult(wvpResult);
        });

        ErrorCallback<StreamInfo> callback = (code, msg, streamInfo) -> {

            R<StreamContent> wvpResult = new R<>();
            if (code == InviteErrorCode.SUCCESS.getCode()) {
                wvpResult.setCode(200);
                wvpResult.setMsg("操作成功");

                if (streamInfo != null) {
                    if (userSetting.getUseSourceIpAsStreamIp()) {
                        streamInfo = streamInfo.clone();//深拷贝
                        String host;
                        try {
                            URL url = new URL(request.getRequestURL().toString());
                            host = url.getHost();
                        } catch (MalformedURLException e) {
                            host = request.getLocalAddr();
                        }
                        streamInfo.changeStreamIp(host);
                    }
                    if (!org.springframework.util.ObjectUtils.isEmpty(streamInfo.getMediaServer().getTranscodeSuffix()) && !"null".equalsIgnoreCase(streamInfo.getMediaServer().getTranscodeSuffix())) {
                        streamInfo.setStream(streamInfo.getStream() + "_" + streamInfo.getMediaServer().getTranscodeSuffix());
                    }
                    wvpResult.setData(new StreamContent(streamInfo));
                } else {
                    wvpResult.setCode(code);
                    wvpResult.setMsg(msg);
                }
            } else {
                wvpResult.setCode(code);
                wvpResult.setMsg(msg);
            }
            result.setResult(wvpResult);
        };

        zlmCloudRecordService.loadRecord(id, callback);
        return result;
    }


    /**
     * 关闭流文件形成播放地址
     *
     * @param id 云端录像id
     * @return
     */
    @GetMapping("/closeStreams/{id}")
    public AjaxResult closeStreams(@PathVariable Long id) {
        zlmCloudRecordService.closeStreams(id);
        return AjaxResult.success();
    }

    /**
     * 下载指定录像文件的压缩包
     *
     * @param response HttpServletResponse
     * @param ids      云端录像id数组
     */
    @GetMapping("/download/zip")
    public void downloadZipFileFromUrl(HttpServletResponse response, Long[] ids) {
        log.info("[下载指定录像文件的压缩包] 查询 ids->{}", ids);
        List<Long> arrayList = new ArrayList<>(List.of(ids));
        List<CloudRecordUrl> cloudRecordItemList = zlmCloudRecordService.getUrlListByIds(arrayList);
        if (ObjectUtils.isEmpty(cloudRecordItemList)) {
            log.warn("[下载指定录像文件的压缩包] 未找到录像文件，ids->{}", ids);
            return;
        }

        // 设置响应头
        response.setContentType("application/zip");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=record_" + System.currentTimeMillis() + ".zip");

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            for (CloudRecordUrl recordUrl : cloudRecordItemList) {
                try {
                    zos.putNextEntry(new ZipEntry(recordUrl.getFileName()));
                    boolean success = false;
                    
                    // 优先尝试从本地文件读取
                    if (recordUrl.getFilePath() != null && !recordUrl.getFilePath().isEmpty()) {
                        try (FileInputStream fis = new FileInputStream(recordUrl.getFilePath())) {
                            byte[] buf = new byte[8192]; // 8KB 缓冲区，提高性能
                            int len;
                            while ((len = fis.read(buf)) != -1) {
                                zos.write(buf, 0, len);
                            }
                            success = true;
                        } catch (IOException e) {
                            log.warn("[下载指定录像文件的压缩包] 本地文件读取失败，尝试网络下载: {}", recordUrl.getFilePath());
                        }
                    }
                    
                    // 如果本地读取失败，尝试网络下载
                    if (!success && recordUrl.getDownloadUrl() != null && !recordUrl.getDownloadUrl().isEmpty()) {
                        success = HttpUtils.downLoadFile(recordUrl.getDownloadUrl(), zos);
                    }

                    if (!success) {
                        log.warn("[下载指定录像文件的压缩包] 文件获取失败: {}", recordUrl.getFileName());
                    }

                    zos.closeEntry();
                } catch (Exception e) {
                    log.error("[下载指定录像文件的压缩包] 处理文件失败: {}, 错误: {}", recordUrl.getFileName(), e.getMessage());
                    // 继续处理下一个文件
                }
            }
        } catch (IOException e) {
            log.error("[下载指定录像文件的压缩包] 创建压缩包失败，查询 ids->{}", ids, e);
        }
    }

    /**
     * 设置录像播放速度
     *
     * @param mediaServerId 使用的节点Id
     * @param app           应用名
     * @param stream        流id
     * @param speed         播放速度
     * @param schema        播放协议
     */
    @GetMapping("/speed")
    public AjaxResult setRecordSpeed(
            @RequestParam(required = true) String mediaServerId,
            @RequestParam(required = true) String app,
            @RequestParam(required = true) String stream,
            @RequestParam(required = true) Integer speed,
            @RequestParam(required = false) String schema
    ) {
        if (schema == null) {
            schema = "ts";
        }

        zlmCloudRecordService.setRecordSpeed(mediaServerId, app, stream, speed, schema);
        return AjaxResult.success();
    }

    /**
     * 定位录像播放到制定位置
     *
     * @param mediaServerId 使用的节点Id
     * @param app           应用名
     * @param stream        流ID
     * @param seek          要定位的时间位置，从录像开始的时间算起
     * @param schema        播放协议
     */
    @GetMapping("/seek")
    public AjaxResult seekRecord(
            @RequestParam(required = true) String mediaServerId,
            @RequestParam(required = true) String app,
            @RequestParam(required = true) String stream,
            @RequestParam(required = true) Double stamp,
            @RequestParam(required = false) String schema
    ) {
        if (schema == null) {
            schema = "ts";
        }
        zlmCloudRecordService.seekRecord(mediaServerId, app, stream, stamp, schema);
        return AjaxResult.success();
    }
}
