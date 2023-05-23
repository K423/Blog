package com.lzh.blog.service;

import com.lzh.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listComment(Long blogId);

    Comment save(Comment comment);
}
