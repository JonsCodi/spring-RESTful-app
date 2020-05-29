package org.restful.soccer_league.domains.utils.search;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.hibernate.query.criteria.internal.path.RootImpl;
import org.restful.soccer_league.domains.utils.enums.RSQLSearchOperationEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class GenericRSQLSpecification<T> extends RSQLSpecification<T> implements Specification<T> {

    private static final String NULL = "null";
    private static final String TO_CHAR = "TO_CHAR";
    private static final String DATE_TIME_SQL_FORMAT = "YYYY-MM-DD HH24:MI:SS";

    public GenericRSQLSpecification(String property, ComparisonOperator operator, List<String> arguments, List<Class> childClasses) {
        super(property, operator, arguments, childClasses);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        criteriaQuery.distinct(true);

        Path<String> propertyExpression = getPathString(root);

        List<Object> args = castArguments(propertyExpression);
        Object argument = args.get(0);

        switch (RSQLSearchOperationEnum.getSimpleOperator(getOperator())) {
            case EQUAL: {
                return statementForEqual(criteriaBuilder, propertyExpression, argument);
            }
            case NOT_EQUAL: {
                return statementForNotEqual(criteriaBuilder, propertyExpression, argument);
            }
            case GREATER_THAN: {
                return statementForGreaterThan(criteriaBuilder, propertyExpression, argument);
            }
            case GREATER_THAN_OR_EQUAL: {
                return statementGreaterThanOrEqual(criteriaBuilder, propertyExpression, argument);
            }
            case LESS_THAN: {
                return statementForLessThan(criteriaBuilder, propertyExpression, argument);
            }
            case LESS_THAN_OR_EQUAL: {
                return statementForLessThanOrEqual(criteriaBuilder, propertyExpression, argument);
            }
            case IN: {
                return propertyExpression.in(args);
            }
            case NOT_IN: {
                return criteriaBuilder.not(propertyExpression.in(args));
            }
        }

        return null;
    }

    private Predicate statementForLessThanOrEqual(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, Object argument) {
        if (argument instanceof LocalDateTime) {
            Expression<String> dateStringExpression = prepareExpressionForDateTimeType(criteriaBuilder, propertyExpression);

            return criteriaBuilder.lessThanOrEqualTo(dateStringExpression, argument.toString());
        }

        return criteriaBuilder.lessThanOrEqualTo(propertyExpression, argument.toString());
    }

    private Predicate statementForLessThan(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, Object argument) {
        if (argument instanceof LocalDateTime) {
            Expression<String> dateStringExpression = prepareExpressionForDateTimeType(criteriaBuilder, propertyExpression);

            return criteriaBuilder.lessThan(dateStringExpression, argument.toString());
        }

        return criteriaBuilder.lessThan(propertyExpression, argument.toString());
    }

    private Predicate statementGreaterThanOrEqual(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, Object argument) {
        if (argument instanceof LocalDateTime) {
            Expression<String> dateStringExpression = prepareExpressionForDateTimeType(criteriaBuilder, propertyExpression);

            return criteriaBuilder.greaterThanOrEqualTo(dateStringExpression, argument.toString());
        }

        return criteriaBuilder.greaterThanOrEqualTo(propertyExpression, argument.toString());
    }

    private Predicate statementForGreaterThan(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, Object argument) {
        if (argument instanceof LocalDateTime) {
            Expression<String> dateStringExpression = prepareExpressionForDateTimeType(criteriaBuilder, propertyExpression);

            return criteriaBuilder.greaterThan(dateStringExpression, argument.toString());
        }

        return criteriaBuilder.greaterThan(propertyExpression, argument.toString());
    }

    private Predicate statementForNotEqual(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, Object argument) {
        if (argument instanceof String) {
            if (argument.equals(NULL)) {
                return criteriaBuilder.isNotNull(propertyExpression);
            }

            return criteriaBuilder.notLike(propertyExpression, argument.toString().replace('*', '%'));
        }

        return criteriaBuilder.notEqual(propertyExpression, argument);
    }

    private Predicate statementForEqual(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, Object argument) {
        if (argument instanceof String) {
            if (argument.equals(NULL)) {
                return criteriaBuilder.isNull(propertyExpression);
            }

            return criteriaBuilder.like(propertyExpression, argument.toString().replace('*', '%'));
        }

        return criteriaBuilder.equal(propertyExpression, argument);
    }

    private Expression<String> prepareExpressionForDateTimeType(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression) {
        return criteriaBuilder.function(TO_CHAR,
                String.class, propertyExpression,
                criteriaBuilder.literal(DATE_TIME_SQL_FORMAT));
    }

}
