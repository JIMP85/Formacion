package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;

@DataJpaTest
class FilmRepositoryTest {

	@Autowired
	private TestEntityManager em;
	
	
	@Autowired
	FilmRepository dao;
	
	
	//Error de prueba por incompatibilidad de datos en h2
//	@BeforeEach
//	void setUp() throws Exception {
//		
//		var objeto = new Film(0,"Pelicula de prueba", new Language(4), (byte) 1,
//				new BigDecimal(10.00), 1, new BigDecimal(10.00));
//		objeto.setLastUpdate(Timestamp.valueOf("2022-02-02 00:00:00"));
//		em.persist(objeto);
//		objeto = new Film(0,"Pelicula de prueba2", new Language(2), (byte)3,
//				new BigDecimal(4.00), 60, new BigDecimal(8.00));
//		objeto.setLastUpdate(Timestamp.valueOf("2022-02-02 00:00:00"));
//		em.persist(objeto);
//		objeto = new Film(0,"Pelicula de prueba3", new Language(1), (byte)5,
//				new BigDecimal(8.00), 34, new BigDecimal(4.00));
//		objeto.setLastUpdate(Timestamp.valueOf("2022-02-02 00:00:00"));
//		em.persist(objeto);
//		objeto = new Film(0,"Pelicula de prueba4", new Language(3), (byte)6,
//				new BigDecimal(18.00), 134, new BigDecimal(14.00));
//		objeto.setLastUpdate(Timestamp.valueOf("2022-02-02 00:00:00"));
//		em.persist(objeto);
//	}
	
	@Test
	void testAll() {
			var datos = dao.findAll();
			
			assertNotNull(datos);
			assertAll("Instance and Size",
					()-> assertTrue(datos instanceof List<Film>),
					()-> assertTrue(datos.size()>0)
					);
	}
}
