package com.ruoyi.dahua.module;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author ： 47040
 * @since ： Created in 2020/7/25 17:59
 */
public class VideoStateSummaryModule {

    public static Map<Integer, NetSDKLib.LLong> getM_hAttachMap() {
        return m_hAttachMap;
    }

    // handle map
    private static Map<Integer, NetSDKLib.LLong> m_hAttachMap = new HashMap<Integer, NetSDKLib.LLong>();

    // Attach 订阅 人数统计事件
    public static boolean attachVideoStatSummary(int channel, NetSDKLib.fVideoStatSumCallBack fVideoStatSumCallBack) {

        if (!m_hAttachMap.containsKey(channel)) {

            NetSDKLib.NET_IN_ATTACH_VIDEOSTAT_SUM inParam = new NetSDKLib.NET_IN_ATTACH_VIDEOSTAT_SUM();
            inParam.nChannel = channel;
            inParam.cbVideoStatSum = fVideoStatSumCallBack;

            NetSDKLib.NET_OUT_ATTACH_VIDEOSTAT_SUM outParam = new NetSDKLib.NET_OUT_ATTACH_VIDEOSTAT_SUM();
            NetSDKLib.LLong m_hAttachHandle = LoginModule.netsdk.CLIENT_AttachVideoStatSummary(LoginModule.m_hLoginHandle, inParam, outParam, 5000);
            if (m_hAttachHandle.longValue() == 0) {
                System.err.printf("Attach Failed!LastError = %s\n", ToolKits.getErrorCodePrint());
                return false;
            }
            m_hAttachMap.put(channel, m_hAttachHandle);
            System.out.printf("Attach Succeed at Channel %d ! AttachHandle: %d. Wait Device Notify Information\n", channel, m_hAttachHandle.longValue());
            return true;
        } else {    // 非 0 说明正在订阅，先退订，再返回订阅失败
            System.err.print("Attach Handle is not Zero, Please Detach First.\n");
            return false;
        }
    }

    // Check if Channel is Attached 是否订阅通道
    public static boolean channelAttached(int channel) {

        return m_hAttachMap.containsKey(channel);
    }

    // Detach 退订 人数统计事件
    public static boolean detachVideoStatSummary(int channel) {
        if (m_hAttachMap.containsKey(channel)) {

            NetSDKLib.LLong m_hAttachHandle = m_hAttachMap.get(channel);

            if (m_hAttachHandle.longValue() != 0) {
                if (!LoginModule.netsdk.CLIENT_DetachVideoStatSummary(m_hAttachHandle)) {
                    System.err.printf("Detach Failed!LastError = %s\n", ToolKits.getErrorCodePrint());
                    return false;
                }
                System.out.println("Channel " + channel + ". Handle: " + m_hAttachHandle.longValue() + ". Detach Succeed!");
                m_hAttachHandle.setValue(0);
                m_hAttachMap.remove(channel);
                return true;
            } else {
                System.err.print("Attach Handle is Zero, no Need for Detach.\n");
                return false;
            }
        } else {
            System.err.print("Attach Handle not found.\n");
            return false;
        }
    }

    // Detach All 退订 人数统计事件
    public static void detachAllVideoStatSummary() {
        Set<Integer> keySet = m_hAttachMap.keySet();

        for (Iterator<Integer> iter = keySet.iterator(); iter.hasNext(); ) {
            int channel = iter.next();

            NetSDKLib.LLong m_hAttachHandle = m_hAttachMap.get(channel);
            if (!LoginModule.netsdk.CLIENT_DetachVideoStatSummary(m_hAttachHandle)) {
                System.err.printf("Detach Failed!LastError = %s\n", ToolKits.getErrorCodePrint());
            }
            System.out.println("Channel " + channel + ". Handle: " + m_hAttachHandle.longValue() + ". Detach Succeed!");
            iter.remove();
        }
    }

    // reAttach 重订 人数统计事件
    public static void reAttachAllVideoStatSummary(NetSDKLib.fVideoStatSumCallBack fVideoStatSumCallBack) {
        Set<Integer> keySet = m_hAttachMap.keySet();
        for (int channel : keySet) {
            NetSDKLib.LLong m_hAttachHandle = m_hAttachMap.get(channel);
            if (!LoginModule.netsdk.CLIENT_DetachVideoStatSummary(m_hAttachHandle)) {
                System.err.printf("Detach Failed!LastError = %s\n", ToolKits.getErrorCodePrint());
            }
            System.out.println("Channel " + channel + ". Handle: " + m_hAttachHandle.longValue() + ". Detach Succeed!");

            NetSDKLib.NET_IN_ATTACH_VIDEOSTAT_SUM inParam = new NetSDKLib.NET_IN_ATTACH_VIDEOSTAT_SUM();
            inParam.nChannel = channel;
            inParam.cbVideoStatSum = fVideoStatSumCallBack;

            NetSDKLib.NET_OUT_ATTACH_VIDEOSTAT_SUM outParam = new NetSDKLib.NET_OUT_ATTACH_VIDEOSTAT_SUM();
            m_hAttachHandle = LoginModule.netsdk.CLIENT_AttachVideoStatSummary(LoginModule.m_hLoginHandle, inParam, outParam, 5000);
            if (m_hAttachHandle.longValue() == 0) {
                System.err.printf("Attach Failed!LastError = %s\n", ToolKits.getErrorCodePrint());
            }
            m_hAttachMap.put(channel, m_hAttachHandle);
            System.out.printf("Attach Succeed at Channel %d ! AttachHandle: %d. Wait Device Notify Information\n", channel, m_hAttachHandle.longValue());
        }
    }

    // clear OSD info
    public static boolean clearVideoStateSummary(int channel) {
        NetSDKLib.NET_CTRL_CLEAR_SECTION_STAT_INFO info = new NetSDKLib.NET_CTRL_CLEAR_SECTION_STAT_INFO();
        info.nChannel = channel;
        info.write();
        if (!LoginModule.netsdk.CLIENT_ControlDevice(LoginModule.m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CLEAR_SECTION_STAT, info.getPointer(), 5000)) {
            System.err.printf("Clear Video State Summary Failed!LastError = %s\n", ToolKits.getErrorCodePrint());
            return false;
        }
        System.out.println("Clear Video State Summary Succeed!");
        return true;
    }
}
