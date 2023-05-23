package com.lzh.blog.service;

import com.lzh.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    /**
     * save type
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * get type by id
     */
    Type getType(Long id);

    /**
     * page search
     */
    Page<Type> listType(Pageable pageable);

    /**
     * update type
     */
    Type updateType(Long id, Type type);

    /**
     * delete
     */
    void deleteType(Long id);

    /**
     * find by name
     */
    Type getByName(String name);

    /**
     * return all type
     * @return
     */
    List<Type> listType();

    /**
     * return limit type
     */
    List<Type> listTypeTop(Integer size);
}
