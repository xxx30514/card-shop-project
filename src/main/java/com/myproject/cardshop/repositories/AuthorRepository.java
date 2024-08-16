package com.myproject.cardshop.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.cardshop.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{
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
}
