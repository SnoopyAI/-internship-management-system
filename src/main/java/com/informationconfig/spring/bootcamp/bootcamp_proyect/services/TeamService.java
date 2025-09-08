package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Team;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.TeamRepository;
import java.util.ArrayList;

@Service
public class TeamService {
    
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team addTeam(Team team) {
        return teamRepository.addTeam(team);
    }

    public ArrayList<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    public boolean deleteTeam(String id) {
        return teamRepository.deleteTeam(id);
    }

    public Team updateTeam(String id, Team updatedTeam) {
        return teamRepository.updateTeam(id, updatedTeam);
    }

    public Team getTeamById(String id) {
        return teamRepository.getTeamById(id);
    }

    public ArrayList<Team> getAllTeams(ArrayList<Team> teams) {
        return teamRepository.getAllTeams(teams);
    }
}
