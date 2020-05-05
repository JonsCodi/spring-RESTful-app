package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;

import java.util.List;

public interface IPersonService<T extends Person> {

    T createOrUpdate(T person);
    T update(T person);
    void delete(T person);
    void deleteById(long id);

    T findByName(String name);
    T findById(Long id);

    List<T> findAll();

}
