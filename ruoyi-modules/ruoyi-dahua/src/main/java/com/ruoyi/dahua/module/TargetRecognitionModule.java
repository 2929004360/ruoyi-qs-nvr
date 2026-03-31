package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NativeString;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * IVSS 和 IPC-FR 人脸功能接口实现, IPC-FD只支持目标检测事件
 */

public class TargetRecognitionModule {
	// 查找句柄
	private static LLong m_FindHandle = null;   
	
	// 查询密令
	public static int nToken = 0;
	
	////////////////////////////////  目标识别 和 目标检测 事件  /////////////////////////////////////////////
	/**
	 * 目标识别事件和目标检测事件订阅
	 * @param channel 通道号
	 * @param callback 回调函数
	 * @return true:成功    false:失败
	 */
	public static LLong realLoadPicture(int channel, fAnalyzerDataCallBack callback) {
		int bNeedPicture = 1; // 是否需要图片
	
		LLong m_hAttachHandle =  LoginModule.netsdk.CLIENT_RealLoadPictureEx(LoginModule.m_hLoginHandle, channel, 
									NetSDKLib.EVENT_IVS_ALL, bNeedPicture, callback, null, null);
        if(m_hAttachHandle.longValue() == 0) {
        	System.err.println("CLIENT_RealLoadPictureEx Failed, Error:" + ToolKits.getErrorCodePrint());
        } else {
        	System.out.println("通道[" + channel + "]订阅成功！");
        }

		return m_hAttachHandle;
	}
	
	/**
	 * 停止订阅
	 * @param m_hAttachHandle 智能订阅句柄
	 */
	public static void stopRealLoadPicture(LLong m_hAttachHandle) {
		if(m_hAttachHandle.longValue() != 0) {
			LoginModule.netsdk.CLIENT_StopLoadPic(m_hAttachHandle);
			m_hAttachHandle.setValue(0);
		}
	}
	
	
	///////////////////////////////////////  人脸库的增、删、改、查  ////////////////////////////////
	
	/**
	 * 查询人脸库
	 * @param groupId 需要查找的人脸库ID; 为空表示查找所有的人脸库
	 */
	public static NET_FACERECONGNITION_GROUP_INFO[] findGroupInfo(String groupId) {	
		NET_FACERECONGNITION_GROUP_INFO[] groupInfoRet = null;
		
		/*
		 * 入参
		 */
		NET_IN_FIND_GROUP_INFO stuIn = new NET_IN_FIND_GROUP_INFO();
		System.arraycopy(groupId.getBytes(), 0, stuIn.szGroupId, 0, groupId.getBytes().length);   

		/*
		 * 出参
		 */
		int max = 20;
		NET_FACERECONGNITION_GROUP_INFO[] groupInfo  = new NET_FACERECONGNITION_GROUP_INFO[max];
		for(int i = 0; i < max; i++) {
			groupInfo[i] = new NET_FACERECONGNITION_GROUP_INFO();
		}
		
		NET_OUT_FIND_GROUP_INFO stuOut = new NET_OUT_FIND_GROUP_INFO();   
		stuOut.pGroupInfos = new Memory(groupInfo[0].size() * groupInfo.length);     // Pointer初始化
		stuOut.pGroupInfos.clear(groupInfo[0].size() * groupInfo.length);
		stuOut.nMaxGroupNum = groupInfo.length;
		
		ToolKits.SetStructArrToPointerData(groupInfo, stuOut.pGroupInfos);  // 将数组内存拷贝给Pointer

		if(LoginModule.netsdk.CLIENT_FindGroupInfo(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000)) {
			
			// 将Pointer的值输出到 数组 NET_FACERECONGNITION_GROUP_INFO
			ToolKits.GetPointerDataToStructArr(stuOut.pGroupInfos, groupInfo);   
			
			if(stuOut.nRetGroupNum > 0) {
				// 根据设备返回的，将有效的人脸库信息返回
				groupInfoRet = new NET_FACERECONGNITION_GROUP_INFO[stuOut.nRetGroupNum];
				for(int i = 0; i < stuOut.nRetGroupNum; i++) {
					groupInfoRet[i] = groupInfo[i];
				}
			}
		} else {
			System.err.println("查询人员信息失败" + ToolKits.getErrorCodePrint());
			return null;
		}
		
		return groupInfoRet;
	}
	
