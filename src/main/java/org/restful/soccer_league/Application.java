package org.restful.soccer_league;

import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.repository.ITeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {

	private final ITeamRepository teamRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Team> teams = new ArrayList<>();
		for(int i = 0;i<50;i++) {
			teams.add(new Team("Team: ".concat(String.valueOf(i))));
		}

		teamRepository.saveAll(teams);
	}
}
