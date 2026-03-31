package com.ruoyi.dahua.frame;

import com.ruoyi.dahua.common.CaseMenu;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.LLong;
import com.ruoyi.dahua.lib.NetSDKLib.NET_DEVICEINFO_Ex;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.io.File;
import java.util.Scanner;

public class RealplayEx {
	public static final NetSDKLib netSdk = NetSDKLib.NETSDK_INSTANCE;
	
	// 登陆句柄
    private LLong loginHandle = new LLong(0);
    
    // 预览预览句柄
    private static LLong lRealHandle = new LLong(0);
    
    
    // 设备信息扩展
    private NET_DEVICEINFO_Ex deviceInfo = new NET_DEVICEINFO_Ex();
	
	//private NET_TIME m_startTime = new NET_TIME(); // 开始时间
	//private NET_TIME m_stopTime = new NET_TIME(); // 结束时间
    
	public void InitTest(){
		// 初始化SDK库
        netSdk.CLIENT_Init(DisConnectCallBack.getInstance(), null);

        // 设置断线重连成功回调函数
        netSdk.CLIENT_SetAutoReconnect(HaveReConnectCallBack.getInstance(), null);
        
        //打开日志，可选
        NetSDKLib.LOG_SET_PRINT_INFO setLog = new NetSDKLib.LOG_SET_PRINT_INFO();
        String logPath = new File(".").getAbsoluteFile().getParent() + File.separator + "sdk_log" + File.separator + "sdk.log";
        setLog.bSetFilePath = 1;
        System.arraycopy(logPath.getBytes(), 0, setLog.szLogFilePath, 0, logPath.getBytes().length);
        setLog.bSetPrintStrategy = 1;
        setLog.nPrintStrategy = 0;
        if (!netSdk.CLIENT_LogOpen(setLog)){
            System.err.println("Open SDK Log Failed!!!");
        }        

	}
	
	public void Login(String m_strIp,int m_nPort,String m_strUser,String m_strPassword ){

		 // 登陆设备
        int nSpecCap = NetSDKLib.EM_LOGIN_SPAC_CAP_TYPE.EM_LOGIN_SPEC_CAP_TCP;    // TCP登入
        IntByReference nError = new IntByReference(0);
        loginHandle = netSdk.CLIENT_LoginEx2(m_strIp, m_nPort, m_strUser,
        		m_strPassword ,nSpecCap, null, deviceInfo, nError);
        if (loginHandle.longValue() != 0) {
            System.out.printf("Login Device[%s] Success!\n", m_strIp);             
        }
        else {
        	System.err.printf("Login Device[%s] Fail.Error[0x%x]\n", m_strIp, netSdk.CLIENT_GetLastError()); 
            LoginOut();
        }
	}
	public void realplay(){
		lRealHandle= netSdk.CLIENT_RealPlayEx(loginHandle, 0, null, 0);
        if(lRealHandle.longValue()!=0){
        	System.out.println("realplay success");
        	netSdk.CLIENT_SetRealDataCallBackEx(lRealHandle, CbfRealDataCallBackEx.getInstance(),null, 31);
        }
	}
	
	public void StopRealPlay(){
		if(netSdk.CLIENT_StopRealPlayEx(lRealHandle)){
			System.out.println("StopRealPlay success");
		}
	}
	public void LoginOut(){
		System.out.println("End Test");
		
		if( loginHandle.longValue() != 0)
		{
			netSdk.CLIENT_Logout(loginHandle);
		}
		System.out.println("See You...");
		
		netSdk.CLIENT_Cleanup();
		System.exit(0);
	}
	
	 public void RunTest(){
			CaseMenu menu=new CaseMenu();
			menu.addItem((new CaseMenu.Item(this , "realplay" , "realplay")));
			menu.addItem((new CaseMenu.Item(this , "StopRealPlay" , "StopRealPlay")));
			menu.run();
		}
	 /**
     * 设备断线回调
     */
    private static class DisConnectCallBack implements NetSDKLib.fDisConnect {

        private DisConnectCallBack() {
        }

        private static class CallBackHolder {
            private static DisConnectCallBack instance = new DisConnectCallBack();
        }

        public static DisConnectCallBack getInstance() {
            return CallBackHolder.instance;
        }

        public void invoke(LLong lLoginID, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
        }
    }
    
    /**
     * 设备重连回调
     */
    private static class HaveReConnectCallBack implements NetSDKLib.fHaveReConnect {
        private HaveReConnectCallBack() {
        }

        private static class CallBackHolder {
            private static HaveReConnectCallBack instance = new HaveReConnectCallBack();
        }

        public static HaveReConnectCallBack getInstance() {
            return CallBackHolder.instance;
        }

        public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

        }
    }
    
    /**
     * 实时预览数据回调函数--扩展(pBuffer内存由SDK内部申请释放)
     */
    public static class CbfRealDataCallBackEx implements NetSDKLib.fRealDataCallBackEx {
        private CbfRealDataCallBackEx() {
        }

        private static class CallBackHolder {
            private static CbfRealDataCallBackEx instance = new CbfRealDataCallBackEx();
        }

        public static CbfRealDataCallBackEx getInstance() {
            return CallBackHolder.instance;
        }

		@Override
		public void invoke(LLong lRealHandle, int dwDataType, Pointer pBuffer,
				int dwBufSize, int param, Pointer dwUser) {
			int bInput=0;
			if(0 != lRealHandle.longValue())
			{
				switch(dwDataType) {
				case 0:
					System.out.println("码流大小为" + dwBufSize + "\n" + "码流类型为原始音视频混合数据");					
					break;
				case 1:
					//标准视频数据
					
					break;
				case 2:
					//yuv 数据
					
					break;
				case 3:
					//pcm 音频数据
					
					break;
				case 4:
					//原始音频数据
					
					break;
				default:
					break;
				}	
			}
		}
    }
    
    public static void main(String []args){
    	RealplayEx XM=new RealplayEx();
        String ip = "172.23.12.231";
        int port = 37777;
        String username = "admin";
        String password = "admin123";
        Scanner scanner = new Scanner(System.in);
        String defaultConfig = "ip:%s,port:%d,username:%s,password:%s,需要修改吗?(y/n)";
        defaultConfig = String.format(defaultConfig, ip, port, username, password);
        System.out.println(defaultConfig);
        String answer = "";
        do {
            answer = scanner.nextLine();
            if ("y".equalsIgnoreCase(answer) || "yes".equalsIgnoreCase(answer)) {
                System.out.println("please input ip");
                ip = scanner.nextLine().trim();
                System.out.println("please input port:");
                port = Integer.parseInt(scanner.nextLine());
                System.out.println("please input username:");
                username = scanner.nextLine().trim();
                System.out.println("please input password:");
                password = scanner.nextLine().trim();
                break;
            } else if ("n".equalsIgnoreCase(answer) || "no".equalsIgnoreCase(answer)) {
                break;
            }
            System.out.println("please input the right word.y/yes/n/no,try again.");
        } while (!(answer.equalsIgnoreCase("y")
                || answer.equalsIgnoreCase("yes")
                || answer.equalsIgnoreCase("no")
                || answer.equalsIgnoreCase("n")));

    	XM.InitTest();
        XM.Login(ip,port,username,password);
	    XM.RunTest();
	    XM.LoginOut();
    }
}
