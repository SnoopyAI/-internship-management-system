package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.TeamService;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Team;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/teams")
public class TeamController {
    
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/add")
    public Team add(@Valid @RequestBody Team team) {
        return this.teamService.addTeam(team);
    }

    @PostMapping("/createVariable")
    public ArrayList<Team> createVariable(@RequestBody ArrayList<Team> teams) {
        return this.teamService.getAllTeams(teams);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<Team> getAllTeams() {
        return this.teamService.getAllTeams();
    }

      @GetMapping("/{id}")
    public Team getTeamById(@PathVariable String id) {
        return this.teamService.getTeamById(id);
    }

    @PatchMapping("/{id}")
    public Team updateTeam(@PathVariable String id, @Valid @RequestBody Team updatedTeam) {
        return this.teamService.updateTeam(id, updatedTeam);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteTeam(@PathVariable String id) {
        return this.teamService.deleteTeam(id);
    }
}
