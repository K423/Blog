package com.lzh.blog.dao;

import com.lzh.blog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("select t, (select count(b) from Blog b where b.type.id = t.id) from Type t order by (select count(b) from Blog b where b.type.id = t.id) desc ")
    List<Type> findTop(Pageable pageable);
}
