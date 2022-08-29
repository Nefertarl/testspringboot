package com.lanyuan.testspringboot.service;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    int removeById(User user);

    List<User> findAll();

    PageInfo<User> getPage(Integer pageNum, Integer pageSize);

    User getById(Integer id);

    int updateById(User user);

}
