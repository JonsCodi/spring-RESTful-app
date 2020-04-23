package org.restful.soccer_league.domains.team.service;

import org.restful.soccer_league.domains.team.entity.Record;

public interface IRecordService {

    void create(Record record);
    void update(Record record);
    void findById(Long id);
    
}
