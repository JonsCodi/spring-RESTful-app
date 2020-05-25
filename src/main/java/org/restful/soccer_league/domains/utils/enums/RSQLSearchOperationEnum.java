package org.restful.soccer_league.domains.utils.enums;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.Getter;

public enum RSQLSearchOperationEnum {
    EQUAL(RSQLOperators.EQUAL),
    NOT_EQUAL(RSQLOperators.NOT_EQUAL),
    GREATER_THAN(RSQLOperators.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
    LESS_THAN(RSQLOperators.LESS_THAN),
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
    IN(RSQLOperators.IN),
    NOT_IN(RSQLOperators.NOT_IN);

    @Getter
    private ComparisonOperator operator;

    private RSQLSearchOperationEnum(ComparisonOperator operator) {
        this.operator = operator;
    }

    public static RSQLSearchOperationEnum getSimpleOperator(ComparisonOperator operator) {
        for (RSQLSearchOperationEnum operation : values()) {
            if (operation.getOperator() == operator) {
                return operation;
            }
        }
        return null;
    }
}
