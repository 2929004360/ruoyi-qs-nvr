package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标信息
*/
public class NET_EVENT_WHOLE_FACE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 目标在抓拍图片中的角度信息, nPitch:抬头低头的俯仰角, nYaw左右转头的偏航角, nRoll头在平面内左偏右偏的翻滚角, 角度值取值范围[-90,90], 三个角度值都为999表示此角度信息无效,参见结构体定义 {@link NetSDKLib.NET_EULER_ANGLE}
    */
    public NetSDKLib.NET_EULER_ANGLE stuFaceCaptureAngle = new NetSDKLib.NET_EULER_ANGLE();
    /**
     * 目标抓拍角度范围(角度最小值,最大值),  三个角度依次分别是Pitch抬头低头,仰俯角;yaw是左右转头,偏航角;Roll是头在平面内左偏右偏，翻滚角,参见结构体定义 {@link NET_ANGEL_RANGE}
    */
    public NET_ANGEL_RANGE[] stuAngelRange = new NET_ANGEL_RANGE[3];
    /**
     * 保留字段
    */
    public byte[]           byReserved = new byte[256];

    public NET_EVENT_WHOLE_FACE_INFO() {
        for(int i = 0; i < stuAngelRange.length; i++){
            stuAngelRange[i] = new NET_ANGEL_RANGE();
        }
    }
}

