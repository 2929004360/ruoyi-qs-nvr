package com.ruoyi.haikang.api.domain;

import lombok.Data;

/**
 * @FileName PresetInfo
 * @Description
 * @Author fengcheng
 * @date 2025-12-20
 **/
@Data
public class PresetInfo {
    public int index;
    public String name;
    public short pan;   // 水平
    public short tilt;  // 垂直
    public short zoom;  // 变焦

    public PresetInfo(int index, String name, short pan, short tilt, short zoom) {
        this.index = index;
        this.name = name;
        this.pan = pan;
        this.tilt = tilt;
        this.zoom = zoom;
    }
}
