package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

import java.io.UnsupportedEncodingException;

public class GateModule {
	// 查询句柄
	private static LLong m_lFindHandle = new LLong(0);
	
    /**
     * 订阅实时上传智能分析数据
     * @return 
     */
    public static LLong realLoadPic(int ChannelId, fAnalyzerDataCallBack m_AnalyzerDataCB) {
    	/**
		 * 说明：
		 * 	通道数可以在有登录是返回的信息 m_stDeviceInfo.byChanNum 获取
		 *  下列仅订阅了0通道的智能事件.
		 */
		int bNeedPicture = 1; // 是否需要图片

		LLong m_hAttachHandle = LoginModule.netsdk.CLIENT_RealLoadPictureEx(LoginModule.m_hLoginHandle, ChannelId,  NetSDKLib.EVENT_IVS_ALL, 
				bNeedPicture , m_AnalyzerDataCB , null , null);
		if( m_hAttachHandle.longValue() != 0  ) {
			System.out.println("CLIENT_RealLoadPictureEx Success  ChannelId : \n" + ChannelId);
		} else {
			System.err.println("CLIENT_RealLoadPictureEx Failed!" + ToolKits.getErrorCodePrint());
			return null;
		}
		
		return m_hAttachHandle;
    }
    
    /**
     * 停止上传智能分析数据－图片
     */
    public static void stopRealLoadPic(LLong m_hAttachHandle) {
        if (0 != m_hAttachHandle.longValue()) {
        	LoginModule.netsdk.CLIENT_StopLoadPic(m_hAttachHandle);
            System.out.println("Stop detach IVS event");
            m_hAttachHandle.setValue(0);
        }
    }
    
    //////////////////////////////////////  卡信息的增、删、改、清空  ////////////////////////////////////////
    
