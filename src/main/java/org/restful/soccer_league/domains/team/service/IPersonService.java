package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IPersonService<T extends Person> {

    T createOrUpdate(T person);

    void update(T person);
    void delete(T person);

    T findById(Long id);

    Page<T> findAll(Pageable pageable);
    Page<T> searchBySpecification(Specification<T> spec, Pageable pageable);

}
