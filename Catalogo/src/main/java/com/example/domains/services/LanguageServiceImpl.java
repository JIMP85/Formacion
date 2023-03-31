package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

public class LanguageServiceImpl implements LanguageService{

	
	@Autowired
	LanguageRepository dao;
	
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
	public Iterable<Language> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Language> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Language> getAll() {
		return dao.findAll();
	}
	
	@Override
	public List<Language> novedades(@NonNull Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

	@Override
	public Language añadir(Language objeto) throws DuplicateKeyException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(dao.existsById(objeto.getLanguageId()))
			throw new DuplicateKeyException(objeto.getErrorsMessage());
		return dao.save(objeto);
	}

	@Override
	public Language modificar(Language objeto) throws NotFoundException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(dao.existsById(objeto.getLanguageId()))
			throw new DuplicateKeyException(objeto.getErrorsMessage());
		return dao.save(objeto);
	}

	@Override
	public void borrar(Language objeto) throws InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(objeto.getLanguageId());
		
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public Optional<Language> getOne(Integer id) {
		return dao.findById(id);
	}
	
	

}
