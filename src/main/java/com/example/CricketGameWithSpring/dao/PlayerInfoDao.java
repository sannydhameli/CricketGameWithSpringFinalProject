package com.example.CricketGameWithSpring.dao;

import com.example.CricketGameWithSpring.entity.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerInfoDao extends JpaRepository<PlayerInfo, Integer> {

    @Query(value = "SELECT COUNT(*) FROM player_info WHERE player_info.player_id = :playerId", nativeQuery = true)
    int countById(@Param("playerId") int playerId);

    @Query(value = "SELECT player_name FROM player_info p WHERE p.player_id = :playerId", nativeQuery = true)
    public String findNameById(@Param("playerId") int playerId);

    @Query(value = "SELECT player_role FROM player_info p WHERE p.player_id = :playerId", nativeQuery = true)
    public String findRoleById(@Param("playerId") int playerId);

    @Query(value = "SELECT * FROM player_info p WHERE p.player_role = :playerRole", nativeQuery = true)
    public List<PlayerInfo> findByRole(String playerRole);
}