	/**
	 * 添加人脸库
	 * @param groupName 需要添加的人脸库名称
	 */
	public static boolean addGroup(String groupName) {
		NET_ADD_FACERECONGNITION_GROUP_INFO addGroupInfo = new NET_ADD_FACERECONGNITION_GROUP_INFO();
		
		// 人脸库名称
		try {
			System.arraycopy(groupName.getBytes("GBK"), 0, addGroupInfo.stuGroupInfo.szGroupName, 0, groupName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}   
		
		/*
		 * 入参
		 */
		NET_IN_OPERATE_FACERECONGNITION_GROUP stuIn = new NET_IN_OPERATE_FACERECONGNITION_GROUP();
		stuIn.emOperateType = EM_OPERATE_FACERECONGNITION_GROUP_TYPE.NET_FACERECONGNITION_GROUP_ADD; // 添加人员组信息
		stuIn.pOPerateInfo = addGroupInfo.getPointer();		
		
		/*
		 * 出参
		 */
		NET_OUT_OPERATE_FACERECONGNITION_GROUP stuOut = new NET_OUT_OPERATE_FACERECONGNITION_GROUP();
		
		addGroupInfo.write();
		boolean bRet = LoginModule.netsdk.CLIENT_OperateFaceRecognitionGroup(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000);
		addGroupInfo.read();
		
		if(bRet) {
			System.out.println("人员组ID : " + new String(stuOut.szGroupId).trim());  // 新增记录的人员组ID,唯一标识一组人员	
		} 

		return bRet;
	}
	
	/**
	 * 修改人脸库
	 * @param groupName 修改后的人脸库名称
	 * @param groupId 需要修改的人脸库ID
	 */
	public static boolean modifyGroup(String groupName, String groupId) {
		NET_MODIFY_FACERECONGNITION_GROUP_INFO modifyGroupInfo = new NET_MODIFY_FACERECONGNITION_GROUP_INFO();
		
		// 人脸库名称
		try {
			System.arraycopy(groupName.getBytes("GBK"), 0, modifyGroupInfo.stuGroupInfo.szGroupName, 0, groupName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, modifyGroupInfo.stuGroupInfo.szGroupId, 0, groupId.getBytes().length);   
		
		/*
		 * 入参
		 */
		NET_IN_OPERATE_FACERECONGNITION_GROUP stuIn = new NET_IN_OPERATE_FACERECONGNITION_GROUP();
		stuIn.emOperateType = EM_OPERATE_FACERECONGNITION_GROUP_TYPE.NET_FACERECONGNITION_GROUP_MODIFY; // 修改人员组信息
		stuIn.pOPerateInfo = modifyGroupInfo.getPointer();		
		
		/*
		 * 出参
		 */
		NET_OUT_OPERATE_FACERECONGNITION_GROUP stuOut = new NET_OUT_OPERATE_FACERECONGNITION_GROUP();
		
		modifyGroupInfo.write();
		boolean bRet = LoginModule.netsdk.CLIENT_OperateFaceRecognitionGroup(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000);
		modifyGroupInfo.read();
		
		if(bRet) {
			System.out.println("修改人脸库成功.");
		}
		
		return bRet;
	}
	
	/**
	 * 删除人脸库
	 * @param groupId 需要删除的人脸库ID; 为空表示删除所有的人脸库
	 */
	public static boolean deleteGroup(String groupId) {
		NET_DELETE_FACERECONGNITION_GROUP_INFO deleteGroupInfo = new NET_DELETE_FACERECONGNITION_GROUP_INFO();
		
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, deleteGroupInfo.szGroupId, 0, groupId.getBytes().length);   
		
		/*
		 * 入参
		 */
		NET_IN_OPERATE_FACERECONGNITION_GROUP stuIn = new NET_IN_OPERATE_FACERECONGNITION_GROUP();
		stuIn.emOperateType = EM_OPERATE_FACERECONGNITION_GROUP_TYPE.NET_FACERECONGNITION_GROUP_DELETE; // 删除人员组信息
		stuIn.pOPerateInfo = deleteGroupInfo.getPointer();		
		
		/*
		 * 出参
		 */
		NET_OUT_OPERATE_FACERECONGNITION_GROUP stuOut = new NET_OUT_OPERATE_FACERECONGNITION_GROUP();
		
		deleteGroupInfo.write();
		boolean bRet = LoginModule.netsdk.CLIENT_OperateFaceRecognitionGroup(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000);
		deleteGroupInfo.read();
		
		if(bRet) {
			System.out.println("删除人脸库成功.");
		} 

		return bRet;
	}
	
	/////////////////////////////  按人脸库布控、撤控   ///////////////////////////////////////
	/**
	 * 以人脸库的角度进行布控
	 * @param groupId 人脸库ID
	 * @param hashMap  key：撤控通道     value：相似度 
	 */
	public static boolean putDisposition(String groupId, HashMap<Integer, Integer> hashMap) {
		int i = 0;
		
		/*
		 * 入参
		 */
		NET_IN_FACE_RECOGNITION_PUT_DISPOSITION_INFO stuIn = new NET_IN_FACE_RECOGNITION_PUT_DISPOSITION_INFO();
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, stuIn.szGroupId, 0, groupId.getBytes().length);
			
		for(Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
			stuIn.stuDispositionChnInfo[i].nChannelID = entry.getKey() - 1;
			stuIn.stuDispositionChnInfo[i].nSimilary = entry.getValue();
		
			i++;
		}

		stuIn.nDispositionChnNum = hashMap.size();        // 布控视频通道个数
		
		/*
		 * 出参
		 */
		NET_OUT_FACE_RECOGNITION_PUT_DISPOSITION_INFO stuOut = new NET_OUT_FACE_RECOGNITION_PUT_DISPOSITION_INFO();
		
		boolean bRet = LoginModule.netsdk.CLIENT_FaceRecognitionPutDisposition(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000);
		if(bRet) {
			System.out.println("通道布控结果个数:" + stuOut.nReportCnt);
		}
		return bRet;
	}
	
