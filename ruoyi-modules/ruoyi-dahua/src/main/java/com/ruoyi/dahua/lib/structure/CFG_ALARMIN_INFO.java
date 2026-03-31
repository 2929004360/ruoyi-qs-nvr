package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib.CFG_ALARM_MSG_HANDLE;
import com.ruoyi.dahua.lib.NetSDKLib.CFG_TIME_SECTION;
import com.ruoyi.dahua.lib.NetSDKLib.SdkStructure;


/**
 * @author 421657
 * @description 外部报警配置
 * @origin autoTool
 * @date 2023/11/01 20:42:35
 */
public class CFG_ALARMIN_INFO extends SdkStructure {
    /**
     * / 报警通道号(0开始)
     */
    public int              nChannelID;
    /**
     * / 使能开关
     */
    public int              bEnable;
    /**
     * / 报警通道名称
     */
    public byte[]           szChnName = new byte[64];
    /**
     * / 报警器类型，0：常闭，1：常开
     */
    public int              nAlarmType;
    /**
     * / 报警联动
     */
    public CFG_ALARM_MSG_HANDLE stuEventHandler = new CFG_ALARM_MSG_HANDLE();
    /**
     * / 事件响应时间段，时间段获取和设置以此成员为准，忽略 stuEventHandler 中的 stuTimeSection
     */
    public CFG_TIME_SECTION[] stuTimeSection = new CFG_TIME_SECTION[7 * 6];
    /**
     * /
     */
    public int              abDevID;
    /**
     * / 设备ID
     */
    public byte[]           szDevID = new byte[128];
    /**
     * / 传感器触发模式, 0:高有效，1低有效；
     * / 具体表现为传感器接地or电源，与nAlarmType组合使用
     */
    public int              nPole;
    /**
     * 传感器感应方式 {@link com.ruoyi.dahua.lib.enumeration.EM_SENSE_METHOD}
     */
    public int              emSense;
    /**
     * / 报警使能控制方式 {@link com.ruoyi.dahua.lib.enumeration.EM_CTRL_ENABLE}
     */
    public int              emCtrl;
    /**
     * / 延时撤防时间,防区类型为"Delay"(延时防区)时才有效, 单位: 秒, 最大时间通过查询能力获得
     * / emCtrl值为EM_CTRL_NORMAL或EM_CTRL_ALWAYS_EN 时有效。
     */
    public int              nDisDelay;
    /**
     * / 防区类型, 具体支持的类型通过查询能力获得 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_DEFENCEAREATYPE}
     */
    public int              emDefenceAreaType;
    /**
     * / 延时布防时间, 防区类型为"Delay"(延时防区)时才有效, 单位: 秒, 最大时间通过查询能力获得
     */
    public int              nEnableDelay;
    /**
     * / 根地址, -1表示无效, 0表示本地通道, 1表示连接在第一个串口上的扩展通道, 2、3...以此类推
     */
    public int              nSlot;
    /**
     * / 第一级级联地址, 表示连接在第nSlot串口上的第nLevel1个探测器或仪表, -1表示无效, 从0开始
     */
    public int              nLevel1;
    /**
     * / 表示nLevel2字段是否存在
     */
    public byte             abLevel2;
    /**
     * / 第二级级联地址, 表示连接在第nLevel1个的仪表上的探测器序号, -1表示无效, 从0开始
     */
    public int              nLevel2;
    /**
     * / 门未关超时时间,单位为s,范围 15s-300s
     */
    public int              nDoorNotClosedTimeout;

    public CFG_ALARMIN_INFO() {
        for (int i = 0; i < stuTimeSection.length; i++) {
            stuTimeSection[i] = new CFG_TIME_SECTION();
        }
    }
}

