package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.example.domains.entities.Language;

class LanguageDTOTest {

	@Test
	void testLanguageDTO() {
		Language idioma = new Language(2, "Italian", new ArrayList<>(), new ArrayList<>());
		LanguageDTO idiomaDTO= LanguageDTO.from(idioma);
		assertEquals(idioma.getLanguageId(), idiomaDTO.getLanguageId());
		assertEquals(idioma.getName(), idiomaDTO.getName());
		assertEquals(idioma.getFilms(), idiomaDTO.getFilms());
		assertEquals(idioma.getFilmsVO(), idiomaDTO.getFilmsVO());
	}

}
