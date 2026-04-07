package com.ruoyi.haikang.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.haikang.service.IHaiKangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * 海康sdk Controller
 *
 * @FileName HaiKangController
 * @Description
 * @Author fengcheng
 * @date 2026-03-28
 **/
@RestController
public class HaiKangController extends BaseController {

    @Autowired
    private IHaiKangService haiKangService;
}
