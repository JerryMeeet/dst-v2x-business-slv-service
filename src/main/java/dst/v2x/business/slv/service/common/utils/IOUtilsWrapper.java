package dst.v2x.business.slv.service.common.utils;

import com.dst.steed.vds.common.base.exception.BusinessException;
import dst.v2x.business.slv.service.common.enums.base.ApiCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * IOUtils工具类
 */
@Slf4j
public final class IOUtilsWrapper extends IOUtils {

    private IOUtilsWrapper() {

    }

    /**
     * 生成文件
     *
     * @param data
     * @param filePath
     */
    public static void write(String filePath, byte[] data) {
        try (OutputStream output = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
            IOUtils.write(data, output);
        } catch (NoSuchFileException e) {
            log.error("文件不存在: {}", filePath, e);
            throw new BusinessException(ApiCode.ERROR.getErrcode(), "文件不存在");
        } catch (IOException e) {
            log.error("生成文件失败: {}", filePath, e);
            throw new BusinessException(ApiCode.ERROR.getErrcode(), "生成文件失败");
        }
    }

    /**
     * 读取文件byte数组
     *
     * @param filePath 文件路径
     * @return 文件内容的byte数组
     */
    public static byte[] toByteArray(String filePath) {
        try (InputStream input = new BufferedInputStream(Files.newInputStream(Paths.get(filePath)))) {
            return IOUtils.toByteArray(input);
        } catch (NoSuchFileException e) {
            // 专门处理文件不存在或路径错误的情况
            log.error("文件不存在或路径错误: {}", filePath, e);
            throw new BusinessException(ApiCode.ERROR.getErrcode(), "文件不存在");
        } catch (IOException e) {
            // 处理其他IO异常
            log.error("文件转byte数组失败: {}", filePath, e);
            throw new BusinessException(ApiCode.ERROR.getErrcode(), "文件转byte数组失败");
        }
    }

}