package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 图片信息
*/
public class NET_RECOGNITION_PIC_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 图片是否算法检测出来的检测过的提交识别服务器时,, 则不需要再时检测定位抠图,1:检测过的,0:没有检测过
    */
    public int              bIsDetected;
    /**
     * 文件在二进制数据块中的偏移位置, 单位:字节
    */
    public int              nOffSet;
    /**
     * 文件大小, 单位:字节
    */
    public int              nLength;
    /**
     * 图片宽度
    */
    public int              nWidth;
    /**
     * 图片高度
    */
    public int              nHeight;
    /**
     * 文件路径
    */
    public byte[]           szFilePath = new byte[256];
    /**
     * 图片ID
    */
    public byte[]           szPictureID = new byte[32];
    /**
     * 该图片建模状态 0-未知, 1-建模失败,可能是图片不符合要求,需要换图片, 2-有可用的特征值(有一张图计算过特征值即可), 3-正在计算特征值(实际的计算过程通常只有几十毫秒，主要用于批量重建人L特征值时，排在批量队列后面的人L。无特征值时，会切换到此状态), 4-已建模，但算法升级导致数据不可用，需要重新建模
    */
    public int              nFeatureState;
    /**
     * 建模失败原因，只有在建模状态为建模失败时用,0-未知,1-图片格式问题,2-无人L或不够清晰,3-多个人L,4-图片解码失败,5-不推荐入库,6-数据库操作失败 ,7-获取图片失败,8-系统异常（如Licence失效、建模分析器未启动导致的失败）,9-水平角,10-俯仰角,11-整图中人L最大占比,12-整图中人L最小占比,13-人LT距大小,14-人L属性判断,15-人L对齐分数
    */
    public int              nFeatureErrCode;
    /**
     * 修改时对于该图片的操作，无该字段或值为0表示修改图片。0-修改 1-新增 2-删除
    */
    public int              nPicOperate;
    /**
     * 在上传图片数据中的图片序号
    */
    public int              nIndexInData;
    /**
     * 保留字节,留待扩展.
    */
    public byte[]           szReserved = new byte[188];

    public NET_RECOGNITION_PIC_INFO() {
    }
}

