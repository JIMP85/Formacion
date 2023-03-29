package com.example;

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspectj.weaver.ast.Var;
import org.hibernate.action.internal.EntityAction;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.core.entities.EntityBase;
import com.example.ioc.Rango;
import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryImpl;
import com.example.ioc.StringRepositoryMockImpl;
import com.example.ioc.StringService;
import com.example.ioc.StringServiceImpl;
import com.example.ioc.UnaTonteria;

import jakarta.persistence.criteria.From;
import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	@Qualifier("Remoto")
	private StringService srv;
	
	@Autowired
	@Qualifier("Local")
	@Lazy
	private StringService srvLocal;
	
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	
	@Autowired
	Rango rango;
	
	@Autowired(required = false)
	UnaTonteria tonteria;

	@Autowired
	JdbcTemplate jdbcTemplate;

//	@Data 
//	@AllArgsConstructor
//	class Actor {
//		private int id;
//		private String first_name, last_name;
//	}
//	class ActorRowMapper implements RowMapper<Actor> {
//	      @Override
//	      public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
//	            return new Actor(rs.getInt("actor_id"), rs.getString("last_name"), rs.getString("first_name"));
//	      }
//	}
	
	@Autowired
	ActorRepository dao;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		
//		StringRepository dao = new StringRepositoryImpl();
//		dao = new StringRepositoryMockImpl();
//		var srv = new StringServiceImpl(dao);
//		System.out.println(srv.get(1));
//		System.out.println(srvLocal.get(1));
//		srv.add("Este es el remoto");
//		srvLocal.add("Este es el local");
//		srv.add("Este es el remoto");
//		srvLocal.add("Este es el local");
//		System.out.println(rango.toString());
//		System.out.println(rango.getMin() + rango.getMax());
//		System.out.println(tonteria != null ? tonteria.dimeAlgo() : "Tonteria nula");
//		System.out.println(config);
//		srv.add("algo");
//		var lst = jdbcTemplate.query("""
//				SELECT actor_id, first_name, last_name
//				from actor
//				""", new ActorRowMapper()
//				);
//		//lst.forEach(System.out::println);
//		jdbcTemplate.queryForList("""
//				SELECT concat(first_name, ' ', last_name)
//				from actor
//				""", String.class).forEach(System.out::println);
//		var actor = new Actor(0,"Pepito","Grillo");
//		dao.save(actor);
//		dao.findAll().forEach(System.out::println);
		
//		dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P")
//			.forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("P", Sort.by("FirstName"))
//			.forEach(System.out::println);
		
//		dao.findConJPQL(5).forEach(System.out::println);
//		dao.findConSQL(5).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 5))
//				.forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThan(root.get("actorId"), 200))
//				.forEach(System.out::println);
		

		
//		var item = dao.findById(1);
//		if(item.isPresent()) {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors()
//			.forEach(o -> System.out.println(o.getFilm().getTitle()));
//		}else {
//			System.out.println("Actor no encontrado");
//		}
//		var actor= new Actor(0," ", "grillo");
//		Validator validator= Validation.buildDefaultValidatorFactory().getValidator();
//		var err = validator.validate(actor);
//		if(err.size()>0) {
//			err.forEach(e-> System.out.println(e.getPropertyPath() + ": " + e.getMessage()));
//		}else {
//			dao.save(actor);
//		}
		
//		if(actor.isInvalid()) {
//			System.out.println(actor.getErrorsMessage());
//		} else 
//			dao.save(actor);
//		}
//		var rslt= dao.findAll(PageRequest.of(1, 20, Sort.by("actorId")));
//		rslt.getContent().forEach(System.out::println);
		
//		var rslt= dao.findAll(PageRequest.of(1, 20, Sort.by("actorId")));
//		rslt.getContent().stream().map(item -> ActorDTO.from(item)).forEach(System.out::println);
		
//		dao.findByActorIdNotNull().forEach(item -> System.out.println(item.getActorId() + " " + item.getNombre()));
		dao.findAllBy(ActorDTO.class).forEach(System.out::println);
		
	}

}
