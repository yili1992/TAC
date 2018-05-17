package com.lee.tac.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import java.io.IOException;

public class EncryptionTool {

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) {
        Base64.Decoder decoder = Base64.getDecoder();
        String result=null;
        try {
            result = new String(decoder.decode(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) {
        byte[] textByte = new byte[0];
        try {
            textByte = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedText = encoder.encodeToString(textByte);
        return new String(encodedText);

    }

    public static void main(String[] args) {
//        String s = "我的字符串seasonszx";
//        System.out.println("原字符串：" + s);
//        String encryptString = encryptBASE64(s);
//        System.out.println("加密后：" + encryptString);
        System.out.println("解密后：" + decryptBASE64("L1VzZXJzL3poYW9saS9Eb3dubG9hZHMvZmlsZXMvMTUyNDYyNzAyOTQzNi54bWw="));
    }
}