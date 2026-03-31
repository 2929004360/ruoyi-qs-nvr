package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.ImageAlgLib;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.lib.structure.NET_IN_RADIOMETRY_RANDOM_REGION_TEMPER;
import com.ruoyi.dahua.lib.structure.NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER;

import java.io.IOException;

public class ThermalCameraModule {
	
	static ImageAlgLib imageAlgApi = ImageAlgLib.IMAGEALG_INSTANCE;
	
	public static class ThermalCameraStatus {
		public boolean bSearching = false;				   			// 是否正在查找
		public int     nFinderHanle;                      			// 取到的查询句柄
		public int     nTotalCount;                       			// 符合此次查询条件的结果总条数
		public LLong hRadiometryHandle = new LLong(0); // 订阅句柄
	}
	
	private static ThermalCameraStatus status = new ThermalCameraStatus();
	
	/**
	 * 订阅温度分布数据（热图）
	 */
	public static boolean radiometryAttach(int nChannel, fRadiometryAttachCB cbNotify) {
		/*
		 * 入参
		 */
		NET_IN_RADIOMETRY_ATTACH stIn = new NET_IN_RADIOMETRY_ATTACH();
		stIn.nChannel = nChannel;  // 通道号
		stIn.cbNotify = cbNotify;  // 回调函数
		
		/*
		 * 出参
		 */
		NET_OUT_RADIOMETRY_ATTACH stOut = new NET_OUT_RADIOMETRY_ATTACH();
		status.hRadiometryHandle = LoginModule.netsdk.CLIENT_RadiometryAttach(LoginModule.m_hLoginHandle, stIn, stOut, 3000);
		
		if(status.hRadiometryHandle.longValue() == 0) {
        	System.err.printf("RadiometryAttach Failed!" + ToolKits.getErrorCodePrint());
        }
		
		return status.hRadiometryHandle.longValue() != 0;
	}
	
	/**
	 * 获取查询总个数
	 */
	public static boolean isAttaching() {
		return status.hRadiometryHandle.longValue() != 0;
	}
	
	/**
	 * 开始获取热图数据
	 */
	public static int radiometryFetch(int nChannel) {
		
		int nStatus = -1;
		
		/*
		 * 入参
		 */
		NET_IN_RADIOMETRY_FETCH stIn = new NET_IN_RADIOMETRY_FETCH();
		stIn.nChannel = nChannel;  // 通道号
		
		/*
		 * 出参
		 */
		NET_OUT_RADIOMETRY_FETCH stOut = new NET_OUT_RADIOMETRY_FETCH();
		
		if(!LoginModule.netsdk.CLIENT_RadiometryFetch(LoginModule.m_hLoginHandle, stIn, stOut, 3000)) {
        	System.err.printf("RadiometryFetch Failed!" + ToolKits.getErrorCodePrint());
		} else {
			nStatus = stOut.nStatus;
		}
		
		return nStatus;
	}
	
	/**
	 * 处理回调数据（热图）
	 * @throws IOException 
	 */
	public static boolean saveData(NET_RADIOMETRY_DATA radiometryData) throws IOException {
		
		if (radiometryData == null) {
			return false;
		}
		
		int nWidth = radiometryData.stMetaData.nWidth;
		int nHeight = radiometryData.stMetaData.nHeight;
		
		short[] pGrayImg = new short[nWidth * nHeight];
		float[] pTempForPixels = new float[nWidth * nHeight];
		
		if(LoginModule.netsdk.CLIENT_RadiometryDataParse(radiometryData, pGrayImg, pTempForPixels)) {
			byte[] pYData = new byte[nWidth*nHeight*2];
			imageAlgApi.drcTable(pGrayImg, (short)nWidth, (short)nHeight, 0, pYData, null);
			ToolKits.savePicture(pYData, "./GrayscaleMap.yuv");
		} else {
			System.err.println("saveData failed!" + ToolKits.getErrorCodePrint());
			return false;
		}
		
		return true;
	}
	
	/**
	 * 取消订阅温度分布数据
	 */
	public static void radiometryDetach() {
		if(status.hRadiometryHandle.longValue() != 0) {
			LoginModule.netsdk.CLIENT_RadiometryDetach(status.hRadiometryHandle);
			status.hRadiometryHandle.setValue(0);
		}
	}
	
	/**
	 * 查询测温点
	 */
	public static NET_RADIOMETRYINFO queryPointTemper(int nChannel, short x, short y) {
		int nQueryType = NetSDKLib.NET_QUERY_DEV_RADIOMETRY_POINT_TEMPER;

		// 入参
		NET_IN_RADIOMETRY_GETPOINTTEMPER stIn = new NET_IN_RADIOMETRY_GETPOINTTEMPER();
		stIn.nChannel = nChannel;
		stIn.stCoordinate.nx = x;
		stIn.stCoordinate.ny = y;
		
		// 出参
		NET_OUT_RADIOMETRY_GETPOINTTEMPER stOut = new NET_OUT_RADIOMETRY_GETPOINTTEMPER();
	
		stIn.write();
		stOut.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_QueryDevInfo(LoginModule.m_hLoginHandle, nQueryType, stIn.getPointer(), stOut.getPointer(), null, 3000);
		if(!bRet) {
        	System.err.printf("QueryPointTemper Failed!" + ToolKits.getErrorCodePrint());
        	return null;
		}
		
		stOut.read();
		return stOut.stPointTempInfo;
	}


