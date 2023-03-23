package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
//		p=null;
		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es una instance de Persona");
		assertAll("Validar propiedades",
		()-> assertEquals(1, p.getId(),"id"),
		()-> assertEquals("Pepito", p.getNombre(),"getNombre"),
		()-> assertEquals("Grillo", p.getApellidos(),"getApellido")
		);
		
	}

}
