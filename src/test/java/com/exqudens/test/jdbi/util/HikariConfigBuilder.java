package com.exqudens.test.jdbi.util;

import com.zaxxer.hikari.HikariConfig;

public class HikariConfigBuilder {

    public static HikariConfigBuilder builder() {
        return new HikariConfigBuilder();
    }

    private String protocol;
    private String subProtocol;
    private String host;
    private Integer port;
    private String schema;
    private String jdbcUrl;
    private String jdbcUrlSuffix;
    private String username;
    private String password;
    private String driverClassName;
    private Long connectionTimeout;
    private Boolean readOnly;
    private Integer maximumPoolSize;

    private HikariConfigBuilder() {
        this(
                "jdbc",
                "mysql",
                "localhost",
                3306,
                "",
                null,
                "?createDatabaseIfNotExist=true&rewriteBatchedStatements=true&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&characterSetResults=UTF-8&logger=com.mysql.cj.core.log.Slf4JLogger&profileSQL=true",
                "root",
                null,
                "com.mysql.cj.jdbc.Driver",
                40_000L,
                false,
                1
        );
    }

    public HikariConfigBuilder(
            String protocol,
            String subProtocol,
            String host,
            Integer port,
            String schema,
            String jdbcUrl,
            String jdbcUrlSuffix,
            String username,
            String password,
            String driverClassName,
            Long connectionTimeout,
            Boolean readOnly,
            Integer maximumPoolSize
    ) {
        this.protocol = protocol;
        this.subProtocol = subProtocol;
        this.host = host;
        this.port = port;
        this.schema = schema;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUrlSuffix = jdbcUrlSuffix;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.connectionTimeout = connectionTimeout;
        this.readOnly = readOnly;
        this.maximumPoolSize = maximumPoolSize;
    }

    public HikariConfigBuilder setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public HikariConfigBuilder setSubProtocol(String subProtocol) {
        this.subProtocol = subProtocol;
        return this;
    }

    public HikariConfigBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public HikariConfigBuilder setPort(Integer port) {
        this.port = port;
        return this;
    }

    public HikariConfigBuilder setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public HikariConfigBuilder setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        return this;
    }

    public HikariConfigBuilder setJdbcUrlSuffix(String jdbcUrlSuffix) {
        this.jdbcUrlSuffix = jdbcUrlSuffix;
        return this;
    }

    public HikariConfigBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public HikariConfigBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public HikariConfigBuilder setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public HikariConfigBuilder setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public HikariConfigBuilder setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public HikariConfigBuilder setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public HikariConfig build() {
        if (jdbcUrl == null) {
            jdbcUrl = String.join(
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
        }
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
