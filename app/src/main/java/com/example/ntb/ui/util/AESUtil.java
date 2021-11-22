package com.example.ntb.ui.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ccy.
 * Date: 2021/10/26
 * Describe :AES加密手机号码
 */
public class AESUtil {
    private static String key = "toa47g2l9k8Rv7aQ";
    private static final String IV_STRING = "oQgIAxlUZqwDpBMN";
    private static final String charset = "UTF-8";

    /** RSA最大加密明文大小 */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /** 加密算法RSA */
    private static final String KEY_ALGORITHM = "RSA";

    private static final String IV_STRINGS = "32-Bytes--String";

    /**
     * @Title:
     * @Description: AES加密 （ios ok）
     * @param
     * @Date 2019/12/31
     * @return String
     * @throws
     * @author
     */
    public static String keyEncryptData(String content) {
        try {
            byte[] contentBytes = content.getBytes(charset);
            byte[] keyBytes = key.getBytes(charset);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            byte[] initParam = IV_STRING.getBytes(charset);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(contentBytes);
            return Base64Utils.encode(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title:
     * @Description: AES解密 （ios ok）
     * @param
     * @Date 2019/12/31
     * @return String
     * @throws
     * @author huangxw
     */
    public static String keyDecryptData(String key ,String content) {
        try {
//        	Map<String, String> map = getModulusAndKeys();
            String privateKey = key;
            byte[] encryptedBytes = Base64Utils.decode(content);
            byte[] keyBytes = privateKey.getBytes(charset);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            byte[] initParam = IV_STRING.getBytes(charset);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decryptedBytes =  cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title:
     * @Description: 密钥配置加载
     * @param
     * @Date 2019/12/31
     * @return String
     * @throws
     * @author huangxw
     */
    public static Map<String, String> getModulusAndKeys() {

        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream in = AESUtil.class
                    .getResourceAsStream("/system.properties");
            Properties prop = new Properties();
            prop.load(in);
            String privateKey = prop.getProperty("privateKeyAes");
            String publicteRsaKey = prop.getProperty("publicKey");
            String privateRsaKey = prop.getProperty("privateKey");
            in.close();
            map.put("privateKeyAes", privateKey);
            map.put("publicRsaKey", publicteRsaKey);
            map.put("privateRsaKey", privateRsaKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private static StringBuffer shortBuffer;
    /**
     * 产生随机密钥(这里产生密钥必须是16位)
     */
    public static String generateKey() {
//        String key = UUID.randomUUID().toString();
//        key = key.replace("-", "").substring(0, 16);// 替换掉-号
//        return key;
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,16).toString().trim();
        return uuid.toString();
    }






    /**
     * 公钥加密RSA
     *
     * @param data //加密后的密文
     * @return
     * @throws Exception
     */
    public static String encryptPublicKey(String data) throws Exception {

//		System.out.println("密钥配置输出："+map.get("publicKey"));
        byte[] dataByte = data.getBytes();
        byte[] keyBytes = Base64Utils.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFyCVQLYTzMWP/UheQTbRr8Muupid6Lo+G6L3h9lELK+XNSTuuzqzyQ67cDpNR+h9VHcsiPF7N2zc/USRxr9iYXYJ2 Obdgrs+bzNXdL0OssaZ1pps3XgZEw1Z1RveQjb0/1hHTJqT9qAXn2TlAEzzygHC4udlYgnyrvLyBJe73xwIDAQAB");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//RSA/ECB/PKCS1Padding
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = dataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据应对分段加密模式
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Utils.encode(encryptedData);
    }
}
