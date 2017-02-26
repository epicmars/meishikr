package com.sin2pi.brick.common.utils.crypt;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * MD5, SHA-1 → SHA-256, SHA-384, SHA-512
 *
 * @author Christopher
 */
public class MessageDigestUtil {

    private static final String ALGR_MD5 = "MD5";
    private static final String ALGR_SHA1 = "SHA-1";
    private static final String ALGR_SHA256 = "SHA-256";
    private static final String ALGR_PBKDF2_WITH_HMACSHA1 = "PBKDF2WithHmacSHA1";
    //
    private static final String NG_ALGR_SHA1PRNG = "SHA1PRNG";
    //
    private static final String UTF8 = "UTF-8";

    //
    private static final int SALT_BYTE_SIZE = 16;
    private static final int HASH_BYTE_SIZE = 24;

    public static String hashServerWithPBKDF2(CharSequence in) {
        int iterationCount = 1000;
        char[] chars = in.toString().toCharArray();
        String salt = generateSalt();
        if (salt != null) {
            // TODO
        }

        PBEKeySpec keySpec = new PBEKeySpec(chars, salt.getBytes(), iterationCount, HASH_BYTE_SIZE * 8);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGR_PBKDF2_WITH_HMACSHA1);
            return toHexString(skf.generateSecret(keySpec).getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算输入字符序列的hash值，使用SHA-256算法
     *
     * @param in 输入字符序列
     * @return hash值16进制字符串
     */
    public static String hashWithSHA256(CharSequence in) {
        return hashWithAlgorithm(ALGR_SHA256, in);
    }

    /**
     * 计算输入字符序列的hash值，使用SHA-1算法
     *
     * @param in 输入字符序列
     * @return hash值16进制字符串
     */
    public static String hashWithSHA1(CharSequence in) {
        return hashWithAlgorithm(ALGR_SHA1, in);
    }

    /**
     * 计算输入字符序列的hash值，使用MD5算法
     *
     * @param in 输入的字符序列
     * @return hash值16进制字符串
     */
    public static String hashWithMD5(CharSequence in) {
        return hashWithAlgorithm(ALGR_MD5, in);
    }

    /**
     * 使用选定的算法计算hash值
     *
     * @param algorithm 选用的算法
     * @param in        输入
     * @return
     */
    private static String hashWithAlgorithm(final String algorithm, final CharSequence in) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] input = in.toString().getBytes(Charset.forName(UTF8));
            digest.update(input);
            byte[] output = digest.digest();
            return toHexString(output);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节转换为16进制字符串
     *
     * @param data 要转换的字节
     * @return b的16进制字符串表示
     */
    private static String toHexString(byte[] data) {
        String result = null;
        if (data != null && data.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                sb.append(Integer.toHexString(data[i] & 0xFF | 0x100).substring(1));
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 生成随机salt
     *
     * @return
     */
    private static String generateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance(NG_ALGR_SHA1PRNG);
            byte[] salt = new byte[SALT_BYTE_SIZE];
            sr.nextBytes(salt);
            return toHexString(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        final String password = "__crakck";
        String result1 = hashWithMD5(password);
        String result3 = hashWithSHA1(password);
        String result4 = hashWithSHA256(password);
        String result5 = hashServerWithPBKDF2(password);
        System.out.println("md5: " + result1.length() + ":" + result1);
        System.out.println("sha1: " + result3.length() + ":" + result3);
        System.out.println("sha256: " + result4.length() + ":" + result4);
        System.out.println("pbkdf2: " + result5.length() + ":" + result5);
        final long finish = System.currentTimeMillis();
        System.out.println("used " + (finish - start) / 1000 + " seconds.");
    }
}
