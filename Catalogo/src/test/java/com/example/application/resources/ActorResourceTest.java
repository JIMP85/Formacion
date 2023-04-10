package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
	}
	
	@Test
	void testGetAllString() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Carmelo Coton"),
		        		new ActorShortMock(3, "Capitan Tan")));
		when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
		mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Carmelo Coton"),
		        		new ActorShortMock(3, "Capitan Tan")));

		when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class))
			.thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3)
				);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
	        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()))
	        .andDo(print());
	}

	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreateOK() throws Exception {
		int id = 1;
		var actor = new Actor(id, "Pepito", "Grillo");
		when(srv.añadir(actor)).thenReturn(actor);
		mockMvc.perform(post("/api/actores/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(ActorDTO.from(actor)))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"))
	        .andDo(print());
	}
	
	@Test
	void testCreateKO() throws Exception {
		var actor = new Actor(1, "Juan", "Perez");
		
		when(srv.añadir(actor)).thenThrow(new InvalidDataException());
		
		mockMvc.perform(post("/api/actores/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(actor))))
				.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@Test
	void testUpdateOK() throws Exception {
		int id = 1;
		var actor = new Actor (id, "Juan", "Tomas");
		
		when(srv.modificar(actor)).thenReturn(actor);
		
		mockMvc.perform(put("/api/actores/v1/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(actor))))
			.andExpect(status().isNoContent())
			.andExpect(status().is2xxSuccessful())
			.andDo(print());
		verify(srv, times(1)).modificar(actor);
	}
	
	@Test
	void testUpdateKO() throws Exception {
		var actor = new Actor (1, "Juan", "Tomas");
		
		when(srv.modificar(actor)).thenThrow(new InvalidDataException());
		
		mockMvc.perform(put("/api/actores/v1/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(actor))))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		doNothing().when(srv).deleteById(1);
		
		mockMvc.perform(delete("/api/actores/v1/1"))
				.andExpect(status().isNoContent())
			.andDo(print());
		
		verify(srv, times(1)).deleteById(1);
		
	}

}
