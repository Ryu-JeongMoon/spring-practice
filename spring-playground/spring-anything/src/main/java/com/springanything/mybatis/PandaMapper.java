package com.springanything.mybatis;

import org.apache.ibatis.annotations.Mapper;

import com.springanything.jpa.Panda;

@Mapper
public interface PandaMapper {

  void save(Panda panda);
}
