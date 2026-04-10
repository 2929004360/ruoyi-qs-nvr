package com.ruoyi.zlm.controller;

import com.alibaba.nacos.api.model.v2.ErrorCode;
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
import com.ruoyi.zlm.service.ErrorCallback;
import com.ruoyi.zlm.service.IZlmCloudRecordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
        return toAjax(zlmCloudRecordService.deleteZlmCloudRecordByIds(ids));
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
                wvpResult.setCode(ErrorCode.SUCCESS.getCode());
                wvpResult.setMsg(ErrorCode.SUCCESS.getMsg());

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
}
