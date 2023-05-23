package com.lzh.blog.service;

import com.lzh.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    List<Tag> listTag(String ids);

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

    Tag getByName(String name);

    List<Tag> listTag();

    List<Tag> listTopTag(Integer size);
}
