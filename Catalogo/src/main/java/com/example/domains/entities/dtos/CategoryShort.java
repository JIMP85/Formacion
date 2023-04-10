package com.example.domains.entities.dtos;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.example.domains.entities.FilmCategory;


public interface CategoryShort {

	
	int getCategoryId();
	@Value("#{target.getName}")
	String getName(); 
}
