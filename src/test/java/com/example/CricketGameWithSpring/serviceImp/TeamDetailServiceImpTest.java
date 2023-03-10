package com.example.CricketGameWithSpring.serviceImp;

import com.example.CricketGameWithSpring.dao.TeamDetailDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeamDetailServiceImpTest {

    @InjectMocks
    TeamDetailServiceImp teamDetailServiceImp;

    @Mock
    TeamDetailDao teamDetailDao;

    @Test
    void getTeamDetailByTeamName() {

        teamDetailServiceImp.getTeamDetailByTeamName("india");
        verify(teamDetailDao).findTeamDetailByTeamName("india");
    }
}