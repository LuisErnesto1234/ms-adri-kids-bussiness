package com.adri.kids.catalog.infrastructure.adapter.out.persistence.specification;

import com.adri.kids.catalog.infrastructure.adapter.in.dto.request.filter.CategoryFilterRequest;
import com.adri.kids.catalog.infrastructure.adapter.out.persistence.entity.CategoryEntity;

import jakarta.persistence.criteria.Predicate;

import lombok.experimental.UtilityClass;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

@UtilityClass
public class CategorySpecification {

    public static Specification<CategoryEntity> byFilter(CategoryFilterRequest filterRequest) {
        return (root, query, cb) -> {
            if (filterRequest == null) {
                return cb.conjunction();
            }

            var predicates = new ArrayList<Predicate>();

            if (filterRequest.name() != null && !filterRequest.name().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.name().toLowerCase() + "%"));
            }
            if (filterRequest.id() != null) {
                predicates.add(cb.equal(root.get("id"), filterRequest.id()));
            }
            if (filterRequest.colorCode() != null && !filterRequest.colorCode().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("colorCode")), "%" + filterRequest.colorCode().toLowerCase() + "%"));
            }
            if (filterRequest.isShowMainMenu()) {
                predicates.add(cb.equal(root.get("isShowMainMenu"), true));
            }
            if (filterRequest.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filterRequest.createdFrom()));
            }
            if (filterRequest.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filterRequest.createdTo()));
            }
            if (filterRequest.status() != null) {
                predicates.add(cb.equal(root.get("status"), filterRequest.status()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
