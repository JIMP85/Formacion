package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Inventory;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilmDTO {

	@JsonProperty("Id")
	private int filmId;
	@JsonProperty("Sinopsis")
	private String description;
	@JsonProperty("Duración")
	private int length;
	@JsonProperty("Calificación")
	private String rating;
	@JsonProperty("Estreno")
	private Short releaseYear;
	@JsonProperty("Título")
	private String title;
	@JsonProperty("Idioma")
	private Language language;
	@JsonProperty("VO")
	private Language languageVO;
	@JsonProperty("Lista de actoresPeliculas")
	private List<FilmActor> filmActors;
	@JsonProperty("Lista de categoriasPeliculas")
	private List<FilmCategory> filmCategories;
	@JsonProperty("Inventario")
	private List<Inventory> inventories;
	
	

	public static FilmDTO from(Film target) {
		
		return new FilmDTO(target.getFilmId(), 
				target.getDescription(),
				target.getLength(),
				target.getRating(),
				target.getReleaseYear(),
				target.getTitle(),
				target.getLanguage(),
				target.getLanguageVO(),
				target.getFilmActors(),
				target.getFilmCategories(),
				target.getInventories()
				);
				
		}
		
		public static Film from(FilmDTO target) {
			
			return new Film(target.getFilmId(), 
					target.getDescription(),
					target.getLength(),
					target.getRating(),
					target.getReleaseYear(),
					target.getTitle(),
					target.getLanguage(),
					target.getLanguageVO(),
					target.getFilmActors(),
					target.getFilmCategories(),
					target.getInventories()
					);
		}
	
	
	
	
}
