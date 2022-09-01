package com.lanyuan.testspringboot.service;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    int removeById(User user);

    int doBathDelUser(Integer[] ids);

    List<User> findAll();

    PageInfo<User> getPage(Integer pageNum, Integer pageSize);

    PageInfo<User> getPage(User user,Integer pageNum, Integer pageSize);

    User getById(Integer id);

    int updateById(User user);

    User login(User user);

    User findByAcunt(String account);

}