	/**
	 * 添加卡
	 * @param cardNo  	  卡号
	 * @param userId  	  用户ID
	 * @param cardName   卡名
	 * @param cardPwd    卡密码
	 * @param cardStatus 卡状态
	 * @param cardType   卡类型
	 * @param useTimes   使用次数
	 * @param isFirstEnter  是否首卡, 1-true, 0-false
	 * @param isValid  		是否有效, 1-true, 0-false
	 * @param startValidTime  有效开始时间
	 * @param endValidTime    有效结束时间
	 * @return true:成功   false:失败
	 */
	public static boolean insertCard(String cardNo, String userId, String cardName, String cardPwd,
								     int cardStatus, int cardType, int useTimes, int isFirstEnter,
								     int isValid, String startValidTime, String endValidTime) {
		/**
		 * 门禁卡记录集信息 
		 */
		NET_RECORDSET_ACCESS_CTL_CARD accessCardInfo = new NET_RECORDSET_ACCESS_CTL_CARD();
		
		// 卡号
		System.arraycopy(cardNo.getBytes(), 0, accessCardInfo.szCardNo, 0, cardNo.getBytes().length);
		
		// 用户ID
		System.arraycopy(userId.getBytes(), 0, accessCardInfo.szUserID, 0, userId.getBytes().length);
		
		// 卡名(设备上显示的姓名)
		try {
			System.arraycopy(cardName.getBytes("GBK"), 0, accessCardInfo.szCardName, 0, cardName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// 卡密码
		System.arraycopy(cardPwd.getBytes(), 0, accessCardInfo.szPsw, 0, cardPwd.getBytes().length);
		
		//-- 设置开门权限
		accessCardInfo.nDoorNum = 2; 
		accessCardInfo.sznDoors[0] = 0;
		accessCardInfo.sznDoors[1] = 1;
		accessCardInfo.nTimeSectionNum = 2; 	  // 与门数对应
		accessCardInfo.sznTimeSectionNo[0] = 255; // 表示第一个门全天有效
		accessCardInfo.sznTimeSectionNo[1] = 255; // 表示第二个门全天有效
		
		// 卡状态
		accessCardInfo.emStatus = cardStatus;
		
		// 卡类型
		accessCardInfo.emType = cardType;

		// 使用次数
		accessCardInfo.nUserTime = useTimes;
		
		// 是否首卡
		accessCardInfo.bFirstEnter = isFirstEnter;
		
		// 是否有效
		accessCardInfo.bIsValid = isValid;
		
		// 有效开始时间
		String[] startTimes = startValidTime.split(" ");
		accessCardInfo.stuValidStartTime.dwYear = Integer.parseInt(startTimes[0].split("-")[0]);
		accessCardInfo.stuValidStartTime.dwMonth = Integer.parseInt(startTimes[0].split("-")[1]);
		accessCardInfo.stuValidStartTime.dwDay = Integer.parseInt(startTimes[0].split("-")[2]);
		accessCardInfo.stuValidStartTime.dwHour = Integer.parseInt(startTimes[1].split(":")[0]);
		accessCardInfo.stuValidStartTime.dwMinute = Integer.parseInt(startTimes[1].split(":")[1]);
		accessCardInfo.stuValidStartTime.dwSecond = Integer.parseInt(startTimes[01].split(":")[2]);
	
		// 有效结束时间
		String[] endTimes = endValidTime.split(" ");
		accessCardInfo.stuValidEndTime.dwYear = Integer.parseInt(endTimes[0].split("-")[0]);
		accessCardInfo.stuValidEndTime.dwMonth = Integer.parseInt(endTimes[0].split("-")[1]);
		accessCardInfo.stuValidEndTime.dwDay = Integer.parseInt(endTimes[0].split("-")[2]);
		accessCardInfo.stuValidEndTime.dwHour = Integer.parseInt(endTimes[1].split(":")[0]);
		accessCardInfo.stuValidEndTime.dwMinute = Integer.parseInt(endTimes[1].split(":")[1]);
		accessCardInfo.stuValidEndTime.dwSecond = Integer.parseInt(endTimes[1].split(":")[2]);
		
		/**
		 * 记录集操作
		 */
		NET_CTRL_RECORDSET_INSERT_PARAM insert = new NET_CTRL_RECORDSET_INSERT_PARAM();
		insert.stuCtrlRecordSetInfo.emType = EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;   // 记录集类型
		insert.stuCtrlRecordSetInfo.pBuf = accessCardInfo.getPointer();
		
		accessCardInfo.write();
		insert.write();
		boolean bRet = LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, 
								   CtrlType.CTRLTYPE_CTRL_RECORDSET_INSERT, insert.getPointer(), 5000);
		insert.read();
		accessCardInfo.read();
		
		if(!bRet) {
			System.err.println("添加卡信息失败." + ToolKits.getErrorCodePrint());
			return false;
		} else {
			System.out.println("添加卡信息成功,卡信息记录集编号 : " + insert.stuCtrlRecordSetResult.nRecNo);
		}
		
		return true;
	}
	
	/**
	 * 修改卡信息
	 * @param recordNo   记录集编号
	 * @param cardNo  	  卡号
	 * @param userId  	  用户ID
	 * @param cardName   卡名
	 * @param cardPwd    卡密码
	 * @param cardStatus 卡状态
	 * @param cardType   卡类型
	 * @param useTimes   使用次数
	 * @param isFirstEnter  是否首卡, 1-true, 0-false
	 * @param isValid  		是否有效, 1-true, 0-false
	 * @param startValidTime  有效开始时间
	 * @param endValidTime    有效结束时间
	 * @return true:成功   false:失败
	 */
	public static boolean modifyCard(int recordNo, String cardNo, String userId, String cardName, String cardPwd,
								     int cardStatus, int cardType, int useTimes, int isFirstEnter,
								     int isValid, String startValidTime, String endValidTime) {
		/**
		 * 门禁卡记录集信息 
		 */
		NET_RECORDSET_ACCESS_CTL_CARD accessCardInfo = new NET_RECORDSET_ACCESS_CTL_CARD();
		// 记录集编号， 修改、删除卡信息必须填写
		accessCardInfo.nRecNo = recordNo;  
		
		// 卡号
		System.arraycopy(cardNo.getBytes(), 0, accessCardInfo.szCardNo, 0, cardNo.getBytes().length);
		
		// 用户ID
		System.arraycopy(userId.getBytes(), 0, accessCardInfo.szUserID, 0, userId.getBytes().length);
		
		// 卡名(设备上显示的姓名)
		try {
			System.arraycopy(cardName.getBytes("GBK"), 0, accessCardInfo.szCardName, 0, cardName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		// 卡密码
		System.arraycopy(cardPwd.getBytes(), 0, accessCardInfo.szPsw, 0, cardPwd.getBytes().length);
		
		//-- 设置开门权限
		accessCardInfo.nDoorNum = 2; 
		accessCardInfo.sznDoors[0] = 0;
		accessCardInfo.sznDoors[1] = 1;
		accessCardInfo.nTimeSectionNum = 2; 	  // 与门数对应
		accessCardInfo.sznTimeSectionNo[0] = 255; // 表示第一个门全天有效
		accessCardInfo.sznTimeSectionNo[1] = 255; // 表示第二个门全天有效
		
		// 卡状态
		accessCardInfo.emStatus = cardStatus;
		
		// 卡类型
		accessCardInfo.emType = cardType;

		// 使用次数
		accessCardInfo.nUserTime = useTimes;
		
		// 是否首卡
		accessCardInfo.bFirstEnter = isFirstEnter;
		
		// 是否有效
		accessCardInfo.bIsValid = isValid;
		
		// 有效开始时间
		String[] startTimes = startValidTime.split(" ");
		accessCardInfo.stuValidStartTime.dwYear = Integer.parseInt(startTimes[0].split("-")[0]);
		accessCardInfo.stuValidStartTime.dwMonth = Integer.parseInt(startTimes[0].split("-")[1]);
		accessCardInfo.stuValidStartTime.dwDay = Integer.parseInt(startTimes[0].split("-")[2]);
		accessCardInfo.stuValidStartTime.dwHour = Integer.parseInt(startTimes[1].split(":")[0]);
		accessCardInfo.stuValidStartTime.dwMinute = Integer.parseInt(startTimes[1].split(":")[1]);
		accessCardInfo.stuValidStartTime.dwSecond = Integer.parseInt(startTimes[01].split(":")[2]);
	
		// 有效结束时间
		String[] endTimes = endValidTime.split(" ");
		accessCardInfo.stuValidEndTime.dwYear = Integer.parseInt(endTimes[0].split("-")[0]);
		accessCardInfo.stuValidEndTime.dwMonth = Integer.parseInt(endTimes[0].split("-")[1]);
		accessCardInfo.stuValidEndTime.dwDay = Integer.parseInt(endTimes[0].split("-")[2]);
		accessCardInfo.stuValidEndTime.dwHour = Integer.parseInt(endTimes[1].split(":")[0]);
		accessCardInfo.stuValidEndTime.dwMinute = Integer.parseInt(endTimes[1].split(":")[1]);
		accessCardInfo.stuValidEndTime.dwSecond = Integer.parseInt(endTimes[1].split(":")[2]);
		
		/**
		 * 记录集操作
		 */
		NET_CTRL_RECORDSET_PARAM update = new NET_CTRL_RECORDSET_PARAM();
    	update.emType = EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;    // 记录集信息类型
    	update.pBuf = accessCardInfo.getPointer();
		
    	accessCardInfo.write();
		update.write();
		boolean bRet = LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, 
								   CtrlType.CTRLTYPE_CTRL_RECORDSET_UPDATE, update.getPointer(), 5000);
		update.read();
		accessCardInfo.read();
		
		if(!bRet) {
			System.err.println("修改卡信息失败." + ToolKits.getErrorCodePrint());
			return false;
		} else {
			System.out.println("修改卡信息成功 ");
		}
		
		return true;
	}
	
	/**
	 * 删除卡信息(单个删除)
	 * @param recordNo 记录集编号
	 */
	public static boolean deleteCard(int recordNo) {
    	/**
		 * 记录集操作
		 */
    	NET_CTRL_RECORDSET_PARAM msg = new NET_CTRL_RECORDSET_PARAM();
    	msg.emType = EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;
    	msg.pBuf = new IntByReference(recordNo).getPointer();

    	msg.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, 
    							   CtrlType.CTRLTYPE_CTRL_RECORDSET_REMOVE, msg.getPointer(), 5000);
    	msg.read();
		
    	if(!bRet){
    		System.err.println("删除卡信息失败." + ToolKits.getErrorCodePrint());
    	} else {
    		System.out.println("删除卡信息成功.");
    	}
    	
    	return bRet;
	}
	
   /**
	 * 清除所有卡信息
	 */
	public static boolean clearCard() {
		/**
		 * 记录集操作
		 */
		NET_CTRL_RECORDSET_PARAM msg = new NET_CTRL_RECORDSET_PARAM();
		msg.emType = EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;    // 门禁卡记录集信息类型
    	
		msg.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, 
    					             CtrlType.CTRLTYPE_CTRL_RECORDSET_CLEAR, msg.getPointer(), 5000);
    	msg.read();
    	if(!bRet){
    		System.err.println("清空卡信息失败." + ToolKits.getErrorCodePrint());
    	} else {
    		System.out.println("清空卡信息成功.");
    	}
    	
    	return bRet;
	}
	
	
	/////////////////////////////////  人脸的增、删、改、清空   ///////////////////////////////////////
	
