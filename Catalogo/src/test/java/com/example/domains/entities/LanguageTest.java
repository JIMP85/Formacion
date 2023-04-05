package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class LanguageTest {

	@ParameterizedTest
	@CsvSource(value= {"Castellano", "Catalan","Gallego","Euskera"})
	void testInsercionIdiomaCorrecto(String idioma) {
		var nuevoIdioma = new Language(0, idioma);
		assertNotNull(nuevoIdioma);
		assertTrue(nuevoIdioma.isValid());
		
	}
	
	@ParameterizedTest
	@CsvSource(value = {"' '","'   '"})
	void testInsercionIdiomaIncorrecto(String idioma) {
		var nuevoIdioma = new Language(0, idioma);
		assertNotNull(nuevoIdioma);
		assertFalse(nuevoIdioma.isValid());
	}

}
