package org.jxch.capital.client.config;

import com.clickhouse.jdbc.ClickHouseDataSource;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties("spring.clickhouse.datasource")
@MapperScan(basePackages = {"org.jxch.capital.client.db.clickhouse.dao"}, sqlSessionFactoryRef = ClickHouseConfig.CLICKHOUSE_SQL_SESSION_FACTORY)
public class ClickHouseConfig {
    public static final String CLICKHOUSE_JDBC_TEMPLATE = "clickhouseJdbcTemplate";
    public static final String CLICKHOUSE_DATASOURCE = "clickhouseDataSource";
    public static final String CLICKHOUSE_SQL_SESSION_FACTORY = "clickhouseSqlSessionFactory";
    public static final String CLICKHOUSE_TRANSACTION_MANAGER = "clickhouseTransactionManager";
    public static final String CLICKHOUSE_SQL_SESSION_TEMPLATE = "clickhouseSqlSessionTemplate";

    private String jdbcUrl;
    private String driverClassName;
    private String user;
    private String password;

    @SneakyThrows
    @Bean(CLICKHOUSE_DATASOURCE)
    public ClickHouseDataSource clickhouseDataSource(ClickHouseConfig config) {
        Properties properties = new Properties();
        properties.setProperty("jdbcUrl", config.jdbcUrl);
        properties.setProperty("driverClassName", config.driverClassName);
        properties.setProperty("user", config.user);
        properties.setProperty("password", config.password);
        return new ClickHouseDataSource(config.jdbcUrl, properties);
    }

    @Bean(CLICKHOUSE_JDBC_TEMPLATE)
    public JdbcTemplate clickhouseJdbcTemplate(@Qualifier(CLICKHOUSE_DATASOURCE) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(CLICKHOUSE_SQL_SESSION_FACTORY)
    public SqlSessionFactory clickhouseSqlSessionFactory(@Qualifier(CLICKHOUSE_DATASOURCE) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("/mapper/clickhouse/**/*.xml"));
        return bean.getObject();
    }

    @Bean(CLICKHOUSE_TRANSACTION_MANAGER)
    public DataSourceTransactionManager clickhouseTransactionManager(@Qualifier(CLICKHOUSE_DATASOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(CLICKHOUSE_SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate clickhouseSqlSessionTemplate(@Qualifier(CLICKHOUSE_SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

}