	/**
	 * 添加人脸
	 * @param userId 用户ID
	 * @param memory 图片缓存
	 * @return
	 */
	public static boolean addFaceInfo(String userId, Memory memory) {
    	int emType = EM_FACEINFO_OPREATE_TYPE.EM_FACEINFO_OPREATE_ADD;  // 添加
    	
    	/**
    	 *  入参
    	 */
    	NET_IN_ADD_FACE_INFO stIn = new NET_IN_ADD_FACE_INFO();
    	
    	// 用户ID
    	System.arraycopy(userId.getBytes(), 0, stIn.szUserID, 0, userId.getBytes().length);  
    	
    	// 人脸照片个数
    	stIn.stuFaceInfo.nFacePhoto = 1;  
    	
    	// 每张图片的大小
		stIn.stuFaceInfo.nFacePhotoLen[0] = (int) memory.size();

    	// 人脸照片数据,大小不超过100K, 图片格式为jpg
		stIn.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto = memory; 
    	
    	/**
    	 *  出参
    	 */
    	NET_OUT_ADD_FACE_INFO stOut = new NET_OUT_ADD_FACE_INFO();
    	
    	stIn.write();
    	stOut.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_FaceInfoOpreate(LoginModule.m_hLoginHandle, emType, stIn.getPointer(), stOut.getPointer(), 5000);
    	stIn.read();
    	stOut.read();
    	if(bRet) {
    		System.out.println("添加人脸成功!");
    	} else {
    		System.err.println("添加人脸失败!" + ToolKits.getErrorCodePrint());
    		return false;
    	}
    	
    	return true;
    }
	
