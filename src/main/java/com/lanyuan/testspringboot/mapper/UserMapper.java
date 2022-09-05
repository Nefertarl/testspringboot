package com.lanyuan.testspringboot.mapper;

import com.lanyuan.testspringboot.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findAll();

    List<User> findAlls(User user);

    User login(User user);

    User findByAcunt(String account);

    User findByAccunt2(String account);

    List<User> selectUserByRid(Integer rid);

    int doBathDelUser(Integer[] ids);

}