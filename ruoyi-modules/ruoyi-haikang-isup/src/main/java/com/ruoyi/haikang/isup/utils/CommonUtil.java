package com.ruoyi.haikang.isup.utils;

import java.nio.charset.StandardCharsets;

public class CommonUtil {
    //SDK时间解析
    public static String parseTime(int time) {
        int dwYear = (time >> 26) + 2000;
        int dwMonth = (time >> 22) & 15;
        int dwDay = (time >> 17) & 31;
        int dwHour = (time >> 12) & 31;
        int dwMinute = (time >> 6) & 63;
        int dwSecond = (time >> 0) & 63;

        String sTime = String.format("%04d", dwYear) +
                String.format("%02d", dwMonth) +
                String.format("%02d", dwDay) +
                String.format("%02d", dwHour) +
                String.format("%02d", dwMinute) +
                String.format("%02d", dwSecond);
//        System.out.println(sTime);
        return sTime;
    }

    public static String parseHikvisionString(byte[] data) {
        if (data == null) {
            return "";
        }

        // 找到第一个 \0（C字符串结尾）
        int len = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                break;
            }
            len++;
        }
        if (len == 0) return "";

        try {
            // 关键：使用 GB2312 编码
            return new String(data, 0, len, "GB2312").trim();
        } catch (Exception e) {
            // 降级方案：尝试 UTF-8
            return new String(data, 0, len, StandardCharsets.UTF_8).trim();
        }
    }

    public static String toIpString(byte[] ipBytes) {
        if (ipBytes == null || ipBytes.length < 4) {
            return "";
        }
        // 判断是否为 IPv4（后12字节全0）
        boolean isIPv4 = true;
        for (int i = 4; i < 16; i++) {
            if (ipBytes[i] != 0) {
                isIPv4 = false;
                break;
            }
        }
        if (isIPv4) {
            return String.format("%d.%d.%d.%d", ipBytes[0] & 0xFF, ipBytes[1] & 0xFF, ipBytes[2] & 0xFF, ipBytes[3] & 0xFF);
        } else {
            // IPv6 处理（略）
            return new String(ipBytes, 0, 16, StandardCharsets.US_ASCII).trim();
        }
    }
}
