package org.restful.soccer_league;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.restful.soccer_league.domains.team.entity.Coach;
import org.restful.soccer_league.domains.team.entity.Person;
import org.restful.soccer_league.domains.team.entity.Player;
import org.restful.soccer_league.domains.team.entity.Team;
import org.restful.soccer_league.domains.team.repository.IPersonRepository;
import org.restful.soccer_league.domains.team.repository.ITeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {

	private final ITeamRepository teamRepository;
	private final IPersonRepository personRepository;

	private static final Faker FAKER = new Faker(new Locale("pt-br"));

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {

		List<Team> teams = new ArrayList<>();
		for(int i = 0;i<50;i++) {
			teams.add(new Team("Team: ".concat(String.valueOf(i))));
		}

		List<Person> persons = new ArrayList<>();
		for(int i = 0;i<25;i++) {
			Player player = new Player("Person: ".concat(String.valueOf(i)), FAKER.address().streetAddress(),
					i, "Position: ".concat(String.valueOf(i)), true);

			persons.add(player);
		}

		for(int i=0;i<25;i++) {
			Coach coach = new Coach("Coach: ".concat(String.valueOf(i)), FAKER.address().streetAddress(),
					FAKER.lordOfTheRings().character(), i);

			persons.add(coach);
		}

		teamRepository.saveAll(teams);
		personRepository.saveAll(persons);
	}
}
