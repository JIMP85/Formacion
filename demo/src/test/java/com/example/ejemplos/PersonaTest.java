package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.core.test.Smoke;
import com.example.exceptions.InvalidDataException;
import com.example.ioc.PersonaRepository;


class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Smoke
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
//		p = null;
//		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades",
			()-> assertEquals(1, p.getId(), "id"),
			()-> assertEquals("Pepito", p.getNombre(), "getNombre"),
			()-> assertEquals("Grillo", p.getApellidos().get(), "getApellidos")
		);
	}
	@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	void repeatedTest(RepetitionInfo repetitionInfo) {
		var p = Persona.builder()
				.id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito" + repetitionInfo.getCurrentRepetition()) // (repetitionInfo.getCurrentRepetition() % 3 == 0 ? "" : repetitionInfo.getCurrentRepetition()))
				.apellidos("Grillo").build();
//		p = null;
//		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades",
			()-> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "id"),
			()-> assertEquals("Pepito" + repetitionInfo.getCurrentRepetition(), p.getNombre(), "getNombre"),
			()-> assertEquals("Grillo", p.getApellidos().get(), "getApellidos")
		);
	}
	
	//Las pruebas del service y del repository deberían estar aparte
	
	@Nested
	class PersonaRepositoryTest{
		@Mock
		PersonaRepository dao;
		
		@BeforeEach
		void setUp() throws Exception{
			dao = mock(PersonaRepository.class);
			doThrow(new IllegalArgumentException()).when(dao).save(null);
			//...
			var p = dao.load();
		}
		
		@Test
		void testLoad () {
			PersonaRepository dao = mock(PersonaRepository.class);
			when(dao.load()).thenReturn(Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build());
			//...
			var p = dao.load();
			assertTrue(p instanceof Persona, "No es instancia de persona");
			assertAll("Validar propiedades",
				()-> assertEquals(1, p.getId(), "id"),
				()-> assertEquals("Pepito", p.getNombre(), "getNombre"),
				()-> assertEquals("Grillo", p.getApellidos().get(), "getApellidos")
			);
			
		}
		
		@Test
		void testSave() throws InvalidDataException {
			
			assertThrows(IllegalArgumentException.class, () -> dao.save(null));
			
		}
		
	}

}
