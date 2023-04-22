package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name = "language")
@NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l")
public class Language extends EntityBase<Language> implements Serializable {
	private static final long serialVersionUID = 1L;

    public static class Partial {}
    public static class Complete extends Partial {}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Language.Complete.class)
	@Column(name = "language_id", unique = true, nullable = false)
	private int languageId;

	@Column(nullable = false, length = 2)
	@Size(min = 2, max = 20)
	@NotNull
	private String name;
	
	
	@Column(name = "last_update", insertable = false, updatable = false, nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonIgnore
	private Timestamp lastUpdate;


	// bi-directional many-to-one association to Film
	@OneToMany(mappedBy = "language")
	@JsonIgnore
	private List<Film> films;

	// bi-directional many-to-one association to Film
	@OneToMany(mappedBy = "languageVO")
	@JsonIgnore
	private List<Film> filmsVO;
	
	public Language() {
		
	}

	public Language(int languageId) {
		this.languageId = languageId;
	}

	public Language(int languageId, @NotNull @Max(20) String name) {
		this.languageId = languageId;
		this.name = name;
	} 

	public Language(int languageId, String name, List<Film> films, List<Film> filmsVO) {
		this.languageId = languageId;
		this.name = name;
		this.films = films;
		this.filmsVO = filmsVO;
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public Film addFilm(Film film) {
		getFilms().add(film);
		film.setLanguage(this);

		return film;
	}

	public Film removeFilm(Film film) {
		getFilms().remove(film);
		film.setLanguage(null);

		return film;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public Film addFilmsVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
		filmsVO.setLanguageVO(this);

		return filmsVO;
	}

	public Film removeFilmsVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
		filmsVO.setLanguageVO(null);

		return filmsVO;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		return languageId == other.languageId;
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(languageId);
	}


}