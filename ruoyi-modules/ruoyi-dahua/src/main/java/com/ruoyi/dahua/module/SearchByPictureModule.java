package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

/**
 * 以图搜图接口实现，跟查询人脸库里的人员信息的查找接口是一样的，入参和实现有区别
 * 目前只支持IVSS
 */
public class SearchByPictureModule {
	// 查询密令
	public static int nToken = 0;
	
	private static LLong m_FindHandle = null;
	
	// 订阅句柄
	private static LLong attachFaceHandle = new LLong(0);
	
	/**
	 * 按条件查询目标识别结果
	 * @param memory 	  图片缓存
	 * @param startTime  起始时间, 历史库需要时间，人脸库不需要时间
	 * @param endTime    结束时间, 历史库需要时间，人脸库不需要时间
	 * @param isHistory  是否是历史库， true-历史库； false-人脸库
	 * @param nChn       通道号， 历史库需要通道号，人脸库不需要通道号
	 * @param similary   相似度
	 * @return 查询到的所有人员个数  
	 */
	public static int startFindPerson(Memory memory,
									  String startTime, 
									  String endTime,
									  boolean isHistory,
									  int nChn,
									  String similary) {	
		
		m_FindHandle = null;
		nToken = 0;
		int nTotalCount = 0;  
		
		/*
		 * 入参, IVVS设备，查询条件只有  stuInStartFind.stPerson 里的参数有效
		 */
		NET_IN_STARTFIND_FACERECONGNITION stuIn = new NET_IN_STARTFIND_FACERECONGNITION();

		// 人员信息查询条件是否有效, 并使用扩展结构体
		stuIn.bPersonExEnable = 1; 
		
		// 图片信息
		if(memory != null) {
			stuIn.pBuffer = memory;
			stuIn.nBufferLen = (int)memory.size();
			stuIn.stPersonInfoEx.wFacePicNum = 1;
			stuIn.stPersonInfoEx.szFacePicInfo[0].dwOffSet = 0;
			stuIn.stPersonInfoEx.szFacePicInfo[0].dwFileLenth = (int)memory.size();
		}
		
		// 相似度
		if(!similary.isEmpty()) {
			stuIn.stMatchOptions.nSimilarity = Integer.parseInt(similary);
		}
		
		stuIn.stFilterInfo.nGroupIdNum = 0;
		stuIn.stFilterInfo.nRangeNum = 1;
		
		if(isHistory) {  // 历史库
			// 通道号
			stuIn.nChannelID = nChn; 
			stuIn.stFilterInfo.szRange[0] = EM_FACE_DB_TYPE.NET_FACE_DB_TYPE_HISTORY;  // 待查询数据库类型，设备只支持一个
			// 开始时间
			String[] startTimeStr = startTime.split("-");
			stuIn.stFilterInfo.stStartTime.dwYear = Integer.parseInt(startTimeStr[0]);
			stuIn.stFilterInfo.stStartTime.dwMonth = Integer.parseInt(startTimeStr[1]);
			stuIn.stFilterInfo.stStartTime.dwDay = Integer.parseInt(startTimeStr[2]);
			stuIn.stFilterInfo.stStartTime.dwHour= 0;
			stuIn.stFilterInfo.stStartTime.dwMinute= 0;
			stuIn.stFilterInfo.stStartTime.dwSecond= 0;
			// 结束时间
			String[] endTimeStr = endTime.split("-");
			stuIn.stFilterInfo.stEndTime.dwYear = Integer.parseInt(endTimeStr[0]);
			stuIn.stFilterInfo.stEndTime.dwMonth = Integer.parseInt(endTimeStr[1]);
			stuIn.stFilterInfo.stEndTime.dwDay = Integer.parseInt(endTimeStr[2]);
			stuIn.stFilterInfo.stEndTime.dwHour=23;
			stuIn.stFilterInfo.stEndTime.dwMinute=59;
			stuIn.stFilterInfo.stEndTime.dwSecond=59;
			stuIn.stFilterInfo.emFaceType = EM_FACERECOGNITION_FACE_TYPE.EM_FACERECOGNITION_FACE_TYPE_ALL;
		} else {        // 人脸库
			stuIn.stFilterInfo.szRange[0] = EM_FACE_DB_TYPE.NET_FACE_DB_TYPE_BLACKLIST;  // 待查询数据库类型，设备只支持一个
		}
	
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
	        System.out.println("CLIENT_StartFindFaceRecognition Failed, Error:" + ToolKits.getErrorCodePrint());
	    }
	    
	    return nTotalCount;
	}
	
	/**
	 * 查找目标识别结果
	 * @param beginNum 查询起始序号
	 * @param nCount  当前想查询的记录条数
	 * @return 返回的人员信息数组
	 */
	public static CANDIDATE_INFOEX[] doFindNextPerson(int beginNum, int nCount) {
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
        NET_OUT_DOFIND_FACERECONGNITION stuOut = new NET_OUT_DOFIND_FACERECONGNITION();;
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
        	
        	// 获取到的信息
        	CANDIDATE_INFOEX[]	stuCandidatesEx = new CANDIDATE_INFOEX[stuOut.nCadidateExNum];
        	for(int i = 0; i < stuOut.nCadidateExNum; i++) {
        		stuCandidatesEx[i] = new CANDIDATE_INFOEX();
        		stuCandidatesEx[i] = stuOut.stuCandidatesEx[i];
        	}
        	
        	return stuCandidatesEx;
        } else {
        	System.out.println("CLIENT_DoFindFaceRecognition Failed, Error:" + ToolKits.getErrorCodePrint());
        }
    
        return null;
	}
	
	/**
	 * 结束查询
	 */
	public static boolean doFindClosePerson() {
		boolean bRet = false;
		if(m_FindHandle.longValue() != 0) {
			bRet = LoginModule.netsdk.CLIENT_StopFindFaceRecognition(m_FindHandle);
		}
		return bRet;
	}
	
	/**
	 * 订阅人脸查询状态
	 * @param faceFindStateCb 人脸状态回调函数
	 * @return
	 */
	public static boolean attachFaceFindState(fFaceFindState faceFindStateCb) {
		/*
		 * 入参
		 */
		NET_IN_FACE_FIND_STATE stuIn = new NET_IN_FACE_FIND_STATE();
		stuIn.nTokenNum = 1;   
		stuIn.nTokens = new IntByReference(nToken);  // 查询令牌
		stuIn.cbFaceFindState = faceFindStateCb;
		
		/*
		 * 出参
		 */
		NET_OUT_FACE_FIND_STATE stuOut = new NET_OUT_FACE_FIND_STATE();
		
		stuIn.write();
		attachFaceHandle = LoginModule.netsdk.CLIENT_AttachFaceFindState(LoginModule.m_hLoginHandle, stuIn, stuOut, 4000);
		stuIn.read();
		
		if(attachFaceHandle.longValue() != 0) {
			System.out.println("AttachFaceFindState Succeed!");
			return true;
		}
		
		return false;
	}
	
	/**
	 * 关闭订阅
	 */
	public static void detachFaceFindState() {
		if(attachFaceHandle.longValue() != 0) {		
			LoginModule.netsdk.CLIENT_DetachFaceFindState(attachFaceHandle);
			attachFaceHandle.setValue(0);
		}
	}
}
