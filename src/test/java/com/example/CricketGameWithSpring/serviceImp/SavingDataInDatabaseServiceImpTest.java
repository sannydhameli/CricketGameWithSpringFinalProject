package com.example.CricketGameWithSpring.serviceImp;

import com.example.CricketGameWithSpring.dao.*;
import com.example.CricketGameWithSpring.elasticsearch.PlayerDaoUsingElasticsearch;
import com.example.CricketGameWithSpring.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SavingDataInDatabaseServiceImpTest {

    @Autowired
    private MatchServiceImp matchServiceImp;

    @InjectMocks
    private SavingDataInDatabaseServiceImp savingDataInDatabaseServiceImp;

    @Mock
    public ScoreBordDetailsDao scoreBordDetailsDao;
    @Mock
    public PlayerDao playerDao;
    @Mock
    public TeamDetailDao teamDetailDao;
    @Mock
    public MatchInfoDao matchInfoDao;
    @Mock
    public HistoryOfMatchDao historyOfCricketMatchDao;

    @Mock
    public PlayerDaoUsingElasticsearch playerDaoUsingElasticsearch;

    @Test
    void savingCricketMatchRelatedDataInDatabase() {

      Player player = new Player();


        List<Player> playersTeam1 = new ArrayList<>();
        playersTeam1.add(new Player(1L,"John", PlayerRole.Batsman,"India",1));
        playersTeam1.add(new Player(2L,"Mike",PlayerRole.Batsman ,"India",1));
        playersTeam1.add(new Player(3L,"Sarah", PlayerRole.AllRounder,"India",1));
        playersTeam1.add(new Player(4L,"David", PlayerRole.Bowler,"India",1));
        playersTeam1.add(new Player(5L,"Jessica", PlayerRole.Bowler, "India",1));

        // create bowlers for team 1
        List<Player> bowlersTeam1 = new ArrayList<>();
        bowlersTeam1.add(playersTeam1.get(3));
        bowlersTeam1.add(playersTeam1.get(4));

        // create players for team 2
        List<Player> playersTeam2 = new ArrayList<>();
        playersTeam2.add(new Player(1L,"Tom", PlayerRole.Batsman,"PAK",1));
        playersTeam2.add(new Player(2L,"Anna", PlayerRole.Batsman, "PAK",1));
        playersTeam2.add(new Player(3L,"Steve", PlayerRole.AllRounder, "PAK",1));
        playersTeam2.add(new Player(4L,"Samantha", PlayerRole.Bowler, "PAK",1));
        playersTeam2.add(new Player(5L,"Alex", PlayerRole.Bowler, "PAK",1));

        // create bowlers for team 2
        List<Player> bowlersTeam2 = new ArrayList<>();
        bowlersTeam2.add(playersTeam2.get(3));
        bowlersTeam2.add(playersTeam2.get(4));

        // create teams for the match
        Team team1 = new Team(1, "Team 1", playersTeam1, bowlersTeam1);
        Team team2 = new Team(1, "Team 2", playersTeam2, bowlersTeam2);

        matchServiceImp.setMatchId(1);
        matchServiceImp.setTeam1(team1);
        matchServiceImp.setTeam2(team2);



        savingDataInDatabaseServiceImp.savingCricketMatchRelatedDataInDatabase(matchServiceImp);

        verify(playerDao,times(2)).saveAll(any());
        verify(playerDaoUsingElasticsearch,times(2)).saveAll(any());

       verify(teamDetailDao,times(2)).save(any(TeamDetail.class));
       verify(scoreBordDetailsDao).save(new ScoreBordDetail(matchServiceImp));
       verify(matchInfoDao).save(new MatchInfo(matchServiceImp));
       verify(historyOfCricketMatchDao).save(new HistoryOfMatch(matchServiceImp));



    }
}