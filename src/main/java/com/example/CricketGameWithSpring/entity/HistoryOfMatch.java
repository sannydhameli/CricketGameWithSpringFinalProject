package com.example.CricketGameWithSpring.entity;

import com.example.CricketGameWithSpring.serviceImp.MatchServiceImp;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "HistoryOfCricketMatch")
@Builder
public class HistoryOfMatch {

    @Id
    @Indexed(name = "indexMatchId")
    private int matchId;
    private List<String> scoreBordDetailOfCricketMatch;

    public HistoryOfMatch(MatchServiceImp matchServiceImp) {
        this.scoreBordDetailOfCricketMatch = matchServiceImp.getFullScoreBordDetail();
        this.matchId = matchServiceImp.getMatchId();
    }
}
