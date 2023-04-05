package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Language;
import com.example.exceptions.InvalidDataException;


@DataJpaTest
@ComponentScan(basePackages = "com.example")
class LanguageServiceImplTest {

	@MockBean
	LanguageRepository dao;
	
	@Autowired
	LanguageServiceImpl rsv;
	
	
	
	@Test
	void testAddInvalidLanguange() {
		Language idioma = new Language(0, "");
		when(dao.existsById(1)).thenReturn(false);
		
		assertThatThrownBy(() -> rsv.añadir(idioma)).isInstanceOf(InvalidDataException.class)
			.hasMessage("ERRORES: name: size must be between 2 and 20.");
		
	}
	
	@Test
	void TestAddNullLanguage() {
		assertThatThrownBy(()-> rsv.añadir(null)).isInstanceOf(InvalidDataException.class)
			.hasMessage("No puede ser nulo");
	}

}
