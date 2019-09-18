package com.exqudens.test.jdbi.repository.dao;

import com.exqudens.test.jdbi.repository.model.TabTest3;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TabTest3Dao {

    @SqlBatch("insert into `tab_test_3`(`name`, `parent_id`) values(:superName, :superParentId)")
    @GetGeneratedKeys
    List<Long> insert(@BindBean List<TabTest3> entities);

    @SqlQuery("select * from `tab_test_3` where `id` in(<ids>)")
    @RegisterFieldMapper(TabTest3.class)
    List<TabTest3> select(@BindList("ids") List<Long> ids);

    @SqlBatch("insert into `tab_test_3`(`id`, `name`, `parent_id`) values(:superId, :superName, :superParentId) on duplicate key update `id` = values(`id`), `name` = values(`name`), `parent_id` = values(`parent_id`)")
    void update(@BindBean List<TabTest3> entities);

    @SqlUpdate("delete from `tab_test_3` where `id` in(<ids>)")
    void delete(@BindList("ids") List<Long> ids);

}
