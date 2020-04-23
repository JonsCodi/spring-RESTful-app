package org.restful.soccer_league.domains.team.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restful.soccer_league.domains.team.entity.Record;
import org.restful.soccer_league.domains.team.repository.IRecordRepository;
import org.restful.soccer_league.domains.team.service.IRecordService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements IRecordService {

    private final IRecordRepository recordRepository;

    @Override
    public void create(Record record) {

    }

    @Override
    public void update(Record record) {

    }

    @Override
    public void findById(Long id) {

    }
}