	/**
	 * 修改人脸
	 * @param userId 用户ID
	 * @param memory 图片缓存
	 * @return
	 */
	public static boolean modifyFaceInfo(String userId, Memory memory) {
    	int emType = EM_FACEINFO_OPREATE_TYPE.EM_FACEINFO_OPREATE_UPDATE;  // 修改
    	
    	/**
    	 *  入参
    	 */
    	NET_IN_UPDATE_FACE_INFO stIn = new NET_IN_UPDATE_FACE_INFO();
    	
    	// 用户ID
    	System.arraycopy(userId.getBytes(), 0, stIn.szUserID, 0, userId.getBytes().length);  
    	
    	// 人脸照片个数
    	stIn.stuFaceInfo.nFacePhoto = 1;  
    	
    	// 每张图片的大小
		stIn.stuFaceInfo.nFacePhotoLen[0] = (int) memory.size();

    	// 人脸照片数据,大小不超过100K, 图片格式为jpg
		stIn.stuFaceInfo.pszFacePhotoArr[0].pszFacePhoto = memory; 
    	
    	/**
    	 *  出参
    	 */
		NET_OUT_UPDATE_FACE_INFO stOut = new NET_OUT_UPDATE_FACE_INFO();
    	
    	stIn.write();
    	stOut.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_FaceInfoOpreate(LoginModule.m_hLoginHandle, emType, stIn.getPointer(), stOut.getPointer(), 5000);
    	stIn.read();
    	stOut.read();
    	if(bRet) {
    		System.out.println("修改人脸成功!");
    	} else {
    		System.err.println("修改人脸失败!" + ToolKits.getErrorCodePrint());
    		return false;
    	}
    	
    	return true;
    }
    
