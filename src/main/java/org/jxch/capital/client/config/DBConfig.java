package org.jxch.capital.client.config;

import jakarta.persistence.EntityManagerFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.jxch.capital.client.db.pg.dao"})
@EntityScan(basePackages = {"org.jxch.capital.client.db.pg.po", "org.jxch.capital.client.db.pg.sharding.po"})
@MapperScan(basePackages = {"org.jxch.capital.client.db.pg.sharding.dao"}, sqlSessionFactoryRef = DBConfig.SHARDING_SQL_SESSION_FACTORY)
public class DBConfig {
    public static final String TRANSACTION_MANAGER = "transactionManager";
    public static final String SHARDING_SQL_SESSION_FACTORY = "SHARDING_SQL_SESSION_FACTORY";

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(TRANSACTION_MANAGER)
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(SHARDING_SQL_SESSION_FACTORY)
    public SqlSessionFactory shardingSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("/mapper/**/*.xml"));
        return bean.getObject();
    }

}
