package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Person;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
interface IPersonBaseRepository<T extends Person> extends PagingAndSortingRepository<T, Long> {

}
