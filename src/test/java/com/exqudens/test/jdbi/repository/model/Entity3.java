package com.exqudens.test.jdbi.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Entity3 {

    private Long id;
    private String name;

    private Entity2 parent;

}
