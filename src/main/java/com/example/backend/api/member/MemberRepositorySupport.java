package com.example.backend.api.member;

import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.model.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport{

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QMember member = QMember.member;

    public Page<Member> getMemberList(String searchText, Pageable pageable){
        QueryResults<Member> memberList = jpaQueryFactory
                .selectFrom(member)
                .where(searchTextEq(searchText))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderBy(pageable))
                .fetchResults();

        return new PageImpl<>(memberList.getResults(), pageable, memberList.getTotal());
    }

    private BooleanExpression searchTextEq(String searchText){
        return searchText != null ? member.name.contains(searchText).or(member.company.contains(searchText)) : null;
    }

    private OrderSpecifier<?> orderBy(Pageable pageable){

        if(!pageable.getSort().isEmpty()){
            for(Sort.Order order : pageable.getSort()){
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()){
                    case "name": return new OrderSpecifier(direction, member.name);
                }
            }
        }
        return new OrderSpecifier(Order.DESC, member.idx);
    }



}
