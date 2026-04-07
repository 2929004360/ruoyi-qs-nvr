package com.ruoyi.zlm.api;

import com.ruoyi.zlm.config.UserSetting;
import com.ruoyi.zlm.service.IMediaServerService;
import com.ruoyi.zlm.utils.ZLMRESTfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * zlm接口
 *
 * @FileName ZlmApiController
 * @Description
 * @Author fengcheng
 * @date 2026-04-01
 **/
@Slf4j
@RestController
@RequestMapping("/api/zlm")
public class ZlmApiController {

    @Autowired
    private ZLMRESTfulUtils zlmresTfulUtils;


    @Autowired
    private IMediaServerService mediaServerService;

    @Autowired
    private UserSetting userSetting;
}
