package com.lanyuan.testspringboot.mapper;

import com.lanyuan.testspringboot.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByUid(Integer uid);

    List<Role> show();

    List<Role> showa(Role role);

    Role findByRolename(String rolename);

    int doBathDelRole(Integer[] ids);

    //
    List<Role> selectRoleAll();

    int removeRelation(Integer uid);

    int removeRid(Integer rid);

    int addRelation(@Param("uid") Integer uid, @Param("r") Integer r);
}