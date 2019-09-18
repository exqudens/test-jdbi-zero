package com.exqudens.test.jdbi.repository.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class TabTest3 {

    @ColumnName("id")
    private Long superId;

    @ColumnName("name")
    private String superName;

    @ColumnName("parent_id")
    private Long superParentId;

}
