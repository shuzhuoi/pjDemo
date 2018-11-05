package com.shuzhuo.core.common.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springside.modules.utils.Encodes;

import com.shuzhuo.core.common.exception.CustomException;

/**
 * 生成token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-20 14:41
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new CustomException("生成Token失败", e);
        }
    }
    
    /**
     * 生成加盐值的密码
     */
    public static String entryptPassword(String plainPassword,String salt) {
        String plain = Encodes.unescapeHtml(plainPassword);
        Sha256Hash sha = new Sha256Hash(plain, salt);
        return sha.toHex();  
    }
    
    public static void main(String[] args) {
		System.out.println(entryptPassword("123456","123456"));
	}
}
