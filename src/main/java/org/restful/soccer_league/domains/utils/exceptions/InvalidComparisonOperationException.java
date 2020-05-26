package org.restful.soccer_league.domains.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidComparisonOperationException extends BusinessException {

    @Getter
    private String comparisonOperation;

    @Getter
    private String field;

    public InvalidComparisonOperationException(String message, String resource, String field, String comparisonOperation) {
        super(message, resource);
        this.field = field;
        this.comparisonOperation = comparisonOperation;
    }
}
