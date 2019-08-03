package com.lu.j1993.mapper;

import com.lu.j1993.pojo.Imgurl;
import com.lu.j1993.pojo.ImgurlExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImgurlMapper {
    int countByExample(ImgurlExample example);

    int deleteByExample(ImgurlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Imgurl record);

    int insertSelective(Imgurl record);

    List<Imgurl> selectByExample(ImgurlExample example);

    Imgurl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Imgurl record, @Param("example") ImgurlExample example);

    int updateByExample(@Param("record") Imgurl record, @Param("example") ImgurlExample example);

    int updateByPrimaryKeySelective(Imgurl record);

    int updateByPrimaryKey(Imgurl record);
}