package org.restful.soccer_league.domains.utils;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;

import java.util.List;
import java.util.stream.Collectors;

public final class RSQLUtils {

    public static List<String> getSelectors(Node node) {
        if(node instanceof OrNode) {
            return ((OrNode) node).getChildren()
                    .stream().map(nodeComparison -> ((ComparisonNode) nodeComparison).getSelector())
                    .collect(Collectors.toList());
        }else {
            return ((AndNode) node).getChildren()
                    .stream().map(nodeComparison -> ((ComparisonNode) nodeComparison).getSelector())
                    .collect(Collectors.toList());
        }
    }
}
