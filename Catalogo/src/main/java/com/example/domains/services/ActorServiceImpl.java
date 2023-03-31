package com.example.domains.services;



import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import org.springframework.dao.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
public class ActorServiceImpl implements ActorService {
	@Autowired
	ActorRepository dao;

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
	public Iterable<Actor> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Actor> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return dao.findById(id);
	}
	
	@Override
	public List<Actor> novedades(@NonNull Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

	@Override
	public Actor añadir(Actor objeto) throws DuplicateKeyException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(dao.existsById(objeto.getActorId()))
			throw new DuplicateKeyException(objeto.getErrorsMessage());
		
		return dao.save(objeto);
	}

	@Override
	public Actor modificar(Actor objeto) throws NotFoundException, InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		if(objeto.isInvalid())
			throw new InvalidDataException(objeto.getErrorsMessage());
		if(!dao.existsById(objeto.getActorId()))
			throw new NotFoundException();
		
		return dao.save(objeto);
	}

	@Override
	public void borrar(Actor objeto) throws InvalidDataException {
		if(objeto == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(objeto.getActorId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}


}