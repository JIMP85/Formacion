package com.example.application.resources;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;




@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {

	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	CategoryService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		
	}
	
	
	@Nested
	class testGetAllNested{
		
		@Test
		void testGetAllOK() throws Exception {
			List<Category> listaCat = new ArrayList<>(
					Arrays.asList(new Category(1,"Sci-fy"),
							new Category(2, "Accion"),
							new Category(3, "Aventuras"),
							new Category(4, "Comedia"))
					);
			when(srv.getByProjection(Category.class)).thenReturn(listaCat);
			mockMvc.perform(get("/api/categorias/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(4))
				.andDo(print());
		}
		
	}
	
	@Nested
	class testGetOneNested {
		
		@Test
		void testGetOneOK() throws Exception {
			var categoria = new Category(1, "Sci-fy");
			
			when(srv.getOne(categoria.getCategoryId())).thenReturn(Optional.of(categoria));
			
			mockMvc.perform(get("/api/categorias/v1/{id}", categoria.getCategoryId()))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.categoryId").value(categoria.getCategoryId()))
	        .andExpect(jsonPath("$.name").value(categoria.getName()))
	        .andDo(print());
		}
		
		@Test
		void testGetOneKO() throws Exception {
			var categoria = new Category(1, "Sci-fy");
			
			when(srv.getOne(categoria.getCategoryId())).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/categorias/v1/{id}", categoria.getCategoryId()))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
				.andDo(print());
		}
	}
	
	@Nested
	class testCrearNested{
		
		@Test
		void testCrearOK() throws Exception {
			var categoria = new Category(1, "Sci-fy");
			when(srv.añadir(categoria)).thenReturn(categoria);
			mockMvc.perform(post("/api/categorias/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(CategoryDTO.from(categoria)))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/categorias/v1/1"))
		        .andDo(print());
		}
		
		@Test
		void testCrearKO() throws Exception {
			var categoria = new Category(1, "Sci-fy");
			
			when(srv.añadir(categoria)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(post("/api/categorias/v1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(CategoryDTO.from(categoria))))
					.andExpect(status().isBadRequest())
				.andDo(print());
		}
	}
	
	@Nested
	class testModificarNested{
		
		@Test
		void testModificarOK() throws JsonProcessingException, Exception {
			var categoria = new Category(1, "Sci-fy");
			
			when(srv.modificar(categoria)).thenReturn(categoria);
			
			mockMvc.perform(put("/api/categorias/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(CategoryDTO.from(categoria))))
				.andExpect(status().isNoContent())
				.andExpect(status().is2xxSuccessful())
				.andDo(print());
			verify(srv, times(1)).modificar(categoria);
		}
		
		@Test
		void testModificarKO() throws JsonProcessingException, Exception {
			var categoria = new Category(1, "Sci-fy");
			
			when(srv.modificar(categoria)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(put("/api/categorias/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(CategoryDTO.from(categoria))))
				.andExpect(status().isBadRequest())
				.andDo(print());
		}
	}
	
	@Nested
	class testBorrarNested {
		@Test
		void testBorrar() throws Exception {
			doNothing().when(srv).deleteById(1);
			
			mockMvc.perform(delete("/api/categorias/v1/1"))
					.andExpect(status().isNoContent())
				.andDo(print());
			
			verify(srv, times(1)).deleteById(1);
		}
	}

}
