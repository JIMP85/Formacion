package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;






class CategoryTest {

	
	@DisplayName("Insercion Correcta")
	@ParameterizedTest
	@CsvSource(value = {"Sci-fy", "Aventuras"})
	void testInsercion(String objeto) {
		var nuevaCategoria = new Category(0, objeto);
		assertNotNull(nuevaCategoria);
		assertTrue(nuevaCategoria.isValid());
				
	}
	
	@DisplayName("Nombre de la categoria en blanco")
	@ParameterizedTest
	@CsvSource(value = {"' '", "'  '"})
	void testNombreCategoriaEnBlanco(String objeto) {
		var nuevaCategoria = new Category(0,objeto);
		assertNotNull(nuevaCategoria);
		assertFalse(nuevaCategoria.isValid());
	}
	
	

}
