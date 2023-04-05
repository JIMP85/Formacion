package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;

import com.example.domains.entities.Language;
import com.example.exceptions.InvalidDataException;



@DataJpaTest
@ComponentScan(basePackages = "com.example")
class FilmServiceImplTest {

	@MockBean
	FilmRepository dao;
	
	@Autowired
	FilmServiceImpl srv;
	
	@Test
	void testAñadirOk() throws Exception {
		
		var pelicula = new Film();
		pelicula.setFilmId(1);
		pelicula.setTitle("Titulo de puerba");
		pelicula.setDescription("Prueba");
		pelicula.setLastUpdate(Timestamp.from(Instant.now()));
		pelicula.setLength(65);
		pelicula.setRating(Rating.RESTRICTED);
		pelicula.setReleaseYear(Short.valueOf("2023"));
		pelicula.setRentalDuration(Byte.valueOf("4"));
		pelicula.setRentalRate(BigDecimal.valueOf(12.00));
		pelicula.setReplacementCost(BigDecimal.valueOf(15.99));
		pelicula.setLanguage(new Language(3));
		pelicula.setLanguageVO(new Language(6));
		pelicula.setActors(List.of());
		pelicula.setCategories(List.of());
		
		when(dao.save(pelicula)).thenReturn(pelicula);
		
		srv.añadir(pelicula);
		
		assertNotNull(srv);
		assertEquals(1, pelicula.getFilmId());

	}
	
	@Test
	void testAddInvalido() {
		var pelicula = new Film();
		
		assertThrows(InvalidDataException.class, ()-> srv.añadir(pelicula));
		
	}
	
	@Test
	void testAddNulo() {
		
		assertThrows(InvalidDataException.class, ()-> srv.añadir(null));
		
	}
	
	@Test
	void testModificarOk() {
		var pelicula = new Film();
		pelicula.setFilmId(1);
		pelicula.setTitle("Titulo de puerba");
		pelicula.setDescription("Prueba");
		pelicula.setLastUpdate(Timestamp.from(Instant.now()));
		pelicula.setLength(65);
		pelicula.setRating(Rating.RESTRICTED);
		pelicula.setReleaseYear(Short.valueOf("2023"));
		pelicula.setRentalDuration(Byte.valueOf("4"));
		pelicula.setRentalRate(BigDecimal.valueOf(12.00));
		pelicula.setReplacementCost(BigDecimal.valueOf(15.99));
		pelicula.setLanguage(new Language(3));
		pelicula.setLanguageVO(new Language(6));
		pelicula.setActors(List.of());
		pelicula.setCategories(List.of());
		
		when(dao.save(pelicula.merge(pelicula))).thenReturn(pelicula);
		
		assertNotNull(pelicula);
		assertEquals(1, pelicula.getFilmId());
	}
	
	@Test
	void testModificarNulo() {
		assertThrows(InvalidDataException.class, ()-> srv.modificar(null));
	}
	
	@Test
	void testDeleteOk() throws InvalidDataException {
		var pelicula = new Film();
		pelicula.setFilmId(1);
		pelicula.setTitle("Titulo de puerba");
		pelicula.setDescription("Prueba");
		pelicula.setLastUpdate(Timestamp.from(Instant.now()));
		pelicula.setLength(65);
		pelicula.setRating(Rating.RESTRICTED);
		pelicula.setReleaseYear(Short.valueOf("2023"));
		pelicula.setRentalDuration(Byte.valueOf("4"));
		pelicula.setRentalRate(BigDecimal.valueOf(12.00));
		pelicula.setReplacementCost(BigDecimal.valueOf(15.99));
		pelicula.setLanguage(new Language(3));
		pelicula.setLanguageVO(new Language(6));
		pelicula.setActors(List.of());
		pelicula.setCategories(List.of());
		
		doNothing().when(dao).delete(pelicula);
		
		srv.borrar(pelicula);
		
	}
	
	@Test
	void testDeleteNulo() {
		assertThrows(InvalidDataException.class, ()-> srv.borrar(null));
	}

}
