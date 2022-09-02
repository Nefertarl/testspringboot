package com.lanyuan.testspringboot.service;


import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.Role;

import java.util.List;

public interface RoleService {

    int addRole(Role role);

    int removeById(Integer id);

    PageInfo<Role> getPage(Integer pageNum, Integer pageSize);

    PageInfo<Role> getPage(Role role,Integer pageNum, Integer pageSize);

    Role getById(Integer id);

    int updateById(Role role);

    Role findByRolename(String rolename);

    int doBathDelRole(Integer[] ids);

    //
    List<Role> selectRoleAll();

    int removeRelation(Integer uid);

    int removeRid(Integer rid);

    int addRelation(Integer uid,Integer[] rids);

}
