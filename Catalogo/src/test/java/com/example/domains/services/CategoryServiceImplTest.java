package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.exceptions.InvalidDataException;



@DataJpaTest
@ComponentScan(basePackages = "com.example")
class CategoryServiceImplTest {

	
	@MockBean
	CategoryRepository dao;
	
	@Autowired
	CategoryServiceImpl srv;
	

	@Test
	void testGetAll() {
		List<Category> lista= new ArrayList<>(
				Arrays.asList(new Category(1,"Sci-fy"),
						new Category(2,"Horror"),
						new Category(3,"Accion"),
						new Category(4,"Comedia"),
						new Category(5,"Aventuras"),
						new Category (6,"Drama")));
		when(dao.findAll()).thenReturn(lista);
		var rslt = srv.getAll();
		assertThat(rslt.size()).isEqualTo(6);
	}

	@Test
	void testGetOneNotfound() {
		when(dao.findById(1)).thenReturn(Optional.empty());
		var rslt = srv.getOne(1);
		assertTrue(rslt.isEmpty());
	}
	
	@Test
	@DisplayName("Añadido incorrecto")
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		when(dao.save(any(Category.class))).thenReturn(null, null);
		assertThrows(InvalidDataException.class, () -> srv.añadir(null));
		verify(dao, times(0)).save(null);
	}
	
}
	

