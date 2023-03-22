package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.experimental.var;

@SuppressWarnings("deprecation")
class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSuma() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(2, 2);
		
		assertEquals(4, rslt);
	}
	
	@Test
	void testSumaPositivoNegativo() { //Aconsejable probar diferentes valores
		var calc = new Calculadora();
		
		var rslt = calc.suma(3, -1);
		
		assertEquals(2, rslt);
	}
	
	@Test
	void testSumaNegativoPositivo() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(-5, 7);
		
		assertEquals(2, rslt);
	}
	
	@Test
	void testSumaNegativoNegativo() {
		var calc = new Calculadora();
		
		var rslt = calc.suma(-1, -4);
		
		assertEquals(-5, rslt);
	}
	
	@Test
	void testSumaDecimales() {  //Da error por la precisión del último bit del double
		var calc = new Calculadora();
		
		var rslt = calc.suma(0.1, 0.2);
		
		assertEquals(0.3, rslt);
	}

	
}
