package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.CategoryShort;
import com.example.domains.entities.dtos.LanguageShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

//@WebMvcTest(CategoryResource.class)
//class CategoryResourceTest {
//
//	
//	@Autowired
//    private MockMvc mockMvc;
//	
//	@MockBean
//	private ActorService srv;
//	
//	@Autowired
//	ObjectMapper mapper;
//	
//	@BeforeEach
//	void setUp() {
//		
//	}
//	
//	@AfterAll
//	void tearDown() {
//		
//	}
//	
//	@Value
//	static class CategoryShortMock implements CategoryShort {
//		int actorId;
//		String nombre;
//		List<Film> peliculas;
//		
//	}
//	
//	@Test
//	void testGetAllString() {
//		
//	}
//
//	@Test
//	void testGetAllPageable() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetOne() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetPeliculas() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCrear() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testModificar() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDelete() {
//		fail("Not yet implemented");
//	}
//
//}
