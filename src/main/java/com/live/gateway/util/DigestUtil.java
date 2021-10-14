package com.live.gateway.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author 胡学汪
 * @Description
 *      APIKEY: RandomStringUtils.randomAlphanumeric(32).toUpperCase()
 * @Date 创建于 2021/10/14 14:40
 */
public class DigestUtil {

    private static final String MD5_ALGORITHM_NAME = "MD5";

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    public static byte[] md5Digest(byte[] bytes) {
        return digest(MD5_ALGORITHM_NAME, bytes);
    }

    private static byte[] digest(String algorithm, byte[] bytes) {
        return getDigest(algorithm).digest(bytes);
    }

    public static String digestAsHexString(String algorithm, byte[] bytes) {
        char[] hexDigest = digestAsHexChars(algorithm, bytes);
        return new String(hexDigest);
    }

    public static String md5DigestAsHexString(byte[] bytes) {
        return digestAsHexString(MD5_ALGORITHM_NAME, bytes);
    }

    private static char[] digestAsHexChars(String algorithm, byte[] bytes) {
        byte[] digest = digest(algorithm, bytes);
        return encodeHex(digest);
    }

    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }

}
