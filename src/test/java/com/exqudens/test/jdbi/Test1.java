package com.exqudens.test.jdbi;

import com.exqudens.test.jdbi.util.HikariConfigUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Test1 {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(Test1.class);
        LOGGER.trace("");
    }

    @Test
    public void test1() throws Throwable {
        LOGGER.trace("");
        HikariConfig hikariConfig = HikariConfigUtils.hikariConfig();
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig)) {
            Jdbi jdbi = Jdbi.create(dataSource);
            jdbi.useHandle(handle -> {
                handle.execute("create database if not exists `db_test_1`");
                handle.execute("create table if not exists `db_test_1`.`tab_test_1`(`id` bigint(20) unsigned not null auto_increment, `name` varchar(255), primary key(`id`))");
                System.out.println("---");
                List<Map<String, Object>> rows = handle.createQuery("show databases").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("show tables from `db_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                handle.createUpdate("truncate table `db_test_1`.`tab_test_1`").execute();
                System.out.println("---");
                rows = handle.prepareBatch("insert into `db_test_1`.`tab_test_1`(`name`) values(?)").bind(0, "aaa").add().bind(0, "bbb").add().executeAndReturnGeneratedKeys().mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("select * from `db_test_1`.`tab_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                handle
                        .prepareBatch("insert into `db_test_1`.`tab_test_1`(`id`, `name`) values(?, ?) on duplicate key update `id` = values(`id`), `name` = values(`name`)")
                        .bind(0, 1L).bind(1, "yyy").add()
                        .bind(0, 2L).bind(1, "zzz").add()
                        .execute();
                System.out.println("---");
                rows = handle.createQuery("select * from `db_test_1`.`tab_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                handle
                        .createUpdate("delete from `db_test_1`.`tab_test_1` where `id` in(?, ?)")
                        .bind(0, 1L).bind(1, 2L)
                        .execute();
                System.out.println("---");
                rows = handle.createQuery("select * from `db_test_1`.`tab_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
            });
        }
    }

}
