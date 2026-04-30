package com.ruoyi.haikang.isup.api.domain;

import lombok.Data;

/**
 * 海康ISUP预置点信息
 *
 * @FileName HaiKangIsupPresetInfo
 * @Description
 * @Author fengcheng
 * @date 2026-04-30
 **/
@Data
public class HaiKangIsupPresetInfo {
    public int index;
    public String name;

    public HaiKangIsupPresetInfo(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
