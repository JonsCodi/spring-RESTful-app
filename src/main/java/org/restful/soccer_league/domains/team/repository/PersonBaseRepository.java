package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Person;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@NoRepositoryBean
interface PersonBaseRepository<T extends Person> extends PagingAndSortingRepository<T, Long> {

    Optional<T> findByName(String name);

}
