package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;



@DataJpaTest
@ComponentScan(basePackages = "com.example")
class ActorServiceImplTest {

	
	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorServiceImpl srv;
	
	@Test
	void testGetAllNoVacia() {
		List<Actor> lista= new ArrayList<>(
				Arrays.asList(new Actor(1,"Dani", "Robira"),
						new Actor(2,"Angel", "Martin"),
						new Actor(3,"Carlos", "Latre"),
						new Actor(4, "Santiago", "Segura")));
		when(dao.findAll()).thenReturn(lista);
		var rslt = srv.getAll();
		assertThat(rslt.size()).isEqualTo(4);
		
				
	}
	
	@Test
	void testGetOneValid() {
		List<Actor> lista= new ArrayList<>(
				Arrays.asList(new Actor(1,"Dani", "Robira"),
						new Actor(2,"Angel", "Martin"),
						new Actor(3,"Carlos", "Latre"),
						new Actor(4, "Santiago", "Segura")));
		when(dao.findById(1)).thenReturn(Optional.of(new Actor(3,"Carlos", "Latre")));
		var rslt = srv.getOne(1);
		assertTrue(rslt.isPresent());
		assertEquals(dao.findById(1), rslt);
	}
	
	@Test
	void testGetOne_notfound() {
		when(dao.findById(1)).thenReturn(Optional.empty());
		var rslt = srv.getOne(1);
		assertTrue(rslt.isEmpty());
	}
	
	@Test
	@DisplayName("Actor añadido satisfactoriamente")
	void testAñadirActorOk () throws DuplicateKeyException, InvalidDataException {
		List<Actor> lista= new ArrayList<>(
				Arrays.asList(new Actor(1,"Dani", "Robira"),
						new Actor(2,"Angel", "Martin"),
						new Actor(3,"Carlos", "Latre"),
						new Actor(4, "Santiago", "Segura")));
		
		Actor actor = new Actor(5,"Florentino", "Fernandez");
		srv.añadir(actor);
		var rslt = srv.getOne(5);
		assertNotNull(rslt);
		assertEquals(dao.findById(5), rslt);
		assertNotEquals(lista.get(3), rslt);
		
	}
	
	@Test
	@DisplayName("Añadido incorrecto")
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		when(dao.save(any(Actor.class))).thenReturn(null, null);
		assertThrows(InvalidDataException.class, () -> srv.añadir(null));
		verify(dao, times(0)).save(null);
		
	}
	

//	@Test
//	void testDeleteById() {
//		fail("Not yet implemented");
//	}

}
