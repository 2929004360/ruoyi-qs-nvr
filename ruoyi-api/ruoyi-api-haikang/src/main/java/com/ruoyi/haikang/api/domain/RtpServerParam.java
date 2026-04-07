package com.ruoyi.haikang.api.domain;

import lombok.Data;

/**
 * @FileName RtpServerParam
 * @Description
 * @Author fengcheng
 * @date 2026-04-07
 **/
@Data
public class RtpServerParam {

    private Long id;

    private String ip;

    private Integer port;

    private String ssrc;
}
