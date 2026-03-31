package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  分辨率枚举,供DH_DSP_ENCODECAP使用 
* @date 2022/08/31 14:44:17
*/
public enum CAPTURE_SIZE {
/**
704*576(PAL)  704*480(NTSC),兼容WWxHH,下同
*/
    CAPTURE_SIZE_D1(0, "704*576(PAL)  704*480(NTSC),兼容WWxHH,下同"),
/**
352*576(PAL)  352*480(NTSC)
*/
    CAPTURE_SIZE_HD1(1, "352*576(PAL)  352*480(NTSC)"),
/**
704*288(PAL)  704*240(NTSC)
*/
    CAPTURE_SIZE_BCIF(2, "704*288(PAL)  704*240(NTSC)"),
/**
352*288(PAL)  352*240(NTSC)
*/
    CAPTURE_SIZE_CIF(3, "352*288(PAL)  352*240(NTSC)"),
/**
176*144(PAL)  176*120(NTSC)
*/
    CAPTURE_SIZE_QCIF(4, "176*144(PAL)  176*120(NTSC)"),
/**
640*480
*/
    CAPTURE_SIZE_VGA(5, "640*480"),
/**
320*240
*/
    CAPTURE_SIZE_QVGA(6, "320*240"),
/**
480*480
*/
    CAPTURE_SIZE_SVCD(7, "480*480"),
/**
160*128
*/
    CAPTURE_SIZE_QQVGA(8, "160*128"),
/**
800*592
*/
    CAPTURE_SIZE_SVGA(9, "800*592"),
/**
1024*768
*/
    CAPTURE_SIZE_XVGA(10, "1024*768"),
/**
1280*800
*/
    CAPTURE_SIZE_WXGA(11, "1280*800"),
/**
1280*1024
*/
    CAPTURE_SIZE_SXGA(12, "1280*1024"),
/**
1600*1024
*/
    CAPTURE_SIZE_WSXGA(13, "1600*1024"),
/**
1600*1200
*/
    CAPTURE_SIZE_UXGA(14, "1600*1200"),
/**
1920*1200
*/
    CAPTURE_SIZE_WUXGA(15, "1920*1200"),
/**
240*192,ND1
*/
    CAPTURE_SIZE_LTF(16, "240*192,ND1"),
/**
1280*720
*/
    CAPTURE_SIZE_720(17, "1280*720"),
/**
1920*1080
*/
    CAPTURE_SIZE_1080(18, "1920*1080"),
/**
1280*960
*/
    CAPTURE_SIZE_1_3M(19, "1280*960"),
/**
1872*1408,2_5M
*/
    CAPTURE_SIZE_2M(20, "1872*1408,2_5M"),
/**
3744*1408
*/
    CAPTURE_SIZE_5M(21, "3744*1408"),
/**
2048*1536
*/
    CAPTURE_SIZE_3M(22, "2048*1536"),
/**
2432*2050
*/
    CAPTURE_SIZE_5_0M(23, "2432*2050"),
/**
1216*1024
*/
    CPTRUTE_SIZE_1_2M(24, "1216*1024"),
/**
1408*1024
*/
    CPTRUTE_SIZE_1408_1024(25, "1408*1024"),
/**
3296*2472
*/
    CPTRUTE_SIZE_8M(26, "3296*2472"),
/**
2560*1920(5_1M)
*/
    CPTRUTE_SIZE_2560_1920(27, "2560*1920(5_1M)"),
/**
960*576(PAL) 960*480(NTSC)
*/
    CAPTURE_SIZE_960H(28, "960*576(PAL) 960*480(NTSC)"),
/**
960*720
*/
    CAPTURE_SIZE_960_720(29, "960*720"),
/**
640*360
*/
    CAPTURE_SIZE_NHD(30, "640*360"),
/**
320*180
*/
    CAPTURE_SIZE_QNHD(31, "320*180"),
/**
160*90
*/
    CAPTURE_SIZE_QQNHD(32, "160*90"),
/**
960*540
*/
    CAPTURE_SIZE_960_540(33, "960*540"),
/**
640*352
*/
    CAPTURE_SIZE_640_352(34, "640*352"),
/**
640*400
*/
    CAPTURE_SIZE_640_400(35, "640*400"),
/**
320*192
*/
    CAPTURE_SIZE_320_192(36, "320*192"),
/**
320*176
*/
    CAPTURE_SIZE_320_176(37, "320*176"),
/**
800*600
*/
    CAPTURE_SIZE_SVGA1(38, "800*600"),
/**
2560*1440
*/
    CAPTURE_SIZE_2560_1440(39, "2560*1440"),
/**
2304*1296
*/
    CAPTURE_SIZE_2304_1296(40, "2304*1296"),
/**
2592*1520
*/
    CAPTURE_SIZE_2592_1520(41, "2592*1520"),
/**
4000*3000
*/
    CAPTURE_SIZE_4000_3000(42, "4000*3000"),
/**
2880*2880
*/
    CAPTURE_SIZE_2880_2880(43, "2880*2880"),
/**
2880*2160
*/
    CAPTURE_SIZE_2880_2160(44, "2880*2160"),
/**
2688*1520
*/
    CAPTURE_SIZE_2688_1520(45, "2688*1520"),
/**
2592*1944
*/
    CAPTURE_SIZE_2592_1944(46, "2592*1944"),
/**
3072*1728
*/
    CAPTURE_SIZE_3072_1728(47, "3072*1728"),
/**
3072*2048
*/
    CAPTURE_SIZE_3072_2048(48, "3072*2048"),
/**
3840*2160
*/
    CAPTURE_SIZE_3840_2160(49, "3840*2160"),
/**

*/
    CAPTURE_SIZE_NR(25, "");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

CAPTURE_SIZE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (CAPTURE_SIZE enumType : CAPTURE_SIZE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (CAPTURE_SIZE enumType : CAPTURE_SIZE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static CAPTURE_SIZE getEnum(int value) {
        for (CAPTURE_SIZE e : CAPTURE_SIZE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return CAPTURE_SIZE.CAPTURE_SIZE_D1;
    }
}

