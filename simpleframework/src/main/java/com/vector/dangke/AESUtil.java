package com.vector.dangke;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static Logger logger= LoggerFactory.getLogger(AESUtil.class);

    public static String Encrypt(String sSrc, SecretKeySpec skeySpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            return Hex.encodeHexString(encrypted);
        } catch (Exception e) {
            logger.error("AESUtil.Encrypt aes error:" + e.getMessage());
        }
        return null;
    }


    public static String Decrypt(String sSrc, SecretKeySpec skeySpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(Hex.decodeHex(sSrc.toCharArray()));
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            logger.error("AESUtil.Decrypt aes error:" + e.getMessage());
        }
        return null;
    }
}
