package com.ruoyi.dahua.lib.enumeration;

/**
 * 设备类型
*/
public enum NET_EM_DEVICE_TYPE
{
    /**
     * 未知类型
    */
    NET_EM_DEVICE_UNKNOWN(0, "未知类型"),
    /**
     * 模拟摄像机
    */
    NET_EM_DEVICE_CAMERA(1, "模拟摄像机"),
    /**
     * 模拟球机
    */
    NET_EM_DEVICE_DOME(2, "模拟球机"),
    /**
     * 模拟矩阵
    */
    NET_EM_DEVICE_MATRIX(3, "模拟矩阵"),
    /**
     * 网络摄像机
    */
    NET_EM_DEVICE_IPC(4, "网络摄像机"),
    /**
     * 视频服务器
    */
    NET_EM_DEVICE_NVS(5, "视频服务器"),
    /**
     * 网络球机
    */
    NET_EM_DEVICE_SD(6, "网络球机"),
    /**
     * 智能存储盒
    */
    NET_EM_DEVICE_ITSE(7, "智能存储盒"),
    /**
     * 智能摄像机
    */
    NET_EM_DEVICE_ITC(8, "智能摄像机"),
    /**
     * 热成像摄像机
    */
    NET_EM_DEVICE_TPC(9, "热成像摄像机"),
    /**
     * 数字硬盘录像机
    */
    NET_EM_DEVICE_DVR(10, "数字硬盘录像机"),
    /**
     * 混合型数字硬盘录像机
    */
    NET_EM_DEVICE_HDVR(11, "混合型数字硬盘录像机"),
    /**
     * 高清CVI接口录像机
    */
    NET_EM_DEVICE_HCVR(12, "高清CVI接口录像机"),
    /**
     * 网络录像机
    */
    NET_EM_DEVICE_NVR(13, "网络录像机"),
    /**
     * 工作在PC上的软件网络录像机
    */
    NET_EM_DEVICE_PCNVR(14, "工作在PC上的软件网络录像机"),
    /**
     * 网络解码器
    */
    NET_EM_DEVICE_NVD(15, "网络解码器"),
    /**
     * 软件网络解码器
    */
    NET_EM_DEVICE_SNVD(16, "软件网络解码器"),
    /**
     * 万能解码器
    */
    NET_EM_DEVICE_UDS(17, "万能解码器"),
    /**
     * 存储服务器式录像机
    */
    NET_EM_DEVICE_SVR(18, "存储服务器式录像机"),
    /**
     * 视频综合平台
    */
    NET_EM_DEVICE_M(19, "视频综合平台"),
    /**
     * IVS服务器
    */
    NET_EM_DEVICE_IVS(20, "IVS服务器"),
    /**
     * 虚拟网络服务器
    */
    NET_EM_DEVICE_VNCSERVER(21, "虚拟网络服务器"),
    /**
     * 虚拟网络客户端
    */
    NET_EM_DEVICE_VNCCLIENT(22, "虚拟网络客户端"),
    /**
     * 嵌入式多屏控制器
    */
    NET_EM_DEVICE_DSCON(23, "嵌入式多屏控制器"),
    /**
     * 通用个人计算机
    */
    NET_EM_DEVICE_PC(24, "通用个人计算机"),
    /**
     * 网络视频存储服务器
    */
    NET_EM_DEVICE_EVS(25, "网络视频存储服务器"),
    /**
     * 视频转码服务器
    */
    NET_EM_DEVICE_VCS(26, "视频转码服务器"),
    /**
     * 报警主机, 15 年前
    */
    NET_EM_DEVICE_A(27, "报警主机, 15 年前"),
    /**
     * 报警主机, 15年后
    */
    NET_EM_DEVICE_ARC(28, "报警主机, 15年后"),
    /**
     * 智能球机
    */
    NET_EM_DEVICE_SDHNI(29, "智能球机"),
    /**
     * 门禁主机
    */
    NET_EM_DEVICE_BSC(30, "门禁主机"),
    /**
     * 门禁读卡器
    */
    NET_EM_DEVICE_BSR(31, "门禁读卡器"),
    /**
     * 媒体网关(接入安卓设备)
    */
    NET_EM_DEVICE_MGATEWAY(32, "媒体网关(接入安卓设备)"),
    /**
     * 智网门口机设备
    */
    NET_EM_DEVICE_VTO(33, "智网门口机设备"),
    /**
     * 智网室内机设备
    */
    NET_EM_DEVICE_VTH(34, "智网室内机设备"),
    /**
     * 可视对讲终端
    */
    NET_EM_DEVICE_VTT(35, "可视对讲终端"),
    /**
     * 管理机
    */
    NET_EM_DEVICE_VTS(36, "管理机"),
    /**
     * 控制器
    */
    NET_EM_DEVICE_VTNC(37, "控制器"),
    /**
     * 消费类卡片机产品
    */
    NET_EM_DEVICE_CE(38, "消费类卡片机产品"),
    /**
     * 手持终端
    */
    NET_EM_DEVICE_MPT(39, "手持终端"),
    /**
     * 金融ATM
    */
    NET_EM_DEVICE_ATM(40, "金融ATM"),
    /**
     * 智网家庭网关
    */
    NET_EM_DEVICE_SHG(41, "智网家庭网关"),
    /**
     * 防护舱控制器
    */
    NET_EM_DEVICE_ARM(42, "防护舱控制器"),
    /**
     * 无线中继设备
    */
    NET_EM_DEVICE_ASM(43, "无线中继设备"),
    /**
     * 报警柱
    */
    NET_EM_DEVICE_VTA(44, "报警柱"),
    /**
     * 机载云台
    */
    NET_EM_DEVICE_UAVGA(45, "机载云台"),
    /**
     * 一体化遥控器
    */
    NET_EM_DEVICE_UAVR(46, "一体化遥控器"),
    /**
     * 无人机
    */
    NET_EM_DEVICE_AIRCRAFT(47, "无人机"),
    /**
     * 地面站
    */
    NET_EM_DEVICE_UAVS(48, "地面站"),
    /**
     * 热成像云台摄像机
    */
    NET_EM_DEVICE_TPCSD(49, "热成像云台摄像机"),
    /**
     * 热成像枪机
    */
    NET_EM_DEVICE_TPCBF(50, "热成像枪机"),
    /**
     * 雷达
    */
    NET_EM_DEVICE_RADAR(51, "雷达"),
    /**
     * 雷达系统
    */
    NET_EM_DEVICE_RADAR_PTZ(52, "雷达系统"),
    /**
     * 摄像雷达
    */
    NET_EM_DEVICE_RADAR_CAM(53, "摄像雷达"),
    /**
     * KVM输入节点
    */
    NET_EM_DEVICE_KVM_IN(54, "KVM输入节点"),
    /**
     * KVM输出节点
    */
    NET_EM_DEVICE_KVM_OUT(55, "KVM输出节点"),
    /**
     * IVSS
    */
    NET_EM_DEVICE_IVSS(56, "IVSS"),
    /**
     * 工牌相机
    */
    NET_EM_DEVICE_GP(57, "工牌相机");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_DEVICE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_DEVICE_TYPE enumType : NET_EM_DEVICE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_DEVICE_TYPE enumType : NET_EM_DEVICE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

