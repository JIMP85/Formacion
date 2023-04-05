package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Film;

public interface LanguageShort {

	int getLanguageId();
	String getName();
	List<Film> getFilms();
	List<Film> getFilmsVO();	
}
