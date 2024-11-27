package com.yuzhihao.learn.h2.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 *
 * 配置数据源连接
 *
 * @author yuzhihao
 */
@Configuration
@AllArgsConstructor
public class DataSourceConfig {

    private final DataSourceProperties dataSourceProperties;

    private final ConfigurableEnvironment environment;

    @Bean
    public DataSource dataSource(){
        // 获取操作系统名称
        String osName = System.getProperty("os.name").toLowerCase();
        boolean packaged = Objects.nonNull(System.getProperty("is.packaged"));
        String appName = environment.getProperty("spring.application.name","vlcj").toLowerCase();

        String url = "jdbc:h2:file:./db/vlcj.db";
        if(packaged){
            if (osName.contains("mac")) {
                Path dir = Paths.get(System.getProperty("user.home"), "Library", "Application Support", appName);
                url = "jdbc:h2:file:".concat(dir.toString()).concat("/vlcj.db");
            } else if (osName.contains("win")) {
                Path dir = Paths.get(System.getenv("APPDATA"), appName);
                url = "jdbc:h2:file:".concat(dir.toString()).concat("/vlcj.db");
            }
        }
        // 更新数据源 URL
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setDriverClassName("org.h2.Driver");
        // 创建数据源
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
