package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;

import lombok.Value;


@Value
public class LanguageDTO {

	
	private int languageId;
	private String name;
	private List<Film> films;
	private List<Film> filmsVO;
	
	
	public static LanguageDTO from(Language target) {
		
		return new LanguageDTO(target.getLanguageId(), target.getName(), target.getFilms(), target.getFilmsVO());
		
	}
	
	
	public static Language from(LanguageDTO target) {
		
		return new Language(target.getLanguageId(), target.getName(), target.getFilms(), target.getFilmsVO());
		
	}
	
	
}
