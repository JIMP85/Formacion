package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository dao;
	
	
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
	public Iterable<Category> getAll(Sort sort) {
		
		return dao.findAll(sort);
	}

	@Override
	public Page<Category> getAll(Pageable pageable) {
		
		return dao.findAll(pageable);
	}

	@Override
	public List<Category> getAll() {
		
		return dao.findAll();
	}
	
	@Override
	public List<Category> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}
	

	@Override
	public Category añadir(Category objeto) throws DuplicateKeyException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(dao.existsById(objeto.getCategoryId()))
			throw new DuplicateKeyException(objeto.getErrorsMessage());
		return dao.save(objeto);
	}

	@Override
	public Category modificar(Category objeto) throws NotFoundException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(dao.existsById(objeto.getCategoryId()))
			throw new DuplicateKeyException(objeto.getErrorsMessage());
		return dao.save(objeto);
	}

	@Override
	public void borrar(Category objeto) throws InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(objeto.getCategoryId());
		
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}

	
}