	/**
	 * 获取测温区域
	 */
	public static NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER radiometryGetRandomRegionTemper(int channel, int pointNum, short[][] nPoints){

		NET_IN_RADIOMETRY_RANDOM_REGION_TEMPER stIn = new NET_IN_RADIOMETRY_RANDOM_REGION_TEMPER();
		NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER stOut = new NET_OUT_RADIOMETRY_RANDOM_REGION_TEMPER();
		stIn.nChannel = channel;
		stIn.nPointNum = pointNum;
		for(int i = 0;i < pointNum;i++){
			stIn.stuPolygon[i].nx = nPoints[i][0];
			stIn.stuPolygon[i].ny = nPoints[i][1];
		}

		stIn.write();
		stOut.write();

		boolean bet = LoginModule.netsdk.CLIENT_RadiometryGetRandomRegionTemper(LoginModule.m_hLoginHandle,stIn.getPointer(),stOut.getPointer(), 3000);

		stIn.read();
		stOut.read();

		if(bet) {

		}else {
			System.err.println("获取测温区域的参数值失败," + ToolKits.getErrorCodePrint());
		}
		return stOut;
	}

	/**
	 * 查询测温项
	 */
	public static NET_RADIOMETRYINFO queryItemTemper(int nChannel, int nPresetId, int nRuleId, int nMeterType) {
		int nQueryType = NetSDKLib.NET_QUERY_DEV_RADIOMETRY_TEMPER;
		
		// 入参
		NET_IN_RADIOMETRY_GETTEMPER stIn = new NET_IN_RADIOMETRY_GETTEMPER();
		stIn.stCondition.nPresetId = nPresetId;
		stIn.stCondition.nRuleId = nRuleId;
		stIn.stCondition.nMeterType = nMeterType; 	// eg: NET_RADIOMETRY_METERTYPE.NET_RADIOMETRY_METERTYPE_AREA;
		stIn.stCondition.nChannel = nChannel;

		// 出参
		NET_OUT_RADIOMETRY_GETTEMPER stOut = new NET_OUT_RADIOMETRY_GETTEMPER();
	
		stIn.write();
		stOut.write();
    	boolean bRet = LoginModule.netsdk.CLIENT_QueryDevInfo(LoginModule.m_hLoginHandle, nQueryType, stIn.getPointer(), stOut.getPointer(), null, 3000);
		if(!bRet) {
        	System.err.printf("QueryPointTemper Failed!" + ToolKits.getErrorCodePrint());
        	return null;
		}
		
		stOut.read();
		return stOut.stTempInfo;
	}
	
	/**
	 * 开始查询信息
	 */
	public static boolean startFind(NET_IN_RADIOMETRY_STARTFIND stuIn) {
		if(status.bSearching) {
			stopFind();
		}
		
		/*
		 * 出参
		 */
		NET_OUT_RADIOMETRY_STARTFIND stuOut = new NET_OUT_RADIOMETRY_STARTFIND();
		stuIn.write();
		stuOut.write();
		status.bSearching = LoginModule.netsdk.CLIENT_StartFind(LoginModule.m_hLoginHandle, 
				NET_FIND.NET_FIND_RADIOMETRY, stuIn.getPointer(), stuOut.getPointer(), 5000);
		if (status.bSearching) {
			stuOut.read();
			status.nFinderHanle = stuOut.nFinderHanle;
			status.nTotalCount = stuOut.nTotalCount;
		}else {
			System.err.printf("startFind Failed!" + ToolKits.getErrorCodePrint());
		}
		
		return status.bSearching;
	}
	
	/**
	 * 获取查询总个数
	 */
	public static int getTotalCount() {
		return status.nTotalCount;
	}
	
	/**
	 * 查询信息
	 */
	public static NET_OUT_RADIOMETRY_DOFIND doFind(int nOffset, int nCount) {
		if(!status.bSearching) {
			System.err.printf("DoFind Failed! [need startFind]");
			return null;
		}
		
		/*
		 * 入参
		 */
		NET_IN_RADIOMETRY_DOFIND stuIn = new NET_IN_RADIOMETRY_DOFIND();
		stuIn.nFinderHanle = status.nFinderHanle;
		stuIn.nBeginNumber = nOffset;
		stuIn.nCount = nCount;
		
		/*
		 * 出参
		 */
		NET_OUT_RADIOMETRY_DOFIND stuOut = new NET_OUT_RADIOMETRY_DOFIND();
		
		stuIn.write();
		stuOut.write();
		if (!LoginModule.netsdk.CLIENT_DoFind(LoginModule.m_hLoginHandle,
				NET_FIND.NET_FIND_RADIOMETRY, stuIn.getPointer(), stuOut.getPointer(), 5000)) {
			System.err.printf("DoFind Failed!" + ToolKits.getErrorCodePrint());
			return null;
		}
		
		stuOut.read();
		return stuOut;
	}
	
	/**
	 * 停止查询信息
	 */
	public static void stopFind() {
		if(!status.bSearching) {
			return;
		}
		
		/*
		 * 入参
		 */
		NET_IN_RADIOMETRY_STOPFIND stuIn = new NET_IN_RADIOMETRY_STOPFIND();
		stuIn.nFinderHanle = status.nFinderHanle;
		
		/*
		 * 出参
		 */
		NET_OUT_RADIOMETRY_STOPFIND stuOut = new NET_OUT_RADIOMETRY_STOPFIND();
		
		stuIn.write();
		stuOut.write();
		LoginModule.netsdk.CLIENT_StopFind(LoginModule.m_hLoginHandle, 
				NET_FIND.NET_FIND_RADIOMETRY, stuIn.getPointer(), stuOut.getPointer(), 5000);
		
		status.bSearching = false;
		status.nFinderHanle = 0;
//		status.nTotalCount = 0;
		
		return;
	}
}
