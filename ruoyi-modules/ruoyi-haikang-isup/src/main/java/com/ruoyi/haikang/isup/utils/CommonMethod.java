package com.ruoyi.haikang.isup.utils;

import com.sun.jna.Pointer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CommonMethod {
    public static void WriteBuffToPointer(byte[] byData, Pointer pInBuffer) {
        pInBuffer.write(0, byData, 0, byData.length);
    }

    public static String byteToString(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }
        int iLengthOfBytes = 0;
        for (byte st : bytes) {
            if (st != 0) {
                iLengthOfBytes++;
            } else
                break;
        }
        String strContent = "";
        try {
            strContent = new String(bytes, 0, iLengthOfBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strContent;
    }

    public static byte[] UTF8toGBK(byte[] utf8Bytes) {
        String utf8Str = new String(utf8Bytes, StandardCharsets.UTF_8);
        byte[] gbkBytes = utf8Str.getBytes(Charset.forName("GBK"));
        return gbkBytes;
    }

    public static String UTF8toGBKStr(byte[] utf8Bytes) {
        return new String(UTF8toGBK(utf8Bytes), Charset.forName("GBK"));
    }

    public static String getResFileAbsPath(String filePath) {
        if (filePath == null) {
            throw new RuntimeException("filePath null error!");
        }
        return System.getProperty("user.dir") + "\\resources\\" + filePath;
    }

    public static String getConsoleInput(String inputTip) {
        System.out.println(inputTip);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void outputToFile(String fileName, String postFix, String fileContent) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss_SSS");

        String folder = System.getProperty("user.dir") + "\\outputFiles\\event\\";
        File directory = new File(folder);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                System.out.println(folder + "_文件夹创建失败！");
            }
        }

        String filePath = folder + fileName + "_" + format.format(new Date()) + postFix;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileContent.getBytes());
            fos.close();
        } catch (IOException e) {
            System.out.println("输出到文件出现异常：" + e.getMessage());
        }
    }
}
