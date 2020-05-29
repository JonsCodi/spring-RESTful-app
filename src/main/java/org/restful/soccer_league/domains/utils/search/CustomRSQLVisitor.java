package org.restful.soccer_league.domains.utils.search;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CustomRSQLVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

    private GenericRSQLSpecBuilder<T> builder;

    public CustomRSQLVisitor(List<Class> childClasses) {
        builder = new GenericRSQLSpecBuilder<T>(childClasses);
    }

    @Override
    public Specification<T> visit(AndNode node, Void param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<T> visit(OrNode node, Void param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<T> visit(ComparisonNode node, Void params) {
        return builder.createSpecification(node);
    }
}
