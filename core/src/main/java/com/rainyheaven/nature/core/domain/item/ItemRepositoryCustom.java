package com.rainyheaven.nature.core.domain.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainyheaven.nature.core.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rainyheaven.nature.core.domain.item.QItem.*;
import static org.springframework.util.StringUtils.hasText;

public interface ItemRepositoryCustom {
    Page<Item> search(String keyword, Pageable pageable);
}

@Repository
class ItemRepositoryImpl extends Querydsl4RepositorySupport implements ItemRepositoryCustom {

    public ItemRepositoryImpl() {
        super(Item.class);
    }

    @Override
    public Page<Item> search(String keyword, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .selectFrom(item)
                .where(
                        nameLike(keyword)
                )
        );
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? item.nameKor.like("%" + name + "%") : null;
    }


}
