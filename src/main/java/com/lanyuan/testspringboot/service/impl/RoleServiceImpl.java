package com.lanyuan.testspringboot.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.mapper.RoleMapper;
import com.lanyuan.testspringboot.pojo.Role;
import com.lanyuan.testspringboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

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
        PageHelper.startPage(pageNum,pageSize);
        List<Role> list = roleMapper.show();
        return new PageInfo<>(list);
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
