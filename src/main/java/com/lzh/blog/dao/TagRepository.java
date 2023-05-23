package com.lzh.blog.dao;

import com.lzh.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

//    @Query("select t, (select count(b) from Blog b where b.type.id = t.id) from Type t order by (select count(b) from Blog b where b.type.id = t.id) desc ")
    @Query("select t, (select count (b) from Blog b join b.tags tag where tag.id = t.id) from Tag t order by (select count (b) from Blog b join b.tags tag where tag.id = t.id) desc ")
    List<Tag> findTop(Pageable pageable);
}
