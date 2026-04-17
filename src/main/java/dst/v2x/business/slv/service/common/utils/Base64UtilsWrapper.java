package dst.v2x.business.slv.service.common.utils;

import com.dst.steed.vds.common.base.exception.BusinessException;
import dst.v2x.business.slv.service.common.enums.base.ApiCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

/**
 * Base64Utils工具类
 */
@Slf4j
public final class Base64UtilsWrapper {

    /**
     * base64文件解码
     *
     * @param base64
     * @return
     */
    public static byte[] decodeFromString(String base64) {
        try {
            return Base64.getDecoder().decode(base64);
        } catch (Exception e) {
            log.error("base64文件解码失败", e);
            throw new BusinessException(ApiCode.SYSTEM_ERROR.getErrcode(), "base64文件解码失败");
        }
    }

    /**
     * base64文件转码
     *
     * @param src
     * @return
     */
    public static String encodeToString(byte[] src) {
        try {
            return Base64.getEncoder().encodeToString(src);
        } catch (Exception e) {
            log.error("base64文件转码失败", e);
            throw new BusinessException(ApiCode.SYSTEM_ERROR.getErrcode(), "base64文件转码失败");
        }
    }


    /**
     * base64文件转码
     *
     * @param src
     * @return
     */
    public static String encodeToString(String src) {
        try {
            return Base64.getEncoder().encodeToString(src.getBytes());
        } catch (Exception e) {
            log.error("base64文件转码失败", e);
            throw new BusinessException(ApiCode.SYSTEM_ERROR.getErrcode(), "base64文件转码失败");
        }
    }
}