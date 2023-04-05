package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.example.domains.entities.Category;

class CategoryDTOTest {

	@Test
	void testCategoryDTO() {
		Category category = new Category(10, "Games" , new ArrayList<>());
		CategoryDTO categoryDTO = CategoryDTO.from(category);
		assertEquals(category.getCategoryId(), categoryDTO.getCategoryId());
		assertEquals(category.getName(), categoryDTO.getName());
		assertEquals(category.getFilmCategories(), categoryDTO.getFilmCategories());
		
	}

}