	/**
	 * 以人脸库的角度进行撤控
	 * @param groupId 人脸库ID
	 * @param arrayList 撤控通道列表
	 */
	public static boolean delDisposition(String groupId, ArrayList<Integer> arrayList) {
		/*
		 * 入参
		 */
		NET_IN_FACE_RECOGNITION_DEL_DISPOSITION_INFO stuIn = new NET_IN_FACE_RECOGNITION_DEL_DISPOSITION_INFO();
		
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, stuIn.szGroupId, 0, groupId.getBytes().length);
		
		// 撤控视频通道列表
		for(int i = 0; i < arrayList.size(); i++) {
			stuIn.nDispositionChn[i] = arrayList.get(i) - 1;  
		}
		
		// 撤控视频通道个数
		stuIn.nDispositionChnNum = arrayList.size();          
		
		/*
		 *  出参
		 */
		NET_OUT_FACE_RECOGNITION_DEL_DISPOSITION_INFO stuOut = new NET_OUT_FACE_RECOGNITION_DEL_DISPOSITION_INFO();
		
		boolean bRet = LoginModule.netsdk.CLIENT_FaceRecognitionDelDisposition(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000);
		if(bRet) {
			System.out.println("通道撤控结果个数:" + stuOut.nReportCnt);
		}
		return bRet;
	}
	
	/////////////////////////////  按通道布控、撤控   ///////////////////////////////////////
	/**
	 * 获取布控在视频通道的组信息
	 * @param channel 通道号
	 */
	public static void GetGroupInfoForChannel(int channel) {
		/*
		 * 入参
		 */
		NET_IN_GET_GROUPINFO_FOR_CHANNEL stIn = new NET_IN_GET_GROUPINFO_FOR_CHANNEL();
		
		// 通道号
		stIn.nChannelID = channel;
		
		/*
		 * 出参
		 */
		NET_OUT_GET_GROUPINFO_FOR_CHANNEL stOut = new NET_OUT_GET_GROUPINFO_FOR_CHANNEL();
		
		if(LoginModule.netsdk.CLIENT_GetGroupInfoForChannel(LoginModule.m_hLoginHandle, stIn, stOut, 4000)) {
			for(int i = 0; i < stOut.nGroupIdNum; i++) {
				System.out.println("人脸库ID：" + new String(stOut.szGroupIdArr[i].szGroupId).trim());
				System.out.println("相似度：" + stOut.nSimilary[i] + "\n");
			}
		} else {
			System.err.println("获取布控在视频通道的组信息失败, " + ToolKits.getErrorCodePrint());
		}	
	}
	/**
	 * 布控通道人员组信息
	 * @param channel
	 * @param groupIds 人脸库ID，长度等于相似度
	 * @param similarys 相似度， 长度等于人脸库ID
	 */
	public static void SetGroupInfoForChannel(int channel, String[] groupIds, int[] similarys) {
		/*
		 * 入参
		 */
		NET_IN_SET_GROUPINFO_FOR_CHANNEL stIn = new NET_IN_SET_GROUPINFO_FOR_CHANNEL();
		
		// 通道号
		stIn.nChannelID = channel;
		
		// 人脸库ID个数
		stIn.nGroupIdNum = groupIds.length;
		
		// 相似度个数
		stIn.nSimilaryNum = similarys.length;
		
		for(int i = 0; i < groupIds.length; i++) {
			// 人脸库ID赋值，用数组拷贝
			System.arraycopy(groupIds[i].getBytes(), 0, stIn.szGroupIdArr[i].szGroupId, 0, groupIds[i].getBytes().length);
			
			// 对应的人脸库的相似度
			stIn.nSimilary[i] = similarys[i];
		}
		
		/*
		 * 出参
		 */
		NET_OUT_SET_GROUPINFO_FOR_CHANNEL stOut = new NET_OUT_SET_GROUPINFO_FOR_CHANNEL();
		
		if(LoginModule.netsdk.CLIENT_SetGroupInfoForChannel(LoginModule.m_hLoginHandle, stIn, stOut, 4000)) {
			
		}
	}
	
	/////////////////////////////   人员操作    ////////////////////////////////////////////
	/**
	 * 按条件查询目标识别结果
	 * @param groupId  人脸库ID
	 * @param isStartBirthday  查询条件是否下发开始生日
	 * @param startTime  生日起始时间
	 * @param isEndBirthday  查询条件是否下发结束生日
	 * @param endTime  生日结束时间
	 * @param personName  姓名
	 * @param sex  性别
	 * @param idType  证件类型
	 * @param idNo  证件号
	 * @return 查询到的所有人员个数  
	 */
	public static int startFindPerson(String groupId, 
									  boolean isStartBirthday, 
									  String startTime, 
									  boolean isEndBirthday,
									  String endTime, 
									  String personName, 
									  int sex, 
									  int idType, 
									  String idNo) {	
		
		m_FindHandle = null;
		nToken = 0;
		
		int nTotalCount = 0;  
		
		/*
		 * 入参, IVVS设备，查询条件只有  stuInStartFind.stPerson 里的参数有效
		 */
		NET_IN_STARTFIND_FACERECONGNITION stuIn = new NET_IN_STARTFIND_FACERECONGNITION();

		stuIn.bPersonExEnable = 1;  	// 人员信息查询条件是否有效, 并使用扩展结构体

		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, stuIn.stPersonInfoEx.szGroupID, 0, groupId.getBytes().length); 
		
		// 姓名
		try {
			System.arraycopy(personName.getBytes("GBK"), 0, stuIn.stPersonInfoEx.szPersonName, 0, personName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		// 性别
		stuIn.stPersonInfoEx.bySex = (byte)sex;
		
		// 证件类型
		stuIn.stPersonInfoEx.byIDType = (byte)idType;
		
		// 证件号
		System.arraycopy(idNo.getBytes(), 0, stuIn.stPersonInfoEx.szID, 0, idNo.getBytes().length); 
		
		stuIn.stFilterInfo.nGroupIdNum = 1;
		
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, stuIn.stFilterInfo.szGroupIdArr[0].szGroupId, 0, groupId.getBytes().length);  

		// 待查询人脸类型
		stuIn.stFilterInfo.emFaceType = EM_FACERECOGNITION_FACE_TYPE.EM_FACERECOGNITION_FACE_TYPE_ALL;
		
		// 开始生日
		if(isStartBirthday) {
			String[] startTimeStr = startTime.split("-");
			
			stuIn.stFilterInfo.stBirthdayRangeStart.dwYear = Integer.parseInt(startTimeStr[0]);
			stuIn.stFilterInfo.stBirthdayRangeStart.dwMonth = Integer.parseInt(startTimeStr[1]);
			stuIn.stFilterInfo.stBirthdayRangeStart.dwDay = Integer.parseInt(startTimeStr[2]);
		}
		
		// 结束生日
		if(isEndBirthday) {
			String[] endTimeStr = endTime.split("-");
			
			stuIn.stFilterInfo.stBirthdayRangeEnd.dwYear = Integer.parseInt(endTimeStr[0]);
			stuIn.stFilterInfo.stBirthdayRangeEnd.dwMonth = Integer.parseInt(endTimeStr[1]);
			stuIn.stFilterInfo.stBirthdayRangeEnd.dwDay = Integer.parseInt(endTimeStr[2]);
		}
		
		stuIn.stFilterInfo.nRangeNum = 1;
		stuIn.stFilterInfo.szRange[0] = EM_FACE_DB_TYPE.NET_FACE_DB_TYPE_BLACKLIST;

	    /*
	     * 出参
	     */
	    NET_OUT_STARTFIND_FACERECONGNITION stuOut = new NET_OUT_STARTFIND_FACERECONGNITION();
	    stuIn.write();
	    stuOut.write();
	    if(LoginModule.netsdk.CLIENT_StartFindFaceRecognition(LoginModule.m_hLoginHandle, stuIn,  stuOut, 4000)) {        
	    	m_FindHandle = stuOut.lFindHandle;
	    	nTotalCount = stuOut.nTotalCount;
	    	nToken = stuOut.nToken;
	    } else {
	        System.out.println("Failed, Error:" + ToolKits.getErrorCodePrint());
	    }
	    
	    return nTotalCount;
	}
	
	/**
	 * 查找目标识别结果
	 * @param beginNum 查询起始序号
	 * @param nCount  当前想查询的记录条数
	 * @return 返回的人员信息数组
	 */
	public static CANDIDATE_INFOEX[] doFindPerson(int beginNum, int nCount) {
    	/*
    	 *入参
    	 */
        NET_IN_DOFIND_FACERECONGNITION  stuIn = new NET_IN_DOFIND_FACERECONGNITION();
        stuIn.lFindHandle = m_FindHandle;
        stuIn.nCount      = nCount;  	 // 当前想查询的记录条数
        stuIn.nBeginNum   = beginNum;     // 查询起始序号
        
        /*
         * 出参
         */
        NET_OUT_DOFIND_FACERECONGNITION stuOut = new NET_OUT_DOFIND_FACERECONGNITION();
        stuOut.bUseCandidatesEx = 1;				// 是否使用候选对象扩展结构体
        
        // 必须申请内存，每次查询几个，必须至少申请几个，最大申请20个
        for(int i = 0; i < nCount; i++) {
            stuOut.stuCandidatesEx[i].stPersonInfo.szFacePicInfo[0].nFilePathLen = 256;
            stuOut.stuCandidatesEx[i].stPersonInfo.szFacePicInfo[0].pszFilePath = new Memory(256);
        }
        
    	stuIn.write();
    	stuOut.write();
        if(LoginModule.netsdk.CLIENT_DoFindFaceRecognition(stuIn, stuOut, 4000)) {
        	stuIn.read();
        	stuOut.read();
        	
        	if(stuOut.nCadidateExNum == 0) {
        		return null;
        	}
        	
        	// 查询到的数据
        	CANDIDATE_INFOEX[]	stuCandidatesEx = new CANDIDATE_INFOEX[stuOut.nCadidateExNum];
        	for(int i = 0; i < stuOut.nCadidateExNum; i++) {
        		stuCandidatesEx[i] = new CANDIDATE_INFOEX();
        		stuCandidatesEx[i] = stuOut.stuCandidatesEx[i];
        	}   
        	
        	return stuCandidatesEx;
        } else {
        	System.out.println("Failed, Error:" + ToolKits.getErrorCodePrint());
        }
    
        return null;
	}
	
	/**
	 * 结束查询
	 */
	public static boolean doFindPerson() {
		boolean bRet = false;
		if(m_FindHandle.longValue() != 0) {
			bRet = LoginModule.netsdk.CLIENT_StopFindFaceRecognition(m_FindHandle);
		}
		return bRet;
	}
	
	/**
	 * 添加人员
	 * @param groupId  人脸库ID
	 * @param memory  图片数据
	 * @param personName 姓名
	 * @param sex  性别
	 * @param isBirthday 是否下发生日
	 * @param birthday  生日
	 * @param byIdType  证件类型
	 * @param idNo  证件号
	 * @return
	 */
	public static boolean addPerson(String groupId, 
									Memory memory, 
									String personName, 
									int sex, 
									boolean isBirthday, 
									String birthday,  
									int byIdType, 
									String idNo) {
		/*
		 *  入参
		 */
		NET_IN_OPERATE_FACERECONGNITIONDB stuIn  = new NET_IN_OPERATE_FACERECONGNITIONDB();
		stuIn.emOperateType = EM_OPERATE_FACERECONGNITIONDB_TYPE.NET_FACERECONGNITIONDB_ADD;
		
		///////// 使用人员扩展信息 //////////
		stuIn.bUsePersonInfoEx = 1;   
		
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, stuIn.stPersonInfoEx.szGroupID, 0, groupId.getBytes().length);
		
		// 生日设置
		if(isBirthday) {
			String[] birthdays = birthday.split("-");
					
			stuIn.stPersonInfoEx.wYear = (short)Integer.parseInt(birthdays[0]);
			stuIn.stPersonInfoEx.byMonth = (byte)Integer.parseInt(birthdays[1]);
			stuIn.stPersonInfoEx.byDay = (byte)Integer.parseInt(birthdays[2]);
		}
		
		// 性别,1-男,2-女,作为查询条件时,此参数填0,则表示此参数无效	
		stuIn.stPersonInfoEx.bySex = (byte)sex;	
		
		// 人员名字	
		try {
			System.arraycopy(personName.getBytes("GBK"), 0, stuIn.stPersonInfoEx.szPersonName, 0, personName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		// 证件类型
		stuIn.stPersonInfoEx.byIDType = (byte)byIdType;  
		
		// 证件号
		System.arraycopy(idNo.getBytes(), 0, stuIn.stPersonInfoEx.szID, 0, idNo.getBytes().length); 					  
		
		// 图片张数、大小、缓存设置
		if(memory != null) {
			stuIn.stPersonInfoEx.wFacePicNum = 1; // 图片张数
			stuIn.stPersonInfoEx.szFacePicInfo[0].dwFileLenth = (int)memory.size();  // 图片大小
			stuIn.stPersonInfoEx.szFacePicInfo[0].dwOffSet = 0;
			
			stuIn.nBufferLen = (int)memory.size();
			stuIn.pBuffer = memory;
		}
		
		/*
		 * 出参
		 */
		NET_OUT_OPERATE_FACERECONGNITIONDB stuOut = new NET_OUT_OPERATE_FACERECONGNITIONDB() ;	
		
		stuIn.write();
		boolean bRet = LoginModule.netsdk.CLIENT_OperateFaceRecognitionDB(LoginModule.m_hLoginHandle, stuIn, stuOut, 3000);
		stuIn.read();
		
		if(bRet) {
			System.out.println("szUID :　" + new String(stuOut.szUID).trim());
		} else {
			System.err.println(ToolKits.getErrorCodePrint());
		}
		
		return bRet;
	}
	
	/**
	 * 修改人员信息
	 * @param groupId 人脸库ID
	 * @param uid  人员唯一标识符
	 * @param memory  图片数据
	 * @param personName  姓名
	 * @param sex  性别
	 * @param isBirthday  是否下发生日
	 * @param birthday  生日
	 * @param byIdType  证件类型
	 * @param idNo  证件号
	 * @return true：成功 ,  false：失败
	 */
	public static boolean modifyPerson(String groupId, 
									   String uid,
									   Memory memory, 
									   String personName, 
									   int sex, 
									   boolean isBirthday, 
									   String birthday,  
									   int byIdType, 
									   String idNo) {
		// 入参
		NET_IN_OPERATE_FACERECONGNITIONDB stuIn  = new NET_IN_OPERATE_FACERECONGNITIONDB();
		stuIn.emOperateType = EM_OPERATE_FACERECONGNITIONDB_TYPE.NET_FACERECONGNITIONDB_MODIFY;
		
		///////// 使用人员扩展信息  ////////
		stuIn.bUsePersonInfoEx = 1;	
		
		// 人脸库ID
		System.arraycopy(groupId.getBytes(), 0, stuIn.stPersonInfoEx.szGroupID, 0, groupId.getBytes().length);
		
		// 人员唯一标识符
		System.arraycopy(uid.getBytes(), 0, stuIn.stPersonInfoEx.szUID, 0, uid.getBytes().length); 
		
		// 生日设置
		if(isBirthday) {
			String[] birthdays = birthday.split("-");
					
			stuIn.stPersonInfoEx.wYear = (short)Integer.parseInt(birthdays[0]);
			stuIn.stPersonInfoEx.byMonth = (byte)Integer.parseInt(birthdays[1]);
			stuIn.stPersonInfoEx.byDay = (byte)Integer.parseInt(birthdays[2]);
		}
		
		// 性别,1-男,2-女,作为查询条件时,此参数填0,则表示此参数无效	
		stuIn.stPersonInfoEx.bySex = (byte)sex;	
		
		// 人员名字	
		try {
			System.arraycopy(personName.getBytes("GBK"), 0, stuIn.stPersonInfoEx.szPersonName, 0, personName.getBytes("GBK").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		// 证件类型
		stuIn.stPersonInfoEx.byIDType = (byte)byIdType;  
		
		// 证件号
		System.arraycopy(idNo.getBytes(), 0, stuIn.stPersonInfoEx.szID, 0, idNo.getBytes().length); 					  
		
		// 图片张数、大小、缓存设置
		if(memory != null) {
			stuIn.stPersonInfoEx.wFacePicNum = 1; // 图片张数
			stuIn.stPersonInfoEx.szFacePicInfo[0].dwFileLenth = (int)memory.size();  // 图片大小
			stuIn.stPersonInfoEx.szFacePicInfo[0].dwOffSet = 0;
			
			stuIn.nBufferLen = (int)memory.size();
			stuIn.pBuffer = memory;
		}					  

		// 出参
		NET_OUT_OPERATE_FACERECONGNITIONDB stuOut = new NET_OUT_OPERATE_FACERECONGNITIONDB() ;	
		
		stuIn.write();
	    if(!LoginModule.netsdk.CLIENT_OperateFaceRecognitionDB(LoginModule.m_hLoginHandle, stuIn, stuOut, 3000)) {
	    	System.err.println("修改人员失败" + ToolKits.getErrorCodePrint());
	    	return false;
	    }
	    stuIn.read();

		return true;
	}
	
	/**
	 * 删除人员信息
	 * @param groupId 人脸库ID
	 * @param sUID  人员唯一标识符
	 */
	public static boolean delPerson(String groupId, String sUID) {    
		/*
		 *  入参
		 */
		NET_IN_OPERATE_FACERECONGNITIONDB stuIn  = new NET_IN_OPERATE_FACERECONGNITIONDB();
		stuIn.emOperateType = EM_OPERATE_FACERECONGNITIONDB_TYPE.NET_FACERECONGNITIONDB_DELETE;
	
		//////// 使用人员扩展信息  //////////
		stuIn.bUsePersonInfoEx = 1;	
		
		// GroupID 赋值
		System.arraycopy(groupId.getBytes(), 0, stuIn.stPersonInfoEx.szGroupID, 0, groupId.getBytes().length);

		// UID赋值
		System.arraycopy(sUID.getBytes(), 0, stuIn.stPersonInfoEx.szUID, 0, sUID.getBytes().length);

		/*
		 *  出参
		 */
		NET_OUT_OPERATE_FACERECONGNITIONDB stuOut = new NET_OUT_OPERATE_FACERECONGNITIONDB() ;	

		boolean bRet = LoginModule.netsdk.CLIENT_OperateFaceRecognitionDB(LoginModule.m_hLoginHandle, stuIn, stuOut, 3000);
	    if(!bRet) {
	    	System.err.println(LoginModule.netsdk.CLIENT_GetLastError());
	    }
	    
		return bRet;
	}
	
	/**
	 * 下载图片, 用于修改人员信息
	 * @param szFileName 需要下载的文件名
	 * @param pszFileDst 存放文件路径
	 */
	public static boolean downloadPersonPic(String szFileName, String pszFileDst) {
		/*
		 * 入参
		 */
		NET_IN_DOWNLOAD_REMOTE_FILE stuIn = new NET_IN_DOWNLOAD_REMOTE_FILE();
		// 需要下载的文件名
		stuIn.pszFileName = new NativeString(szFileName).getPointer();
		
		// 存放文件路径
		stuIn.pszFileDst = new NativeString(pszFileDst).getPointer();

		/*
		 * 出参
		 */
		NET_OUT_DOWNLOAD_REMOTE_FILE stuOut = new NET_OUT_DOWNLOAD_REMOTE_FILE();
		
		if(!LoginModule.netsdk.CLIENT_DownloadRemoteFile(LoginModule.m_hLoginHandle, stuIn, stuOut, 5000)) {
			System.err.println("下载图片失败!" + ToolKits.getErrorCodePrint());
			return false;
		}
		return true;
	}

	/**
	 * 显示/关闭规则库
	 * @param RealPlayHandle  实时预览
	 * @param bTrue    1-打开, 0-关闭
	 * @return
	 */
	public static void renderPrivateData(LLong m_hRealPlayHandle, int bTrue) {
		if(m_hRealPlayHandle.longValue() != 0) {
			LoginModule.netsdk.CLIENT_RenderPrivateData(m_hRealPlayHandle, bTrue);
		}	
	}
	
	//////////////////////////  查询事件对比记录   /////////////////////////
	private static LLong lFindHandle = new LLong(0);    // 查找句柄
	
	/**
	 * 获取查找句柄
	 * @param nChn  通道号
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 */
	public static boolean findFile(int nChn, String startTime, String endTime) {
		int type = EM_FILE_QUERY_TYPE.NET_FILE_QUERY_FACE;

		/**
		 *  查询条件
		 */
		MEDIAFILE_FACERECOGNITION_PARAM findContion = new MEDIAFILE_FACERECOGNITION_PARAM();
		
		// 开始时间
		String[] starts = startTime.split(" ");
		
		findContion.stStartTime.dwYear = Integer.parseInt(starts[0].split("-")[0]);
		findContion.stStartTime.dwMonth = Integer.parseInt(starts[0].split("-")[1]);
		findContion.stStartTime.dwDay = Integer.parseInt(starts[0].split("-")[2]);
		findContion.stStartTime.dwHour = Integer.parseInt(starts[1].split(":")[0]);
		findContion.stStartTime.dwMinute = Integer.parseInt(starts[1].split(":")[1]);
		findContion.stStartTime.dwSecond = Integer.parseInt(starts[1].split(":")[2]);

		// 结束时间
		String[] ends = endTime.split(" ");
		findContion.stEndTime.dwYear = Integer.parseInt(ends[0].split("-")[0]);
		findContion.stEndTime.dwMonth = Integer.parseInt(ends[0].split("-")[1]);
		findContion.stEndTime.dwDay = Integer.parseInt(ends[0].split("-")[2]);
		findContion.stEndTime.dwHour = Integer.parseInt(ends[1].split(":")[0]);
		findContion.stEndTime.dwMinute = Integer.parseInt(ends[1].split(":")[1]);
		findContion.stEndTime.dwSecond = Integer.parseInt(ends[1].split(":")[2]);
		
		// 通道号
		findContion.nChannelId = nChn;
		
		/**
		 * 以下注释的查询条件参数，目前设备不支持，后续会逐渐增加
		 */
//		// 地点,支持模糊匹配 
//		String machineAddress = "";
//		System.arraycopy(machineAddress.getBytes(), 0, findContion.szMachineAddress, 0, machineAddress.getBytes().length);
//		
//		// 待查询报警类型
//		findContion.nAlarmType = EM_FACERECOGNITION_ALARM_TYPE.NET_FACERECOGNITION_ALARM_TYPE_ALL;
		
//		// 人员组数 
//		findContion.nGroupIdNum = 1;  
//		
//		// 人员组ID(人脸库ID)
//		String groupId = "";
//		System.arraycopy(groupId.getBytes(), 0, findContion.szGroupIdArr[0].szGroupId, 0, groupId.getBytes().length);
//		
//		// 人员信息扩展是否有效
//		findContion.abPersonInfoEx = 1;     
//		
//		// 人员组ID(人脸库ID)
//		System.arraycopy(groupId.getBytes(), 0, findContion.stPersonInfoEx.szGroupID, 0, groupId.getBytes().length);
		
		findContion.write();
		lFindHandle = LoginModule.netsdk.CLIENT_FindFileEx(LoginModule.m_hLoginHandle, type, findContion.getPointer(), null, 3000);
		if(lFindHandle.longValue() == 0) {
			System.err.println("FindFileEx Failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		findContion.read();
		
		return true;
	}
	
	
	/**
	 * 查询对比数据
	 * @param nFindCount 每次查询的个数
	 */
	public static MEDIAFILE_FACERECOGNITION_INFO[] findNextFile(int nFindCount) {
		MEDIAFILE_FACERECOGNITION_INFO[] msg = new MEDIAFILE_FACERECOGNITION_INFO[nFindCount];
		for (int i = 0; i < msg.length; ++i) {
			msg[i] = new MEDIAFILE_FACERECOGNITION_INFO();
			msg[i].bUseCandidatesEx = 1;
		}
		
		int MemorySize = msg[0].size() * nFindCount;
		Pointer pointer = new Memory(MemorySize);
		pointer.clear(MemorySize);
		
		ToolKits.SetStructArrToPointerData(msg, pointer);

		int nRetCount = LoginModule.netsdk.CLIENT_FindNextFileEx(lFindHandle, nFindCount, pointer, MemorySize, null, 3000);
		ToolKits.GetPointerDataToStructArr(pointer, msg);
		
		if (nRetCount <= 0) {
			System.err.println("FindNextFileEx failed!" + ToolKits.getErrorCodePrint());
            return null;
		} 

		MEDIAFILE_FACERECOGNITION_INFO[] retInfo = new MEDIAFILE_FACERECOGNITION_INFO[nRetCount];
		for (int i = 0; i < retInfo.length; ++i) {
			retInfo[i] = new MEDIAFILE_FACERECOGNITION_INFO();
			retInfo[i] = msg[i];
		}
		
		return retInfo;	
	}
	
	public static void findCloseFile() {
		if(lFindHandle.longValue() != 0) {
			LoginModule.netsdk.CLIENT_FindCloseEx(lFindHandle);	
			lFindHandle.setValue(0);
		}
	}
}
