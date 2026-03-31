package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 历史数据内容
 * @date 2022/04/20 11:31:57
 */
public class NET_THINGS_HISTORYDATA extends NetSDKLib.SdkStructure {
    /**
     * 标识符, "CZSJ_ZMZTGB"等
     */
    public byte[]           szEventID = new byte[32];
    /**
     * 设备ID
     */
    public byte[]           szDeviceID = new byte[32];
    /**
     * 设备序列ID
     */
    public byte[]           szDevSN = new byte[64];
    /**
     * 数据时间
     */
    public NET_TIME_EX      stuEventTime = new NET_TIME_EX();
    /**
     * 产品ID
     */
    public byte[]           szProductID = new byte[32];
    /**
     * 序号
     */
    public int              nOrder;
    /**
     * 实际返回的pstuProInfo个数
     */
    public int              nRetProInfoNum;
    /**
     * 用户分配的pstuProInfo个数
     */
    public int              nMaxProInfoNum;
    /**
     * 查询结果物模型属性信息,用户分配内存,大小为 nMaxProInfoNum * sizeof{@link NET_THINGS_HISTORYDATA_PROPER_INFO}
     * 格式：参数含义;参数名称;数据类型
     * 1. 当前状态;DQZT;bool
     * 2. 分合闸原因;FHZYY;int
     * 3. 事件动作;Happen; int
     * 4. 对应值;DYZ;int
     * 5. 报警动作;Action; int
     * 6. A相对应值;DYZA;int
     * 7. B相对应值;DYZB;int
     * 8. C相对应值;DYZC;int
     * 9. 检测结果;JCJG;bool
     * 10. 失败原因;SBYY;string
     * 11. 设备ID;SBID;string
     * 12. 变更类型;BGLX;int
     * 13. 任务ID;RWID;string
     * 14. 执行时间;ZXSJ;string
     * 15. 失败原因;SBYY;string
     * 16. 执行参数; CJXX_ZMZT; bool
     * 17. 执行结果;ZXJG;bool
     */
    public Pointer          pstuProInfo;
    /**
     * null
     */
    public byte[]           szReserve = new byte[512];
}

