package dst.v2x.business.slv.service.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.dst.steed.vds.common.base.domain.user.SSOUser;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.base.IsDeleteEnum;
import dst.v2x.business.slv.service.common.utils.DstUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

/**
 * mybatis-plus 配置
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@Primary
public class MybatisPlusConfig implements MetaObjectHandler {
    /**
     * 分页插件3.5x
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认500条-1,表示不受限制
        /*paginationInnerInterceptor.setMaxLimit(-1L);*/
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        //开启 count 的join优化，只针对部分left join
        paginationInnerInterceptor.setOptimizeJoin(true);
        return paginationInnerInterceptor;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
        return mybatisPlusInterceptor;
    }

    /**
     * 设置自动注入
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "modifyTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "isDeleted", Boolean.class, IsDeleteEnum.EFFECTIVE.getIndex());
        //待框架提供再完善
//        Optional<SSOUser> user = DstUserUtil.getUser();
        Optional<SSOUser> user = null;
        if (user != null && user.isPresent() && user.get() != null) {
            SSOUser currentUser = user.get();
            log.debug(">>>插入操作,用户中心返回的登录人信息为:{}", DstJsonUtil.toString(currentUser));
            this.strictInsertFill(metaObject, "creator", String.class, String.valueOf(currentUser.getId()));
            this.strictInsertFill(metaObject, "creatorName", String.class, currentUser.getRealname());
            this.strictInsertFill(metaObject, "modifier", String.class, String.valueOf(currentUser.getId()));
            this.strictInsertFill(metaObject, "modifierName", String.class, currentUser.getRealname());
        } else {
            log.debug(">>>插入操作,用户中心返回的登录人信息为空，默认系统操作:");
            this.strictInsertFill(metaObject, "creator", String.class, String.valueOf(DstUserUtil.ADMIN_ID));
            this.strictInsertFill(metaObject, "creatorName", String.class, DstUserUtil.ADMIN_NAME);
            this.strictInsertFill(metaObject, "modifier", String.class, String.valueOf(DstUserUtil.ADMIN_ID));
            this.strictInsertFill(metaObject, "modifierName", String.class, DstUserUtil.ADMIN_NAME);
        }
    }

    /**
     * 设置自动更新
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "modifyTime", Date.class, new Date());
        Optional<SSOUser> user = DstUserUtil.getUser();
        if (user != null && user.isPresent() && user.get() != null) {
            SSOUser currentUser = user.get();
            log.debug(">>>更新操作,用户中心返回的登录人信息为:{}", DstJsonUtil.toString(currentUser));
            this.strictUpdateFill(metaObject, "modifier", String.class, String.valueOf(currentUser.getId()));
            this.strictInsertFill(metaObject, "modifierName", String.class, currentUser.getRealname());
        } else {
            log.debug(">>>更新操作,用户中心返回的登录人信息为空,默认系统操作");
            this.strictUpdateFill(metaObject, "modifier", String.class, String.valueOf(DstUserUtil.ADMIN_ID));
            this.strictInsertFill(metaObject, "modifierName", String.class, DstUserUtil.ADMIN_NAME);
        }
    }

    /**
     * 加解密
     *
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new MybatisPlusCustomizers();
    }

    static class MybatisPlusCustomizers implements ConfigurationCustomizer {
        @Override
        public void customize(MybatisConfiguration configuration) {
            configuration.getTypeHandlerRegistry().register(LocalDateTime.class, EncryptAndDecryptHandler.class);
        }
    }
}
