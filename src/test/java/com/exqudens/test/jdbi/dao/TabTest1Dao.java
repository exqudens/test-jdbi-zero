package com.exqudens.test.jdbi.dao;

import com.exqudens.test.jdbi.model.TabTest1;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TabTest1Dao {

    @SqlBatch("insert into `db_test_1`.`tab_test_1`(`name`) values(:superName)")
    @GetGeneratedKeys
    List<Long> insert(@BindBean List<TabTest1> entities);

    @SqlQuery("select * from `db_test_1`.`tab_test_1`")
    @RegisterFieldMapper(TabTest1.class)
    List<TabTest1> select();

    @SqlBatch("insert into `db_test_1`.`tab_test_1`(`id`, `name`) values(:superId, :superName) on duplicate key update `id` = values(`id`), `name` = values(`name`)")
    void update(@BindBean List<TabTest1> entities);

    @SqlUpdate("delete from `db_test_1`.`tab_test_1` where `id` in(<ids>)")
    void delete(@BindList("ids") List<Long> ids);

}
