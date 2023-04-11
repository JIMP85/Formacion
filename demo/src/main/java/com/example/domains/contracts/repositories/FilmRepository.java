package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Film;

import lombok.NonNull;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>, RepositoryWithProjections{

	<T> List <T> findAllBy(Class<T> type);

	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(@NonNull Timestamp fecha);
}