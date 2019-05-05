package com.cms.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.pojo.PlayerGradeRel;
import com.cms.pojo.PlayerGradeRelKey;
@Service
public interface PlayerGradeRelMapper {
    int deleteByPrimaryKey(PlayerGradeRelKey key);

    int insert(PlayerGradeRel record);

    int insertSelective(PlayerGradeRel record);

    PlayerGradeRel selectByPrimaryKey(PlayerGradeRelKey key);

    int updateByPrimaryKeySelective(PlayerGradeRel record);

    int updateByPrimaryKey(PlayerGradeRel record);
    
    List<PlayerGradeRel> queryByCondition(PlayerGradeRel playerGradeRel);
}