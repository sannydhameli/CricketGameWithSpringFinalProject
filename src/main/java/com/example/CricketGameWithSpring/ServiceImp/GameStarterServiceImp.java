package com.example.CricketGameWithSpring.ServiceImp;

import com.example.CricketGameWithSpring.Dao.*;
import com.example.CricketGameWithSpring.Entity.*;
import com.example.CricketGameWithSpring.InputValidationException;
import com.example.CricketGameWithSpring.Service.GameStarterService;
import com.example.CricketGameWithSpring.controller.MyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameStarterServiceImp implements GameStarterService
{
    @Autowired
    private MatchInfoDao matchInfoDao;
    @Autowired
    private PlayerInfoDao playerInfoDao;
    @Autowired
    private MatchServiceImp matchServiceImp;
    @Override
    public List<String> start(MatchDetails matchDetails)
    {
        matchServiceImp.setMatchId(this.setCurrentMatchId());
        matchServiceImp.setTeam1(getTeam(matchDetails.getTeam1Name(),matchDetails.getTeam1PlayerId()));
        matchServiceImp.setTeam2(getTeam(matchDetails.getTeam2Name(),matchDetails.getTeam2PlayerId()));
        matchServiceImp.setOvers(matchDetails.getOvers());
        return matchServiceImp.startGame();
    }

    public  int setCurrentMatchId(){
        MatchInfo lastDocument = matchInfoDao.findFirstByOrderByIdDesc().orElse(null);
        if(lastDocument==null) return 1;
        else return lastDocument.getId()+1;
    }
    @Override
    public Team getTeam(String teamName, List<Integer> teamPlayerId){
        List<Player> players = getPlayersOfTeam(teamPlayerId);
        Team team = new Team();
        team.setMatchId(matchServiceImp.getMatchId());
        team.setTeamName(teamName);
        team.setPlayersOfTeam(players);
        team.setBowlersInTeam(team.getBowlerInTeam());
        for(Player player : players) player.setTeamName(teamName);
        return team;
    }
    @Override
    public List<Player> getPlayersOfTeam(List<Integer> teamPlayerId){
        List<Player>  playersOfTeam = new ArrayList<Player>();
        for(int id : teamPlayerId){
            int is_valid  = playerInfoDao.countById(id);
            if(is_valid > 0){
                Player player = new Player();
                player.setPlayerName(playerInfoDao.findNameById(id));
                player.setPlayerRole(playerInfoDao.findRoleById(id));
                playersOfTeam.add(player);
            }
            else {throw new InputValidationException("YOU are NOT Give Valid Player Input ID Player , Please Give valid Input Id from our PlayerInfo table");}
        }
        return playersOfTeam;
    }


}
