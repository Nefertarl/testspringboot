package com.lanyuan.testspringboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.mapper.UserMapper;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    //逻辑删除
    @Override
    public int removeById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    //批量逻辑删除
    @Override
    public int doBathDelUser(Integer[] ids) {

        for (Integer id : ids){

            User user1 = userMapper.selectByPrimaryKey(id);

            if(user1!=null){
                user1.setDel("1");
                userMapper.updateByPrimaryKeySelective(user1);
            }
        }
        return 2;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    //分页查询用户
    @Override
    public PageInfo<User> getPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.findAll();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<User> getPage(User user,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.findAlls(user);
        return new PageInfo<>(list);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public User findByAcunt(String account) {
        return userMapper.findByAcunt(account);
    }

    @Override
    public User findByAccunt2(String account) {
        return userMapper.findByAccunt2(account);
    }

}
