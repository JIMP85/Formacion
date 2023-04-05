package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.domains.entities.Film.Rating;


class FilmTest {

	@ParameterizedTest
	@CsvSource(value={"1", "0.1","0.01"})
	void testRentalRateCorrecto(BigDecimal rate) {
		var pelicula = new Film(0,"Pelicula de prueba", "descripion", (short)2023, new Language(3), new Language(2), (byte) 4, rate,
				100, BigDecimal.valueOf(40.56), Rating.GENERAL_AUDIENCES);
		assertNotNull(pelicula);
		assertTrue(pelicula.isValid());
	}
	
	@ParameterizedTest
	@CsvSource(value = {"-0.1", "-0.01", "-1"})
	void testRentalRateValorIncorrecto(BigDecimal rate) {
		var pelicula = new Film(0,"Pelicula de prueba", "descripion", (short)2023, new Language(3), new Language(2), (byte) 4, rate,
				100, BigDecimal.valueOf(40.56), Rating.GENERAL_AUDIENCES);
		assertNotNull(pelicula);
		assertFalse(pelicula.isValid());
	}

}
