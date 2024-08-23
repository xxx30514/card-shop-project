package com.myproject.cardshop.specification;

import org.springframework.data.jpa.domain.Specification;

import com.myproject.cardshop.entities.Author;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AuthorSpecification {

	public static Specification<Author> hasAge(int age) {
		return (Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (age < 0) {
				//return null;
				return builder.disjunction();
			}
			return builder.equal(root.get("age"), age);
		};
	}
	
	public static Specification<Author> hasLastName(String lastName) {
		//Lambda表達式會自動推斷參數類型
		return (root, query, builder) -> {
			return builder.equal(root.get("lastName"), lastName);
		};
	}
	public static Specification<Author> firstNameLike(String firstName) {
		return (root, query, builder) -> {
			if (firstName == null) {
				return builder.disjunction();
			}
			return builder.like(root.get("firstName"), "%"+firstName+"%");
		};
	}
}
