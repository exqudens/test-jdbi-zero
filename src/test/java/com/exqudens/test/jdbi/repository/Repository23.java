package com.exqudens.test.jdbi.repository;

import com.exqudens.test.jdbi.repository.dao.TabTest2Dao;
import com.exqudens.test.jdbi.repository.dao.TabTest3Dao;
import com.exqudens.test.jdbi.repository.model.Entity3;
import com.exqudens.test.jdbi.repository.model.TabTest2;
import com.exqudens.test.jdbi.repository.model.TabTest3;
import com.exqudens.test.jdbi.repository.model.Entity2;
import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public interface Repository23 {

    @CreateSqlObject
    TabTest2Dao tabTest2Dao();

    @CreateSqlObject
    TabTest3Dao tabTest3Dao();

    @Transaction
    default List<Entity2> insert(List<Entity2> entities2) {
        List<Entry<Entity2, TabTest2>> entries2 = new ArrayList<>();
        entries2.addAll(entries2(entities2));
        List<TabTest2> beans2 = entries2
                .stream()
                .map(Entry::getValue)
                .collect(Collectors.toList());
        TabTest2Dao tabTest2Dao = tabTest2Dao();
        List<Long> ids2 = tabTest2Dao.insert(beans2);
        for (int i = 0; i < beans2.size(); i++) {
            beans2.get(i).setSuperId(ids2.get(i));
            entries2.get(i).getKey().setId(beans2.get(i).getSuperId());
        }
        List<Entry<Entity3, TabTest3>> entries3 = new ArrayList<>();
        for (Entity2 entity2 : entities2) {
            entries3.addAll(entries3(entity2.getChildren()));
        }
        List<TabTest3> beans3 = entries3
                .stream()
                .map(Entry::getValue)
                .collect(Collectors.toList());
        TabTest3Dao tabTest3Dao = tabTest3Dao();
        List<Long> ids3 = tabTest3Dao.insert(beans3);
        for (int i = 0; i < beans3.size(); i++) {
            beans3.get(i).setSuperId(ids3.get(i));
            entries3.get(i).getKey().setId(beans3.get(i).getSuperId());
        }
        return entities2;
    }

    default List<Entry<Entity2, TabTest2>> entries2(List<Entity2> entities) {
        List<Entry<Entity2, TabTest2>> entries = new ArrayList<>();
        for (Entity2 key : entities) {
            TabTest2 value = new TabTest2(
                    key.getId(),
                    key.getName()
            );
            Entry<Entity2, TabTest2> entry = new SimpleEntry<>(key, value);
            entries.add(entry);
        }
        return entries;
    }

    default List<Entry<Entity3, TabTest3>> entries3(List<Entity3> entities) {
        List<Entry<Entity3, TabTest3>> entries = new ArrayList<>();
        for (Entity3 key : entities) {
            TabTest3 value = new TabTest3(
                    key.getId(),
                    key.getName(),
                    key.getParent().getId()
            );
            Entry<Entity3, TabTest3> entry = new SimpleEntry<>(key, value);
            entries.add(entry);
        }
        return entries;
    }

}
