package com.shinemo.mpush.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    private static final String KEY = "1234567887654321";

    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * base 64 encrypt
     * @param bytes
     * @return
     */
    private static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base64 decode
     * @param base64Code
     * @return
     * @throws Exception
     */
    private static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ?null:new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * AES 加密
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    private static byte[] aesEncryptToBytes(String content,String encryptKey) throws Exception{
        KeyGenerator kegn = KeyGenerator.getInstance("AES");
        kegn.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(encryptKey.getBytes(),"AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String aesEncrypt(String content) throws Exception{
        String encryptKey = KEY;
        return base64Encode(aesEncryptToBytes(content,encryptKey));
    }

    private static String aesDecryptByBytes(byte[] encryptBytes,String decryptKey) throws Exception{
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(126);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(decryptKey.getBytes(),"AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String aesDecrypt(String encryptStr) throws Exception{
        String encryptKey = KEY;
        return StringUtils.isEmpty(encryptStr)? null:aesDecryptByBytes(base64Decode(encryptStr),KEY);
    }
}
