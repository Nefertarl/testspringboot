package com.lanyuan.testspringboot.service;


import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.Role;

import java.util.List;

public interface RoleService {

    int addRole(Role role);

    int removeById(Role role);

    List<Role> findAll();

    PageInfo<Role> getPage(Integer pageNum, Integer pageSize);

    Role getById(Integer id);

    int updateById(Role role);

}
