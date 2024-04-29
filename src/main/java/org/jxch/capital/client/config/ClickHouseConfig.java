package org.jxch.capital.client.config;

import com.clickhouse.jdbc.ClickHouseDataSource;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties("spring.clickhouse.datasource")
public class ClickHouseConfig {
    public static final String CLICKHOUSE_JDBC_TEMPLATE = "clickhouseJdbcTemplate";
    public static final String CLICKHOUSE_DATASOURCE = "clickhouseDataSource";

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

}
