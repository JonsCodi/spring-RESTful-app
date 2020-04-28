package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Person;

public interface IPersonService<T extends Person> {

    T createOrUpdate(T person);
    T update(T person);
    void delete(T person);
    void deleteById(long id);

    T findByName(String name);

}
