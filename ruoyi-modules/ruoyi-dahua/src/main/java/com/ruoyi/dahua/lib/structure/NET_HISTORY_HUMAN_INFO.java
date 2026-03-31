package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 历史库人体信息
*/
public class NET_HISTORY_HUMAN_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 上衣颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int              emCoatColor;
    /**
     * 上衣类型,参见枚举定义 {@link NetSDKLib.EM_COAT_TYPE}
    */
    public int              emCoatType;
    /**
     * 裤子颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int              emTrousersColor;
    /**
     * 裤子类型,参见枚举定义 {@link NetSDKLib.EM_TROUSERS_TYPE}
    */
    public int              emTrousersType;
    /**
     * 是否戴帽子,参见枚举定义 {@link NetSDKLib.EM_HAS_HAT}
    */
    public int              emHasHat;
    /**
     * 是否带包,参见枚举定义 {@link NetSDKLib.EM_HAS_BAG}
    */
    public int              emHasBag;
    /**
     * 包围盒(8192坐标系),参见结构体定义 {@link NET_RECT}
    */
    public NET_RECT         stuBoundingBox = new NET_RECT();
    /**
     * 年龄
    */
    public int              nAge;
    /**
     * 性别,参见枚举定义 {@link NetSDKLib.EM_SEX_TYPE}
    */
    public int              emSex;
    /**
     * 角度,参见枚举定义 {@link NetSDKLib.EM_ANGLE_TYPE}
    */
    public int              emAngle;
    /**
     * 是否打伞,参见枚举定义 {@link NetSDKLib.EM_HAS_UMBRELLA}
    */
    public int              emHasUmbrella;
    /**
     * 包类型,参见枚举定义 {@link NetSDKLib.EM_BAG_TYPE}
    */
    public int              emBag;
    /**
     * 上半身衣服图案,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_PATTERN}
    */
    public int              emUpperPattern;
    /**
     * 头发样式,参见枚举定义 {@link NetSDKLib.EM_HAIR_STYLE}
    */
    public int              emHairStyle;
    /**
     * 帽类型,参见枚举定义 {@link NetSDKLib.EM_CAP_TYPE}
    */
    public int              emCap;
    /**
     * 是否有背包,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_HAS_BACK_BAG}
    */
    public int              emHasBackBag;
    /**
     * 是否带手提包,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_HAS_CARRIER_BAG}
    */
    public int              emHasCarrierBag;
    /**
     * 是否有肩包,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_HAS_SHOULDER_BAG}
    */
    public int              emHasShoulderBag;
    /**
     * 是否有斜跨包,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_HAS_MESSENGER_BAG}
    */
    public int              emMessengerBag;
    /**
     * 人体图片信息,参见结构体定义 {@link NET_HISTORY_HUMAN_IMAGE_INFO}
    */
    public NET_HISTORY_HUMAN_IMAGE_INFO stuImageInfo = new NET_HISTORY_HUMAN_IMAGE_INFO();
    /**
     * 图片信息,参见结构体定义 {@link NET_HISTORY_HUMAN_IMAGE_INFO}
    */
    public NET_HISTORY_HUMAN_IMAGE_INFO stuFaceImageInfo = new NET_HISTORY_HUMAN_IMAGE_INFO();
    /**
     * 人体唯一标识符，由服务端生成
    */
    public byte[]           szUID = new byte[64];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[192];

    public NET_HISTORY_HUMAN_INFO() {
    }
}

