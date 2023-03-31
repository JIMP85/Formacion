package com.example.domains.core.contracts.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import lombok.NonNull;

public interface DomainService <E, K>{

	List<E> getAll();
	
	E añadir (E item) throws DuplicateKeyException, InvalidDataException, com.example.exceptions.DuplicateKeyException;
	
	E modificar (E item) throws NotFoundException, InvalidDataException;
	 
	void borrar(E item) throws InvalidDataException;
	void deleteById(K id);

	Optional<E> getOne(K id);
	
}
