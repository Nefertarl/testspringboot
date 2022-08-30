package com.lanyuan.testspringboot.service.impl;


import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.Role;
import com.lanyuan.testspringboot.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Override
    public int addRole(Role role) {
        return 0;
    }

    @Override
    public int removeById(Role role) {
        return 0;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public PageInfo<Role> getPage(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Role getById(Integer id) {
        return null;
    }

    @Override
    public int updateById(Role role) {
        return 0;
    }
}
