package org.restful.soccer_league.domains.team.factory;

import org.restful.soccer_league.domains.team.api.v1.web.request.RecordRequest;
import org.restful.soccer_league.domains.team.entity.Record;

public class RecordFactory {

    public static Record createRecord(RecordRequest recordRequest) {
        return new Record(recordRequest.getWins(), recordRequest.getLosses());
    }
}