 	/**
   	 * 删除人脸(单个删除)
   	 * @param userId 用户ID
   	 */
    public static boolean deleteFaceInfo(String userId) {
    	int emType = EM_FACEINFO_OPREATE_TYPE.EM_FACEINFO_OPREATE_REMOVE;
    	
    	/**
    	 * 入参
    	 */
    	NET_IN_REMOVE_FACE_INFO inRemove = new NET_IN_REMOVE_FACE_INFO();
    	
    	// 用户ID
    	System.arraycopy(userId.getBytes(), 0, inRemove.szUserID, 0, userId.getBytes().length);  
    	
    	/**
    	 *  出参
    	 */
    	NET_OUT_REMOVE_FACE_INFO outRemove = new NET_OUT_REMOVE_FACE_INFO();
    	
    	inRemove.write();
    	outRemove.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_FaceInfoOpreate(LoginModule.m_hLoginHandle, emType, inRemove.getPointer(), outRemove.getPointer(), 5000);
    	inRemove.read();
    	outRemove.read();
    	if(bRet) {
    		System.out.println("删除人脸成功!");
    	} else {
    		System.err.println("删除人脸失败!" + ToolKits.getErrorCodePrint());
    	}
    	
    	return bRet;
    }
    
  	/**
  	 * 清除所有人脸
  	 */
    public static boolean clearFaceInfo() {
    	int emType = EM_FACEINFO_OPREATE_TYPE.EM_FACEINFO_OPREATE_CLEAR;  // 清除
    	
    	/**
    	 *  入参
    	 */
    	NET_IN_CLEAR_FACE_INFO stIn = new NET_IN_CLEAR_FACE_INFO();
    	
    	/**
    	 *  出参
    	 */
    	NET_OUT_REMOVE_FACE_INFO stOut = new NET_OUT_REMOVE_FACE_INFO();
    	
    	stIn.write();
    	stOut.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_FaceInfoOpreate(LoginModule.m_hLoginHandle, emType, 
    							stIn.getPointer(), stOut.getPointer(), 5000);
    	stIn.read();
    	stOut.read();
    	if(bRet) {
    		System.out.println("清空人脸成功!");
    	} else {
    		System.err.println("清空人脸失败!" + ToolKits.getErrorCodePrint());
    	}
    	
    	return bRet;
    }

