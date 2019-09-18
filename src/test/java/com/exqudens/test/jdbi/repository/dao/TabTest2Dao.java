package com.exqudens.test.jdbi.repository.dao;

import com.exqudens.test.jdbi.repository.model.TabTest2;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TabTest2Dao {

    @SqlBatch("insert into `tab_test_2`(`name`) values(:superName)")
    @GetGeneratedKeys
    List<Long> insert(@BindBean List<TabTest2> entities);

    @SqlQuery("select * from `tab_test_2` where `id` in(<ids>)")
    @RegisterFieldMapper(TabTest2.class)
    List<TabTest2> select(@BindList("ids") List<Long> ids);

    @SqlBatch("insert into `tab_test_2`(`id`, `name`) values(:superId, :superName) on duplicate key update `id` = values(`id`), `name` = values(`name`)")
    void update(@BindBean List<TabTest2> entities);

    @SqlUpdate("delete from `tab_test_2` where `id` in(<ids>)")
    void delete(@BindList("ids") List<Long> ids);

}
