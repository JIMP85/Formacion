package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = {"/api/idiomas/v1"})
public class LanguageResource {

	
	@Autowired
	private LanguageService languageSrv;
	
	@GetMapping
	public List<Language> getAll(@RequestParam(required = false) String sort){
		if(sort != null)
			return (List<Language>)languageSrv.getByProjection(Sort.by(sort), Language.class);
		return languageSrv.getByProjection(Language.class);		
	}
	
	
	@GetMapping(path = "/id/{id}")
	public LanguageDTO getOne(@PathVariable int id) throws NotFoundException {
		var idioma = languageSrv.getOne(id);
		if(idioma.isEmpty())
			throw new NotFoundException();
		return LanguageDTO.from(idioma.get());
	}
	
	@GetMapping(path = "/idIdioma/{id}")
	public List<ElementoDTO<Integer, String>> getPeliculaDeIdioma(@PathVariable int id) throws NotFoundException{
		var item = languageSrv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilms().stream()
				.map(o -> new ElementoDTO<>(o.getFilmId(), o.getTitle()))
				.toList();
	}
	
	@GetMapping(path= "/idIdiomaVO/{id}")
	public List<ElementoDTO<Integer, String>> getPeliculaDeIdiomaVO(@PathVariable int id) throws NotFoundException{
		var item = languageSrv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmsVO().stream()
				.map(o -> new ElementoDTO<>(o.getFilmId(), o.getTitle()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody LanguageDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var language = LanguageDTO.from(item);
		var newLanguage = languageSrv.añadir(language);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newLanguage.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}
	

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody LanguageDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getLanguageId())
			throw new BadRequestException("No coinciden los identificadores");
		languageSrv.modificar(LanguageDTO.from(item));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		languageSrv.deleteById(id);
	}
}
