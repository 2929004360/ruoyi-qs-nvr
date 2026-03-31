package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 421657
 * @description 通讯录格式扩展
 * @origin autoTool
 * @date 2023/10/26 09:34:52
 */
public class NET_VT_CONTACTINFO_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 名字
     */
    public byte[]           szFirstName = new byte[32];
    /**
     * / 姓
     */
    public byte[]           szFamilyName = new byte[32];
    /**
     * / 可视对讲短号
     */
    public byte[]           szVTShortNumber = new byte[16];
    /**
     * / 可视对讲中号
     */
    public byte[]           szVTMiddleNumber = new byte[16];
    /**
     * / 可视对讲长号, 或VTH模拟室内机的序列号
     */
    public byte[]           szVTLongNumber = new byte[32];
    /**
     * / 可视对讲网络地址
     */
    public byte[]           szVTNetAddress = new byte[40];
    /**
     * / 室内机关联的门口号码
     */
    public byte[]           szVTOPosition = new byte[16];
    /**
     * / 昵称
     */
    public byte[]           szNickName = new byte[32];
    /**
     * / 备注
     */
    public byte[]           szNotes = new byte[32];
    /**
     * / 可视对讲所属设备系列, 与设备类型大类不完全对等, 取决于对讲所属的应用场景, "VTH": 室内机, "VTO": 门口机, "VTS": 管理机, "VTA": 紧急求助终端
     */
    public byte[]           szType = new byte[32];
    /**
     * / 注册方式: "public", "local"
     */
    public byte[]           szRegisterType = new byte[32];
    /**
     * / 注册密码
     */
    public byte[]           szVTHPassword = new byte[32];
    /**
     * / 所属幢号
     */
    public byte[]           szVTOBuilding = new byte[32];
    /**
     * / 所属单元号
     */
    public byte[]           szVTOUnit = new byte[32];
    /**
     * / 所属群组
     */
    public byte[]           szGroupNumber = new byte[32];
    /**
     * / 通道号，手机根据通道号订阅呼叫通知消息
     */
    public int              nChannel;
    /**
     * / 楼层号个数
     */
    public int              nFloorsNum;
    /**
     * / 楼层号
     */
    public byte[]           szFloors = new byte[256 * 4];
    /**
     * / 室内机开门触发梯控
     */
    public int              bLiftControlByVTH;
    /**
     * / 通讯录格式扩展，用户申请内存, refer to {@link NET_VT_CONTACTINFO_INFO_EX}
     */
    public Pointer          pContactInfoEx;
    /**
     * /保留字段
     */
    public byte[]           szResvered = new byte[256 - NetSDKLib.POINTERSIZE];

    public NET_VT_CONTACTINFO_INFO() {
    }
}

