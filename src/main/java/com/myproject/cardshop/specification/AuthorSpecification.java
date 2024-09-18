package com.myproject.cardshop.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.myproject.cardshop.entities.Author;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AuthorSpecification {

	public static Specification<Author> hasAge(int age) {
		return (Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (age < 0) {
				//builder.conjunction();return true  建立and查詢時使用條件為空也不會影響查詢結果 僅單獨使用此方法且age<0時將忽略此條件 回傳其他符合的資料
				//return false 建立or查詢時使用 僅單獨使用此方法且age<0時會回傳所有資料; 
				return builder.conjunction();
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
			//isBlank 檢測是否null 空字串 空白
			if (StringUtils.isBlank(firstName)) {
				return builder.disjunction();
			}
			//處理大小寫敏感的問題
			return builder.like(builder.lower(root.get("firstName")), "%"+firstName.toLowerCase()+"%");
		};
	}
	//匿名內部類別寫法
	public static Specification<Author> firstNameLike1(String firstName) {
		return new Specification<Author>() {
			private static final long serialVersionUID = 1L;
			@Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
            }
        };
	}
}
