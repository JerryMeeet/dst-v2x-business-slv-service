package dst.v2x.business.slv.service.common.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 加密解密工具类
 */
public class EncryptUtils {
    public static final String key = "j3HkHhWrT93W7adt";

    public static String getIv() {
        String iv = key.substring(0, 5);
        return String.format("%1$-16s", iv).replace(' ', '0');
    }

    /**
     * 加密数据
     */
    public static String encrypt(String data) {
        if(StringUtils.isEmpty(data)){
            return "";
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(getIv().getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
            return data;
        }
    }

    /**
     * 解密数据
     */
    public static String decrypt(String data) {
        if(StringUtils.isEmpty(data)){
            return "";
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(getIv().getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
            return data;
        }
    }

}