package com.ruoyi.dahua.lib.enumeration;

/**
 * 分辨率枚举,细分到具体分辨率
*/
public enum EM_CAPTURE_SIZE_EX
{
    /**
     * D1   704*576(PAL)  704*480(NTSC),兼容WWxHH,下同
    */
    EM_CAPTURE_SIZE_EX_D1(0, "D1   704*576(PAL)  704*480(NTSC),兼容WWxHH,下同"),
    /**
     * 704*576(PAL)
    */
    EM_CAPTURE_SIZE_EX_D1_PAL(1, "704*576(PAL)"),
    /**
     * 704*480(NTSC)
    */
    EM_CAPTURE_SIZE_EX_D1_NTSC(2, "704*480(NTSC)"),
    /**
     * HD1   352*576(PAL)  352*480(NTSC)
    */
    EM_CAPTURE_SIZE_EX_HD1(3, "HD1   352*576(PAL)  352*480(NTSC)"),
    /**
     * 352*576(PAL)
    */
    EM_CAPTURE_SIZE_EX_HD1_PAL(4, "352*576(PAL)"),
    /**
     * 352*480(NTSC)
    */
    EM_CAPTURE_SIZE_EX_HD1_NTSC(5, "352*480(NTSC)"),
    /**
     * BCIF   704*288(PAL)  704*240(NTSC)
    */
    EM_CAPTURE_SIZE_EX_BCIF(6, "BCIF   704*288(PAL)  704*240(NTSC)"),
    /**
     * 704*288(PAL)
    */
    EM_CAPTURE_SIZE_EX_BCIF_PAL(7, "704*288(PAL)"),
    /**
     * 704*240(NTSC)
    */
    EM_CAPTURE_SIZE_EX_BCIF_NTSC(8, "704*240(NTSC)"),
    /**
     * CIF    352*288(PAL)  352*240(NTSC)
    */
    EM_CAPTURE_SIZE_EX_CIF(9, "CIF    352*288(PAL)  352*240(NTSC)"),
    /**
     * 352*288(PAL)
    */
    EM_CAPTURE_SIZE_EX_CIF_PAL(10, "352*288(PAL)"),
    /**
     * 352*240(NTSC)
    */
    EM_CAPTURE_SIZE_EX_CIF_NTSC(11, "352*240(NTSC)"),
    /**
     * QCIF    176*144(PAL)  176*120(NTSC)
    */
    EM_CAPTURE_SIZE_EX_QCIF(12, "QCIF    176*144(PAL)  176*120(NTSC)"),
    /**
     * 176*144(PAL)
    */
    EM_CAPTURE_SIZE_EX_QCIF_PAL(13, "176*144(PAL)"),
    /**
     * 176*120(NTSC)
    */
    EM_CAPTURE_SIZE_EX_QCIF_NTSC(14, "176*120(NTSC)"),
    /**
     * 640*480
    */
    EM_CAPTURE_SIZE_EX_VGA(15, "640*480"),
    /**
     * 320*240
    */
    EM_CAPTURE_SIZE_EX_QVGA(16, "320*240"),
    /**
     * 480*480
    */
    EM_CAPTURE_SIZE_EX_SVCD(17, "480*480"),
    /**
     * 160*128
    */
    EM_CAPTURE_SIZE_EX_QQVGA(18, "160*128"),
    /**
     * 800*592
    */
    EM_CAPTURE_SIZE_EX_SVGA(19, "800*592"),
    /**
     * 1024*768
    */
    EM_CAPTURE_SIZE_EX_XVGA(20, "1024*768"),
    /**
     * 1280*800
    */
    EM_CAPTURE_SIZE_EX_WXGA(21, "1280*800"),
    /**
     * 1280*1024
    */
    EM_CAPTURE_SIZE_EX_SXGA(22, "1280*1024"),
    /**
     * 1600*1024
    */
    EM_CAPTURE_SIZE_EX_WSXGA(23, "1600*1024"),
    /**
     * 1600*1200
    */
    EM_CAPTURE_SIZE_EX_UXGA(24, "1600*1200"),
    /**
     * 1920*1200
    */
    EM_CAPTURE_SIZE_EX_WUXGA(25, "1920*1200"),
    /**
     * 240*192,ND1
    */
    EM_CAPTURE_SIZE_EX_LTF(26, "240*192,ND1"),
    /**
     * 1280*720
    */
    EM_CAPTURE_SIZE_EX_720(27, "1280*720"),
    /**
     * 1920*1080
    */
    EM_CAPTURE_SIZE_EX_1080(28, "1920*1080"),
    /**
     * 1280*960
    */
    EM_CAPTURE_SIZE_EX_1_3M(29, "1280*960"),
    /**
     * 1872*1408,2_5M
    */
    EM_CAPTURE_SIZE_EX_2M(30, "1872*1408,2_5M"),
    /**
     * 3744*1408
    */
    EM_CAPTURE_SIZE_EX_5M(31, "3744*1408"),
    /**
     * 2048*1536
    */
    EM_CAPTURE_SIZE_EX_3M(32, "2048*1536"),
    /**
     * 2432*2050
    */
    EM_CAPTURE_SIZE_EX_5_0M(33, "2432*2050"),
    /**
     * 1216*1024
    */
    EM_CAPTURE_SIZE_EX_1_2M(34, "1216*1024"),
    /**
     * 1408*1024
    */
    EM_CAPTURE_SIZE_EX_1408_1024(35, "1408*1024"),
    /**
     * 3296*2472
    */
    EM_CAPTURE_SIZE_EX_8M(36, "3296*2472"),
    /**
     * 2560*1920(5_1M)
    */
    EM_CAPTURE_SIZE_EX_2560_1920(37, "2560*1920(5_1M)"),
    /**
     * 960H    960*576(PAL) 960*480(NTSC)
    */
    EM_CAPTURE_SIZE_EX_960H(38, "960H    960*576(PAL) 960*480(NTSC)"),
    /**
     * 960*576(PAL)
    */
    EM_CAPTURE_SIZE_EX_960H_PAL(39, "960*576(PAL)"),
    /**
     * 960*480(NTSC)
    */
    EM_CAPTURE_SIZE_EX_960H_NTSC(40, "960*480(NTSC)"),
    /**
     * 960*720
    */
    EM_CAPTURE_SIZE_EX_960_720(41, "960*720"),
    /**
     * 640*360
    */
    EM_CAPTURE_SIZE_EX_NHD(42, "640*360"),
    /**
     * 320*180
    */
    EM_CAPTURE_SIZE_EX_QNHD(43, "320*180"),
    /**
     * 160*90
    */
    EM_CAPTURE_SIZE_EX_QQNHD(44, "160*90"),
    /**
     * 960*540
    */
    EM_CAPTURE_SIZE_EX_960_540(45, "960*540"),
    /**
     * 640*352
    */
    EM_CAPTURE_SIZE_EX_640_352(46, "640*352"),
    /**
     * 640*400
    */
    EM_CAPTURE_SIZE_EX_640_400(47, "640*400"),
    /**
     * 320*192
    */
    EM_CAPTURE_SIZE_EX_320_192(48, "320*192"),
    /**
     * 320*176
    */
    EM_CAPTURE_SIZE_EX_320_176(49, "320*176"),
    /**
     * 800*600
    */
    EM_CAPTURE_SIZE_EX_SVGA1(50, "800*600"),
    /**
     * 2560*1440
    */
    EM_CAPTURE_SIZE_EX_2560_1440(51, "2560*1440"),
    /**
     * 2304*1296
    */
    EM_CAPTURE_SIZE_EX_2304_1296(52, "2304*1296"),
    /**
     * 2592*1520
    */
    EM_CAPTURE_SIZE_EX_2592_1520(53, "2592*1520"),
    /**
     * 4000*3000
    */
    EM_CAPTURE_SIZE_EX_4000_3000(54, "4000*3000"),
    /**
     * 2880*2880
    */
    EM_CAPTURE_SIZE_EX_2880_2880(55, "2880*2880"),
    /**
     * 2880*2160
    */
    EM_CAPTURE_SIZE_EX_2880_2160(56, "2880*2160"),
    /**
     * 2688*1520
    */
    EM_CAPTURE_SIZE_EX_2688_1520(57, "2688*1520"),
    /**
     * 2592*1944
    */
    EM_CAPTURE_SIZE_EX_2592_1944(58, "2592*1944"),
    /**
     * 3072*1728
    */
    EM_CAPTURE_SIZE_EX_3072_1728(59, "3072*1728"),
    /**
     * 3072*2048
    */
    EM_CAPTURE_SIZE_EX_3072_2048(60, "3072*2048"),
    /**
     * 3840*2160
    */
    EM_CAPTURE_SIZE_EX_3840_2160(61, "3840*2160"),
    EM_CAPTURE_SIZE_EX_NR(255, ""),
    /**
     * 未知
    */
    EM_CAPTURE_SIZE_EX_UNKNOWN(-1, "未知");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CAPTURE_SIZE_EX(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CAPTURE_SIZE_EX enumType : EM_CAPTURE_SIZE_EX.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CAPTURE_SIZE_EX enumType : EM_CAPTURE_SIZE_EX.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

