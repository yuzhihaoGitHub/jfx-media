package com.yuzhihao.learn.h2.config;

import com.yuzhihao.learn.config.VlcRuntime;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@Configuration
@AllArgsConstructor
public class DataSourceConfig {

    private final DataSourceProperties dataSourceProperties;

    private final ConfigurableEnvironment environment;

    @Bean
    public DataSource dataSource(){
        // 获取当前工作目录
        log.info("应用程序目录: " + VlcRuntime.USER_DIR);

        // 也可以尝试获取 java.home
        log.info("Java 安装目录: " + VlcRuntime.JAVA_HOME);
        // 获取操作系统名称
        String osName = System.getProperty("os.name").toLowerCase();
        boolean packaged = Objects.nonNull(System.getProperty("is.packaged"));
        String appName = environment.getProperty("spring.application.name","vlcj").toLowerCase();

        String url = "jdbc:h2:file:${vlcj}/db/vlcj.db";//
        String dir = ".";
        if(packaged){
            dir = Paths.get(VlcRuntime.JAVA_HOME,  appName).toString();
//            if (osName.contains("mac")) {
//                dir = Paths.get(System.getProperty("user.home"), "Library", "Application Support", appName).toString();
//            } else if (osName.contains("win")) {
//                dir = Paths.get(System.getenv("APPDATA"), appName).toString();
//            }
        }
        // 更新数据源 URL
        dataSourceProperties.setUrl(url.replace("${vlcj}",dir));
        dataSourceProperties.setDriverClassName("org.h2.Driver");
        // 创建数据源
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
