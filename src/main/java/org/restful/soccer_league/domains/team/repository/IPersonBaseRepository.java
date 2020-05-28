package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Person;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

interface IPersonBaseRepository<T extends Person> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {

}
