package org.restful.soccer_league.domains.team.repository;

import org.restful.soccer_league.domains.team.entity.Record;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IRecordRepository extends PagingAndSortingRepository<Record, Long> {


}
