package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FilmServiceImpl implements FilmService {
	@Autowired
	FilmRepository dao;

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Film> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return dao.findById(id);
	}
	
	@Override
	public List<Film> novedades(@NonNull Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

	@Override
	@Transactional
	public Film añadir(Film objeto) throws DuplicateKeyException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(dao.existsById(objeto.getFilmId()))
			throw new DuplicateKeyException(objeto.getErrorsMessage());
		var actor = objeto.getActors();
		var categoria = objeto.getCategories();
		objeto.clearActors();
		objeto.clearCategories();
		var nuevoObjeto = dao.save(objeto);
		nuevoObjeto.setActors(actor);
		nuevoObjeto.setCategories(categoria);
		return dao.save(nuevoObjeto);
	}

	@Override
	@Transactional
	public Film modificar(Film objeto) throws NotFoundException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		var leido = dao.findById(objeto.getFilmId());
		if(leido.isEmpty())
			throw new NotFoundException();
		return dao.save(objeto.merge(leido.get()));
	}

	@Override
	public void borrar(Film objeto) throws InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(objeto.getFilmId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}


}
