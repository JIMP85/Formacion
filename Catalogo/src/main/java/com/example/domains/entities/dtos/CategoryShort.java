package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.FilmCategory;

public interface CategoryShort {

	
	int getCategoryId();
	String getName();
	List<FilmCategory> getFilmCategories(); 
}
