package com.lzh.blog.service;

import com.lzh.blog.entity.User;

public interface UserService {

    /**
     * 用户查询
     */
    User checkUser(String username, String password);
}
