package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.FilmCategory;

import lombok.Value;


@Value
public class CategoryDTO {

	private int categoryId;
	private String name;
	private List<FilmCategory> filmCategories;
	
	


	public static CategoryDTO from(Category target) {
		
		return new CategoryDTO(target.getCategoryId(), target.getName(), target.getFilmCategories());
		
	}
	
	
	public static Category from(CategoryDTO target) {
		
		return new Category (target.getCategoryId(), target.getName(), target.getFilmCategories());
		
	}
	
	
}
