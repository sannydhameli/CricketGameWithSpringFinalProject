package com.example.CricketGameWithSpring.serviceImp;

import com.example.CricketGameWithSpring.entity.Ball;
import com.example.CricketGameWithSpring.entity.Player;
import com.example.CricketGameWithSpring.entity.Team;
import com.example.CricketGameWithSpring.service.PlayCricketMatchService;
import org.springframework.stereotype.Service;

@Service
public class PlayCricketMatchServiceImp implements PlayCricketMatchService {

    public static final int numOfBallInOver = 6;
    public static final int wicket = 7;
    public static final int lastWicket = 10;
    public static final int six = 6;
    public static final int four = 4;

    @Override
    public int playCricketMatchBetweenTwoTeam(Team battingTeam, Team bowlingTeam, int overs) {

        int batsmanNo = 0;
        int lastBowlerNo = -1;

        Player strikerBatsman = battingTeam.getBatsman(batsmanNo++); // on strike
        Player offStrikerBatsman = battingTeam.getBatsman(batsmanNo++); // off side

        for (int i = 0; i < overs; i++) {

            int NextBowlerNo = bowlingTeam.getNextBowlerNo(lastBowlerNo);
            Player bowler = bowlingTeam.getBowler(NextBowlerNo);
            lastBowlerNo = NextBowlerNo;

            for (int j = 0; j < numOfBallInOver; j++) {
                int run = randomRunOrWicketGeneratorOnEveryBall();

                if (run == wicket) {

                    // consider as wicket for Now

           Ball ball =  Ball.builder()
                            .nameOfBatsmanWhoPlayBall(strikerBatsman.getPlayerName())
                            .overNoOfBall(i)
                            .serialNoOfBall(j)
                            .typeOfBall("WicketDown")
                            .runsCoveredOnBall(0)
                            .nameOfBowlerWhoBowledBall(bowler.getPlayerName())
                            .build();

                    bowler.addWicketByPlayer();
                    bowler.addBallBowledByPlayer();

                    strikerBatsman.addBallsFacedByPlayer();

                    battingTeam.addBallDetails(ball);
                    battingTeam.addWicketLoss();

                    if (battingTeam.getWicketLossOfTeam() == lastWicket) {
                        return battingTeam.getScoreOfTeam();
                    }

                    strikerBatsman = battingTeam.getBatsman(batsmanNo++);
                } else {
                    // 0,1,2,3,4,5,6

                    Ball ball =  Ball.builder()
                                     .nameOfBatsmanWhoPlayBall(strikerBatsman.getPlayerName())
                                     .overNoOfBall(i)
                                     .serialNoOfBall(j)
                                     .typeOfBall(Integer.toString(run) + " Run gained On this Ball")
                                     .runsCoveredOnBall(run)
                                     .nameOfBowlerWhoBowledBall(bowler.getPlayerName())
                                     .build();

                    strikerBatsman.addRunByPlayer(run);
                    strikerBatsman.addBallsFacedByPlayer();

                    bowler.addRunConsiderByPlayer(run);
                    bowler.addBallBowledByPlayer();

                    battingTeam.addScore(run);
                    battingTeam.addBallDetails(ball);

                    if ((run % 2) == 1) {
                        // Strike change  by batsman at run : 1,3,5  here we can use this also --->
                        Player tempPlayer = strikerBatsman;
                        strikerBatsman = offStrikerBatsman;
                        offStrikerBatsman = tempPlayer;
                    }

                }
            }

            battingTeam.addOversPlay();
            // Strike change every over

            Player tempPlayer = strikerBatsman;
            strikerBatsman = offStrikerBatsman;
            offStrikerBatsman = tempPlayer;
        }

        return battingTeam.getScoreOfTeam();

    }

    public int randomRunOrWicketGeneratorOnEveryBall() {
        int num = (int) (Math.random() * 150);
        if (num > 140) {
            return wicket;
        } else if (num > 130) {
            return six;
        } else if (num > 100) {
            return four;
        } else {
            return (int) (Math.random() * 5);
        }
    }


}
