package com.yuzhihao.learn.h2.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 初始化H2
 *
 * @author yuzhihao
 */
@Log4j2
@Configuration
public class H2SqlDataSourceScriptDatabaseInitializer extends SqlDataSourceScriptDatabaseInitializer {

    private boolean exec = false;

    public H2SqlDataSourceScriptDatabaseInitializer(DataSource dataSource, SqlInitializationProperties properties) {
        super(dataSource, properties);
    }

    @Override
    protected boolean isEmbeddedDatabase() {
        try {
            if (!exec) {
                exec = Boolean.TRUE.equals(new JdbcTemplate(super.getDataSource()).execute(new IsEmbedded()));
            }
            return exec;
        } catch (DataAccessException ex) {
            // Could not connect, which means it's not embedded
            return false;
        }
    }

    private static final class IsEmbedded implements ConnectionCallback<Boolean> {

        @Override
        public Boolean doInConnection(Connection connection) throws SQLException, DataAccessException {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet schemas = metaData.getTables(null, null, "SYS%", new String[]{"TABLE"});
            int count = 0;
            while (schemas.next()) {
                log.debug("h2 table name ：{}", schemas.getString("TABLE_NAME"));
                count++;
            }
            if (count > 0) {
                return false;
            }
            log.info("h2 database init ...");
            return true;
        }

    }
}
