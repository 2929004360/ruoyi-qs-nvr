package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 查询联动规则输出参数(对应 EM_THINGS_SERVICE_TYPE_QUERULE)
 * @date 2022/04/20 10:50:22
 */
public class NET_OUT_THINGS_SERVICE_QUERULE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小, 调用者必须初始化该字段
     */
    public int              dwSize;
    /**
     * 物模型协议错误码定义
     */
    public int              nErrcode;
    /**
     * 产品ID
     */
    public byte[]           szProductID = new byte[64];
    /**
     * 设备ID
     */
    public byte[]           szDeviceID = new byte[64];
    /**
     * 页个数
     */
    public int              nPageCount;
    /**
     * 每一页的数据大小
     */
    public int              nPageSize;
    /**
     * 总数
     */
    public int              nTotal;
    /**
     * 按页查询
     */
    public int              nPage;
    /**
     * 实际返回的pstuRules个数
     */
    public int              nRetRulesNum;
    /**
     * 用户分配的pstuRules个数
     */
    public int              nMaxRulesNum;
    /**
     * 规则详情，用户分配内存,大小为 nMaxRulesNum * sizeof{@link NET_THINGS_SERVICE_QUERULE_RULES}
     */
    public Pointer          pstuRules;

    public NET_OUT_THINGS_SERVICE_QUERULE() {
        this.dwSize = this.size();
    }
}

