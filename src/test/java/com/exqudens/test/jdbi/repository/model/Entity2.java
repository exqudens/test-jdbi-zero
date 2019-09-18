package com.exqudens.test.jdbi.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "children")
public class Entity2 {

    private Long id;
    private String name;

    private List<Entity3> children;

}
