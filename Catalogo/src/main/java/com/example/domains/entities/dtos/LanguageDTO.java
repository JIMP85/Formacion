package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class LanguageDTO {
	
	@JsonProperty("Id")
	private int languageId;
	@JsonProperty("nombre")
	private String name;
	@JsonProperty("Lista películas")
	private List<Film> films;
	@JsonProperty("Lista peliculasVO")
	private List<Film> filmsVO;
	
	public static LanguageDTO from(Language target) {
		
		return new LanguageDTO(target.getLanguageId(),
				target.getName(),
				target.getFilms(),
				target.getFilmsVO()
				);
	}
	
	public static Language from(LanguageDTO target) {
		
		return new Language(target.getLanguageId(),
				target.getName(),
				target.getFilms(),
				target.getFilmsVO()
				);
	}
	

}
