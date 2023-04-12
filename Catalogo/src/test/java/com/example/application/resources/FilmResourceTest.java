package com.example.application.resources;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FilmResource.class)
class FilmResourceTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	FilmService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	
	@Nested
	class TestGetNested{
		
		@Test
		void testGetAllOK() throws Exception {
			List<FilmShortDTO> listaPelis = new ArrayList<>(
			        Arrays.asList(new FilmShortDTO(1, "Star Wars"),
			        		new FilmShortDTO(2, "The Avengers"),
			        		new FilmShortDTO(3, "John Wick 4"),
			        		new FilmShortDTO(4, "The Lord of the Rings")
			        		));
			
			when(srv.getByProjection(FilmShortDTO.class)).thenReturn(listaPelis);
			mockMvc.perform(get("/api/peliculas/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(4))
				.andDo(print());
		}
		
		@Test
		void testAllPageableOK() throws Exception {
			List<FilmShortDTO> listaPelis = new ArrayList<>(
			        Arrays.asList(new FilmShortDTO(1, "Star Wars"),
			        		new FilmShortDTO(2, "The Avengers"),
			        		new FilmShortDTO(3, "John Wick 4"),
			        		new FilmShortDTO(4, "The Lord of the Rings")
			        		));
			
			when(srv.getByProjection(PageRequest.of(0, 20), FilmShortDTO.class)).thenReturn(new PageImpl<>(listaPelis));
			
			mockMvc.perform(get("/api/peliculas/v1").queryParam("page", "0"))
						.andExpect(status().isOk())
						.andExpect(content().contentType("application/json"))
						.andExpect(jsonPath("$.content.size()").value(4))
						.andExpect(jsonPath("$.size").value(4));
		}
		
		@Test
		void testGetOneOK() throws Exception {
			var pelicula = new Film();
			pelicula.setFilmId(1);
			pelicula.setTitle("Prueba pelicula");
			pelicula.setDescription("Descripcion prueba");
			pelicula.setLength(180);
			pelicula.setRating(Rating.PARENTS_STRONGLY_CAUTIONED);
			pelicula.setReleaseYear(Short.valueOf("2002"));
			pelicula.setRentalDuration(Byte.valueOf("3"));
			pelicula.setRentalRate(BigDecimal.valueOf(5.23));
			pelicula.setReplacementCost(BigDecimal.valueOf(15.99));
			pelicula.setLanguage(null);
			pelicula.setLanguageVO(null);
			pelicula.setActors(List.of());
			pelicula.setCategories(List.of());
			
			when(srv.getOne(pelicula.getFilmId())).thenReturn(Optional.of(pelicula));
			mockMvc.perform(get("/api/peliculas/v1/{id}", pelicula.getFilmId()))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.filmId").value(pelicula.getFilmId()))
		        .andExpect(jsonPath("$.Titulo").value(pelicula.getTitle()))
		        .andExpect(jsonPath("$.description").value(pelicula.getDescription()))
		        .andExpect(jsonPath("$.rating").value("PG-13"))
		       .andDo(print());
		}
		
	}
	
	@Nested
	class TestCreateNested{
		
		@Test
		void testCrearFilmOK() throws Exception {
			var pelicula = new Film();
			pelicula.setFilmId(1);
			pelicula.setDescription("Descripcion prueba");
			pelicula.setLength(180);
			pelicula.setRating(Rating.PARENTS_STRONGLY_CAUTIONED);
			pelicula.setReleaseYear(Short.valueOf("2002"));
			pelicula.setRentalDuration(Byte.valueOf("3"));
			pelicula.setRentalRate(BigDecimal.valueOf(5.23));
			pelicula.setReplacementCost(BigDecimal.valueOf(15.99));
			pelicula.setTitle("Prueba pelicula");
			pelicula.setLanguage(null);
			pelicula.setLanguageVO(null);
			pelicula.setActors(List.of());
			pelicula.setCategories(List.of());
			
			when(srv.añadir(pelicula)).thenReturn(pelicula);
			mockMvc.perform(post("/api/peliculas/v1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(FilmEditDTO.from(pelicula)))
					)
					.andExpect(status().isCreated())
			        .andExpect(header().string("Location", "http://localhost/api/peliculas/v1/1"))
			       .andDo(print());
		}
	}

}
