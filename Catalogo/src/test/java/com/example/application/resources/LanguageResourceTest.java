package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	LanguageService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Nested
	class GetAllNested{
		
		@Test
		void testGetAllOK() throws Exception {
			List<Language> listaIdioma = new ArrayList<>(
					Arrays.asList(new Language(1, "Catellano"),
							new Language(2, "Catalan"),
							new Language(3, "Gallego"),
							new Language(4, "Euskera"))
					);
			when(srv.getByProjection(Language.class)).thenReturn(listaIdioma);
			mockMvc.perform(get("/api/idiomas/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(4))
				.andDo(print());
		}
		
	}
	
	@Nested
	class GetOneNested{
		
		@Test
		void testGetOneOK() throws Exception {
			var idioma = new Language (1, "Castellano");
			
			when(srv.getOne(idioma.getLanguageId())).thenReturn(Optional.of(idioma));
			
			mockMvc.perform(get("/api/idiomas/v1/id/{id}", idioma.getLanguageId()))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.languageId").value(idioma.getLanguageId()),
					jsonPath("$.name").value(idioma.getName()))
				.andDo(print());
		}
		
		@Test
		void testGetOneKO() throws Exception {
			var idioma = new Language (1, "Castellano");
			
			when(srv.getOne(idioma.getLanguageId())).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/idiomas/v1/id/{id}", idioma.getLanguageId()))
			 		.andExpect(status().isNotFound())
			 		.andExpect(jsonPath("$.title").value("Not Found"))
			 	.andDo(print());
		}
	}
	
	@Nested
	class testCrearNested {
		@Test
		void testCrearOK() throws Exception{
			var idioma = new Language (1,"Castellano");
			
			when(srv.añadir(idioma)).thenReturn(idioma);
			
			mockMvc.perform(post("/api/idiomas/v1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(idioma)))
					)
					.andExpect(status().isCreated())
					.andExpect(header().string("Location", "http://localhost/api/idiomas/v1/1"))
				.andDo(print());
		}
		
		@Test
		void testCrearKO() throws Exception {
			var idioma = new Language (1,"Castellano");
			
			when(srv.añadir(idioma)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(post("/api/idiomas/v1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(idioma))))
					.andExpect(status().isBadRequest())
				.andDo(print());
			
		}
	}
	
	@Nested
	class testModificarNested{
		
		@Test
		void testModificarOK() throws JsonProcessingException, Exception {
			var idioma = new Language(1, "Castellano");
			
			when(srv.modificar(idioma)).thenReturn(idioma);
			
			mockMvc.perform(put("/api/idiomas/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(idioma))))
				.andExpect(status().isNoContent())
				.andExpect(status().is2xxSuccessful())
				.andDo(print());
			verify(srv, times(1)).modificar(idioma);
		}
		
		@Test
		void testModificarKO() throws JsonProcessingException, Exception {
			var idioma = new Language(1, "Castellano");
			
			when(srv.modificar(idioma)).thenThrow(new InvalidDataException());
			
			mockMvc.perform(put("/api/idiomas/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(LanguageDTO.from(idioma))))
				.andExpect(status().isBadRequest())
				.andDo(print());
		}
		
	}
	
	@Nested
	class testBorrarNested {
		@Test
		void testBorrar() throws Exception {
			doNothing().when(srv).deleteById(1);
			
			mockMvc.perform(delete("/api/idiomas/v1/1"))
					.andExpect(status().isNoContent())
				.andDo(print());
			
			verify(srv, times(1)).deleteById(1);
		}
	}

}
