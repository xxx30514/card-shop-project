package com.myproject.cardshop.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.cardshop.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author>{
	
	@Transactional
	List<Author> findByNamedQuery(@Param("age") int age);	
	
	@Modifying 
	@Transactional
	int updateByNamedQuery(@Param("age") int age, @Param("id") Integer id);
	
	// update Author a set a.age = 22 where a.id =1
	@Modifying // JPA方法預設只讀 若要進行新增、更新或刪除操作須使用此註解
	@Transactional // 新增、更新或刪除操作須進行事務管理
	@Query("update Author a set a.age = :age where a.id = :id")
	int updateAuthor(@Param("age") int age, @Param("id") Integer id);
	
	// derived query
	// 此方法相當於原生SQL的 select * from author where first_name = '${firstName}'
	List<Author> findAllByFirstName(String firstName);
	
	// 忽略大小寫 ex.JASON&Jason IgnoreCase || IgnoringCase 皆可 僅是命名風格差異
	List<Author> findAllByFirstNameIgnoreCase(String firstName);
	
	// 此方法相當於原生SQL的 select * from author where first_name like '%${firstName}%'
	List<Author> findAllByFirstNameContainingIgnoreCase(String firstName);
	
	// select * from author where first_name like '${firstName}%'
	List<Author> findAllByFirstNameStartsWithIgnoreCase(String firstName);
	
	// select * from author where first_name like '%${firstName}'
	List<Author> findAllByFirstNameEndsWithIgnoreCase(String firstName);
	
	//  select * from author where first_name in ('','','')
	List<Author> findAllByFirstNameInIgnoreCase(List<String> firstNames);
	
	Author findByFirstName(String firstName);
}
