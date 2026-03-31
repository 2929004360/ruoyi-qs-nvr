package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.NET_TIME_EX;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_FACERECOGNITION(目标识别)对应的数据块描述信息,简化版本
 * @date 2022/05/23
 */
public class DEV_EVENT_FACERECOGNITION_INFO_V1 extends NetSDKLib.SdkStructure {
    public int              nChannelID;                           // 通道号
    public byte[]           szName = new byte[128];               // 事件名称
    public int              nEventID;                             // 事件ID
    public NET_TIME_EX      UTC;                                  // 事件发生的时间
    public NET_MSG_OBJECT   stuObject;                            // 检测到的物体
    public int              nCandidateNum;                        // 当前人脸匹配到的候选对象数量
    public Pointer          stuCandidates;                        //当前人脸匹配到的候选对象信息,参考CANDIDATE_INFOEX数组
    public byte             bEventAction;                         // 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
    public byte             byImageIndex;                         // 图片的序号,同一时间内(精确到秒)可能有多张图片,从0开始
    public byte[]           byReserved1 = new byte[2];            // 对齐
    public int              bGlobalScenePic;                      // 全景图是否存在, 类型为BOOL, 取值为0或者1
    public NET_PIC_INFO     stuGlobalScenePicInfo;                // 全景图片信息
    public byte[]           szSnapDevAddress = new byte[NetSDKLib.MAX_PATH]; // 抓拍当前人脸的设备地址,如：滨康路37号
    public int              nOccurrenceCount;                     // 事件触发累计次数， 类型为unsigned int
    public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;            // 智能事件公共信息
    public NET_FACE_DATA    stuFaceData;                          // 人脸数据
    public byte[]           szUID = new byte[NetSDKLib.NET_COMMON_STRING_32]; // 抓拍人员写入数据库的唯一标识符
    public NET_FEATURE_VECTOR stuFeatureVector;                   // 特征值信息
    public byte[]           szFeatureVersion = new byte[32];      // 特征值算法版本
    public int              emFaceDetectStatus;                   // 人脸在摄像机画面中的状态,详见EM_FACE_DETECT_STATUS
    public byte[]           szSourceID = new byte[32];            // 事件关联ID,同一个物体或图片生成多个事件时SourceID相同
    public NET_PASSERBY_INFO stuPasserbyInfo;                     // 路人库信息
    public int              nStayTime;                            // 路人逗留时间 单位：秒
    public byte[]           bReserved = new byte[432];            // 保留字节,留待扩展.
    public NET_GPS_INFO     stuGPSInfo;                           // GPS信息
    public byte[]           szSerialUUID = new byte[22];          // 级联物体ID唯一标识
	       																			// 格式如下：前2位%d%d:01-视频片段,02-图片,03-文件,99-其他;
																					// 中间14位YYYYMMDDhhmmss:年月日时分秒;后5位%u%u%u%u%u：物体ID，如00001
    public byte[]           byReserved = new byte[2];             // 对齐
    public NET_CUSTOM_PROJECTS_INFO stuCustomProjects;            // 项目信息
    public int              bIsDuplicateRemove;                   // 智慧零售，是否符合去重策略（TRUE：符合 FALSE：不符合）
    public byte[]           byReserved2 = new byte[4];            // 字节对齐
    public NET_IMAGE_INFO_EX2[] stuImageInfo = (NET_IMAGE_INFO_EX2[])new NET_IMAGE_INFO_EX2().toArray(32); // 图片信息数组
    public int              nImageInfoNum;                        // 图片信息个数
    public NET_MSG_OBJECT_SUPPLEMENT stuObjectSupplement;         // 检测到的物体补充字段
    public int              nMode;                                //0-普通  1-开启陌生人模式
    public	SCENE_IMAGE_INFO stuThumImageInfo = new SCENE_IMAGE_INFO(); //大图（全景图）的缩略图信息
    public	SCENE_IMAGE_INFO stuHumanImageInfo = new SCENE_IMAGE_INFO(); //人体图片信息
    public	byte[]           szVideoPath = new byte[256];          //违章关联视频FTP上传路径
    public	byte[]           byReserved3 = new byte[316];          //保留字节
}

