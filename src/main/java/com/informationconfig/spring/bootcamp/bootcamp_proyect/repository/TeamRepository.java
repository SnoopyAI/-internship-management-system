package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Team;
import java.util.ArrayList;

@Repository
public class TeamRepository {
    
    private ArrayList<Team> teams = new ArrayList<>();

    public Team addTeam(Team team) {
        teams.add(team);
        return team;
    }

    public ArrayList<Team> getAllTeams(ArrayList<Team> teams) {
        teams.addAll(teams);
        return teams;
    }

    public boolean deleteTeam(String id) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamId() == id) {
                teams.remove(i);
                return true;
            }
        }
        return false;
    }

    public Team updateTeam(String id, Team updatedTeam) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamId().equals(id)) {
                teams.set(i, updatedTeam);
                return updatedTeam;
            }
        }
        return null; // or throw an exception if preferred
    }

    public Team getTeamById(String id) {
        for (Team team : teams) {
            if (team.getTeamId().equals(id)) {
                return team;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<Team> getAllTeams() {
        return teams;
    }
}
