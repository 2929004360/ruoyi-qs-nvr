package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 火警屏蔽区域
*/
public class NET_FIREWARN_SHIELDREGION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 屏蔽区使能
    */
    public int              bEnable;
    /**
     * 窗口id号
    */
    public int              nId;
    /**
     * 屏蔽区名称
    */
    public byte[]           szName = new byte[32];
    /**
     * 屏蔽区颜色
    */
    public int[]            nColor = new int[4];
    /**
     * 屏蔽区OSD是否显示
    */
    public int              bShow;
    /**
     * 云台方向与放大倍数，显示值，扩大100倍值, 第一个元素为水平角度，0-36000；, 第二个元素为垂直角度，（-18000）-（18000）；, 第三个元素为显示放大倍数，0-MaxZoom*100;, 用于记录屏蔽区位置
    */
    public int[]            nAbsPosition = new int[3];
    /**
     * 屏蔽区多边形类型，多边形中每个顶点的坐标归一化到[0,8192]区间，最多16边形,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuArea = new NET_POINT_EX[16];
    /**
     * 屏蔽区多边形顶点个数
    */
    public int              nAreaNum;
    /**
     * 屏蔽区配置模式，0为原有模式，从热成像画面绘制的屏蔽区；1为大屏蔽区，从可见光画面绘制的屏蔽区，默认0
    */
    public int              nConfigMode;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[1024];

    public NET_FIREWARN_SHIELDREGION_INFO() {
        for(int i = 0; i < stuArea.length; i++){
            stuArea[i] = new NET_POINT_EX();
        }
    }
}

