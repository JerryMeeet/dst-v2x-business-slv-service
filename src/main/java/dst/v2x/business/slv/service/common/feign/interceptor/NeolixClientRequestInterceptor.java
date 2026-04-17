package dst.v2x.business.slv.service.common.feign.interceptor;

import com.dst.steed.vds.common.util.DstSpringUtil;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.service.NeolixRemoteApiService;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;


/**
 * 新石器-请求配置拦截器
 */
@Slf4j
public class NeolixClientRequestInterceptor implements RequestInterceptor {

    private String clientSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        Request request = requestTemplate.request();
        String timestamp = String.valueOf(System.currentTimeMillis());
        // nonce取两位随机整数
        String nonce = generateNonce();

        NeolixRemoteApiService neolixRemoteApiService =  DstSpringUtil.getBean(NeolixRemoteApiService.class);

        String accessToken = neolixRemoteApiService.getNeolixToken();

        // 计算sign
        String signString = getSignature(timestamp, nonce, clientSecret);

        // 构造请求参数
        String param;
        if(requestTemplate.url().contains("?")){
            param = "&";
        }else {
            param = "?";
        }
        param += "signature=" + signString + "&timeStamp=" + timestamp + "&nonce=" + nonce + "&access_token=" + accessToken;
        String requestBodyStr;
        if (Objects.isNull(request.body())) {
            requestBodyStr = "";
        }else {
            requestBodyStr = new String(request.body(), StandardCharsets.UTF_8);
        }

        // 执行请求
        log.debug("新石器请求地址url：{}，请求参数：{}", requestTemplate.feignTarget().url() + requestTemplate.url() + param, requestBodyStr);
        String requestUrl = requestTemplate.url() + param;
        requestTemplate.uri(requestUrl);

    }

    /**
     * 两位随机整数
     */
    public String generateNonce() {
        SecureRandom random = new SecureRandom();
        return String.format("%02d", random.nextInt(100));
    }

    /**
     * 生成签名
     *
     * @param timestamp 秒级时间戳
     * @param nonce     两位随机整数
     * @param appSecret 即client_secret
     * @return
     */
    public static String getSignature(String timestamp, String nonce, String appSecret) {
        //对token,timestamp nonce 按字典排序
        String[] paramArr = new String[]{appSecret, timestamp, nonce};
        Arrays.sort(paramArr);    //将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //对拼接后的字符串进行sha1加密 update使用指定的字节数组对摘要进行最后更新
            byte[] digest = md.digest(content.toString().getBytes());
            //完成摘要计算
            ciphertext = byteToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将sha1加密后的字符串与signature进行对比
        return ciphertext;
    }

    /**
     * 字节转成字符串
     */
    public static String byteToString(byte[] digest) {
        try {
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为十六进制数
            for (int i = 0; i < digest.length; i++) {
                String shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
