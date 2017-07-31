package com.baidu.disconf.web.utils;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
         
         private final static String DES = "DES";
         
         private final static String PASSWORD_CRYPT_KEY = "__10lukou_";
         
         private final static int MAX_GENERATE_COUNT = 99999;
         
         private static int generateCount = 0;
         
         /**
         * 加密
         * 
          * @param src
         *            数据源
         * @param key
         *            密钥，长度必须是8的倍数
         * @return 返回加密后的数据
         * @throws Exception
         */
         public static byte[] encrypt(byte[] src, byte[] key) throws Exception
         {
                   // DES算法要求有一个可信任的随机数源
                   SecureRandom sr = new SecureRandom();
                   // 从原始密匙数据创建DESKeySpec对象
                   DESKeySpec dks = new DESKeySpec(key);
                   // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
                   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
                   SecretKey securekey = keyFactory.generateSecret(dks);
                   // Cipher对象实际完成加密操作
                   Cipher cipher = Cipher.getInstance(DES);
                   // 用密匙初始化Cipher对象
                   cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
                   // 现在,获取数据并加密,正式执行加密操作
                   return cipher.doFinal(src);
         }
         
         /**
         * 二进制转字符串
         * 
          * @param b
         * @return
         */
         public static String byte2hex(byte[] b)
         {
                   String hs = "";
                   String stmp = "";
                   for (int n = 0; n < b.length; n++)
                   {
                            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
                            if(stmp.length() == 1)
                                     hs = hs + "0" + stmp;
                            else
                                     hs = hs + stmp;
                   }
                   return hs.toUpperCase();
         }
         
         public static byte[] hex2byte(byte[] b)
         {
                   if((b.length % 2) != 0)
                            throw new IllegalArgumentException("长度不是偶数");
                   byte[] b2 = new byte[b.length / 2];
                   for (int n = 0; n < b.length; n += 2)
                   {
                            String item = new String(b, n, 2);
                            b2[n / 2] = (byte) Integer.parseInt(item, 16);
                   }
                   return b2;
         }
         
         /**
         * 解密
         * 
          * @param src
         *            数据源
         * @param key
         *            密钥，长度必须是8的倍数
         * @return 返回解密后的原始数据
         * @throws Exception
         */
         public static byte[] decrypt(byte[] src, byte[] key) throws Exception
         {
                   // DES算法要求有一个可信任的随机数源
                   SecureRandom sr = new SecureRandom();
                   // 从原始密匙数据创建一个DESKeySpec对象
                   DESKeySpec dks = new DESKeySpec(key);
                   // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
                   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
                   SecretKey securekey = keyFactory.generateSecret(dks);
                   // Cipher对象实际完成解密操作
                   Cipher cipher = Cipher.getInstance(DES);
                   // 用密匙初始化Cipher对象
                   cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
                   // 现在，获取数据并解密
                   // 正式执行解密操作
                   return cipher.doFinal(src);
         }
         
         /**
         * 原文加密(加密时调用这个方法即可) 解密和加密共用一把密匙:PASSWORD_CRYPT_KEY
         * 
          * @param content
         *            要加密的原文内容
         * @return 返回加密后的字符串
         * @throws Exception
         */
         public final static String encrypt(String content)
         {
                   try
                   {
                            return byte2hex(encrypt(content.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
                   }
                   catch (Exception e)
                   {
                            e.printStackTrace();
                            return null;
                   }
         }
         
         /**
         * 密文解密(解密时调用这个方法即可) 解密和加密共用一把密匙:PASSWORD_CRYPT_KEY
         * 
          * @param password
         *            密文
         * @return 返回解密后的内容
         * @throws Exception
         */
         public final static String decrypt(String password)
         {
                   try
                   {
                            return new String(decrypt(hex2byte(password.getBytes()), PASSWORD_CRYPT_KEY.getBytes()));
                   }
                   catch (Exception e)
                   {
                            e.printStackTrace();
                            return null;
                   }
         }
         
         public final static synchronized String unique()
         {
                   if(generateCount > MAX_GENERATE_COUNT)
                            generateCount = 0;
                   
                   String uniqueNumber = Long.toString(System.currentTimeMillis()) + Integer.toString(generateCount);
                   
                   generateCount++;
                   return uniqueNumber;
         }
         
         
         public static void main(String[] args)
         {
                   String pwd="Pfzd_2017";
                   String encryptpwd=encrypt(pwd);
                   System.out.println("加密后:"+encryptpwd);
                   String decryptpwd=decrypt(encryptpwd);
                   System.out.println("解密后:"+decryptpwd);
         }
         
}

