package com.ruoyi.haikang.isup.utils;

/**
 * @author fengcheng
 */
public class OsSelect {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
