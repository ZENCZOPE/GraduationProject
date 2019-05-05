package com.cms.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.pojo.PlayerGameRel;
import com.cms.pojo.PlayerGameRelKey;
@Service
public interface PlayerGameRelMapper {
    int deleteByPrimaryKey(PlayerGameRelKey key);
    /**
     * 根据gameId删除
     * @param gameId
     * @return
     */
    int deleteByGameId(int gameId);

    int insert(PlayerGameRel record);
    /**
     * 插入数据
     * @param record
     * @return
     */
    int insertSelective(PlayerGameRel record);

    PlayerGameRel selectByPrimaryKey(PlayerGameRelKey key);
    /**
     * 根据gameId查询
     * @param idNo
     * @return
     */
    List<PlayerGameRel> queryByGameId(int idNo);
    
    /**
     * 根据playerId查询
     * @param idNo
     * @return
     */
    List<PlayerGameRel> queryByPlayerId(int idNo);

    int updateByPrimaryKeySelective(PlayerGameRel record);

    int updateByPrimaryKey(PlayerGameRel record);
    
    List<PlayerGameRel> queryByCondition(PlayerGameRel playerGameRel);
}