    /**
     * 查询卡信息，获取查询句柄
     * @param cardNo 卡号，为空，查询所有的
     * @return
     */
    public static boolean findCard(String cardNo) {
    	/**
		 * 查询条件
		 */
		FIND_RECORD_ACCESSCTLCARD_CONDITION findCondition = new FIND_RECORD_ACCESSCTLCARD_CONDITION();

		if(!cardNo.isEmpty()) {
			// 卡号查询条件是否有效
			findCondition.abCardNo = 1;   
			
			// 卡号
			System.arraycopy(cardNo.getBytes(), 0, findCondition.szCardNo, 0, cardNo.getBytes().length);
		}
		
		/**
		 * CLIENT_FindRecord 接口入参
		 */
		NET_IN_FIND_RECORD_PARAM stIn = new NET_IN_FIND_RECORD_PARAM();
		stIn.emType = EM_NET_RECORD_TYPE.NET_RECORD_ACCESSCTLCARD;
		if(!cardNo.isEmpty()) {
			stIn.pQueryCondition = findCondition.getPointer();
		}
		
		/**
		 * CLIENT_FindRecord 接口出参
		 */
		NET_OUT_FIND_RECORD_PARAM stOut = new NET_OUT_FIND_RECORD_PARAM();

		findCondition.write();
		if(!LoginModule.netsdk.CLIENT_FindRecord(LoginModule.m_hLoginHandle, stIn, stOut, 5000)) {
			System.err.println("没查到卡信息!" + ToolKits.getErrorCodePrint());
			return false;
		}
		findCondition.read();
		
		m_lFindHandle = stOut.lFindeHandle;
		return true;
    }
    
    /**
     * 查询具体的卡信息
     * @param nFindCount 每次查询的个数
     * @return 返回具体的查询信息
     */
    public static NET_RECORDSET_ACCESS_CTL_CARD[] findNextCard(int nFindCount) {
    	// 用于申请内存
		NET_RECORDSET_ACCESS_CTL_CARD[] pstRecord = new NET_RECORDSET_ACCESS_CTL_CARD[nFindCount];
		for(int i = 0; i < nFindCount; i++) {
			pstRecord[i] = new NET_RECORDSET_ACCESS_CTL_CARD();
		}
		
		/**
		 *  CLIENT_FindNextRecord 接口入参
		 */
		NET_IN_FIND_NEXT_RECORD_PARAM stNextIn = new NET_IN_FIND_NEXT_RECORD_PARAM();
		stNextIn.lFindeHandle = m_lFindHandle;
		stNextIn.nFileCount = nFindCount;  //想查询的记录条数
		
		/**
		 *  CLIENT_FindNextRecord 接口出参
		 */
		NET_OUT_FIND_NEXT_RECORD_PARAM stNextOut = new NET_OUT_FIND_NEXT_RECORD_PARAM();
		stNextOut.nMaxRecordNum = nFindCount;
		stNextOut.pRecordList = new Memory(pstRecord[0].dwSize * nFindCount);  // 申请内存
		stNextOut.pRecordList.clear(pstRecord[0].dwSize * nFindCount);	
		
		ToolKits.SetStructArrToPointerData(pstRecord, stNextOut.pRecordList); // 将数组内存拷贝给指针
			
		if(LoginModule.netsdk.CLIENT_FindNextRecord(stNextIn, stNextOut, 5000)) {												
			if(stNextOut.nRetRecordNum == 0) {
				return null;
			}

			ToolKits.GetPointerDataToStructArr(stNextOut.pRecordList, pstRecord);  // 获取卡信息
					
			// 获取有用的信息
			NET_RECORDSET_ACCESS_CTL_CARD[] pstRecordEx = new NET_RECORDSET_ACCESS_CTL_CARD[stNextOut.nRetRecordNum];
			for(int i = 0; i < stNextOut.nRetRecordNum; i++) {
				pstRecordEx[i] = new NET_RECORDSET_ACCESS_CTL_CARD();
				pstRecordEx[i] = pstRecord[i];
			}
			
			return pstRecordEx;			
		} 
		
		return null;
    }
    
    /**
     * 关闭查询
     */
    public static void findCardClose() {
    	if(m_lFindHandle.longValue() != 0) {
    		LoginModule.netsdk.CLIENT_FindRecordClose(m_lFindHandle);  
    		m_lFindHandle.setValue(0);
    	} 	
    }
}
