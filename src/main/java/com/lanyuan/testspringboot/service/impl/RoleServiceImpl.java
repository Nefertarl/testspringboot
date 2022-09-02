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
        return roleMapper.insertSelective(role);
    }

    @Override
    public int removeById(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Role> getPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> list = roleMapper.show();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Role> getPage(Role role,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> list = roleMapper.showa(role);
        return new PageInfo<>(list);
    }

    @Override
    public Role getById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role findByRolename(String rolename) {
        return roleMapper.findByRolename(rolename);
    }

    @Override
    public int doBathDelRole(Integer[] ids) {
        int i = roleMapper.doBathDelRole(ids);
        return i;
    }

    //关系表的操作
    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectRoleAll();
    }

    @Override
    public int removeRelation(Integer uid) {
        return roleMapper.removeRelation(uid);
    }

    @Override
    public int removeRid(Integer rid) {
        return roleMapper.removeRid(rid);
    }

    @Override
    public int addRelation(Integer uid, Integer[] rids) {
        for (Integer r : rids){
            roleMapper.addRelation(uid,r);
        }
        return 0;
    }
}
