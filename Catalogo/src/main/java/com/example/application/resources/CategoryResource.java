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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = { "/api/categorias/v1" })
public class CategoryResource {
	
	@Autowired
	private CategoryService categoriaSrv;
	
	@GetMapping
	public List<Category> getAll(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<Category>)categoriaSrv.getByProjection(Sort.by(sort), Category.class);
		return categoriaSrv.getByProjection(Category.class);
	}
	
	
	@GetMapping(path = "/{id}")
	public Category getOne(@PathVariable int id) throws NotFoundException {
		var item = categoriaSrv.getOne(id);
		
		if(item.isEmpty())
			throw new NotFoundException();
		
		return item.get();
	}
	
	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<ElementoDTO<Integer, String>> getPeliculas(@PathVariable int id) throws NotFoundException {
		var item = categoriaSrv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmCategories().stream()
				.map(o -> new ElementoDTO<>(o.getFilm().getFilmId(), o.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> crearCategoria(@Valid @RequestBody CategoryDTO objeto) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var categoria = CategoryDTO.from(objeto);
		var nuevaCategoria = categoriaSrv.añadir(categoria);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(nuevaCategoria.getCategoryId()).toUri();
		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modificarCategoria(@PathVariable int id, @Valid @RequestBody Category item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getCategoryId())
			throw new BadRequestException("ID's diferentes");
		
		categoriaSrv.modificar(item);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		categoriaSrv.deleteById(id);
	}

	
	
	
}
