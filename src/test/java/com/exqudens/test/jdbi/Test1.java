package com.exqudens.test.jdbi;

import com.exqudens.test.jdbi.dao.TabTest1Dao;
import com.exqudens.test.jdbi.model.TabTest1;
import com.exqudens.test.jdbi.repository.Repository23;
import com.exqudens.test.jdbi.repository.model.Entity2;
import com.exqudens.test.jdbi.repository.model.Entity3;
import com.exqudens.test.jdbi.util.HikariConfigBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.FieldMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test1 {

    private static final Logger LOGGER;

    private static MySQLContainer mySQLContainer;

    static {
        LOGGER = LoggerFactory.getLogger(Test1.class);
        LOGGER.trace("");
    }

    @BeforeClass
    public static void beforeClass() throws Throwable {
        mySQLContainer = new MySQLContainer()
        .withDatabaseName("db_test_1");
        mySQLContainer.start();
    }

    @AfterClass
    public static void afterClass() throws Throwable {
        mySQLContainer.stop();
    }

    @Test
    public void test1() throws Throwable {
        LOGGER.trace("");
        HikariConfig hikariConfig = HikariConfigBuilder
                .builder()
                .setJdbcUrl(mySQLContainer.getJdbcUrl())
                .setUsername(mySQLContainer.getUsername())
                .setPassword(mySQLContainer.getPassword())
                .build();
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig)) {
            Jdbi jdbi = Jdbi.create(dataSource);
            jdbi.useHandle(handle -> {
                handle.execute("drop table if exists `tab_test_1`");
                handle.execute("create table if not exists `tab_test_1`(`id` bigint(20) unsigned not null auto_increment, `name` varchar(255), primary key(`id`))");
                System.out.println("---");
                List<Map<String, Object>> rows = handle.createQuery("show databases").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("show tables").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.prepareBatch("insert into `tab_test_1`(`name`) values(?)").bind(0, "aaa").add().bind(0, "bbb").add().executeAndReturnGeneratedKeys().mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("select * from `db_test_1`.`tab_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                handle
                        .prepareBatch("insert into `tab_test_1`(`id`, `name`) values(?, ?) on duplicate key update `id` = values(`id`), `name` = values(`name`)")
                        .bind(0, 1L).bind(1, "yyy").add()
                        .bind(0, 2L).bind(1, "zzz").add()
                        .execute();
                System.out.println("---");
                rows = handle.createQuery("select * from `tab_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                handle
                        .createUpdate("delete from `tab_test_1` where `id` in(?, ?)")
                        .bind(0, 1L).bind(1, 2L)
                        .execute();
                System.out.println("---");
                rows = handle.createQuery("select * from `tab_test_1`").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
            });
        }
    }

    @Test
    public void test2() throws Throwable {
        LOGGER.trace("");
        HikariConfig hikariConfig = HikariConfigBuilder
                .builder()
                .setJdbcUrl(mySQLContainer.getJdbcUrl())
                .setUsername(mySQLContainer.getUsername())
                .setPassword(mySQLContainer.getPassword())
                .build();
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig)) {
            Jdbi jdbi = Jdbi.create(dataSource);
            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.useHandle(handle -> {
                handle.registerRowMapper(FieldMapper.factory(TabTest1.class));
                System.out.println("---");
                handle.execute("drop table if exists `tab_test_1`");
                handle.execute("create table if not exists `tab_test_1`(`id` bigint(20) unsigned not null auto_increment, `name` varchar(255), primary key(`id`))");
                System.out.println("---");
                List<Map<String, Object>> rows = handle.createQuery("show databases").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("show tables").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.prepareBatch("insert into `tab_test_1`(`name`) values(:superName)").bindBean(new TabTest1(null, "aaa")).add().bindBean(new TabTest1(null, "bbb")).add().executeAndReturnGeneratedKeys().mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                List<TabTest1> beans = handle.createQuery("select * from `tab_test_1`").mapTo(TabTest1.class).list();
                beans.stream().forEach(System.out::println);
                System.out.println("---");
                handle
                        .prepareBatch("insert into `tab_test_1`(`id`, `name`) values(:superId, :superName) on duplicate key update `id` = values(`id`), `name` = values(`name`)")
                        .bindBean(new TabTest1(1L, "yyy")).add()
                        .bindBean(new TabTest1(2L, "zzz")).add()
                        .execute();
                System.out.println("---");
                beans = handle.createQuery("select * from `tab_test_1`").mapTo(TabTest1.class).list();
                beans.stream().forEach(System.out::println);
                System.out.println("---");
            });
        }
    }

    @Test
    public void test3() throws Throwable {
        LOGGER.trace("");
        HikariConfig hikariConfig = HikariConfigBuilder
                .builder()
                .setJdbcUrl(mySQLContainer.getJdbcUrl())
                .setUsername(mySQLContainer.getUsername())
                .setPassword(mySQLContainer.getPassword())
                .build();
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig)) {
            Jdbi jdbi = Jdbi.create(dataSource);
            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.useHandle(handle -> {
                System.out.println("---");
                handle.execute("drop table if exists `tab_test_1`");
                handle.execute("create table if not exists `tab_test_1`(`id` bigint(20) unsigned not null auto_increment, `name` varchar(255), primary key(`id`))");
                System.out.println("---");
                List<Map<String, Object>> rows = handle.createQuery("show databases").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("show tables").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                TabTest1Dao tabTest1Dao = handle.attach(TabTest1Dao.class);
                List<TabTest1> beans = Stream.of(new TabTest1(null, "aaa"), new TabTest1(null, "bbb")).collect(Collectors.toList());
                List<Long> ids = tabTest1Dao.insert(beans);
                for (int i = 0; i < beans.size(); i++) {
                    beans.get(i).setSuperId(ids.get(i));
                }
                beans.stream().forEach(System.out::println);
                System.out.println("---");
                beans = tabTest1Dao.select(ids);
                beans.stream().forEach(System.out::println);
                System.out.println("---");
                for (int i = 0; i < beans.size(); i++) {
                    beans.get(i).setSuperName(beans.get(i).getSuperName().toUpperCase());
                }
                tabTest1Dao.update(beans);
                System.out.println("---");
                beans = tabTest1Dao.select(ids);
                beans.stream().forEach(System.out::println);
                System.out.println("---");
                tabTest1Dao.delete(ids);
                System.out.println("---");
                beans = tabTest1Dao.select(ids);
                beans.stream().forEach(System.out::println);
                System.out.println("---");
            });
        }
    }

    @Test
    public void test4() throws Throwable {
        LOGGER.trace("");
        HikariConfig hikariConfig = HikariConfigBuilder
                .builder()
                .setJdbcUrl(mySQLContainer.getJdbcUrl())
                .setUsername(mySQLContainer.getUsername())
                .setPassword(mySQLContainer.getPassword())
                .build();
        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig)) {
            Jdbi jdbi = Jdbi.create(dataSource);
            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.useHandle(handle -> {
                System.out.println("---");
                handle.execute("drop table if exists `tab_test_2`");
                handle.execute("drop table if exists `tab_test_3`");
                handle.execute("create table if not exists `tab_test_2`(`id` bigint(20) unsigned not null auto_increment, `name` varchar(255), primary key(`id`))");
                handle.execute("create table if not exists `tab_test_3`(`id` bigint(20) unsigned not null auto_increment, `name` varchar(255), `parent_id` bigint(20) unsigned not null, primary key(`id`))");
                System.out.println("---");
                List<Map<String, Object>> rows = handle.createQuery("show databases").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                rows = handle.createQuery("show tables").mapToMap().list();
                rows.stream().forEach(System.out::println);
                System.out.println("---");
                Repository23 repository23 = handle.attach(Repository23.class);
                List<Entity2> entities2 = Stream
                        .of(
                                new Entity2(null, "a", new ArrayList<>()),
                                new Entity2(null, "b", new ArrayList<>())
                        ).map(entity2 -> {
                                entity2.getChildren().addAll(Stream.of(new Entity3(null, entity2.getName() + "-1", entity2)).collect(Collectors.toList()));
                                return entity2;
                        })
                        .collect(Collectors.toList());
                entities2 = repository23.insert(entities2);
                entities2.stream().forEach(entity2 -> {
                    System.out.println(entity2);
                    entity2.getChildren().stream().forEach(entity3 -> {
                        System.out.println("  " + entity3);
                    });
                });
                System.out.println("---");
            });
        }
    }

}
