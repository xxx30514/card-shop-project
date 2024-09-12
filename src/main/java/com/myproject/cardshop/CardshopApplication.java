package com.myproject.cardshop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;

import com.github.javafaker.Faker;
import com.myproject.cardshop.entities.Author;
import com.myproject.cardshop.entities.Course;
import com.myproject.cardshop.entities.Video;
import com.myproject.cardshop.repositories.AuthorRepository;
import com.myproject.cardshop.repositories.CourseRepository;
import com.myproject.cardshop.repositories.VideoRepository;
import com.myproject.cardshop.specification.AuthorSpecification;

import jakarta.persistence.FetchType;


@SpringBootApplication
public class CardshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardshopApplication.class, args);
	}

    @Bean
    CommandLineRunner commandLineRunner(AuthorRepository repository, CourseRepository courseRepository, VideoRepository videoRepository) {
		return args->{
//			List<Author> authorList = new ArrayList<>();
//			Faker faker = new Faker();
//			LocalDateTime currentDateTime = LocalDateTime.now();
//			for (int i = 0; i < 50; i++) {
//				Author author = Author.builder().firstName(faker.name().firstName()).lastName(faker.name().lastName()).age(faker.number().numberBetween(25, 50)).email("fake"+i+"@gmail.com").createDateTime(currentDateTime).build();
//				authorList.add(author);
//			}
//			repository.saveAll(authorList);
//			Author author = Author.builder().firstName("Jason").lastName("Yeh").age(31).email("test@gmail.com").createDateTime(currentDateTime).build();
//			Author author2 = Author.builder().firstName("James").lastName("Yeh").age(31).email("test1@gmail.com").createDateTime(currentDateTime).build();
//			List<Author> authors = new ArrayList<>();
//			authors.add(author);
//			authors.add(author2);
//			repository.saveAll(authors);//若有設定持久化級聯 則僅要持久化course 即會操作關聯表格
//			//若無級聯操作在引用authors 前需要持久化
//			Course course = Course.builder().name("程式語言").description("SpringBoot").authors(authors).build();
//			List<Author> authors2 = new ArrayList<>();
//			authors2.add(author);
//			Course course1 = Course.builder().name("程式語言").description("Java").authors(authors2).build();
//			List<Course> courses = new ArrayList<>();
//			courses.add(course);
//			courses.add(course1);
//			courseRepository.saveAll(courses); 	 	
//			//courseRepository.delete(course1);
//			//courseRepository.save(course);
//			//courseRepository.save(course1);//分開插入資料 會造成author已處於分離狀態(detached) 分離狀態無法再持久化
//			//author.getCourses().add(course);//private List<Course> courses = new ArrayList<>(); 需要初始化 不然會空指針異常
//			var video = Video.builder().name("Iron Man").length(126).build();
//			videoRepository.save(video);
			//repository.updateAuthor(32, 1);	
			//repository.findByNamedQuery(40).forEach(System.out::println);// 要在Author中的Course集合中加上fetch = FetchType.EAGER 否則會有LazyInitializationException
			//repository.updateByNamedQuery(32, 2);
			Specification<Author> specification = Specification.where(AuthorSpecification.hasAge(-1)).or(AuthorSpecification.firstNameLike("MA"));
			repository.findAll(specification).forEach(System.out::println);
		};
	}
	
}
