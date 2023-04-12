package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;


@RestController
@RequestMapping(path = "api/peliculas/v1")
public class FilmResource {

	@Autowired
	private FilmService filmSrv;
	
	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<FilmShortDTO>)filmSrv.getByProjection(Sort.by(sort), FilmShortDTO.class);
		return filmSrv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAll(Pageable pageable) {
		return filmSrv.getByProjection(pageable, FilmShortDTO.class);
	}
	
	@GetMapping(path = "/{id}")
	public FilmDTO getOne(@PathVariable int id) throws NotFoundException {
		var pelicula = filmSrv.getOne(id);
		if(pelicula.isEmpty())
			throw new NotFoundException();
		return FilmDTO.from(pelicula.get());
	}
	
	@GetMapping(path = "/{id}/actores")
	public List <ElementoDTO<Integer, String>> getActoresDePelicula(@PathVariable int id) throws NotFoundException {
		var pelicula = filmSrv.getOne(id);
		if(pelicula.isEmpty())
			throw new NotFoundException();
		return pelicula.get().getActors().stream()
				.map(o -> new ElementoDTO<>(o.getActorId(), o.getFirstName()+ ' '+o.getLastName()))
				.toList(); 
	}
	
	@GetMapping(path = "/{id}/categorias")
	public List <ElementoDTO<Integer, String>> getCategoriasDePelicula(@PathVariable int id) throws NotFoundException {
		var pelicula = filmSrv.getOne(id);
		if(pelicula.isEmpty())
			throw new NotFoundException();
		return pelicula.get().getCategories().stream()
				.map(o -> new ElementoDTO<>(o.getCategoryId(), o.getName()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> crearPelicula(@Valid @RequestBody FilmEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		Film newItem = filmSrv.añadir(FilmEditDTO.from(item));
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		
		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modificar(@PathVariable int id, @Valid @RequestBody FilmEditDTO objeto) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != objeto.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		filmSrv.modificar(FilmEditDTO.from(objeto));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		filmSrv.deleteById(id);
	}
	
	
	
	
}
