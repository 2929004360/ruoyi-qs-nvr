package com.ruoyi.zlm.hook;

import com.ruoyi.zlm.api.hook.ABLHookParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnRecordMp4ABLHookParam extends ABLHookParam {
    private String fileName;
    private String startTime;
    private String endTime;
    private long fileSize;
}
