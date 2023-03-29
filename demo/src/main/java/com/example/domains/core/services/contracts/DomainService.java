package com.example.domains.core.services.contracts;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import com.example.domains.entities.Actor;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

public interface DomainService <E, K>{

	List<E> getAll();
	
	E add (E item) throws DuplicateKeyException, InvalidDataException;
	
	E modify (E item) throws NotFoundException, InvalidDataException;
	 
	void delete(E item) throws InvalidDataException;
	void deleteById(K id);

	Optional<E> getOne(K id);
	
}
