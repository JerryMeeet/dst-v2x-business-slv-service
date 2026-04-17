package dst.v2x.business.slv.service.common.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.dst.steed.vds.common.base.domain.user.SSOUser;
import com.dst.steed.vds.common.constant.MinConstant;
import com.dst.steed.vds.common.util.DstThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @Author zhang
 * @create 2024/9/4 11:20
 */
public class DstUserUtil {

    // ========= 默认超级管理员 =========

    public static final Long ADMIN_ID = 1L;

    public static final String ADMIN_NAME = "system/系统自动推送";

    public static final String ADMIN_USER_NAME = "system";

    public static final String ADMIN_REAL_NAME = "系统自动推送";

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID
     */
    public static Long getCurrentUserId() {
        SSOUser ssoUser = DstUserUtil.getUser().orElse(new SSOUser());
        return ssoUser.getId();
    }

    /**
     * 获取当前用户所属组织ID
     *
     * @return 当前用户所属组织ID
     */
    public static Long getCurrentOrgId() {
        return 1L;
    }

    /**
     * 获取当前用户名称
     *
     * @return 当前用户名称
     */
    public static String getCurrentUserNameOrNull() {
        //return "admin";
        SSOUser ssoUser = DstUserUtil.getUser().orElse(new SSOUser());
        if (StrUtil.isNotBlank(ssoUser.getJobNumber())) {
            return StrUtil.concat(true, ssoUser.getUsername(), "/", ssoUser.getJobNumber());
        }
        return ssoUser.getUsername();
    }

    public static Optional<SSOUser> getUser() {
        /*SSOUser user = new SSOUser();
        user.setId(9999L);
        user.setUsername("admin");
        user.setRealname("admin");*/
        return Optional.ofNullable(getUserInfo());
    }

    public static String getUserToken() {
        return Optional.ofNullable(DstThreadLocalUtil.getRequest())
                .map(request -> request.getHeader(MinConstant.HEADER_TOKEN))
                .orElse(null);
    }

    public static SSOUser getUserInfo() {
        return Optional.ofNullable(DstThreadLocalUtil.getRequest())
                .map(request -> request.getHeader(MinConstant.HEADER_USER))
                .filter(StringUtils::isNotEmpty)
                .map(header -> JSONObject.parseObject(URLDecoder.decode(header, StandardCharsets.UTF_8), SSOUser.class))
                .orElse(null);
    }

}
