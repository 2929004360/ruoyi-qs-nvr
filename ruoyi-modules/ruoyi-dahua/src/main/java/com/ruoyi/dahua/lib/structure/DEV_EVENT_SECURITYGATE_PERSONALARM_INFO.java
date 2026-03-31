package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 10:06 事件类型 EVENT_IVS_SECURITYGATE_PERSONALARM
 *        （安全门人员报警事件）对应的数据块描述信息
 */
public class DEV_EVENT_SECURITYGATE_PERSONALARM_INFO extends NetSDKLib.SdkStructure {
    public int              nChannelID;                           // 通道号
    public int              nAction;                              // 0:脉冲 1:开始 2:停止
    public byte[]           szName = new byte[128];               // 事件名称
    public double           PTS;                                  // 时间戳(单位是毫秒)
    public NET_TIME_EX      UTC = new NET_TIME_EX();              // 时间
    public int              nEventID;                             // 事件ID
	/**
	 * 人员通过方向枚举,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_SECURITYGATE_PERSON_PASS_DIRECTION }
	 */
    public int              emDirection;                          // 人员通过方向
	/**
	 * 报警级别,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_SECURITYGATE_ALARM_LEVEL }
	 */
    public int              emAlarmLevel;                         // 报警级别
    public int              nChannelIn;                           // 关联进入通道
    public int              ChannelOut;                           // 关联离开通道
    public NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO stuImageInfo = new NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO(); // 人脸图片信息
    public int              nAlarmPositionNum;                    // 报警位置个数
	/**
	 * 报警位置,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_SECURITYGATE_ALARM_POSITION }
	 */
    public int[]            emAlarmPosition = new int[18];        // 报警位置
    public NET_SECURITYGATE_ALARM_FACEIMAGE_INFO stuFaceImageInfo = new NET_SECURITYGATE_ALARM_FACEIMAGE_INFO(); // 人脸小图
    public NET_SECURITYGATE_ALARM_FACE_INFO stuSecurityGateFaceInfo = new NET_SECURITYGATE_ALARM_FACE_INFO(); // 人脸信息
    public byte[]           byReserved = new byte[4];             // 保留字段
    public int              nCandidateNum;                        // 候选人个数, 最大支持50
    public NET_SECURITYGATE_CANDIDATE[] stuCandidates = (NET_SECURITYGATE_CANDIDATE[]) new NET_SECURITYGATE_CANDIDATE().toArray(50); // 候选人信息
    public int              nDoorRegion;                          // 门体分区 0表示6分区 1表示12分区 2表示18分区
    public int              nAlaramPositionNum;                   // 报警位置个数
    public int[]            nAlaramPosition = new int[18];        // 报警位置数组 当nDoorRegion为0时，数组个数最多6个 0表示1区，1表示2区...5表示6区
												// 当nDoorRegion为1时，数组个数最多12个 0表示左1,1表示右1,2表示左2,3表示右2...10表示左6,11表示右6
												// 当nDoorRegion为2时，数组个数最多18个
												// 0表示左1,1表示中1,2表示右2,3表示左1,4表示中1,5表示右2...15表示左6,16表示中6,17表示右6
    public int              nPositionNumEx;                       // 报警位置个数拓展
    public int[]            emAlarmPositionEx = new int[128];     // 报警位置拓展 ,参考枚举EM_SECURITYGATE_ALARM_POSITION
    public int              nAlaramPositionNumEx;                 // 报警位置个数拓展
    public int[]            nAlaramPositionEx = new int[128];     // 报警位置数组拓展 门体分区相关
    public int              nAlarmInfoNum;                        // 分类报警信息个数
    public NET_SECURITYGATE_ALARM_INFO[] stuAlarmInfo = new NET_SECURITYGATE_ALARM_INFO[32]; // 分类报警信息
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND(); //事件公共扩展字段结构体
    public int              nPartitionRow;                        // DoorRegion门体分区对应的区位分布的行数
    public int              nPartitionColumn;                     // DoorRegion门体分区对应的区位分布的列数
    /**
     * 注册库人员图片,参见结构体定义 {@link NET_SECURITYGATE_ALARM_CANDIDATE_IMAGES_INFO}
    */
    public NET_SECURITYGATE_ALARM_CANDIDATE_IMAGES_INFO[] stuCandidateImages = new NET_SECURITYGATE_ALARM_CANDIDATE_IMAGES_INFO[16];
    /**
     * 注册库人员图片个数
    */
    public int              nCandidateImagesNum;
    /**
     * 分类报警信息个数
    */
    public int              nAlarmInfoExNum;
    /**
     * 分类报警信息,参见结构体定义 {@link NET_SECURITYGATE_ALARM_INFO}
    */
    public NET_SECURITYGATE_ALARM_INFO[] stuAlarmInfoEx = new NET_SECURITYGATE_ALARM_INFO[128];
    public byte[]           szResvered = new byte[500];           // 保留字节

	public DEV_EVENT_SECURITYGATE_PERSONALARM_INFO() {
		for (int i = 0; i < stuAlarmInfo.length; i++) {
			stuAlarmInfo[i] = new NET_SECURITYGATE_ALARM_INFO();
		}
	}
}

