package dst.v2x.business.slv.service.common.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"dst.v2x.business.slv.service.infrastructure.biz.*.mapper"},
        sqlSessionFactoryRef = "mysqlSessionFactory")
public class MysqlDataSourceConfig {
    @Autowired
    private MybatisPlusConfig mybatisPlusConfig;

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 构建事务管理器
     * @param dataSource
     * @return
     */
    @Primary
    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 构建sqlSession工厂
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "mysqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource,
                                               MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        // 修改为mp的SqlSessionFactoryBean
        final MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setGlobalConfig(globalConfig()); // 关键：绑定全局配置
        sessionFactoryBean.setTypeHandlersPackage("dst.v2x.business.slv.service.infrastructure.biz.*.entity");
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/mysql/*.xml"));
        sessionFactoryBean.setPlugins(mybatisPlusInterceptor);
        return sessionFactoryBean.getObject();
    }

    private GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(mybatisPlusConfig); // 绑定自动填充处理器
        return globalConfig;
    }

    /**
     * 构建sqlSession
     * @param sqlSessionFactory
     * @return
     */
    @Primary
    @Bean(name = "mysqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}