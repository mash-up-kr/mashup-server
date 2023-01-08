package kr.mashup.branding.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

public class QueryUtils {
    private QueryUtils() {
    }

    public static List<OrderSpecifier> toOrderSpecifiers(Path<?> path, Sort sort) {
        return sort.stream()
            .map(it -> new OrderSpecifier(
                toOrder(it),
                Expressions.path(Object.class, path, it.getProperty())
            ))
            .collect(Collectors.toList());
    }

    private static Order toOrder(Sort.Order sortOrder) {
        return sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;
    }

    public static <T> Page<T> toPage(QueryResults<T> queryResults, Pageable pageable) {
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }


}
