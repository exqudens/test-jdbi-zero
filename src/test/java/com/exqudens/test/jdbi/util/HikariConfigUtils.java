package com.exqudens.test.jdbi.util;

import com.zaxxer.hikari.HikariConfig;

public class HikariConfigUtils {

    public static HikariConfig hikariConfig(
            String host,
            String schema,
            Integer port,
            String username,
            String password,
            Boolean readOnly
    ) {
        return hikariConfig(
                "jdbc",
                "mysql",
                host,
                port,
                schema,
                "?createDatabaseIfNotExist=true&rewriteBatchedStatements=true&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&characterSetResults=UTF-8&logger=com.mysql.cj.core.log.Slf4JLogger&profileSQL=true",
                username,
                password,
                "com.mysql.cj.jdbc.Driver",
                40000L,
                readOnly,
                1
        );
    }

    public static HikariConfig hikariConfig(
            String protocol,
            String subProtocol,
            String host,
            Integer port,
            String schema,
            String jdbcUrlSuffix,
            String username,
            String password,
            String driverClassName,
            Long connectionTimeout,
            Boolean readOnly,
            Integer maximumPoolSize
    ) {
        String jdbcUrl = String.join(
                "",
                protocol,
                ":",
                subProtocol,
                "://",
                host,
                ":",
                String.valueOf(port),
                "/",
                schema,
                jdbcUrlSuffix,
                "&failOverReadOnly=" + !readOnly
        );

        return hikariConfig(jdbcUrl, username, password, driverClassName, connectionTimeout, readOnly, maximumPoolSize);
    }

    public static HikariConfig hikariConfig(
            String jdbcUrl,
            String username,
            String password,
            String driverClassName,
            Long connectionTimeout,
            Boolean readOnly,
            Integer maximumPoolSize
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        hikariConfig.setReadOnly(readOnly);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);

        return hikariConfig;
    }

}
