package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;

import java.util.Optional;

public interface IPersonService<T extends Person> {

    void create(T person);
    void update(T person);
    void delete(T person);
    void deleteById(long id);

    T findByName(String name);

}
