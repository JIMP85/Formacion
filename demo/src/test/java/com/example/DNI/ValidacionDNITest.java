package com.example.DNI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.validacion.ValidacionNIF;


public class ValidacionDNITest {
	@Nested
	class valido {
		
		@Nested
		class OK {
			
			
			@ParameterizedTest(name= "Caso: {0}")
			@ValueSource(strings = {"12345678Z"})
			void testValido(String caso){
			assertTrue(ValidacionNIF.isNIF(caso), "DNI correcto");
			}
		}
		
		@Nested
		class KO {
			
			
			@ParameterizedTest (name= "Caso: {0}")
			@ValueSource(strings = {"00000000K","00000001R","99999999R","123445","R98234586","POIUYTMK9", " "})
			@EmptySource
			void validacionNegativa(String caso) {
				
				assertFalse(ValidacionNIF.isNIF(caso), "no válidos");
			
		}
						
		
		}
	
	}

}
