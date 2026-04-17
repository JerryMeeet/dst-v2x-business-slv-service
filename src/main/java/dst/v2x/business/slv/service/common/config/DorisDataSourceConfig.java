package dst.v2x.business.slv.service.common.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = {"dst.v2x.business.slv.service.infrastructure.doris.mapper"},
        sqlSessionFactoryRef = "dorisSessionFactory")
public class DorisDataSourceConfig {


    @Bean(name = "dorisDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.doris")
    public DataSource dorisDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 构建事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "dorisTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("dorisDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 构建sqlSession工厂
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "dorisSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dorisDataSource") DataSource dataSource,
                                               MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        // 修改为mp的SqlSessionFactoryBean
        final MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPlugins(mybatisPlusInterceptor);
        sessionFactoryBean.setTypeHandlersPackage("dst.v2x.business.slv.service.infrastructure.biz.*.entity");
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/doris/*.xml"));
        return sessionFactoryBean.getObject();
    }

    /**
     * 构建sqlSession
     * @param sqlSessionFactory
     * @return
     */
    @Primary
    @Bean(name = "dorisSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dorisSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}