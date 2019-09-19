package com.zhouww.first.springboot.CommonUtils;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 加解密
 * 
 *
 */
public class RSACoder {

    /**
     * 算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 算法/工作模式/填充方式
     */
    public final static String CHIPER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 密钥长度
     */
    public static final int KEY_SIZE1024 = 1024;
    public static final int KEY_SIZE2048 = 2048;

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";
    public static final String SIGNATURE_ALGORITHMS_SHA256WITHRSA = "SHA256withRSA";

    /**
     * 用私钥对信息生成数字签名
     * 
     * @param data
     *            加密数据
     * @param privateKey
     *            私钥
     * @return
     */
    public static byte[] sign(byte[] data, byte[] privateKey) {
        try {
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);

            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }
    }
    /**
     * 用私钥对信息生成数字签名
     *
     * @param data
     *            加密数据
     * @param privateKey
     *            私钥
     * @return
     */
    public static byte[] signSHA256withRSA(byte[] data, byte[] privateKey) {
        try {
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHMS_SHA256WITHRSA);
            signature.initSign(priKey);
            signature.update(data);

            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }
    }

    public static String sign(String content, String privateKey)   {

        try {
            byte[] privateKeyByte=Base64.decodeBase64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHMS_SHA256WITHRSA);
        signature.initSign(priKey);
        signature.update(content.getBytes());
        byte[] signed = signature.sign();
//		return (new BASE64Encoder()).encodeBuffer(signed);
        return Hex.encodeHexString((signed));
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }
    }


    /**
     * 校验数字签名
     * 
     * @param data
     *            数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * @return
     */
    public static boolean verifyByPublicKeySHA256withRSA(byte[] data, byte[] publicKey, byte[] sign) {

        try {
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取公钥匙对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHMS_SHA256WITHRSA);
            signature.initVerify(pubKey);
            signature.update(data);

            // 验证签名是否正常
            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }

    }
    /**
     * 校验数字签名
     *
     * @param data
     *            数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * @return
     */
    public static boolean verifyByPublicKey(byte[] data, byte[] publicKey, byte[] sign) {

        try {
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取公钥匙对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);

            // 验证签名是否正常
            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException("Error when verify sign, ermsg: " + e.getMessage(), e);
        }

    }

    /**
     * 私钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) {
        try {
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            // 生成私钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when decrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    /**
     * 公钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) {
        try {
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            // 生成公钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when decrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    /**
     * 公钥加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) {
        try {
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            // 生成公钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when encrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) {
        try {
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            // 生成私钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Error when encrypt key, ermsg: " + e.getMessage(), e);
        }
    }

    public static Map<String, Object> initKey() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(KEY_SIZE2048);

            KeyPair keyPair = keyPairGen.generateKeyPair();

            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, Object> keyMap = new HashMap<String, Object>();
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new RuntimeException("Error when init key, ermsg: " + e.getMessage(), e);
        }
    }

    public static String decryptAES(String key, String text) {
        try {
            if (key == null) {
                throw new RuntimeException("key is null");
            } else if (16 != key.length() && 24 != key.length() && 32 != key.length()) {
                throw new RuntimeException("key must be 16/24/32");
            } else {
                byte[] raw = key.getBytes("UTF-8");
                SecretKeySpec sKeySpec = new SecretKeySpec(raw, KEY_ALGORITHM_AES);
                Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
                cipher.init(2, sKeySpec);

                try {
                    byte[] original = cipher.doFinal(parseHexStr2Byte(text));
                    return new String(original, "UTF-8");
                } catch (Exception var9) {
                    throw new RuntimeException(var9);
                }
            }
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
    private static final String KEY_ALGORITHM_AES = "AES";
    /**
     * AES证书加密
     *
     * @param key
     * @param data
     * @return
     */
    public static String encryptAES(String key, String data) {
        try {
            if (key == null) {
                throw new RuntimeException("key is null");
            } else if (16 != key.length() && 24 != key.length() && 32 != key.length()) {
                throw new RuntimeException("key must be 16/24/32");
            } else {
                byte[] raw = key.getBytes("UTF-8");
                SecretKeySpec sKeySpec = new SecretKeySpec(raw, KEY_ALGORITHM_AES);
                Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
                cipher.init(1, sKeySpec);
                byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
                return parseByte2HexStr(encrypted);
            }
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();

        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toLowerCase());
        }
        return sb.toString();
    }
    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for (int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }
    public static byte[] intToBCD(int len) {
        byte[] bcdByte = new byte[2];
        bcdByte[0] = (byte) ((len / 100) / 10 * 0x10 + (len / 100) % 10);
        bcdByte[1] = (byte) ((len % 100) / 10 * 0x10 + (len % 100) % 10);
        return bcdByte;
    }

    public static byte[] generateDesKey(int length) throws Exception {
        //实例化
        KeyGenerator kgen = null;
        kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(length);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return skey.getEncoded();

    }
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
    ///Map<String,Object> map  =initKey();
        //System.out.println("公钥:" + Base64.encodeBase64String(getPublicKey(map)));
        //System.out.println("私钥:" + Base64.encodeBase64String(getPrivateKey(map)));
        //encryptByPublicKey(byte[] data, byte[] key)
      //  System.out.println("jiami:" + Base64.encodeBase64String(getPublicKey(map)));
        //System.out.println("私钥:" + Base64.encodeBase64String(getPrivateKey(map)));

      //  System.out.println("jiami:" + encoder.encodeToString(generateDesKey(128)));
        System.out.println("jiami:" + RSACoder.encryptAES("xry5eihgCbglNXef", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoWZ6DON6GWqiGVhi7OhEFQhG3snHaLn6+P0v1urvhYSbEF7+BkUD9QUmf0QOWwpI515f0Ri6uC2ShY9LJxDTq8p+EeI3SqlhcPwtwK4Y8bwyYcTJTAaz2+aR0NpLfMW/Tz0jVL5mwbGQbjJvytGvZNEjKcmiE+rZgcWpdin4qRxPKEnCbvI560ri1GMtTihGT3iFfw9wvDzbsSY9bR59sZmKCU2FKJua1A1AXImR7eRIqc0li+6dMAOSse1qq0poZO6MqmxH/+W+vInbiKLl5NWEPo6NyBeqJ/QXeyapNWcmaePAqpTyECGXQRa/oHX1PrFgVVfOZGCJHCgWhRXpswIDAQAB"));



        /*
         * Map<String, Object> map = initKey(); byte[] pri =
         * ((Key)map.get(PRIVATE_KEY)).getEncoded(); String priKey =
         * EncodeUtil.bytesToHexString(pri); System.out.println(priKey);
         * System.out.println("--------"); byte[] pub =
         * ((Key)map.get(PUBLIC_KEY)).getEncoded(); String pubKey =
         * EncodeUtil.bytesToHexString(pub); System.out.println(pubKey);
         */

        //		String data = "1234567890AVBCDS";
        //		byte[] result = encryptByPrivateKey(data.getBytes(), EncodeUtil
        //				.hexStringToByte(Constants.RSAPRIKEY));
        //
        //		byte[] deRe = decryptByPublicKey(result, EncodeUtil
        //				.hexStringToByte(Constants.RSAPUBKEY));
        //
        //		System.out.println(EncodeUtil.bytesToHexString(result));
        //		System.out.println("-------");
        //		System.out.println(new String(deRe, "UTF-8"));
        // 生成之后，采用不同的方式来转换成16进制字符串
        // iphone生成方式
        /*
         * System.out.println(EncodeUtil.bytesToHexString(privateKey.getModulus()
         * .toByteArray()));
         * System.out.println(EncodeUtil.bytesToHexString(privateKey
         * .getPrivateExponent().toByteArray()));
         */
        // java生成方式
        /*
         * System.out.println(EncodeUtil.bytesToHexString(publicKey.getEncoded())
         * );
         * System.out.println(EncodeUtil.bytesToHexString(privateKey.getEncoded
         * ()));
         */

        //		Map keyMap = initKey();
        //		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
        //		RSAPrivateKey privateKey = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
        //		System.out.println("public:"+EncodeUtil.bytesToHexString(publicKey.getEncoded()));
        //		System.out.println("private:"+EncodeUtil.bytesToHexString(privateKey.getEncoded()));
    }

}
