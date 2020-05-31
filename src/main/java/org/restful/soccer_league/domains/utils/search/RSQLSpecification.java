package org.restful.soccer_league.domains.utils.search;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.hibernate.query.criteria.internal.path.RootImpl;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.restful.soccer_league.domains.utils.exceptions.InvalidComparisonOperationException;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.restful.soccer_league.domains.utils.enums.RSQLSearchOperationEnum.GREATER_THAN;
import static org.restful.soccer_league.domains.utils.enums.RSQLSearchOperationEnum.GREATER_THAN_OR_EQUAL;
import static org.restful.soccer_league.domains.utils.enums.RSQLSearchOperationEnum.LESS_THAN;
import static org.restful.soccer_league.domains.utils.enums.RSQLSearchOperationEnum.LESS_THAN_OR_EQUAL;

@AllArgsConstructor
@Getter
public abstract class RSQLSpecification<T> {

    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;
    private List<Class> childClasses;

    public List<Object> castArguments(Path<?> propertyExpression) {
        Class<? extends Object> type = propertyExpression.getJavaType();

        try {
            return arguments.stream().map(arg -> {
                if (type.equals(Integer.class) || type.equals(int.class)) {
                    return Integer.parseInt(arg);
                } else if (type.equals(Long.class)) {
                    return Long.parseLong(arg);
                } else if (type.equals(Byte.class)) {
                    return Byte.parseByte(arg);
                } else if (type.equals(LocalDateTime.class)) {
                    return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    return Boolean.valueOf(arg);
                } else {
                    return arg;
                }
            }).collect(Collectors.toList());
        } catch (DateTimeParseException ex) {
            throw new InvalidComparisonOperationException(ex.getMessage(),
                    propertyExpression.getParentPath().getModel().getBindableJavaType().getSimpleName(),
                    property, getOperator().getSymbol());
        }
    }

    //TODO: Melhorar esse processo de buscar propriedade que são aninhadas com outros objetos.
    @SuppressWarnings("unchecked")
    private Join getJoin(PluralAttribute attr, From from) {
        switch (attr.getCollectionType()) {
            case COLLECTION:
                return from.join((CollectionAttribute) attr);
            case SET:
                return from.join((SetAttribute) attr);
            case LIST:
                return from.join((ListAttribute) attr);
            case MAP:
                return from.join((MapAttribute) attr);
            default:
                return null;
        }
    }

    public Path<String> getPathString(Root<T> root) {
        Path<String> propertyExpression = null;
        Exception catchException = null;
        try {
            propertyExpression = parseProperty(root);
        } catch (IllegalArgumentException ex) {
            for (int i = 0; i < getChildClasses().size(); i++) {
                Root<T> rootChild = ((RootImpl) root).treatAs(getChildClasses().get(i));

                try{ propertyExpression = parseProperty(rootChild); break;
                } catch (IllegalArgumentException illegalArgumentException){
                    //LOG DE ERRO...
                    catchException = illegalArgumentException;
                }
            }
        }

        if(Objects.isNull(propertyExpression)) {
            throw new RuntimeException(catchException.getCause());
        }

        return propertyExpression;
    }

    private Path<String> parseProperty(Root<?> root) {
        Path<String> path;

        if (property.contains(".")) {
            // Nested properties
            String[] pathSteps = property.split("\\.");
            String step = pathSteps[0];
            path = root.get(step);
            From lastFrom = root;

            for (int i = 1; i <= pathSteps.length - 1; i++) {
                if (path instanceof PluralAttributePath) {
                    PluralAttribute attr = ((PluralAttributePath) path).getAttribute();
                    Join join = getJoin(attr, lastFrom);
                    path = join.get(pathSteps[i]);
                    lastFrom = join;
                } else if (path instanceof SingularAttributePath) {
                    SingularAttribute attr = ((SingularAttributePath) path).getAttribute();
                    if (attr.getPersistentAttributeType() != Attribute.PersistentAttributeType.BASIC) {
                        Join join = lastFrom.join(attr, JoinType.LEFT);
                        path = join.get(pathSteps[i]);
                        lastFrom = join;
                    } else {
                        path = path.get(pathSteps[i]);
                    }
                } else {
                    path = path.get(pathSteps[i]);
                }
            }
        } else {
            path = root.get(property);
        }

        return path;
    }

    public void isInvalidOperationWithStringTypeThenThrowException(RootImpl root, Path<String> propertyExpression, String field) {
        boolean isInvalidOperation = getOperator().getSymbol().equals(GREATER_THAN.getOperator().getSymbol()) ||
                getOperator().getSymbol().equals(GREATER_THAN_OR_EQUAL.getOperator().getSymbol()) ||
                getOperator().getSymbol().equals(LESS_THAN.getOperator().getSymbol()) ||
                getOperator().getSymbol().equals(LESS_THAN_OR_EQUAL.getOperator().getSymbol());

        boolean isInvalidJavaType = propertyExpression.getModel().getBindableJavaType().equals(String.class);

        if (isInvalidOperation && isInvalidJavaType) {
            throw new InvalidComparisonOperationException("Invalid Operator for that field type",
                    root.getEntityType().getName(),
                    field, getOperator().getSymbol());
        }
    }

}
