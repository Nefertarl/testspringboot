package com.lanyuan.testspringboot.mapper;

import com.lanyuan.testspringboot.pojo.Relation;

public interface RelationMapper {
    int insert(Relation record);

    int insertSelective(Relation record);
}