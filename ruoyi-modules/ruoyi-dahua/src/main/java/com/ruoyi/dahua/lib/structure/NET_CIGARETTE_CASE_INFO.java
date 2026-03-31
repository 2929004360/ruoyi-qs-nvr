package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  销售香烟信息 
* @date 2022/12/14 14:33:00
*/
public class NET_CIGARETTE_CASE_INFO extends NetSDKLib.SdkStructure {
/** 
销售烟盒种类,目前支持的类型如下
0	"GuiYan-Yinghuangjingpin"	贵烟(硬黄精品)
1	"HuangGuoShu-Lanjiapin"	黄果树(蓝佳品)
2	"GuiYan-Yinggaozun"	贵烟(硬高遵)
3	"GuiYan-Xi"	贵烟(喜)
4	"YunYan-Ruanzhenpin"	云烟(软珍品）
5	"YunYan-Zi"	云烟(紫）
6	"GuiYan-Fu"	贵烟(福)
7	"GuiYan-Cui"	贵烟(萃)
8	"GuiYan-Jinbaihe"	贵烟(金百合)
9	"YuXi-Ruan"	玉溪(软)
10	"GuiYan-Kuayue"	贵烟(跨越)
11	"ZhongHua-Ying"	中华(硬)
12	"HuangHeLou-Ruanlan"	黄鹤楼(软蓝)
13	"GuiYan-Ruangaozun"	贵烟(软高遵)
14	"GuiYan-Guojiuxiang30"	贵烟(国酒香30)
15	"GuiYan-Hongzhongzhi"	贵烟(红中支)
16	"YunYan-Ruandazhongjiu"	云烟(软大重九)
17	"YunYan-Zhongzhijinyaodai"	云烟(中支金腰带)
18	"HuangHeLou-Tianxiaminglou"	黄鹤楼(天下名楼)
19	"GuiYan-Xizhiguojiuxiang30"	贵烟(细支国酒香30)
20	"NanJing-Xuanhemen"	南京(炫赫门)
21	"ZhongHua-Ruan"	中华(软)
22	"HuangGuoShu-Changzhenghongxingzhaoyao"	黄果树(长征红星照耀)
23	"FuRongWang-Ying"	芙蓉王(硬)
24	"TianZi-Jin"	天子(金)
25	"BaiSha-Hetianxia"	白沙(和天下)
26	"NanJing-Shierchaibohe"	南京(十二钗薄荷)
27	"GuiYan-Shengxiao"	贵烟(生肖)
28	"TianZi-ZhongZhi"	天子(中支)
29	"YunYan-Ruanyinxiangyanzhuang"	云烟(软印象烟庄)
30	"ZhongHua-Shuangzhongzhi"	中华(双中支)
31	"GuiYan-Xingzhe"	贵烟(行者)
32	"GuiYan-Guizhongzhi"	贵烟(贵中支)
33	"YuXi-Zhongzhijingjie"	玉溪(中支境界)
34	"ZhongHua-Jinzhongzhi"	中华(金中支)
35	"YunYan-Heijingangyinxiang"	云烟(黑金刚印象)
36	"YunYan-Xiaoxiongmaojiayuan"	云烟(小熊猫家园)
37	"GuiYan-Fuzhongzhi"	贵烟(福中支)
38	"GuiYan-Xigui"	贵烟(喜贵)
39	"JiaoZi-Kuanzhaihaoyunxizhi"	娇子(宽窄好运细支)
40	"GuiYan-Xizhixingzhe"	贵烟(细支行者)
41	"BaiSha-Ruanhetianxia"	白沙(软和天下)
42	"GuiYan-Yingxiaoguojiuxiang"	贵烟(硬小国酒香)
43	"YunYan-Xizhizhenpin"	云烟(细支珍品)
44	"YunYan-Xizhiyunlong"	云烟(细支云龙)
45	"ChangBaiShan-777"	长白山(777)
46	“LiQun-Xinbon”	利群(新版)
47	“HuangHeLou-Yingxiagurouqing”	黄鹤楼(硬峡谷柔情)
48	“Huangshan-Xinzhiwanyan”	黄山(新制皖烟)
49	“QiPiLang-Hong”	七匹狼(红)
50	“BaiSha-Yingxizhihetianxia”	白沙(硬细支和天下)
*/
    public			byte[]         szCigaretteType = new byte[128];
/**
 销售烟盒数量，单位为盒
*/
    public			int            CigaretteNum;
/** 
预留字节
*/
    public			byte[]         szReserved = new byte[1020];

public NET_CIGARETTE_CASE_INFO(){
}
}

