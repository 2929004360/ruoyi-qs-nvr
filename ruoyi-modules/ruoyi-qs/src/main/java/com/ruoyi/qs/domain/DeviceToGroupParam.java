package com.ruoyi.qs.domain;

import lombok.Data;

import java.util.List;

@Data
public class DeviceToGroupParam {

    private String parentId;
    private String businessGroup;
    private List<Long> deviceIds;
    private Boolean all;
}
